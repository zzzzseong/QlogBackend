package com.Qlog.backend.service.jwt;

import com.Qlog.backend.controller.dto.auth.AuthResponse;
import com.Qlog.backend.controller.dto.auth.AuthenticationRequest;
import com.Qlog.backend.controller.dto.auth.RegisterRequest;
import com.Qlog.backend.domain.Role;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = new User(request.getLoginId(), passwordEncoder.encode(request.getPassword()),
                request.getName(), Role.USER);

        userService.save(user);
        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
    public AuthResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLoginId(),
                        request.getPassword()
                )
        );
        User user = userService.findByLoginId(request.getLoginId());
        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

}
