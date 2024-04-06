package com.example.demo.controller;

import com.example.demo.dto.request.AuthenticationRequestDto;
import com.example.demo.dto.request.RefreshTokenRequestDTO;
import com.example.demo.dto.request.RegisterRequestDto;
import com.example.demo.dto.response.AuthenticationResponseDto;
import com.example.demo.module.RefreshToken;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;
import com.example.demo.service.RefreshTokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {
     private final AuthenticationService authService;
    private final ObjectMapper objectMapper;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate( @RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("send-email")
    public ResponseEntity<String> sendEmail(@RequestBody JsonNode request) {
       authService.sendOtpEmail(request.get("email").asText());
        return ResponseEntity.ok("Email Send !");
    }

    @PostMapping("verify-otp")
    public ResponseEntity<Boolean> verifyOtp(@RequestBody JsonNode request) {
        return ResponseEntity.ok(authService.verifyOtp(request.get("email").asText(), request.get("otp").asInt()));
    }

    @PutMapping("reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody JsonNode request) {
        authService.resetPassword(request.get("email").asText(), request.get("password").asText());
        return ResponseEntity.ok("Password reset successfully");
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
           return ResponseEntity.ok(authService.refreshToken(refreshTokenRequestDTO));
        }


}
