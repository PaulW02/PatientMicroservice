package com.example.patientmicroservice.controllers;


import com.example.patientmicroservice.dtos.*;
import com.example.patientmicroservice.entities.Condition;
import com.example.patientmicroservice.entities.Encounter;
import com.example.patientmicroservice.entities.Observation;
import com.example.patientmicroservice.entities.Patient;
import com.example.patientmicroservice.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    // Inject your service or repository here
    @Autowired
    private PatientService patientService;

    // Define your REST endpoints
    @GetMapping("/")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient patient: patients) {
            patientDTOS.add(new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getAge()));
        }
        return ResponseEntity.ok(patientDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            PatientDTO patientDTO = new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getAge());
            return ResponseEntity.ok(patientDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patient) {
        Patient createdPatient = patientService.createPatient(patient.getFirstName(), patient.getLastName(), patient.getAge());
        PatientDTO createdPatientDTO = new PatientDTO(createdPatient.getId(), createdPatient.getFirstName(), createdPatient.getLastName(), createdPatient.getAge());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatientDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        boolean deleted = patientService.deletePatient(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<PatientDetailsDTO> getPatientByUserId(@PathVariable Long id)
    {
       Patient p =  patientService.getPatientByUserId(id);
        List<Condition> conditions = p.getConditions();
        List<Observation> observations = p.getObservations();
        List<Encounter> encounters = p.getEncounters();
        List<ConditionDTO> conditionDTOs = new ArrayList<>();
        for (Condition condition : conditions) {
            conditionDTOs.add(ConditionDTO.fromEntity(condition));
        }

        List<ObservationDTO> observationDTOs = new ArrayList<>();
        for (Observation observation : observations) {
            observationDTOs.add(ObservationDTO.fromEntity(observation));
        }

        List<EncounterDTO> encounterDTOs = new ArrayList<>();
        for (Encounter encounter : encounters) {
            encounterDTOs.add(EncounterDTO.fromEntity(encounter));
        }
       PatientDetailsDTO patientDetailsDTO = new PatientDetailsDTO(p.getId(), conditionDTOs, observationDTOs, encounterDTOs, p.getFirstName(), p.getLastName(), p.getAge());

       return ResponseEntity.ok(patientDetailsDTO);
    }
    @GetMapping("/search")
    public ResponseEntity<List<PatientDTO>> findPatientByFirstNameAndLastName(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        List<Patient> patients = patientService.getPatientByFirstNameAndLastName(firstName, lastName);
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient patient: patients) {
            patientDTOS.add(new PatientDTO(patient.getId(),patient.getFirstName(), patient.getLastName(), patient.getAge()));
        }
        if (patientDTOS != null) {
            return ResponseEntity.ok(patientDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<PatientDetailsDTO> getPatientDetails(@PathVariable Long id) {

        PatientDetailsDTO patientDetails = patientService.getPatientDetailsById(id);
        if (patientDetails != null) {
            return ResponseEntity.ok(patientDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
