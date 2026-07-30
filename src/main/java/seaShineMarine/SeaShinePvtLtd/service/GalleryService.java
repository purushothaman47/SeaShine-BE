package seaShineMarine.SeaShinePvtLtd.service;

import org.springframework.web.multipart.MultipartFile;
import seaShineMarine.SeaShinePvtLtd.model.GalleryEntity;
import seaShineMarine.SeaShinePvtLtd.storage.ImageDownload;

import java.util.List;

public interface GalleryService {

    List<GalleryEntity> getAllImages();

    /**
     * @param imageFile required — unlike a resume, a gallery entry with no
     *                  image doesn't make sense, so this throws a 400
     *                  ResponseStatusException if it's missing/empty.
     */
    void uploadImage(GalleryEntity gallery, MultipartFile imageFile);

    /** Loads the image file so it can be streamed back — used by the public <img> tag. */
    ImageDownload getImage(Integer id);

    void deleteImage(Integer id);
}
