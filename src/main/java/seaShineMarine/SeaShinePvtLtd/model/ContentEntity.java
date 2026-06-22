package seaShineMarine.SeaShinePvtLtd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentEntity {

    private Integer id;
    private String sectionName;
    private String title;
    private String subtitle;
    private String content;
    private String imageUrl;
    private String status;
}