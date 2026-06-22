package seaShineMarine.SeaShinePvtLtd.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seaShineMarine.SeaShinePvtLtd.model.AdminUser;
import seaShineMarine.SeaShinePvtLtd.rowmapper.AdminUserRowMapper;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl
        implements AuthRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AdminUserRowMapper rowMapper;

    @Override
    public AdminUser findByUsername(String username) {

        String sql = """
                SELECT *
                FROM admin_users
                WHERE username = ?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                rowMapper,
                username);
    }
}