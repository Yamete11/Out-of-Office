package com.example.mockpro.repositories;

import com.example.mockpro.entities.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long> {
    RequestStatus findByTitle(String title);
}
