package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Ad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends CrudRepository<Ad, Integer> {

}
