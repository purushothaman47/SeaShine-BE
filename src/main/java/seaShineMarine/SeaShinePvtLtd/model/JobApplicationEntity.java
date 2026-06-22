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
    private String resumePath;
    private String coverLetter;
    private String applicationStatus;
}
