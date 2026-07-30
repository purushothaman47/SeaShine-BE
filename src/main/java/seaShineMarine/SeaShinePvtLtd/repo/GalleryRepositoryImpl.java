package seaShineMarine.SeaShinePvtLtd.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seaShineMarine.SeaShinePvtLtd.model.GalleryEntity;
import seaShineMarine.SeaShinePvtLtd.rowmapper.GalleryRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GalleryRepositoryImpl implements GalleryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final GalleryRowMapper rowMapper;

    @Override
    public List<GalleryEntity> getAllImages() {

        String sql = """
                SELECT *
                FROM gallery
                ORDER BY display_order ASC, id DESC
                """;

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public GalleryEntity getImageById(Integer id) {

        String sql = """
                SELECT *
                FROM gallery
                WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int saveImage(GalleryEntity gallery) {

        String sql = """
                INSERT INTO gallery
                (title,
                 image_filename,
                 image_original_filename,
                 image_content_type,
                 image_size,
                 display_order,
                 status,
                 uploaded_by)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                gallery.getTitle(),
                gallery.getImageFilename(),
                gallery.getImageOriginalFilename(),
                gallery.getImageContentType(),
                gallery.getImageSize(),
                gallery.getDisplayOrder() != null ? gallery.getDisplayOrder() : 1,
                "ACTIVE",
                gallery.getUploadedBy()
        );
    }

    @Override
    public int deleteImage(Integer id) {

        String sql = """
                DELETE FROM gallery
                WHERE id=?
                """;

        return jdbcTemplate.update(sql, id);
    }
}
