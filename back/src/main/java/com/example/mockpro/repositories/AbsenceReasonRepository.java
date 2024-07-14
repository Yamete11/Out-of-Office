package com.example.mockpro.repositories;

import com.example.mockpro.entities.AbsenceReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceReasonRepository extends JpaRepository<AbsenceReason, Long> {
}
