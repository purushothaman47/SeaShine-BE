package seaShineMarine.SeaShinePvtLtd.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Stores gallery images on disk, separately from FileStorageService (which
 * handles resumes). Kept as its own class because the validation rules
 * differ (image types vs document types) and, unlike resumes, gallery
 * images are served publicly — no admin JWT required to view them, since
 * the public website's Gallery page needs to display them in <img> tags.
 */
public interface ImageStorageService {

    StoredFile store(MultipartFile file);

    Resource loadAsResource(String storedFilename);

    void delete(String storedFilename);
}
