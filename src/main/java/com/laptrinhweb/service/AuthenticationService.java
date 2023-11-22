package com.laptrinhweb.service;


import com.laptrinhweb.dto.AuthenticationRequest;
import com.laptrinhweb.dto.ChangePasswordRequest;
import com.laptrinhweb.dto.RegisterRequest;
import com.laptrinhweb.exception.EmailExistedException;
import com.laptrinhweb.exception.ServerErrorException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface AuthenticationService {
    ResponseEntity<?> register(RegisterRequest request) throws ServerErrorException, EmailExistedException;

    ResponseEntity<?> authenticate(AuthenticationRequest request);

    ResponseEntity<?> refresh(HttpServletRequest request);

    ResponseEntity<?> changePassword(ChangePasswordRequest request);
}
