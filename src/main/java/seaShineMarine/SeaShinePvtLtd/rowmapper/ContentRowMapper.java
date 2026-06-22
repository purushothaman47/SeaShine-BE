package seaShineMarine.SeaShinePvtLtd.rowmapper;

import seaShineMarine.SeaShinePvtLtd.model.ContentEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ContentRowMapper implements RowMapper<ContentEntity> {

    @Override
    public ContentEntity mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        ContentEntity content = new ContentEntity();

        content.setId(rs.getInt("id"));
        content.setSectionName(rs.getString("section_name"));
        content.setTitle(rs.getString("title"));
        content.setSubtitle(rs.getString("subtitle"));
        content.setContent(rs.getString("content"));
        content.setImageUrl(rs.getString("image_url"));
        content.setStatus(rs.getString("status"));

        return content;
    }
}