package com.netcracker.auto.service;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Ad_;
import com.netcracker.auto.entity.Transport_;
import com.netcracker.auto.entity.User;
import com.netcracker.auto.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AdService {

    private AdRepository adRepository;

    @Autowired
    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @Transactional
    public Optional<Ad> findById(int id) {
        return adRepository.findById(id);
    }

    @Transactional
    public List<Ad> findByUser(User user) {
        return adRepository.findByUser(user);
    }

    @Transactional
    public List<Ad> findAll() {
        return (List<Ad>) adRepository.findAll();
    }

    @Transactional
    public List<Ad> findUnVerified() {
        List<Ad> list = adRepository.findByVerifiedAndStatus("open");
        return list;
    }

    public void saveAd(Ad ad) {
        adRepository.save(ad);
    }

    public List<Ad> findByKeyword(String keyword) {
        return adRepository.findByKeyword(keyword);
    }

    public List<Ad> getAdsByNameAndYears(String name, Integer yearStart, Integer yearEnd) {
        return adRepository.findAll(Specification.where(
                nameLike(name)).and(yearBetween(parseDate(yearStart), parseDate(yearEnd))));
    }

    public List<Ad> getAdsBetweenYears(Integer yearStart, Integer yearEnd) {
        return adRepository.findAll(Specification.where(yearBetween(parseDate(yearStart), parseDate(yearEnd))));
    }

    private Date parseDate(Integer year) {
        Date date = null;
        if (year != 0) {
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-01-01");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        assert date != null;
        return date;
    }

    private Specification<Ad> brandLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(Ad_.TRANSPORT).get(Transport_.BRAND), "%" + name + "%");
    }

    private Specification<Ad> modelLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(Ad_.TRANSPORT).get(Transport_.MODEL), "%" + name + "%");
    }

    private Specification<Ad> nameLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get(Ad_.TRANSPORT).get(Transport_.MODEL), "%" + name + "%"),
                        criteriaBuilder.like(root.get(Ad_.TRANSPORT).get(Transport_.BRAND), "%" + name + "%")
                );
    }

    private Specification<Ad> yearBetween(Date yearStart, Date yearEnd) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.between(root.get(Ad_.YEAR_OF_ISSUE), yearStart, yearEnd);
    }

    /*public List<Ad> findByLikeCriteria(String text, Integer yearStart, Integer yearEnd) {
        return adRepository.findAll((Specification<Ad>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (text != null) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("transport_id").get("brand"), "%" + text + "%"),
                        criteriaBuilder.like(root.get("transport_id").get("model"), "%" + text + "%")));
            }
            if (yearStart != 0 && yearEnd != 0) {
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                //LocalDate dateEnd = LocalDate.parse(yearEnd + "-01-01", formatter);
                Date dateStart = null, dateEnd = null;
                try {
                    dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(yearStart + "-01-01");
                    dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(yearEnd + "-01-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                predicates.add(criteriaBuilder.between(root.get("yearOfIssue"), dateStart, dateEnd));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }*/
}
