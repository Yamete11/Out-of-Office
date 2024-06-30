package group.toprank_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TopRankBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopRankBeApplication.class, args);
    }

}
