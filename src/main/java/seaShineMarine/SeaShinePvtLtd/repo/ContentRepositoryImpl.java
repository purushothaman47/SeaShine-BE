package seaShineMarine.SeaShinePvtLtd.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seaShineMarine.SeaShinePvtLtd.model.ContentEntity;
import seaShineMarine.SeaShinePvtLtd.rowmapper.ContentRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContentRepositoryImpl implements ContentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ContentRowMapper rowMapper;

    @Override
    public List<ContentEntity> getAllContent() {

        String sql = "SELECT * FROM content_management";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ContentEntity getContentById(Integer id) {

        String sql =
                "SELECT * FROM content_management WHERE id=?";

        return jdbcTemplate.queryForObject(sql,
                rowMapper,
                id);
    }

    @Override
    public ContentEntity getContentBySection(String sectionName) {

        String sql =
                "SELECT * FROM content_management WHERE section_name=?";

        return jdbcTemplate.queryForObject(sql,
                rowMapper,
                sectionName);
    }

    @Override
    public int addContent(ContentEntity content) {

        String sql = """
                INSERT INTO content_management
                (section_name,title,subtitle,
                 content,image_url,status)
                VALUES(?,?,?,?,?,?)
                """;

        return jdbcTemplate.update(
                sql,
                content.getSectionName(),
                content.getTitle(),
                content.getSubtitle(),
                content.getContent(),
                content.getImageUrl(),
                content.getStatus());
    }

    @Override
    public int updateContent(ContentEntity content) {

        String sql = """
                UPDATE content_management
                SET section_name=?,
                    title=?,
                    subtitle=?,
                    content=?,
                    image_url=?,
                    status=?
                WHERE id=?
                """;

        return jdbcTemplate.update(
                sql,
                content.getSectionName(),
                content.getTitle(),
                content.getSubtitle(),
                content.getContent(),
                content.getImageUrl(),
                content.getStatus(),
                content.getId());
    }

    @Override
    public int deleteContent(Integer id) {

        String sql =
                "DELETE FROM content_management WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }
}