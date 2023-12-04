package com.example.patientmicroservice.controllers;


import com.example.patientmicroservice.dtos.ConditionDTO;
import com.example.patientmicroservice.dtos.CreateConditionDTO;
import com.example.patientmicroservice.dtos.PatientDTO;
import com.example.patientmicroservice.entities.Condition;
import com.example.patientmicroservice.entities.Patient;
import com.example.patientmicroservice.services.ConditionService;
import com.example.patientmicroservice.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/condition")
public class ConditionController {
    @Autowired
    private ConditionService conditionService;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<ConditionDTO> getAllConditions() {
        List<Condition> conditions = conditionService.getAllConditions();
        return conditions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ConditionDTO getConditionById(@PathVariable Long id) {
        Condition condition = conditionService.getConditionById(id);
        return convertToDTO(condition);
    }

    @PostMapping("/")
    public ResponseEntity<ConditionDTO> createCondition(@RequestBody CreateConditionDTO createConditionDTO) {
        Condition condition = new Condition();
        condition.setConditionName(createConditionDTO.getCondition());
        condition.setPatient(patientService.getPatientById(createConditionDTO.getPatientId()));
        condition = conditionService.createCondition(condition);
        ConditionDTO conditionDTO = new ConditionDTO(condition.getId(), condition.getConditionName(), new PatientDTO(condition.getPatient().getId(), condition.getPatient().getFirstName(), condition.getPatient().getLastName(), condition.getPatient().getAge()));
        return ResponseEntity.ok(conditionDTO);
    }

    @PutMapping("/{id}")
    public Condition updateCondition(@PathVariable Long id, @RequestBody ConditionDTO updatedConditionDTO) {
        Condition updatedCondition = convertToEntity(updatedConditionDTO);
        return conditionService.updateCondition(id, updatedCondition);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCondition(@PathVariable Long id) {
        return conditionService.deleteCondition(id);
    }

    private ConditionDTO convertToDTO(Condition condition) {
        PatientDTO patientDTO = new PatientDTO(condition.getPatient().getId(), condition.getPatient().getFirstName(), condition.getPatient().getLastName(), condition.getPatient().getAge());
        ConditionDTO dto = new ConditionDTO(condition.getId(), condition.getConditionName(), patientDTO);
        return dto;
    }

    private Condition convertToEntity(ConditionDTO conditionDTO) {
        Condition condition = new Condition(conditionDTO.getId(), conditionDTO.getConditionName(), new Patient(conditionDTO.getPatient().getId(), conditionDTO.getPatient().getFirstName(), conditionDTO.getPatient().getLastName(), conditionDTO.getPatient().getAge()));
        return condition;
    }
}

