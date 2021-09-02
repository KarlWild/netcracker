package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Favourite;
import com.netcracker.auto.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends CrudRepository<Favourite, Integer> {

//    @Query("select t from Ad t where t.id = :ad_id")
//    List<Ad> findFavourite(@Param("ad_id") Favourite ad_id);

//    @Query("select a from Ad a where a.ad_id in (select t.ad_id from favourite_ads t where t.user_id=:id)")

//    @Query(
//            value="select * from ad a where a in :ids",
//            nativeQuery = true)
   // List<Ad> findFavourite(@Param("ids") List<Ad> user_id);

    @Query("select a from Favourite a where a.user_id = :id")
    List<Favourite> findFavouriteByUser_id(@Param("id") User user_id );

    @Query("select a from Favourite a where a.user_id = :id and a.ad_id=:adId")
    Favourite findFavourite(@Param("id") User user_id, @Param("adId") Ad ad);
//select * from
//         ad a,
//    where ad_id in (select ad_id
//          from favourite_ads
//          where user_id=:userId) 1,6,10,11
}
