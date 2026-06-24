package seaShineMarine.SeaShinePvtLtd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seaShineMarine.SeaShinePvtLtd.model.CareerEntity;
import seaShineMarine.SeaShinePvtLtd.service.CareerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/careers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CareerController {

    private final CareerService careerService;

    @GetMapping
    public List<CareerEntity> getAllCareers() {
        return careerService.getAllCareers();
    }

    @GetMapping("/{id}")
    public CareerEntity getCareerById(
            @PathVariable Integer id) {

        return careerService.getCareerById(id);
    }

    @PostMapping
    public String addCareer(
            @RequestBody CareerEntity career) {

        careerService.addCareer(career);

        return "Career Added Successfully";
    }

    @PutMapping("/{id}")
    public String updateCareer(
            @PathVariable Integer id,
            @RequestBody CareerEntity career) {

        career.setId(id);

        careerService.updateCareer(career);

        return "Career Updated Successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteCareer(
            @PathVariable Integer id) {

        careerService.deleteCareer(id);

        return "Career Deleted Successfully";
    }
}