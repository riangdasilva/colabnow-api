package riangdasilva.colabnowapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import riangdasilva.colabnowapi.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserDetails findByUsername(String username);

    boolean existsByUsername(String username);
}
