package seaShineMarine.SeaShinePvtLtd.service;

import seaShineMarine.SeaShinePvtLtd.model.ServiceEntity;

import java.util.List;

public interface ServiceService {

    List<ServiceEntity> getAllServices();

    ServiceEntity getServiceById(Integer id);

    void addService(ServiceEntity service);

    void updateService(ServiceEntity service);

    void deleteService(Integer id);
}