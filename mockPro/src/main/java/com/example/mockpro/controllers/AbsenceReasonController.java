package com.example.mockpro.controllers;

import com.example.mockpro.dto.AbsenceReasonDTO;
import com.example.mockpro.entities.AbsenceReason;
import com.example.mockpro.services.AbsenceReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/absenceReasons")
public class AbsenceReasonController {

    private final AbsenceReasonService absenceReasonService;

    @Autowired
    public AbsenceReasonController(AbsenceReasonService absenceReasonService) {
        this.absenceReasonService = absenceReasonService;
    }

    @GetMapping
    public ResponseEntity<List<AbsenceReasonDTO>> getAllAbsenceReasons() {
        return new ResponseEntity<>(absenceReasonService.getAllAbsenceReasons(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceReasonDTO> getAbsenceReasonById(@PathVariable Long id) {
        return new ResponseEntity<>(absenceReasonService.getAbsenceReasonById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AbsenceReasonDTO> createAbsenceReason(@RequestBody AbsenceReasonDTO absenceReasonDTO) {
        return new ResponseEntity<>(absenceReasonService.createAbsenceReason(absenceReasonDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbsenceReasonDTO> updateAbsenceReason(@PathVariable Long id, @RequestBody AbsenceReasonDTO absenceReasonDTO) {
        return new ResponseEntity<>(absenceReasonService.updateAbsenceReason(id, absenceReasonDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsenceReason(@PathVariable Long id) {
        absenceReasonService.deleteAbsenceReason(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
