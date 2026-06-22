package seaShineMarine.SeaShinePvtLtd.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import seaShineMarine.SeaShinePvtLtd.model.ServiceEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ServiceRowMapper implements RowMapper<ServiceEntity> {

    @Override
    public ServiceEntity mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        ServiceEntity service = new ServiceEntity();

        service.setId(rs.getInt("id"));
        service.setServiceName(rs.getString("service_name"));
        service.setShortDescription(rs.getString("short_description"));
        service.setDescription(rs.getString("description"));
        service.setImageUrl(rs.getString("image_url"));
        service.setDisplayOrder(rs.getInt("display_order"));
        service.setStatus(rs.getString("status"));

        return service;
    }
}