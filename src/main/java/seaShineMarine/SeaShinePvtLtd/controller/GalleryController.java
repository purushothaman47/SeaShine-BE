package seaShineMarine.SeaShinePvtLtd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seaShineMarine.SeaShinePvtLtd.model.GalleryEntity;
import seaShineMarine.SeaShinePvtLtd.service.GalleryService;
import seaShineMarine.SeaShinePvtLtd.storage.ImageDownload;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService service;

    /**
     * Public — the marketing site's Gallery page needs this list without
     * logging in. Add "/api/v1/gallery", "/api/v1/gallery/**" to
     * SecurityConfig's public GET allowlist (see the delivery notes).
     */
    @GetMapping
    public List<GalleryEntity> getAllImages() {
        return service.getAllImages();
    }

    /**
     * Public — served directly into <img src="..."> tags on the public
     * site, so it can't require a JWT the browser has no way to attach.
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable Integer id) {

        ImageDownload download = service.getImage(id);

        MediaType mediaType = download.contentType() != null
                ? MediaType.parseMediaType(download.contentType())
                : MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.ok()
                .contentType(mediaType)
                // "inline" (not "attachment") so browsers render it instead of downloading it.
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename(download.originalFilename() != null ? download.originalFilename() : "image")
                                .build()
                                .toString())
                .body(download.resource());
    }

    /**
     * Admin-only — multipart/form-data with an optional "title" field and
     * a required "image" file part. Not in the public allowlist, so it
     * falls under anyRequest().authenticated() like every other admin write.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer displayOrder,
            @RequestParam("image") MultipartFile image) {

        GalleryEntity gallery = new GalleryEntity();
        gallery.setTitle(title);
        gallery.setDisplayOrder(displayOrder);

        service.uploadImage(gallery, image);

        return "Image Uploaded Successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteImage(@PathVariable Integer id) {
        service.deleteImage(id);
        return "Image Deleted Successfully";
    }
}
