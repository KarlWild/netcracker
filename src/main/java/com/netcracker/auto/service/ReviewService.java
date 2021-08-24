package com.netcracker.auto.service;

import com.netcracker.auto.entity.Review;
import com.netcracker.auto.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anton Popkov
 */
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findReviews(String username) {
        return reviewRepository.findAllByUsername(username);
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public Double calculateSellerRatingByUsername(String username) {
        return reviewRepository.averageRatingByUsername(username);
    }
}
