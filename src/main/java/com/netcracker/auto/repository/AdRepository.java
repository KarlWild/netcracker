package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Long> {

}
