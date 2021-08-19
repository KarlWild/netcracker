package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Anton Popkov
 */
@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findAllByUsername(String username);
}
