package seaShineMarine.SeaShinePvtLtd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationEntity {

    private Integer id;
    private Integer careerId;
    private String applicantName;
    private String email;
    private String phone;

    /** Generated, on-disk filename — e.g. "3f2a1c4e-....pdf". Never the original name. */
    private String resumePath;

    /** The filename the applicant's browser actually sent, shown to admins. */
    private String resumeOriginalFilename;

    /** e.g. "application/pdf" — used to set the Content-Type on download. */
    private String resumeContentType;

    /** File size in bytes, shown in the admin UI next to the download link. */
    private Long resumeSize;

    private String coverLetter;
    private String applicationStatus;
}
