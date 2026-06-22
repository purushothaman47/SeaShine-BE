package seaShineMarine.SeaShinePvtLtd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seaShineMarine.SeaShinePvtLtd.config.JwtUtil;
import seaShineMarine.SeaShinePvtLtd.model.AdminUser;
import seaShineMarine.SeaShinePvtLtd.model.LoginRequest;
import seaShineMarine.SeaShinePvtLtd.repo.AuthRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;

    @Override
    public String login(LoginRequest request) {

        AdminUser user =
                authRepository.findByUsername(
                        request.getUsername());

        if (user == null) {
            throw new RuntimeException("User Not Found");
        }

        if (!user.getPassword()
                .equals(request.getPassword())) {

            throw new RuntimeException(
                    "Invalid Credentials");
        }

        return jwtUtil.generateToken(
                user.getUsername());
    }
}