package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    public Photo findByFileName(String fileName);
}
