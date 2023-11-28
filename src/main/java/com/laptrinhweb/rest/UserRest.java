package com.laptrinhweb.rest;

import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.dto.UserProfile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.PanelUI;
import java.sql.Blob;

@RequestMapping("/api/v1/user/profile")

public interface UserRest {

    @GetMapping("")
    public ResponseEntity<ResponseData> getProfile();

    @PostMapping("")
    public  ResponseEntity<ResponseData> setProfile(
            @RequestBody UserProfile userProfile
            );

    @PostMapping("/avatar")
    public  ResponseEntity<ResponseData> setAvatar(
            @RequestParam("image")MultipartFile file
            );

    @GetMapping("/avatar")
    public ResponseEntity<byte[]> getAvartar();

    @PatchMapping("")
    public  ResponseEntity<ResponseData> updateProfile(@RequestBody UserProfile userProfile);
}
