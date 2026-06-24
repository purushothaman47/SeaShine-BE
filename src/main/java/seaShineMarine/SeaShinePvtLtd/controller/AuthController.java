package seaShineMarine.SeaShinePvtLtd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seaShineMarine.SeaShinePvtLtd.model.LoginRequest;
import seaShineMarine.SeaShinePvtLtd.model.LoginResponse;
import seaShineMarine.SeaShinePvtLtd.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        String token =
                authService.login(request);

        return new LoginResponse(token);
    }
}