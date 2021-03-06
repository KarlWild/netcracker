package com.netcracker.auto.repository;

import com.netcracker.auto.entity.Ad;
import com.netcracker.auto.entity.Transport;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {

    @Query("select distinct t.brand from Transport t")
    List<String> findDistinctBrand();

    @Query("select distinct t.model from Transport t where t.brand = :brand")
    List<String> findDistinctModels(@Param("brand") String brand);

    @Query("select distinct CONCAT(t.generationYears, ' ', t.generationName) from Transport t where t.brand = :brand and t.model = :model")
    List<String> findDistinctGeneration(@Param("brand") String brand, @Param("model") String model);

    @Query("select t from Transport t where t.model = :model and CONCAT(t.generationYears, ' ', t.generationName) =:generation")
    List<Transport> findByModelAndGeneration(@Param("model") String model, @Param("generation") String generation);


    @Query("select t.transportId from Transport t where t.model = :model and t.brand = :brand and t.generationYears = :generationYears and t.generationName=:generationName")
    int findUniqueId(@Param("model") String model, @Param("brand")  String brand, @Param("generation") String generation);

}