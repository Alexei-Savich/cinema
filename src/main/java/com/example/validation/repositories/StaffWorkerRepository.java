package com.example.validation.repositories;

import com.example.validation.entities.StaffWorker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffWorkerRepository extends JpaRepository<StaffWorker, Long> {
    Optional<StaffWorker> findByEmail(String email);
}
