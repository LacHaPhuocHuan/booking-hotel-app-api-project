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
//@Api(value = "Hotel API", tags = "Authentication Operations")
public interface AuthenticationRest {
    @PostMapping("/register")
//    @ApiOperation(value = "Register User", notes = "Register a new user with the provided details.")
    ResponseEntity<?> register(@RequestBody RegisterRequest request) throws ServerErrorException, EmailExistedException;
    @PostMapping("/authentication")
//    @ApiOperation(value = "User Authentication", notes = "Authenticate a user with the provided credentials.")
    ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request);

    @PostMapping("/refresh")
//    @ApiOperation(value = "Refresh Token", notes = "Refresh the authentication token.")
    ResponseEntity<?> refresh(@NonNull HttpServletRequest request);

    @PutMapping("/change-password")
//    @ApiOperation(value = "Change Password", notes = "Change the user's password.")
    ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest);
}
