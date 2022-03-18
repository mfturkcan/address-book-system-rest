package com.mfturkcan.addressbooksystemrest.repositories;

import com.mfturkcan.addressbooksystemrest.models.TimeTablePart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimeTablePartRepository extends JpaRepository<TimeTablePart, Integer> {
    Optional<List<TimeTablePart>> findByBookUserId(Integer integer);
}