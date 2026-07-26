package seaShineMarine.SeaShinePvtLtd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seaShineMarine.SeaShinePvtLtd.model.JobApplicationEntity;
import seaShineMarine.SeaShinePvtLtd.model.UpdateApplicationStatusRequest;
import seaShineMarine.SeaShinePvtLtd.service.JobApplicationService;
import seaShineMarine.SeaShinePvtLtd.storage.ResumeDownload;

import java.util.List;

@RestController
@RequestMapping("/api/v1/job-applications")
@RequiredArgsConstructor
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

    /**
     * Public submission endpoint. Was JSON (@RequestBody); is now
     * multipart/form-data so applicants can attach a PDF/Word resume
     * alongside the text fields in the same request. The Angular public
     * site posts a FormData object with these exact field names.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String applyJob(
            @RequestParam Integer careerId,
            @RequestParam String applicantName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String coverLetter,
            @RequestParam(value = "resume", required = false) MultipartFile resume) {

        JobApplicationEntity application = new JobApplicationEntity();
        application.setCareerId(careerId);
        application.setApplicantName(applicantName);
        application.setEmail(email);
        application.setPhone(phone);
        application.setCoverLetter(coverLetter);

        service.saveApplication(application, resume);

        return "Application Submitted Successfully";
    }

    /**
     * Admin-only. Body: {"status": "SHORTLISTED"} — one of PENDING,
     * "UNDER REVIEW", SHORTLISTED, REJECTED. Not in SecurityConfig's public
     * allowlist, so it requires the admin JWT like every other
     * job-applications write.
     */
    @PutMapping("/{id}/status")
    public String updateStatus(
            @PathVariable Integer id,
            @RequestBody UpdateApplicationStatusRequest request) {

        service.updateStatus(id, request.status());

        return "Status Updated Successfully";
    }

    /**
     * Admin-only download. Not listed in SecurityConfig's public GET
     * allowlist, so it falls through to anyRequest().authenticated() —
     * same JWT requirement as every other job-applications GET.
     */
    @GetMapping("/{id}/resume")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable Integer id) {

        ResumeDownload download = service.getResume(id);

        MediaType mediaType = download.contentType() != null
                ? MediaType.parseMediaType(download.contentType())
                : MediaType.APPLICATION_OCTET_STREAM;

        String downloadName = download.originalFilename() != null
                ? download.originalFilename()
                : "resume";

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(downloadName)
                                .build()
                                .toString())
                .body(download.resource());
    }

    @DeleteMapping("/{id}")
    public String deleteApplication(
            @PathVariable Integer id) {

        service.deleteApplication(id);

        return "Application Deleted Successfully";
    }
}
