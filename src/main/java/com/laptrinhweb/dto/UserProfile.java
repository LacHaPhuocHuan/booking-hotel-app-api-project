package com.laptrinhweb.dto;

import com.laptrinhweb.entity.SexEnum;
import com.laptrinhweb.security.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private SexEnum sex;
    private Date dateOfBirth;
    private String avatarName;
    private byte[] avatar;
    private String address;

}
