package com.laptrinhweb.repository;

import com.laptrinhweb.entity.Avatar;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
@ReadingConverter
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
