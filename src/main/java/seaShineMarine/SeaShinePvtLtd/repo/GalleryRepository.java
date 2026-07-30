package seaShineMarine.SeaShinePvtLtd.repo;

import seaShineMarine.SeaShinePvtLtd.model.GalleryEntity;

import java.util.List;

public interface GalleryRepository {

    List<GalleryEntity> getAllImages();

    GalleryEntity getImageById(Integer id);

    int saveImage(GalleryEntity gallery);

    int deleteImage(Integer id);
}
