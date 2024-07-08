package com.example.mockpro.services;

import com.example.mockpro.entities.Position;
import com.example.mockpro.entities.Subdivision;
import com.example.mockpro.repositories.PositionRepository;
import com.example.mockpro.repositories.SubdivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    private PositionRepository positionRepository;

    @Autowired
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> getAll(){
        return positionRepository.findAll().stream().toList();
    }
}
