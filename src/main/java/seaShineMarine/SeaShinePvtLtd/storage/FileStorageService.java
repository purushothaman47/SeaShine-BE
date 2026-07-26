package seaShineMarine.SeaShinePvtLtd.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    /**
     * Validates and writes an uploaded resume to disk under a generated,
     * collision-proof filename. Throws a 400-level
     * org.springframework.web.server.ResponseStatusException if the file
     * is empty, too large, or not a PDF/Word document.
     */
    StoredFile store(MultipartFile file);

    /**
     * Loads a previously stored file by its generated filename
     * (job_applications.resume_path) so it can be streamed back for
     * download. Throws a 404-level ResponseStatusException if it can't
     * be found.
     */
    Resource loadAsResource(String storedFilename);

    /**
     * Best-effort delete of a previously stored file, e.g. when its
     * owning application is deleted. Never throws — a missing file on
     * disk shouldn't block deleting the database row.
     */
    void delete(String storedFilename);
}
