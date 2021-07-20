package by.tms.repository;

import by.tms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
    List<User> findAllByUsername(String user);
    User findById(long id);
    List<User> findUsersById(long id);
}
