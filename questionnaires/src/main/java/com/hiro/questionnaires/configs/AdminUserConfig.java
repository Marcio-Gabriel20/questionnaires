package com.hiro.questionnaires.configs;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hiro.questionnaires.entity.Role;
import com.hiro.questionnaires.entity.User;
import com.hiro.questionnaires.enums.RoleType;
import com.hiro.questionnaires.repository.RoleRepository;
import com.hiro.questionnaires.repository.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role roleAdmin = roleRepository.findByName(RoleType.ADMIN.name());
        Optional<User> userAdmin = userRepository.findByLogin("admin");

        userAdmin.ifPresentOrElse(
            (user) -> {
                System.out.println("Admin já existe");
            },

            () -> {
                User user = new User();
                user.setLogin("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setRoles(Set.of(roleAdmin));

                userRepository.save(user);
            }
        );
    }
    
}