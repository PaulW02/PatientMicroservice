package com.example.patientmicroservice.controllers;



import com.example.patientmicroservice.dtos.CreateEncounterDTO;
import com.example.patientmicroservice.dtos.EncounterDTO;
import com.example.patientmicroservice.dtos.ObservationDTO;
import com.example.patientmicroservice.dtos.PatientDTO;
import com.example.patientmicroservice.entities.Encounter;
import com.example.patientmicroservice.entities.Observation;
import com.example.patientmicroservice.entities.Patient;
import com.example.patientmicroservice.services.EncounterService;
import com.example.patientmicroservice.services.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/encounter")
public class EncounterController {
    @Autowired
    private EncounterService encounterService;
    @Autowired
    private ObservationService observationService;

    @PostMapping
    public ResponseEntity<EncounterDTO> createEncounter(@RequestBody CreateEncounterDTO createEncounterDTO) {
        Encounter encounter = encounterService.createEncounter(LocalDate.now(), createEncounterDTO.getPatientId());
        EncounterDTO newEncounter = new EncounterDTO(encounter.getId(), encounter.getVisitDate(),new PatientDTO(encounter.getPatient().getId(), encounter.getPatient().getFirstName(), encounter.getPatient().getLastName(), encounter.getPatient().getAge()));
        return ResponseEntity.ok(newEncounter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncounterDTO> getEncounterbyId(@PathVariable Long id) {
        Encounter encounter = encounterService.getEncounter(id);
        EncounterDTO encounterDTO = new EncounterDTO(encounter.getVisitDate(),new PatientDTO(encounter.getPatient().getId(),encounter.getPatient().getFirstName(),encounter.getPatient().getLastName(),encounter.getPatient().getAge()));
        return ResponseEntity.ok(encounterDTO);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<EncounterDTO>> getPatientEncounters(@PathVariable Long patientId) {
        List<Encounter> patientEncounters = encounterService.getPatientEncounters(patientId);
        List<EncounterDTO> returnValue = new ArrayList<>();
        for (int i = 0; i < patientEncounters.size(); i++)
        {
            LocalDate visitDate = patientEncounters.get(i).getVisitDate();
            Patient p = patientEncounters.get(i).getPatient();
            PatientDTO patientDTO = new PatientDTO(p.getId(),p.getFirstName(),p.getLastName(),p.getAge());
            returnValue.add( new  EncounterDTO(visitDate,patientDTO));
        }
        return ResponseEntity.ok(returnValue);
    }


    @PutMapping("/{encounterId}")
    public ResponseEntity<Encounter> updateEncounter(@PathVariable Long encounterId, @RequestBody EncounterDTO updatedEncounter) {
        Encounter encounter = new Encounter(updatedEncounter.getId(),updatedEncounter.getVisitDate(),new Patient(updatedEncounter.getPatientDTO().getId(),updatedEncounter.getPatientDTO().getFirstName(),updatedEncounter.getPatientDTO().getLastName(),updatedEncounter.getPatientDTO().getAge()));
        Encounter updated = encounterService.updateEncounter(encounterId, encounter);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{encounterId}")
    public void deleteEncounter(@PathVariable Long encounterId) {
      encounterService.deleteEncounter(encounterId);
    }

    @GetMapping("/{id}/observations")
    public ResponseEntity<List<ObservationDTO>> getObservationDTOByEncounterId(@PathVariable Long id) {


        List<Observation> observations = observationService.getObservationByEncounterId(id);
        List<ObservationDTO> dtos = new ArrayList<>();

        for (int i = 0; i < observations.size(); i++) {
            PatientDTO patientDTO = new PatientDTO(observations.get(i).getPatient().getId(), observations.get(i).getPatient().getFirstName(), observations.get(i).getPatient().getLastName(), observations.get(i).getPatient().getAge());
            ObservationDTO dto = new ObservationDTO(observations.get(i).getType(), observations.get(i).getValue(), patientDTO);
            dtos.add(dto);
        }

        //dtos.add(new ObservationDTO("cancer",23,new PatientDTO(1,"george","bahadi",)))
        return ResponseEntity.ok(dtos);
    }

}
