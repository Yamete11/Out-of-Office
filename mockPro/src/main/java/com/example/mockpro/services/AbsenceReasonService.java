package com.example.mockpro.services;

import com.example.mockpro.dto.AbsenceReasonDTO;
import com.example.mockpro.entities.AbsenceReason;
import com.example.mockpro.repositories.AbsenceReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbsenceReasonService {

    private final AbsenceReasonRepository absenceReasonRepository;

    @Autowired
    public AbsenceReasonService(AbsenceReasonRepository absenceReasonRepository) {
        this.absenceReasonRepository = absenceReasonRepository;
    }

    public List<AbsenceReasonDTO> getAllAbsenceReasons() {
        return absenceReasonRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AbsenceReasonDTO getAbsenceReasonById(Long id) {
        AbsenceReason absenceReason = absenceReasonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Absence Reason not found"));
        return convertToDTO(absenceReason);
    }

    public AbsenceReasonDTO createAbsenceReason(AbsenceReasonDTO absenceReasonDTO) {
        AbsenceReason absenceReason = convertToEntity(absenceReasonDTO);
        return convertToDTO(absenceReasonRepository.save(absenceReason));
    }

    public AbsenceReasonDTO updateAbsenceReason(Long id, AbsenceReasonDTO absenceReasonDTO) {
        AbsenceReason existingAbsenceReason = absenceReasonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Absence Reason not found"));
        existingAbsenceReason.setTitle(absenceReasonDTO.getTitle());
        return convertToDTO(absenceReasonRepository.save(existingAbsenceReason));
    }

    public void deleteAbsenceReason(Long id) {
        absenceReasonRepository.deleteById(id);
    }

    private AbsenceReasonDTO convertToDTO(AbsenceReason absenceReason) {
        AbsenceReasonDTO dto = new AbsenceReasonDTO();
        dto.setId(absenceReason.getId());
        dto.setTitle(absenceReason.getTitle());
        return dto;
    }

    private AbsenceReason convertToEntity(AbsenceReasonDTO dto) {
        AbsenceReason absenceReason = new AbsenceReason();
        absenceReason.setTitle(dto.getTitle());
        return absenceReason;
    }
}
