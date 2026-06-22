package seaShineMarine.SeaShinePvtLtd.service;

import seaShineMarine.SeaShinePvtLtd.model.JobApplicationEntity;

import java.util.List;

public interface JobApplicationService {

    List<JobApplicationEntity> getAllApplications();

    JobApplicationEntity getApplicationById(Integer id);

    void saveApplication(JobApplicationEntity application);

    void deleteApplication(Integer id);
}