package seaShineMarine.SeaShinePvtLtd.repo;

import seaShineMarine.SeaShinePvtLtd.model.CareerEntity;

import java.util.List;

public interface CareerRepository {

    List<CareerEntity> getAllCareers();

    CareerEntity getCareerById(Integer id);

    int addCareer(CareerEntity career);

    int updateCareer(CareerEntity career);

    int deleteCareer(Integer id);
}
