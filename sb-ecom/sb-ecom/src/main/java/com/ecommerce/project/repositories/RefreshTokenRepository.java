package com.ecommerce.project.repositories;

import com.ecommerce.project.model.RefreshToken;
import com.ecommerce.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);

    Optional<RefreshToken> findByUserUserId(Long id);
}
