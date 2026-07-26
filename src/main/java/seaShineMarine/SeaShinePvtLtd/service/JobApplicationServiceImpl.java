package seaShineMarine.SeaShinePvtLtd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import seaShineMarine.SeaShinePvtLtd.model.JobApplicationEntity;
import seaShineMarine.SeaShinePvtLtd.repo.JobApplicationRepository;
import seaShineMarine.SeaShinePvtLtd.storage.FileStorageService;
import seaShineMarine.SeaShinePvtLtd.storage.ResumeDownload;
import seaShineMarine.SeaShinePvtLtd.storage.StoredFile;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl
        implements JobApplicationService {

    private static final Set<String> ALLOWED_STATUSES =
            Set.of("PENDING", "UNDER REVIEW", "SHORTLISTED", "REJECTED");

    private final JobApplicationRepository repository;
    private final FileStorageService fileStorageService;

    @Override
    public List<JobApplicationEntity> getAllApplications() {
        return repository.getAllApplications();
    }

    @Override
    public JobApplicationEntity getApplicationById(Integer id) {
        return repository.getApplicationById(id);
    }

    @Override
    public void saveApplication(JobApplicationEntity application, MultipartFile resumeFile) {

        if (resumeFile != null && !resumeFile.isEmpty()) {
            StoredFile stored = fileStorageService.store(resumeFile);
            application.setResumePath(stored.storedFilename());
            application.setResumeOriginalFilename(stored.originalFilename());
            application.setResumeContentType(stored.contentType());
            application.setResumeSize(stored.size());
        }

        repository.saveApplication(application);
    }

    @Override
    public void updateStatus(Integer id, String status) {

        if (status == null || !ALLOWED_STATUSES.contains(status)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status must be one of: " + String.join(", ", ALLOWED_STATUSES));
        }

        JobApplicationEntity application = repository.getApplicationById(id);
        if (application == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found");
        }

        repository.updateStatus(id, status);
    }

    @Override
    public ResumeDownload getResume(Integer applicationId) {

        JobApplicationEntity application = repository.getApplicationById(applicationId);

        if (application == null || application.getResumePath() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No resume was uploaded for this application");
        }

        return new ResumeDownload(
                fileStorageService.loadAsResource(application.getResumePath()),
                application.getResumeOriginalFilename(),
                application.getResumeContentType());
    }

    @Override
    public void deleteApplication(Integer id) {

        JobApplicationEntity application = repository.getApplicationById(id);

        if (application != null && application.getResumePath() != null) {
            fileStorageService.delete(application.getResumePath());
        }

        repository.deleteApplication(id);
    }
}
