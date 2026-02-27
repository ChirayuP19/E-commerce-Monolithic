package com.ecommerce.project.starter;

import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDataConfig {
//    @Bean
//    public CommandLineRunner initRoles(RoleRepository roleRepository) {
//        return args -> {
//            // Check and create ROLE_USER
//            if (roleRepository.findByRoleName(AppRole.ROLE_USER).isEmpty()) {
//                roleRepository.save(new Role(AppRole.ROLE_USER));
//                System.out.println("Seeded: ROLE_USER");
//            }
//
//            // Check and create ROLE_ADMIN
//            if (roleRepository.findByRoleName(AppRole.ROLE_ADMIN).isEmpty()) {
//                roleRepository.save(new Role(AppRole.ROLE_ADMIN));
//                System.out.println("Seeded: ROLE_ADMIN");
//            }
//
//            // Optional: Check and create ROLE_SELLER
//            if (roleRepository.findByRoleName(AppRole.ROLE_SELLER).isEmpty()) {
//                roleRepository.save(new Role(AppRole.ROLE_SELLER));
//                System.out.println("Seeded: ROLE_SELLER");
//            }
//        };
//    }
}
