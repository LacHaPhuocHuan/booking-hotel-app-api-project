package com.laptrinhweb.rest;

import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.dto.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;

@RequestMapping("/api/v1/user/profile")
public interface UserRest {

    @GetMapping("")
    public ResponseEntity<ResponseData> getProfile();

    @PostMapping("")
    public  ResponseEntity<ResponseData> setProfile(@RequestBody UserProfile userProfile);

    @PatchMapping("")
    public  ResponseEntity<ResponseData> updateProfile(@RequestBody UserProfile userProfile);
}
