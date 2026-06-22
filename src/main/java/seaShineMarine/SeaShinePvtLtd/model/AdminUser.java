package seaShineMarine.SeaShinePvtLtd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser {

    private Integer id;
    private String username;
    private String password;
    private String role;
    private String status;
}