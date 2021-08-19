package com.netcracker.auto.service;

import com.netcracker.auto.entity.*;
import com.netcracker.auto.repository.AdRepository;
import com.netcracker.auto.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService {

  private FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteService (FavouriteRepository favouriteRepository){
        this.favouriteRepository=favouriteRepository;
    }

    @Transactional
    public List<Ad> findFavourite(User user_id) {
        return favouriteRepository.findFavourite(user_id);
    }

}
