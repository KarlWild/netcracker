package com.netcracker.auto.service;

import com.netcracker.auto.entity.Review;
import com.netcracker.auto.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Anton Popkov
 */
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findReviews(String username) {
        return reviewRepository.findAllByUsername(username);
    }
}
