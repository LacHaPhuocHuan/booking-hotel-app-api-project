package com.laptrinhweb.service.serviceImpl;

import ch.qos.logback.core.model.Model;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.dto.UserProfile;
import com.laptrinhweb.repository.UserRepository;
import com.laptrinhweb.service.IProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utils.Validation;

import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ProfileServiceImpl implements IProfileService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<ResponseData> getMyProfile() {
        var userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByEmail(userDetail.getUsername()).orElseThrow(() -> new UsernameNotFoundException(""));
        var profile = modelMapper.map(user, UserProfile.class);

        return ResponseEntity.ok(ResponseData.builder()
                .message("This is your profile")
                .status(HttpStatus.OK)
                .data(profile)
                .build());

    }

    @Override
    public ResponseEntity<ResponseData> createProfile(UserProfile userProfile) {
        var userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByEmail(userDetail.getUsername()).orElseThrow(() -> new UsernameNotFoundException(""));
        user.setDateOfBirth(userProfile.getDateOfBirth());
        user.setAvatar(userProfile.getAvatar());
        if (Validation.validaPhone(userProfile.getPhone())) {
            user.setPhone(userProfile.getPhone());
        }
        user.setSex(userProfile.getSex());
        user.setLastname(userProfile.getLastname());
        user.setFirstname(userProfile.getFirstname());
        user.setAddress(userProfile.getAddress());
        userRepository.save(user);

        return ResponseEntity.status(200).body(
                ResponseData.builder()
                        .message("Succfully")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseData> updateProfile(UserProfile userProfile) {
        var userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByEmail(userDetail.getUsername()).orElseThrow(() -> new UsernameNotFoundException(""));
        if (Objects.nonNull(userProfile.getDateOfBirth())) {
            user.setDateOfBirth(userProfile.getDateOfBirth());
        }
        if (Objects.nonNull(userProfile.getEmail()) && Validation.validaEmail(userProfile.getEmail())) {
            boolean isExistedEmail = userRepository.existsByEmail(userProfile.getEmail());
            if (isExistedEmail) {
                log.error("This Email was existed");
                return ResponseEntity.status(400).body(
                        ResponseData.builder()
                                .message("This Email was existed")
                                .status(HttpStatus.BAD_REQUEST)
                                .build()
                );
            }
            user.setEmail(userProfile.getEmail());
        }
        if (Objects.nonNull(userProfile.getAvatar())) {
            user.setAvatar(user.getAvatar());
        }
        if (Objects.nonNull(userProfile.getPhone()) && Validation.validaPhone(userProfile.getPhone())) {
            user.setPhone(userProfile.getPhone());
        }
        if (Objects.nonNull(userProfile.getSex())) {
            user.setSex(userProfile.getSex());
        }
        if (Objects.nonNull(userProfile.getFirstname()) && Objects.nonNull(userProfile.getLastname())) {
            user.setLastname(userProfile.getLastname());
            user.setFirstname(userProfile.getFirstname());
        }
        if(Objects.nonNull(userProfile.getAddress()))
            user.setAddress(userProfile.getAddress());
        userRepository.save(user);
        return ResponseEntity.status(200).body(
                ResponseData.builder()
                        .message("Succfully")
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
