package riangdasilva.colabnowapi.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import riangdasilva.colabnowapi.dtos.LoginDto;
import riangdasilva.colabnowapi.dtos.RegisterDto;
import riangdasilva.colabnowapi.models.UserModel;
import riangdasilva.colabnowapi.repositories.UserRepository;
import riangdasilva.colabnowapi.services.AuthService;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = authService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(token);

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.username())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        var newUser = new UserModel(registerDto.username(), hashedPassword, registerDto.role());

        userRepository.save(newUser);

        return ResponseEntity.ok("Register successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful");
    }


}
