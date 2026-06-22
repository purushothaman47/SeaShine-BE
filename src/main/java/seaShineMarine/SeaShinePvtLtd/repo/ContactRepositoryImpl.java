package seaShineMarine.SeaShinePvtLtd.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seaShineMarine.SeaShinePvtLtd.model.ContactEntity;
import seaShineMarine.SeaShinePvtLtd.rowmapper.ContactRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContactRepositoryImpl
        implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ContactRowMapper rowMapper;

    @Override
    public List<ContactEntity> getAllMessages() {

        String sql =
                "SELECT * FROM contact_messages ORDER BY id DESC";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ContactEntity getMessageById(Integer id) {

        String sql =
                "SELECT * FROM contact_messages WHERE id=?";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int saveMessage(ContactEntity contact) {

        String sql = """
                INSERT INTO contact_messages
                (name,email,phone,subject,message,status)
                VALUES(?,?,?,?,?,?)
                """;

        return jdbcTemplate.update(
                sql,
                contact.getName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getSubject(),
                contact.getMessage(),
                "NEW");
    }

    @Override
    public int deleteMessage(Integer id) {

        String sql =
                "DELETE FROM contact_messages WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }
}
