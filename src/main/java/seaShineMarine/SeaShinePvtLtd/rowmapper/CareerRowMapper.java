package seaShineMarine.SeaShinePvtLtd.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import seaShineMarine.SeaShinePvtLtd.model.CareerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CareerRowMapper implements RowMapper<CareerEntity> {

    @Override
    public CareerEntity mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        CareerEntity career = new CareerEntity();

        career.setId(rs.getInt("id"));
        career.setJobTitle(rs.getString("job_title"));
        career.setDepartment(rs.getString("department"));
        career.setLocation(rs.getString("location"));
        career.setExperienceRequired(
                rs.getString("experience_required"));
        career.setEmploymentType(
                rs.getString("employment_type"));
        career.setDescription(rs.getString("description"));
        career.setStatus(rs.getString("status"));

        return career;
    }
}
