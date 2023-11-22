package com.laptrinhweb.rest.restImpl;

import com.laptrinhweb.dto.AuthenticationRequest;
import com.laptrinhweb.dto.RegisterRequest;
import com.laptrinhweb.exception.EmailExistedException;
import com.laptrinhweb.exception.ServerErrorException;
import com.laptrinhweb.rest.AuthenticationRest;
import com.laptrinhweb.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Log4j2
public class AuthenticationRestImpl implements AuthenticationRest {
    @Autowired
    AuthenticationService service;
    @Override
    public ResponseEntity<?> register(RegisterRequest request) throws ServerErrorException, EmailExistedException {
        return service.register(request);
    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticationRequest request)  {
        log.info("Initiate authentication");
        return service.authenticate(request);

    }

    @Override
    public ResponseEntity<?> refresh(@NonNull HttpServletRequest request) {
        return service.refresh(request);
    }


}
