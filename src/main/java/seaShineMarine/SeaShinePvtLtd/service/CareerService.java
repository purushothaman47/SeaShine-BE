package seaShineMarine.SeaShinePvtLtd.service;

import seaShineMarine.SeaShinePvtLtd.model.CareerEntity;

import java.util.List;

public interface CareerService {

    List<CareerEntity> getAllCareers();

    CareerEntity getCareerById(Integer id);

    void addCareer(CareerEntity career);

    void updateCareer(CareerEntity career);

    void deleteCareer(Integer id);
}