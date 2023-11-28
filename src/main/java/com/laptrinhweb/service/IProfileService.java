package com.laptrinhweb.service;

import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.dto.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

public interface IProfileService {
    ResponseEntity<ResponseData> getMyProfile();

    ResponseEntity<ResponseData> createProfile(UserProfile userProfile);

    ResponseEntity<ResponseData> updateProfile(UserProfile userProfile);

    ResponseEntity<ResponseData> setAvatar(MultipartFile file);

    ResponseEntity<byte[]> getAvatar();
}
