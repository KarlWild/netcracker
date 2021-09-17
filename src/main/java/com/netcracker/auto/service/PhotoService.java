package com.netcracker.auto.service;

import com.netcracker.auto.entity.Photo;
import com.netcracker.auto.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public Photo getNoPhoto() {
        return photoRepository.findByFileName("nophoto.jpg");
    }

}
