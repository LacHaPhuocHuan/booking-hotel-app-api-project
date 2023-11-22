package com.laptrinhweb.rest;

import com.laptrinhweb.dto.AuthenticationRequest;
import com.laptrinhweb.dto.ChangePasswordRequest;
import com.laptrinhweb.dto.RegisterRequest;
import com.laptrinhweb.exception.EmailExistedException;
import com.laptrinhweb.exception.ServerErrorException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@RequestMapping("/api/v1/auth")
public interface AuthenticationRest {
    @PostMapping("/register")
     ResponseEntity<?> register(@RequestBody RegisterRequest request) throws ServerErrorException, EmailExistedException;
    @PostMapping("/authentication")
     ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request);

    @PostMapping("/refresh")
     ResponseEntity<?> refresh(@NonNull HttpServletRequest request);

    @PutMapping("/change-password")
    ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest);
}
