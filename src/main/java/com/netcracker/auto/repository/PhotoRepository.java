package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    public Photo findByFileName(String fileName);
}
