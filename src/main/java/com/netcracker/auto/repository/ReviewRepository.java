package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Anton Popkov
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUsername(String username);
}
