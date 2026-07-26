package seaShineMarine.SeaShinePvtLtd.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seaShineMarine.SeaShinePvtLtd.model.JobApplicationEntity;
import seaShineMarine.SeaShinePvtLtd.rowmapper.JobApplicationRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JobApplicationRepositoryImpl
        implements JobApplicationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JobApplicationRowMapper rowMapper;

    @Override
    public List<JobApplicationEntity> getAllApplications() {

        String sql = """
                SELECT *
                FROM job_applications
                ORDER BY id DESC
                """;

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public JobApplicationEntity getApplicationById(Integer id) {

        String sql = """
                SELECT *
                FROM job_applications
                WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int saveApplication(JobApplicationEntity application) {

        String sql = """
                INSERT INTO job_applications
                (career_id,
                 applicant_name,
                 email,
                 phone,
                 resume_path,
                 resume_original_filename,
                 resume_content_type,
                 resume_size,
                 cover_letter,
                 application_status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                application.getCareerId(),
                application.getApplicantName(),
                application.getEmail(),
                application.getPhone(),
                application.getResumePath(),
                application.getResumeOriginalFilename(),
                application.getResumeContentType(),
                application.getResumeSize(),
                application.getCoverLetter(),
                "PENDING"
        );
    }

    @Override
    public int updateStatus(Integer id, String status) {

        String sql = """
                UPDATE job_applications
                SET application_status = ?
                WHERE id = ?
                """;

        return jdbcTemplate.update(sql, status, id);
    }

    @Override
    public int deleteApplication(Integer id) {

        String sql = """
                DELETE FROM job_applications
                WHERE id=?
                """;

        return jdbcTemplate.update(sql, id);
    }
}
