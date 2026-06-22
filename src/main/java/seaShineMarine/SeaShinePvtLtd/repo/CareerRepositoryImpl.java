package seaShineMarine.SeaShinePvtLtd.repo;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seaShineMarine.SeaShinePvtLtd.model.CareerEntity;
import seaShineMarine.SeaShinePvtLtd.rowmapper.CareerRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CareerRepositoryImpl implements CareerRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CareerRowMapper rowMapper;

    @Override
    public List<CareerEntity> getAllCareers() {

        String sql = """
                SELECT *
                FROM careers
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public CareerEntity getCareerById(Integer id) {

        String sql = """
                SELECT *
                FROM careers
                WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int addCareer(CareerEntity career) {

        String sql = """
                INSERT INTO careers
                (job_title,
                 department,
                 location,
                 experience_required,
                 employment_type,
                 description,
                 status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                career.getJobTitle(),
                career.getDepartment(),
                career.getLocation(),
                career.getExperienceRequired(),
                career.getEmploymentType(),
                career.getDescription(),
                career.getStatus()
        );
    }

    @Override
    public int updateCareer(CareerEntity career) {

        String sql = """
                UPDATE careers
                SET job_title=?,
                    department=?,
                    location=?,
                    experience_required=?,
                    employment_type=?,
                    description=?,
                    status=?
                WHERE id=?
                """;

        return jdbcTemplate.update(
                sql,
                career.getJobTitle(),
                career.getDepartment(),
                career.getLocation(),
                career.getExperienceRequired(),
                career.getEmploymentType(),
                career.getDescription(),
                career.getStatus(),
                career.getId()
        );
    }

    @Override
    public int deleteCareer(Integer id) {

        String sql = """
                DELETE FROM careers
                WHERE id=?
                """;

        return jdbcTemplate.update(sql, id);
    }
}