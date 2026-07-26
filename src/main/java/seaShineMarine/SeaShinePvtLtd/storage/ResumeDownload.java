package seaShineMarine.SeaShinePvtLtd.storage;

import org.springframework.core.io.Resource;

/**
 * Everything JobApplicationController needs to stream a resume back to
 * an admin as a file download.
 */
public record ResumeDownload(
        Resource resource,
        String originalFilename,
        String contentType) {
}
