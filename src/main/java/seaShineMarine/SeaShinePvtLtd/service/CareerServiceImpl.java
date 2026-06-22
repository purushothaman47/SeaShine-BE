package seaShineMarine.SeaShinePvtLtd.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seaShineMarine.SeaShinePvtLtd.model.CareerEntity;
import seaShineMarine.SeaShinePvtLtd.repo.CareerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerServiceImpl implements CareerService {

    private final CareerRepository careerRepository;

    @Override
    public List<CareerEntity> getAllCareers() {
        return careerRepository.getAllCareers();
    }

    @Override
    public CareerEntity getCareerById(Integer id) {
        return careerRepository.getCareerById(id);
    }

    @Override
    public void addCareer(CareerEntity career) {
        careerRepository.addCareer(career);
    }

    @Override
    public void updateCareer(CareerEntity career) {
        careerRepository.updateCareer(career);
    }

    @Override
    public void deleteCareer(Integer id) {
        careerRepository.deleteCareer(id);
    }
}
