package seaShineMarine.SeaShinePvtLtd.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import seaShineMarine.SeaShinePvtLtd.model.GalleryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GalleryRowMapper implements RowMapper<GalleryEntity> {

    @Override
    public GalleryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        GalleryEntity gallery = new GalleryEntity();

        gallery.setId(rs.getInt("id"));
        gallery.setTitle(rs.getString("title"));
        gallery.setImageFilename(rs.getString("image_filename"));
        gallery.setImageOriginalFilename(rs.getString("image_original_filename"));
        gallery.setImageContentType(rs.getString("image_content_type"));
        gallery.setImageSize(rs.getObject("image_size", Long.class));
        gallery.setDisplayOrder(rs.getObject("display_order", Integer.class));
        gallery.setStatus(rs.getString("status"));
        gallery.setUploadedBy(rs.getObject("uploaded_by", Integer.class));

        return gallery;
    }
}
