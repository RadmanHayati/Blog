package com.scenius.mosaheb;

import com.scenius.mosaheb.entities.Role;
import com.scenius.mosaheb.entities.User;
import com.scenius.mosaheb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MosahebApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(MosahebApplication.class, args);
    }


    public void run(String... args) throws Exception {
        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if (adminAccount == null) {
            User user = new User();
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setPhone("09197822480");
            user.setFirstname("Radman");
            user.setLastname("Hayati");
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        }
    }
}
