package seaShineMarine.SeaShinePvtLtd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntity {

    private Integer id;

    private String serviceName;

    private String shortDescription;

    private String description;

    private String imageUrl;

    private Integer displayOrder;

    private String status;
}