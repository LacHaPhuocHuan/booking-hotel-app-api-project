package com.laptrinhweb.service.serviceImpl;

import com.laptrinhweb.dto.AccessTokenJson;
import com.laptrinhweb.dto.AuthenticationRequest;
import com.laptrinhweb.dto.ChangePasswordRequest;
import com.laptrinhweb.dto.RegisterRequest;
import com.laptrinhweb.entity.JwtToken;
import com.laptrinhweb.entity.User;
import com.laptrinhweb.exception.EmailExistedException;
import com.laptrinhweb.exception.ServerErrorException;
import com.laptrinhweb.repository.JwtTokenRepository;
import com.laptrinhweb.repository.UserRepository;
import com.laptrinhweb.security.JwtService;
import com.laptrinhweb.security.Role;
import com.laptrinhweb.security.TokenType;
import com.laptrinhweb.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utils.Validation;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;



@Service
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository repository;
    @Autowired
    JwtService jwtService;
    @Autowired
    JwtTokenRepository jwtTokenRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<?> register(RegisterRequest request) throws ServerErrorException, EmailExistedException {
            var user= modelMapper.map(request, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setNonClocked(true);
            user.setEnabled(true);
            user.setRole(Role.USER);
            user.setFirstname(user.getEmail());
            if(validateUserRequest(user))
                throw new ServerErrorException("Information is incorrect");
            User userByUsernameOnDB=repository.findByEmail(request.getEmail()).orElse(null);
            if( !Objects.isNull(userByUsernameOnDB))
                throw new EmailExistedException("Email (username) existed! ");
            var userDave= repository.save(user);
            var jwt=jwtService.generatedClaim(userDave.getEmail(),userDave.getRole().getAuthorities());
            var token= JwtToken.builder()
                    .token(jwt)
                    .tokenType(TokenType.BEARER)
                    .user(userDave)
                    .isEnabled(true)
                    .isNonExpired(true)
                    .build();
            var tokenSave=jwtTokenRepository.save(token);
            var tokenRefresh=jwtService.generatedRefreshToken(userDave);
            var accessToken= AccessTokenJson.builder()
                    .accessToken(jwt)
                    .refreshToken(tokenRefresh)
                    .build();
            return ResponseEntity.ok(accessToken);

    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        log.info("Authenticated! ");
        if(!validate(request))
            throw new BadCredentialsException("Email and password don't enclosed! ");
        Authentication authentication=null;

        try {
            authentication =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            if(authentication.getCredentials()==null){
                log.debug("Authentication failed: no credentials provided");
            }
            if(authentication.isAuthenticated()){

                var user=repository.findByEmail(request.getEmail()).orElseThrow(()->new UsernameNotFoundException("Email don't exist"));
                var tokens=jwtTokenRepository.findAllByUserId(user.getId());
                log.info("LIST TOKEN : {}", tokens.size());
                var jwtToken = jwtService.generatedClaim(user.getEmail(), user.getRole().getAuthorities());
                log.info("JWT TOKEN:  {}", jwtToken);
                var tokenRefresh=jwtService.generatedRefreshToken(user);
                resultTokens(tokens);
                saveToken(jwtToken,user);
                log.info("OK ROI");
                return ResponseEntity.ok().body(
                        AccessTokenJson.builder()
                                .accessToken(jwtToken)
                                .refreshToken(tokenRefresh)
                                .build()
                );
            }
            return new ResponseEntity<>("{\"message\":\"user or password is incorrect.\"}", HttpStatus.UNAUTHORIZED);

        }catch (Exception e){
            e.printStackTrace();
            if( authentication==null )
                return  ResponseEntity.status(400).body("{\"message\":\"Authentication failed: no correct credentials provided \"}");


        }

        return new ResponseEntity<>("{\"message\":\"Server arisen bug. \"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        var authToken = request.getHeader("Authorization");
        if (authToken == null || !authToken.startsWith("Bearer"))
            return ResponseEntity.status(400).body("Failed to find refresh token !");
        var refreshToken = authToken.substring(7);
        var username = jwtService.extractUsername(refreshToken);
        var jwtToken ="";
        if (username != null) {
            var user = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Refresh token is incorrect"));
            var tokens=jwtTokenRepository.findAllByUserId(user.getId());
            jwtToken = jwtService.generatedClaim(user.getEmail(), user.getRole().getAuthorities());
            saveToken(jwtToken,user);
            resultTokens(tokens);
            return ResponseEntity.ok().body(
                    AccessTokenJson.builder()
                            .refreshToken(refreshToken)
                            .accessToken(jwtToken)
                            .build()
            );
        }
        return ResponseEntity.status(400).body("Refresh token is incorrect");
    }




    private boolean validate(AuthenticationRequest request) {
        return request.getEmail().length()>0 && request.getPassword().length()>0;
    }

    private void saveToken(String jwtToken, User user) {
        try {
            jwtTokenRepository.save(
                    JwtToken.builder()
                            .tokenType(TokenType.BEARER)
                            .isNonExpired(true)
                            .isEnabled(true)
                            .user(user)
                            .token(jwtToken)
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void resultTokens(List<JwtToken> tokens) {
        try {
            if (tokens.isEmpty())
                return;
            tokens.forEach(t -> {
                t.setEnabled(false);
                t.setNonExpired(false);
            });
            jwtTokenRepository.saveAll(tokens);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean validateUserRequest(User user) {
        return user==null &&
                user.getPassword()==null &&
                user.getEmail()==null &&
                Validation.validaEmail(user.getEmail());
    }



    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequest request) {
        var userDetails= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(userDetails.getUsername());
        var user=repository.findByEmail(userDetails.getUsername()).orElseThrow(()->new RuntimeException("Server Error"));
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), request.oldPassword())
        );
        if(authentication.isAuthenticated())
        {
            user.setPassword(passwordEncoder.encode(request.newPassword()));
            repository.save(user);
            return  ResponseEntity.ok("Change password success.");
        }
        return ResponseEntity.status(400).body("Change password fail. Old Password is incorrect");
    }


}
