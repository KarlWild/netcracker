package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.ComparisonAds;
import com.netcracker.auto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Popkov
 */
@Repository
public interface ComparisonRepository extends CrudRepository<ComparisonAds, Long> {
    List<ComparisonAds> findByUser(User user);


    @Override
    Optional<ComparisonAds> findById(Long aLong);

    Optional<ComparisonAds> findByAdAndUser(Ad ad, User user);

}
