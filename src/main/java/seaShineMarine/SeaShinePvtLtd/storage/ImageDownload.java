package seaShineMarine.SeaShinePvtLtd.storage;

import org.springframework.core.io.Resource;

/**
 * Everything GalleryController needs to stream an image back — same shape
 * as ResumeDownload, kept as its own type so gallery code doesn't read
 * oddly (a "resume" for an image).
 */
public record ImageDownload(
        Resource resource,
        String originalFilename,
        String contentType) {
}
