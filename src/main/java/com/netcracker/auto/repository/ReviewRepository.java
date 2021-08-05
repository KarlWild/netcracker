package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Anton Popkov
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUsername(String username);
}
