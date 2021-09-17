package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Anton Popkov
 */
@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findAllByUsername(String username);

    //SELECT ROUND(AVG(CAST(r.RATING as decimal(2,1))), 1) FROM REVIEWS r WHERE USERNAME = 'anton@mail.ru'
    @Query(value =
            "SELECT ROUND(AVG(CAST(r.rating as decimal(2,1))), 1) FROM REVIEWS r WHERE r.username = ?1",
            nativeQuery = true)
    Double averageRatingByUsername(String name);
}
