package seaShineMarine.SeaShinePvtLtd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seaShineMarine.SeaShinePvtLtd.model.ServiceEntity;
import seaShineMarine.SeaShinePvtLtd.repo.ServiceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public List<ServiceEntity> getAllServices() {
        return serviceRepository.getAllServices();
    }

    @Override
    public ServiceEntity getServiceById(Integer id) {
        return serviceRepository.getServiceById(id);
    }

    @Override
    public void addService(ServiceEntity service) {
        serviceRepository.addService(service);
    }

    @Override
    public void updateService(ServiceEntity service) {
        serviceRepository.updateService(service);
    }

    @Override
    public void deleteService(Integer id) {
        serviceRepository.deleteService(id);
    }
}
