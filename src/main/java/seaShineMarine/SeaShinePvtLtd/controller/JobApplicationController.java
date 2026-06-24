package seaShineMarine.SeaShinePvtLtd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seaShineMarine.SeaShinePvtLtd.model.JobApplicationEntity;
import seaShineMarine.SeaShinePvtLtd.service.JobApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job-applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class JobApplicationController {

    private final JobApplicationService service;

    @GetMapping
    public List<JobApplicationEntity> getAllApplications() {
        return service.getAllApplications();
    }

    @GetMapping("/{id}")
    public JobApplicationEntity getApplicationById(
            @PathVariable Integer id) {

        return service.getApplicationById(id);
    }

    @PostMapping
    public String applyJob(
            @RequestBody JobApplicationEntity application) {

        service.saveApplication(application);

        return "Application Submitted Successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteApplication(
            @PathVariable Integer id) {

        service.deleteApplication(id);

        return "Application Deleted Successfully";
    }
}