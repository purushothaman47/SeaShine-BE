package seaShineMarine.SeaShinePvtLtd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactEntity {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String subject;
    private String message;
    private String status;
}
