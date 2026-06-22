package seaShineMarine.SeaShinePvtLtd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerEntity {

    private Integer id;
    private String jobTitle;
    private String department;
    private String location;
    private String experienceRequired;
    private String employmentType;
    private String description;
    private String status;
}
