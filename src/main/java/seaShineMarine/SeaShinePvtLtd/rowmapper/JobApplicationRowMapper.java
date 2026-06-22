package seaShineMarine.SeaShinePvtLtd.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import seaShineMarine.SeaShinePvtLtd.model.JobApplicationEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JobApplicationRowMapper
        implements RowMapper<JobApplicationEntity> {

    @Override
    public JobApplicationEntity mapRow(
            ResultSet rs,
            int rowNum) throws SQLException {

        JobApplicationEntity application =
                new JobApplicationEntity();

        application.setId(rs.getInt("id"));
        application.setCareerId(rs.getInt("career_id"));
        application.setApplicantName(
                rs.getString("applicant_name"));
        application.setEmail(rs.getString("email"));
        application.setPhone(rs.getString("phone"));
        application.setResumePath(
                rs.getString("resume_path"));
        application.setCoverLetter(
                rs.getString("cover_letter"));
        application.setApplicationStatus(
                rs.getString("application_status"));

        return application;
    }
}
