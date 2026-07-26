package seaShineMarine.SeaShinePvtLtd.storage;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

/**
 * Stores uploaded resumes on the local filesystem, under the directory
 * configured by app.upload.resume-dir (default: uploads/resumes, relative
 * to wherever the application is run from).
 *
 * This directory is intentionally NOT under src/main/resources/static, so
 * files are never served directly by Spring's static resource handler —
 * the only way to read one back is through
 * GET /api/v1/job-applications/{id}/resume, which sits behind the admin
 * JWT like every other job-applications GET endpoint.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf", "doc", "docx");

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    private static final long MAX_FILE_SIZE_BYTES = 5L * 1024 * 1024; // 5 MB

    @Value("${app.upload.resume-dir:uploads/resumes}")
    private String resumeDir;

    private Path resumeRoot;

    @PostConstruct
    void init() {
        resumeRoot = Paths.get(resumeDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(resumeRoot);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not create resume upload directory: " + resumeRoot, e);
        }
    }

    @Override
    public StoredFile store(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resume file is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Resume file must be smaller than 5 MB");
        }

        String originalFilename = sanitizeOriginalFilename(file.getOriginalFilename());
        String extension = extensionOf(originalFilename);

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Resume must be a PDF or Word document (.pdf, .doc, .docx)");
        }

        String contentType = file.getContentType();
        if (contentType != null && !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Resume must be a PDF or Word document (.pdf, .doc, .docx)");
        }

        String storedFilename = UUID.randomUUID() + "." + extension;
        Path target = resumeRoot.resolve(storedFilename);

        try {
            file.transferTo(target);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not save the uploaded resume", e);
        }

        return new StoredFile(storedFilename, originalFilename, contentType, file.getSize());
    }

    @Override
    public Resource loadAsResource(String storedFilename) {

        // storedFilename always comes from our own database column (never
        // directly from a client request), but we still refuse anything
        // that isn't a plain filename as defense in depth against path
        // traversal.
        if (storedFilename == null
                || storedFilename.contains("..")
                || storedFilename.contains("/")
                || storedFilename.contains("\\")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found");
        }

        try {
            Path file = resumeRoot.resolve(storedFilename).normalize();

            if (!file.startsWith(resumeRoot) || !Files.exists(file)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found");
            }

            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found");
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found", e);
        }
    }

    @Override
    public void delete(String storedFilename) {
        if (storedFilename == null || storedFilename.contains("..") || storedFilename.contains("/")) {
            return;
        }
        try {
            Files.deleteIfExists(resumeRoot.resolve(storedFilename));
        } catch (IOException ignored) {
            // Best-effort cleanup — an orphaned file on disk shouldn't
            // stop the application record from being deleted.
        }
    }

    private String sanitizeOriginalFilename(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            return "resume";
        }
        // Strip any directory components a browser/proxy might include.
        String name = Paths.get(originalFilename).getFileName().toString();
        return name.isBlank() ? "resume" : name;
    }

    private String extensionOf(String filename) {
        int dot = filename.lastIndexOf('.');
        if (dot < 0 || dot == filename.length() - 1) {
            return "";
        }
        return filename.substring(dot + 1).toLowerCase();
    }
}
