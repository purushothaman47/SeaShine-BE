package seaShineMarine.SeaShinePvtLtd.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seaShineMarine.SeaShinePvtLtd.model.ServiceEntity;
import seaShineMarine.SeaShinePvtLtd.rowmapper.ServiceRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ServiceRowMapper rowMapper;

    @Override
    public List<ServiceEntity> getAllServices() {
        String sql = "SELECT * FROM services ORDER BY display_order";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ServiceEntity getServiceById(Integer id) {
        String sql = "SELECT * FROM services WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int addService(ServiceEntity service) {
        String sql = """
            INSERT INTO services
            (service_name, short_description, description,
             image_url, display_order, status)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        return jdbcTemplate.update(
                sql,
                service.getServiceName(),
                service.getShortDescription(),
                service.getDescription(),
                service.getImageUrl(),
                service.getDisplayOrder(),
                service.getStatus());
    }

    @Override
    public int updateService(ServiceEntity service) {
        String sql = """
            UPDATE services
            SET service_name=?,
                short_description=?,
                description=?,
                image_url=?,
                display_order=?,
                status=?
            WHERE id=?
            """;

        return jdbcTemplate.update(
                sql,
                service.getServiceName(),
                service.getShortDescription(),
                service.getDescription(),
                service.getImageUrl(),
                service.getDisplayOrder(),
                service.getStatus(),
                service.getId());
    }

    @Override
    public int deleteService(Integer id) {
        String sql = "DELETE FROM services WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }
}
