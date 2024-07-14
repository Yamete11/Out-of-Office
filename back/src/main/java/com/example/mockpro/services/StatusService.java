package com.example.mockpro.services;

import com.example.mockpro.entities.Status;
import com.example.mockpro.entities.User;
import com.example.mockpro.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<Status> getAll(){
        return statusRepository.findAll().stream().toList();
    }
}
