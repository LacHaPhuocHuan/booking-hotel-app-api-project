package com.laptrinhweb.entity;

import com.laptrinhweb.security.TokenType;
import jakarta.persistence.*;
import lombok.*;

import static com.laptrinhweb.security.TokenType.BEARER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbJwtToken")
@NamedQuery(name = "JwtToken.findAllByUserId", query = " select t from JwtToken t join t.user u where u.id=:user_id and" +
        " ( t.isNonExpired=true or  t.isEnabled = true) ")
public class JwtToken {
    @Id
    @GeneratedValue()
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(length = 10000)
    private String token;
    private boolean isNonExpired;
    private boolean isEnabled;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType=BEARER;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
