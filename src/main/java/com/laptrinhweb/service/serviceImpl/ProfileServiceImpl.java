package com.laptrinhweb.service.serviceImpl;

import ch.qos.logback.core.model.Model;
import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.dto.UserProfile;
import com.laptrinhweb.entity.Avatar;
import com.laptrinhweb.entity.User;
import com.laptrinhweb.repository.AvatarRepository;
import com.laptrinhweb.repository.UserRepository;
import com.laptrinhweb.service.IProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utils.Validation;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ProfileServiceImpl implements IProfileService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AvatarRepository avatarRepository;


    @Override
    public ResponseEntity<ResponseData> getMyProfile() {
        var userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByEmail(userDetail.getUsername()).orElseThrow(() -> new UsernameNotFoundException(""));
        var profile = modelMapper.map(user, UserProfile.class);
        if (Objects.nonNull(profile.getAvatar()))
            profile.setAvatar(null);
        return ResponseEntity.ok(ResponseData.builder()
                .message("This is your profile")
                .status(HttpStatus.OK)
                .data(profile)
                .build());

    }

    @Override
    public ResponseEntity<ResponseData> createProfile(UserProfile userProfile) {
        var user = getCurrentUser();

        user.setDateOfBirth(userProfile.getDateOfBirth());
        if (Validation.validaPhone(userProfile.getPhone())) {
            user.setPhone(userProfile.getPhone());
        }
        user.setSex(userProfile.getSex());
        user.setLastname(userProfile.getLastname());
        user.setFirstname(userProfile.getFirstname());
        user.setAddress(userProfile.getAddress());
        userRepository.save(user);

        return success();
    }

    @Override
    public ResponseEntity<ResponseData> updateProfile(UserProfile userProfile) {
        var user = getCurrentUser();
        if (Objects.nonNull(userProfile.getDateOfBirth())) {
            user.setDateOfBirth(userProfile.getDateOfBirth());
        }
        if (Objects.nonNull(userProfile.getEmail()) && Validation.validaEmail(userProfile.getEmail())) {
            boolean isExistedEmail = userRepository.existsByEmail(userProfile.getEmail());
            if (isExistedEmail) {
                String message = "This Email was existed";
                log.error("{}", message);
                return badRequest(message);
            }
            user.setEmail(userProfile.getEmail());
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
        if (Objects.nonNull(userProfile.getAddress()))
            user.setAddress(userProfile.getAddress());
        userRepository.save(user);
        return success();

    }

    private User getCurrentUser() {
        var userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByEmail(userDetail.getUsername()).orElseThrow(() -> new UsernameNotFoundException(""));
        return user;
    }

    private ResponseEntity<ResponseData> badRequest(String message) {
        return ResponseEntity.status(400).body(
                ResponseData.builder()
                        .message(message)
                        .status(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

    private ResponseEntity<ResponseData> success() {
        return ResponseEntity.status(200).body(
                ResponseData.builder()
                        .message("Succfully")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    private ResponseEntity<ResponseData> success(Object object) {
        return ResponseEntity.status(200).body(
                ResponseData.builder()
                        .message("Succfully")
                        .data(object)
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseData> setAvatar(MultipartFile file) {

        if (Objects.isNull(file))
            return badRequest("please set avatar images.");
        var user = getCurrentUser();
        try {
            var avatar = Avatar.builder()
                    .imageName(file.getOriginalFilename() + "_" + ZonedDateTime.now().toString())
                    .imageData(new SerialBlob(file.getBytes()))
                    .user(user)
                    .build();
            avatar = avatarRepository.save(avatar);
            user.setAvatarImg(avatar);
            userRepository.save(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return success();
    }

    @Override
    public ResponseEntity<byte[]> getAvatar() {

        var user = getCurrentUser();
        byte[] bytes = null;
        try {
            Blob avatarBlob = user.getAvatarImg().getImageData();
            int avatarBlobLent = (int) avatarBlob.length();
            bytes = avatarBlob.getBytes(1, avatarBlobLent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (bytes != null)
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        else return ResponseEntity.notFound().build();
    }

}
