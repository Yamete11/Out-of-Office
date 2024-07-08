package com.example.mockpro.services;

import com.example.mockpro.entities.Status;
import com.example.mockpro.entities.Subdivision;
import com.example.mockpro.repositories.StatusRepository;
import com.example.mockpro.repositories.SubdivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubdivisionService {
    private SubdivisionRepository subdivisionRepository;

    @Autowired
    public SubdivisionService(SubdivisionRepository subdivisionRepository) {
        this.subdivisionRepository = subdivisionRepository;
    }

    public List<Subdivision> getAll(){
        return subdivisionRepository.findAll().stream().toList();
    }
}
