package com.laptrinhweb.rest;

import com.laptrinhweb.dto.ResponseData;
import com.laptrinhweb.dto.UserProfile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;

@RequestMapping("/api/v1/user/profile")
//@Api(value = "Hotel API", tags = "User Operations")

public interface UserRest {

    @GetMapping("")
//    @ApiOperation(value = "Get User Profile", notes = "Retrieve the user's profile.")
    public ResponseEntity<ResponseData> getProfile();

    @PostMapping("")
//    @ApiOperation(value = "Set User Profile", notes = "Set the user's profile.")
    public  ResponseEntity<ResponseData> setProfile(@RequestBody UserProfile userProfile);

    @PatchMapping("")
//    @ApiOperation(value = "Update User Profile", notes = "Update the user's profile.")
    public  ResponseEntity<ResponseData> updateProfile(@RequestBody UserProfile userProfile);
}
