package seaShineMarine.SeaShinePvtLtd.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import seaShineMarine.SeaShinePvtLtd.model.AdminUser;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AdminUserRowMapper
        implements RowMapper<AdminUser> {

    @Override
    public AdminUser mapRow(
            ResultSet rs,
            int rowNum) throws SQLException {

        AdminUser user = new AdminUser();

        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));

        return user;
    }
}