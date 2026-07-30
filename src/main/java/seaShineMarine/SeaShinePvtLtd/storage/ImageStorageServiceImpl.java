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
 * Stores gallery images on the local filesystem, under the directory
 * configured by app.upload.gallery-dir (default: uploads/gallery).
 *
 * Mirrors FileStorageServiceImpl's approach (see that class for resumes)
 * but with image-specific validation, a separate directory, and images are
 * intentionally readable through a public endpoint (GalleryController)
 * rather than an admin-only one.
 */
@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp", "gif");

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp",
            "image/gif"
    );

    private static final long MAX_FILE_SIZE_BYTES = 5L * 1024 * 1024; // 5 MB

    @Value("${app.upload.gallery-dir:uploads/gallery}")
    private String galleryDir;

    private Path galleryRoot;

    @PostConstruct
    void init() {
        galleryRoot = Paths.get(galleryDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(galleryRoot);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not create gallery upload directory: " + galleryRoot, e);
        }
    }

    @Override
    public StoredFile store(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image file is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image file must be smaller than 5 MB");
        }

        String originalFilename = sanitizeOriginalFilename(file.getOriginalFilename());
        String extension = extensionOf(originalFilename);

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image must be a JPG, PNG, WEBP, or GIF file");
        }

        String contentType = file.getContentType();
        if (contentType != null && !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Image must be a JPG, PNG, WEBP, or GIF file");
        }

        String storedFilename = UUID.randomUUID() + "." + extension;
        Path target = galleryRoot.resolve(storedFilename);

        try {
            file.transferTo(target);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not save the uploaded image", e);
        }

        return new StoredFile(storedFilename, originalFilename, contentType, file.getSize());
    }

    @Override
    public Resource loadAsResource(String storedFilename) {

        if (storedFilename == null
                || storedFilename.contains("..")
                || storedFilename.contains("/")
                || storedFilename.contains("\\")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }

        try {
            Path file = galleryRoot.resolve(storedFilename).normalize();

            if (!file.startsWith(galleryRoot) || !Files.exists(file)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
            }

            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found", e);
        }
    }

    @Override
    public void delete(String storedFilename) {
        if (storedFilename == null || storedFilename.contains("..") || storedFilename.contains("/")) {
            return;
        }
        try {
            Files.deleteIfExists(galleryRoot.resolve(storedFilename));
        } catch (IOException ignored) {
            // Best-effort cleanup.
        }
    }

    private String sanitizeOriginalFilename(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            return "image";
        }
        String name = Paths.get(originalFilename).getFileName().toString();
        return name.isBlank() ? "image" : name;
    }

    private String extensionOf(String filename) {
        int dot = filename.lastIndexOf('.');
        if (dot < 0 || dot == filename.length() - 1) {
            return "";
        }
        return filename.substring(dot + 1).toLowerCase();
    }
}
