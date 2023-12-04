package com.example.patientmicroservice.services;


import com.example.patientmicroservice.entities.Encounter;
import com.example.patientmicroservice.entities.Patient;
import com.example.patientmicroservice.repositories.EncounterRepository;
import com.example.patientmicroservice.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EncounterServiceImpl implements EncounterService {
    @Autowired
    private EncounterRepository encounterRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Encounter createEncounter(LocalDate visitDate, Long patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        Encounter encounter = new Encounter(visitDate, patient.get());
        return encounterRepository.save(encounter);
    }

    @Override
    public Encounter getEncounter(Long encounterId) {
        Optional<Encounter> encounter = encounterRepository.findById(encounterId);
        return encounter.orElse(null);
    }
    /*
    @Override
    public List<Encounter> getPatientAll(Long patientId) {
        // Implementera hämtning av alla möten för en specifik patient
        // Du kan använda encounterRepository för att hämta dessa möten
        return encounterRepository.findByPatientId(patientId);
    }*/

    @Override
    public Encounter updateEncounter(Long encounterId, Encounter updatedEncounter) {
        Optional<Encounter> existingEncounter = encounterRepository.findById(encounterId);
        if (existingEncounter.isPresent()) {
            updatedEncounter.setId(encounterId);
            return encounterRepository.save(updatedEncounter);
        } else {
            return null;
        }
    }

    @Override
    public void deleteEncounter(Long encounterId) {
        encounterRepository.deleteById(encounterId);
    }



    @Override
    public List<Encounter> getPatientEncounters(Long patientId) {
        return null;
    }




}


