package seaShineMarine.SeaShinePvtLtd.service;

import seaShineMarine.SeaShinePvtLtd.model.LoginRequest;

public interface AuthService {

    String login(LoginRequest request);
}