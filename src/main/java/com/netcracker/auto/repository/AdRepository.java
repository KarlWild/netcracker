package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    @Query("select t from Ad t where t.verified = false and t.status LIKE CONCAT('%',:status,'%')")
    List<Ad> findByVerifiedAndStatus(@Param("status") String status);
    @Override
    <S extends Ad> S save(S s);
}
