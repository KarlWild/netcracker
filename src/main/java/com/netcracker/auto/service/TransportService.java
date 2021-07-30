package com.netcracker.auto.service;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Transport;
import com.netcracker.auto.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Service
public class TransportService {

    private TransportRepository transportRepository;

    @Autowired
    public TransportService(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    @Transactional
    public List<String> findDistinctBrand() {
        List<String> list = transportRepository.findDistinctBrand();
        list.sort(new TransportService.ComparatorIgnoreCaseAndSpace());
        return list;
    }

    @Transactional
    public List<String> findDistinctModels(String brand) {
        List<String> list = transportRepository.findDistinctModels(brand);
        list.sort(new TransportService.ComparatorIgnoreCaseAndSpace());
        return list;
    }

    @Transactional
    public Map<String, String> getDistinctGenerationsAndLinks(String brand, String model) {
        Map<String, String> generations = new TreeMap<>();
        List<String> list = transportRepository.findDistinctGeneration(brand, model);
        list.forEach(temp -> generations.put(temp, temp.replaceAll("/", "_")));
        return generations;
    }

    @Transactional
    public List<Transport> findByModelAndGeneration(String model, String generationLink) {
        return transportRepository.findByModelAndGeneration(model, generationLink.replaceAll("_", "/"));
    }

    @Transactional
    public Optional<Transport> findById(Integer id) {
        return transportRepository.findById(id);
    }

    private static class ComparatorIgnoreCaseAndSpace implements Comparator<String> {
        @Override
        public int compare(String s, String t1) {
            s = s.replace(" ", "");
            t1 = t1.replace(" ", "");
            return s.compareToIgnoreCase(t1);
        }
    }

}