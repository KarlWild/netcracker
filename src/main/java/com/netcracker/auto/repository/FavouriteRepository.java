package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Favourite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteRepository extends CrudRepository<Favourite, Integer> {

    @Query("select t from Ad t where t.id = :ad_id")
    List<Ad> findFavourite(@Param("ad_id") Favourite ad_id);

}
