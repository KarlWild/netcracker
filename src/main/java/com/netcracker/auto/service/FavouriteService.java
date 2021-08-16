package com.netcracker.auto.service;

import com.netcracker.auto.entity.*;
import com.netcracker.auto.repository.AdRepository;
import com.netcracker.auto.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class FavouriteService {
  private FavouriteRepository favouriteRepository;
    private AdRepository adRepository;

    @Autowired
    public FavouriteService (FavouriteRepository favouriteRepository, AdRepository adRepository){
        this.favouriteRepository=favouriteRepository;
        this.adRepository=adRepository;
    }

    @Transactional
    public List<Ad> findFavourite(Favourite ad_id) {
        return favouriteRepository.findFavourite(ad_id);
    }

}
