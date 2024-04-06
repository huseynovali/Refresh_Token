package com.example.demo.service;

import com.example.demo.module.RefreshToken;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private  final UserRepo userRepo;

    public RefreshToken generateRefreshToken(String email) {
          RefreshToken refreshToken = RefreshToken.builder()
                  .user(userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found")))
                  .token(UUID.randomUUID().toString())
                  .expiryDate(Instant.now().plusMillis(60000))
                    .build();
            return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

}
