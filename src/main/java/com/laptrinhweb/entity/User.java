package com.laptrinhweb.entity;

import com.laptrinhweb.security.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbUser")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String password;
    private boolean isNonClocked;
    private boolean isEnabled;
    private Role role;
    private SexEnum sex;
    private Date dateOfBirth;
    private String address;
    @OneToOne
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private Avatar avatarImg;



}
