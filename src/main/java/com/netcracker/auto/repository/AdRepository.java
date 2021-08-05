package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Ad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends CrudRepository<Ad, Integer> {
    @Query("select t from Ad t where t.verified = false and t.status = :status")
    List<Ad> findByVerifiedAndStatus(@Param("status") String status);

    @Override
    <S extends Ad> S save(S s);

}
