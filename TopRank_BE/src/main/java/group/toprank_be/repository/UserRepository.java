package group.toprank_be.repository;

import group.toprank_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
}
