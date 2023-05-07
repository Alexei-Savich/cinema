package com.example.validation.repositories;

import com.example.validation.entities.StaffWorker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StaffWorkerRepository extends JpaRepository<StaffWorker, Long> {
    Optional<StaffWorker> findByEmail(String email);

    @Query("SELECT s FROM StaffWorker s WHERE " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(s.surname) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<StaffWorker> searchWorkers(@Param("query") String query, Pageable pageable);
}
