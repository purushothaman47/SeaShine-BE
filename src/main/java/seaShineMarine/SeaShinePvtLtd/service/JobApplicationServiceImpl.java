package seaShineMarine.SeaShinePvtLtd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seaShineMarine.SeaShinePvtLtd.model.JobApplicationEntity;
import seaShineMarine.SeaShinePvtLtd.repo.JobApplicationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl
        implements JobApplicationService {

    private final JobApplicationRepository repository;

    @Override
    public List<JobApplicationEntity> getAllApplications() {
        return repository.getAllApplications();
    }

    @Override
    public JobApplicationEntity getApplicationById(Integer id) {
        return repository.getApplicationById(id);
    }

    @Override
    public void saveApplication(JobApplicationEntity application) {
        repository.saveApplication(application);
    }

    @Override
    public void deleteApplication(Integer id) {
        repository.deleteApplication(id);
    }
}