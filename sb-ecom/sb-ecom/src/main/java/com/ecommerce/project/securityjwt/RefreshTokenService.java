package com.ecommerce.project.securityjwt;

import com.ecommerce.project.model.RefreshToken;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repositories.RefreshTokenRepository;
import com.ecommerce.project.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${app.refreshExpirationMs}")
    private Long refreshExpirationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public RefreshToken createRefreshToken(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Check if a refresh token already exists for this user
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserUserId(user.getUserId());

        // Use existing token or create a new one
        RefreshToken token = existingToken.orElse(new RefreshToken());
        if (existingToken.isPresent()) {
            // Update the existing token
            token = existingToken.get();
            token.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
            token.setToken(UUID.randomUUID().toString());
        } else {
            // Create a new token
            token = new RefreshToken();
            token.setUser(user);
            token.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
            token.setToken(UUID.randomUUID().toString());
        }

        // Save or update the token
        return refreshTokenRepository.save(token);
    }


    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }

}

