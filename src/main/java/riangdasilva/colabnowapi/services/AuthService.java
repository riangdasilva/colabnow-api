package riangdasilva.colabnowapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import riangdasilva.colabnowapi.models.UserModel;
import riangdasilva.colabnowapi.repositories.UserRepository;

import java.time.Instant;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Value("${app.secret}")
    private String secret;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public String generateToken(UserModel userModel) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT
                    .create()
                    .withIssuer("auth0")
                    .withSubject("auth")
                    .withClaim("username", userModel.getUsername())
                    .withClaim("role", userModel.getRole().getRole())
                    .withExpiresAt(Instant.now().plusSeconds(60 * 4))
                    .sign(algorithm);

        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth0")
                    .withSubject("auth")
                    .build()
                    .verify(token)
                    .getClaim("username")
                    .asString();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }
}
