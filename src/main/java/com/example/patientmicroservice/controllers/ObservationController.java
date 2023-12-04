package com.example.patientmicroservice.controllers;


import com.example.patientmicroservice.dtos.CreateObservationDTO;
import com.example.patientmicroservice.dtos.ObservationDTO;
import com.example.patientmicroservice.dtos.PatientDTO;
import com.example.patientmicroservice.entities.Observation;
import com.example.patientmicroservice.entities.Patient;
import com.example.patientmicroservice.services.EncounterService;
import com.example.patientmicroservice.services.ObservationService;
import com.example.patientmicroservice.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/observation")
public class ObservationController {
    @Autowired
    private ObservationService observationService;

    @Autowired
    private EncounterService encounterService;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<ObservationDTO> getAllObservations() {
        List<Observation> observations = observationService.getAllObservations();
        return observations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ObservationDTO getObservationById(@PathVariable Long id) {
        Observation observation = observationService.getObservationById(id);
        return convertToDTO(observation);
    }

    @PostMapping("/")
    public ResponseEntity<ObservationDTO> createObservation(@RequestBody CreateObservationDTO createObservationDTO) {
        Observation observation = new Observation(createObservationDTO.getType(), createObservationDTO.getValue(), patientService.getPatientById(createObservationDTO.getPatientId()), encounterService.getEncounter(createObservationDTO.getEncounterId()));
        observation = observationService.createObservation(observation);
        ObservationDTO observationDTO = new ObservationDTO(observation.getType(), observation.getValue(), new PatientDTO(observation.getPatient().getId(), observation.getPatient().getFirstName(), observation.getPatient().getLastName(), observation.getPatient().getAge()));
        return ResponseEntity.ok(observationDTO);
    }

    @PutMapping("/{id}")
    public Observation updateObservation(@PathVariable Long id, @RequestBody ObservationDTO updatedObservationDTO) {
        Observation updatedObservation = convertToEntity(updatedObservationDTO);
        return observationService.updateObservation(id, updatedObservation);
    }

    @DeleteMapping("/{id}")
    public boolean deleteObservation(@PathVariable Long id) {
        return observationService.deleteObservation(id);
    }

    private ObservationDTO convertToDTO(Observation observation) {
        PatientDTO patientDTO = new PatientDTO(observation.getPatient().getId(), observation.getPatient().getFirstName(), observation.getPatient().getLastName(), observation.getPatient().getAge());
        ObservationDTO dto = new ObservationDTO(observation.getType(), observation.getValue(), patientDTO);
        return dto;
    }

     static Observation convertToEntity(ObservationDTO observationDTO) {
        Observation observation = new Observation(observationDTO.getId(), observationDTO.getType(), observationDTO.getValue(), new Patient(observationDTO.getPatientDTO().getId(), observationDTO.getPatientDTO().getFirstName(), observationDTO.getPatientDTO().getLastName(), observationDTO.getPatientDTO().getAge()));
        return observation;
    }
}
