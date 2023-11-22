package com.laptrinhweb.service;

import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.dto.UserProfile;
import org.springframework.http.ResponseEntity;

public interface IProfileService {
    ResponseEntity<ResponseData> getMyProfile();

    ResponseEntity<ResponseData> createProfile(UserProfile userProfile);

    ResponseEntity<ResponseData> updateProfile(UserProfile userProfile);
}
