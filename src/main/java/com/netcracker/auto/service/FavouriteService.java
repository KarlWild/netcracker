package com.netcracker.auto.service;

import com.netcracker.auto.entity.*;
import com.netcracker.auto.repository.AdRepository;
import com.netcracker.auto.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteService {

  private FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteService (FavouriteRepository favouriteRepository){
        this.favouriteRepository=favouriteRepository;
    }

    @Transactional
    public List<Ad> findFavourites(User user_id) {
        List<Favourite> list =favouriteRepository.findFavouriteByUser_id(user_id);
        List<Ad> listOfAds = list.stream().map(Favourite::getAd).collect(Collectors.toList());
        //List<Long> listOfIds = listOfUsers.stream().map(User::getUserId).collect(Collectors.toList());
        // List<Ad> ads=favouriteRepository.findFavourite(listOfAds);
         return listOfAds;
    }

    @Transactional
    public Favourite findFavourite(User user, Ad ad){
        return favouriteRepository.findFavourite(user, ad);
    }

}
