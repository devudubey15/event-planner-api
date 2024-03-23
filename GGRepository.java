package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.entity.GG;
import java.time.LocalDate;
import java.util.List;

public interface GGRepository extends MongoRepository<GG, String> {
    @Query("{ 'date': { $gte: ?0, $lte: ?1 } }")
    List<GG> findByDateBetweenAndLocationNear(LocalDate startDate, LocalDate endDate);
}
