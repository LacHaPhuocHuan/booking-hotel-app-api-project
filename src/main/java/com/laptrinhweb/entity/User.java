package com.laptrinhweb.entity;

import com.laptrinhweb.security.Role;
import jakarta.persistence.*;
import lombok.*;

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


}
