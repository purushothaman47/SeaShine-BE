package seaShineMarine.SeaShinePvtLtd.repo;

import seaShineMarine.SeaShinePvtLtd.model.JobApplicationEntity;

import java.util.List;

public interface JobApplicationRepository {

    List<JobApplicationEntity> getAllApplications();

    JobApplicationEntity getApplicationById(Integer id);

    int saveApplication(
            JobApplicationEntity application);

    int updateStatus(Integer id, String status);

    int deleteApplication(Integer id);
}
