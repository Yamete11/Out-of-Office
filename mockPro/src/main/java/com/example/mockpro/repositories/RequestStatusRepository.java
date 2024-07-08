package com.example.mockpro.repositories;

import com.example.mockpro.entities.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long> {
}
