package seaShineMarine.SeaShinePvtLtd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import seaShineMarine.SeaShinePvtLtd.model.GalleryEntity;
import seaShineMarine.SeaShinePvtLtd.repo.GalleryRepository;
import seaShineMarine.SeaShinePvtLtd.storage.ImageDownload;
import seaShineMarine.SeaShinePvtLtd.storage.ImageStorageService;
import seaShineMarine.SeaShinePvtLtd.storage.StoredFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository repository;
    private final ImageStorageService imageStorageService;

    @Override
    public List<GalleryEntity> getAllImages() {
        return repository.getAllImages();
    }

    @Override
    public void uploadImage(GalleryEntity gallery, MultipartFile imageFile) {

        if (imageFile == null || imageFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An image file is required");
        }

        StoredFile stored = imageStorageService.store(imageFile);
        gallery.setImageFilename(stored.storedFilename());
        gallery.setImageOriginalFilename(stored.originalFilename());
        gallery.setImageContentType(stored.contentType());
        gallery.setImageSize(stored.size());

        repository.saveImage(gallery);
    }

    @Override
    public ImageDownload getImage(Integer id) {

        GalleryEntity gallery = repository.getImageById(id);

        if (gallery == null || gallery.getImageFilename() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }

        return new ImageDownload(
                imageStorageService.loadAsResource(gallery.getImageFilename()),
                gallery.getImageOriginalFilename(),
                gallery.getImageContentType());
    }

    @Override
    public void deleteImage(Integer id) {

        GalleryEntity gallery = repository.getImageById(id);

        if (gallery != null && gallery.getImageFilename() != null) {
            imageStorageService.delete(gallery.getImageFilename());
        }

        repository.deleteImage(id);
    }
}
