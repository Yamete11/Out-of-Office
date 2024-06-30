package group.toprank_be.runner;
import group.toprank_be.entity.User;
import group.toprank_be.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setUsername("gleb");
        user1.setEmail("glebasher11@gmail.com");
        user1.setPassword(passwordEncoder.encode("12345"));

        userRepository.save(user1);

    }
}
