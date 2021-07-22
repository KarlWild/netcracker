package com.netcracker.auto.service;

import com.netcracker.auto.entity.Photo;
import com.netcracker.auto.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PhotoService {

    private PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Transactional
    public Photo getNoPhoto() {
        return photoRepository.findByFileName("nophoto.jpg");
    }

}
