package seaShineMarine.SeaShinePvtLtd.storage;

/**
 * Metadata about a file that has just been written to disk by
 * {@link FileStorageService#store}.
 *
 * @param storedFilename   the generated, on-disk filename (safe, unique,
 *                          never trusts the original name) — this is what
 *                          gets saved in job_applications.resume_path
 * @param originalFilename the filename the applicant's browser sent —
 *                          shown to admins and used as the download name
 * @param contentType       the uploaded file's content type, e.g.
 *                          "application/pdf"
 * @param size              size in bytes
 */
public record StoredFile(
        String storedFilename,
        String originalFilename,
        String contentType,
        long size) {
}
