package seaShineMarine.SeaShinePvtLtd.repo;

import seaShineMarine.SeaShinePvtLtd.model.AdminUser;

public interface AuthRepository {

    AdminUser findByUsername(String username);
}