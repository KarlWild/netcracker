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
        List<Ad> list = adRepository.findByVerifiedAndStatus("неактивно");
        return list;
    }
    @Transactional
    public List<Object[]> findAllAddresses(){
        return adRepository.findAllAddresses();
    }

    public void saveAd(Ad ad) {
        adRepository.save(ad);
    }

    public void updateAd(int adId, Ad updatedAd){
        Ad ad=findById(adId).get();
        ad.setAddress(updatedAd.getAddress());
        ad.setColor(updatedAd.getColor());
        ad.setDescription(updatedAd.getDescription());
        ad.setDriveUnit(updatedAd.getDriveUnit());
        ad.setMileage(updatedAd.getMileage());
        ad.setNumberOfOwners(updatedAd.getNumberOfOwners());
        ad.setPrice(updatedAd.getPrice());
        ad.setStateNumber(updatedAd.getStateNumber());
        ad.setSts(updatedAd.getSts());
        ad.setVin(updatedAd.getVin());
        ad.setYearOfIssue(updatedAd.getYearOfIssue());

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

    public List<Ad> findByAnyCriteria(String brand, String model,
                                   Integer yearStart, Integer yearEnd,
                                   Integer priceStart, Integer priceEnd,
                                   Integer mileageStart, Integer mileageEnd,
                                   Integer ownersCount) {
        Date dateStart = yearStart == null ? null : parseDate(yearStart);
        Date dateEnd = yearEnd == null ? null : parseDate(yearEnd);

        Integer finalPriceStart = priceStart == null ? 0 : priceStart;
        Integer finalPriceEnd = priceEnd == null ? Integer.MAX_VALUE : priceEnd;
        Integer finalMileageStart = mileageStart == null ? 0 : mileageStart;
        Integer finalMileageEnd = mileageEnd == null ? Integer.MAX_VALUE : mileageEnd;


        Specification<Ad> specification = Specification
                .where(brand == null ? null : brandLike(brand))
                .and(model == null ? null : modelLike(model))
                .and(yearBetween(dateStart, dateEnd))
                .and(priceBetween(finalPriceStart, finalPriceEnd))
                .and(mileageBetween(finalMileageStart, finalMileageEnd))
                .and(ownersCount == null ? null : ownersEqual(ownersCount));
        return adRepository.findAll(specification);
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

    private static Specification<Ad> brandLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(Ad_.TRANSPORT).get(Transport_.BRAND), "%" + name + "%");
    }

    private static Specification<Ad> modelLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(Ad_.TRANSPORT).get(Transport_.MODEL), "%" + name + "%");
    }

    private static Specification<Ad> nameLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get(Ad_.TRANSPORT).get(Transport_.MODEL), "%" + name + "%"),
                        criteriaBuilder.like(root.get(Ad_.TRANSPORT).get(Transport_.BRAND), "%" + name + "%")
                );
    }

    private static Specification<Ad> yearBetween(Date yearStart, Date yearEnd) {
        Date finalYearStart = yearStart != null ? yearStart
                : new GregorianCalendar(1950, Calendar.JANUARY, 1).getTime();;
        Date finalYearEnd = yearEnd != null ? yearEnd
                : new GregorianCalendar().getTime();

        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.between(root.get(Ad_.YEAR_OF_ISSUE), finalYearStart, finalYearEnd);
    }

    private static Specification<Ad> priceBetween(Integer priceStart, Integer priceEnd) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.between(root.get(Ad_.PRICE), priceStart, priceEnd);
    }

    private static Specification<Ad> mileageBetween(Integer mileageStart, Integer mileageEnd) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.between(root.get(Ad_.MILEAGE), mileageStart, mileageEnd);
    }

    private static Specification<Ad> ownersEqual(Integer count) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Ad_.NUMBER_OF_OWNERS), count);
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
