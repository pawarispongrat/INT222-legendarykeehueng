package sit.int221.announcement.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.announcement.models.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>,EntityRepository<User> {


    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
