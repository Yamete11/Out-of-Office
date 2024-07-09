package com.example.mockpro.services;

import com.example.mockpro.entities.RequestStatus;
import com.example.mockpro.repositories.RequestStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestStatusService {
    private final RequestStatusRepository requestStatusRepository;

    @Autowired
    public RequestStatusService(RequestStatusRepository requestStatusRepository) {
        this.requestStatusRepository = requestStatusRepository;
    }

    public List<RequestStatus> getAll(){
        return requestStatusRepository.findAll().stream().toList();
    }
}
