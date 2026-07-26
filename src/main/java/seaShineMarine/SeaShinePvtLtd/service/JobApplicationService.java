package seaShineMarine.SeaShinePvtLtd.service;

import org.springframework.web.multipart.MultipartFile;
import seaShineMarine.SeaShinePvtLtd.model.JobApplicationEntity;
import seaShineMarine.SeaShinePvtLtd.storage.ResumeDownload;

import java.util.List;

public interface JobApplicationService {

    List<JobApplicationEntity> getAllApplications();

    JobApplicationEntity getApplicationById(Integer id);

    /**
     * @param resumeFile may be null/empty — a resume is optional; when
     *                   present it's validated and stored by
     *                   FileStorageService before the row is inserted.
     */
    void saveApplication(JobApplicationEntity application, MultipartFile resumeFile);

    /**
     * @param status must be one of PENDING, UNDER REVIEW, SHORTLISTED, REJECTED
     *               (validated in the implementation — throws a 400
     *               ResponseStatusException for anything else).
     */
    void updateStatus(Integer id, String status);

    /** Loads the resume file for an application so it can be streamed to an admin. */
    ResumeDownload getResume(Integer applicationId);

    void deleteApplication(Integer id);
}
