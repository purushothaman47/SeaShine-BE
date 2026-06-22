package seaShineMarine.SeaShinePvtLtd.repo;

import seaShineMarine.SeaShinePvtLtd.model.ServiceEntity;

import java.util.List;

public interface ServiceRepository {

    List<ServiceEntity> getAllServices();

    ServiceEntity getServiceById(Integer id);

    int addService(ServiceEntity service);

    int updateService(ServiceEntity service);

    int deleteService(Integer id);
}
