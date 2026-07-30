package seaShineMarine.SeaShinePvtLtd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryEntity {

    private Integer id;

    /** Optional caption shown under the image on the public site. */
    private String title;

    /** Generated, on-disk filename — e.g. "3f2a1c4e-....jpg". Never the original name. */
    private String imageFilename;

    /** The filename the admin's browser actually sent, shown for reference. */
    private String imageOriginalFilename;

    /** e.g. "image/jpeg" — used to set the Content-Type when serving the image. */
    private String imageContentType;

    /** File size in bytes. */
    private Long imageSize;

    private Integer displayOrder;
    private String status;
    private Integer uploadedBy;
}
