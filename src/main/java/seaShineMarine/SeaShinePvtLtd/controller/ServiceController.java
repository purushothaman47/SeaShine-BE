package seaShineMarine.SeaShinePvtLtd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seaShineMarine.SeaShinePvtLtd.model.ServiceEntity;
import seaShineMarine.SeaShinePvtLtd.service.ServiceService;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping
    public List<ServiceEntity> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ServiceEntity getById(@PathVariable Integer id) {
        return serviceService.getServiceById(id);
    }

    @PostMapping
    public ResponseEntity<String> addService(
            @RequestBody ServiceEntity service) {

        serviceService.addService(service);

        return ResponseEntity.ok("Service Added Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateService(
            @PathVariable Integer id,
            @RequestBody ServiceEntity service) {

        service.setId(id);

        serviceService.updateService(service);

        return ResponseEntity.ok("Service Updated Successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(
            @PathVariable Integer id) {

        serviceService.deleteService(id);

        return ResponseEntity.ok("Service Deleted Successfully");
    }
}