package com.laptrinhweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Table(name = "tb_avatar")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageName;
    @Lob
    private Blob imageData;
    @OneToOne(mappedBy = "avatarImg")
    private User user;
}
