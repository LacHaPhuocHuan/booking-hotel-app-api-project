package com.laptrinhweb.rest.restImpl;

import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.dto.UserProfile;
import com.laptrinhweb.rest.UserRest;
import com.laptrinhweb.service.IProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserRestImpl implements UserRest
{
    private final IProfileService profileService;
    @Override
    public ResponseEntity<ResponseData> getProfile() {
        try {
            return profileService.getMyProfile();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server Is Problem").build());
    }

    @Override
    public ResponseEntity<ResponseData> setProfile(UserProfile userProfile) {
        try {
            return profileService.createProfile(userProfile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server Is Problem").build());
    }

    @Override
    public ResponseEntity<ResponseData> updateProfile(UserProfile userProfile) {
        try {
            return profileService.updateProfile(userProfile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(500).body(ResponseData.builder().message("Server Is Problem").build());
    }
}
