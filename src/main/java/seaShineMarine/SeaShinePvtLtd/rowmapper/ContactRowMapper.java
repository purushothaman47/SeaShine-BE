package seaShineMarine.SeaShinePvtLtd.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import seaShineMarine.SeaShinePvtLtd.model.ContactEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ContactRowMapper
        implements RowMapper<ContactEntity> {

    @Override
    public ContactEntity mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        ContactEntity contact = new ContactEntity();

        contact.setId(rs.getInt("id"));
        contact.setName(rs.getString("name"));
        contact.setEmail(rs.getString("email"));
        contact.setPhone(rs.getString("phone"));
        contact.setSubject(rs.getString("subject"));
        contact.setMessage(rs.getString("message"));
        contact.setStatus(rs.getString("status"));

        return contact;
    }
}
