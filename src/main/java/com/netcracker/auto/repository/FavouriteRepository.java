package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Favourite;
import com.netcracker.auto.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteRepository extends CrudRepository<Favourite, Integer> {

//    @Query("select t from Ad t where t.id = :ad_id")
//    List<Ad> findFavourite(@Param("ad_id") Favourite ad_id);

    @Query(
            value="select * from ad a where a.ad_id in (select ad_id from favourite_ads where user_id=id)",
            nativeQuery = true)
    List<Ad> findFavourite(@Param("id") User user_id );

//select * from
//         ad a,
//    where ad_id in (select ad_id
//          from favourite_ads
//          where user_id=:userId) 1,6,10,11
}
