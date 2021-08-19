package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.netcracker.auto.entity.Favourite;

import java.util.Date;
import java.util.List;

@Repository
public interface AdRepository extends CrudRepository<Ad, Integer>, JpaSpecificationExecutor<Ad> {

    @Query("select t from Ad t where t.verified = false and t.status = :status")
    List<Ad> findByVerifiedAndStatus(@Param("status") String status);

    @Override
    <S extends Ad> S save(S s);

    @Query("select t from Ad t where t.user_id = :user_id")
    List<Ad> findByUser(@Param("user_id") User user_id);

    @Query("from Ad ad where ad.transport.model like %:keyword% or ad.transport.brand like %:keyword%")
    List<Ad> findByKeyword(@Param("keyword") String keyword);
   // public void update(Ad ad);

    /*@Query("select t from Ad t where t.id = :ad_id")
    List<Ad> findFavourite(@Param("ad_id") Favourite ad_id);*/



}
