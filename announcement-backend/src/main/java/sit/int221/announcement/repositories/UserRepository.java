package sit.int221.announcement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.announcement.models.User;

public interface UserRepository extends JpaRepository<User,Integer> { }
