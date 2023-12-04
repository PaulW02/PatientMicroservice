package com.example.patientmicroservice.services;


import com.example.patientmicroservice.entities.Encounter;

import java.time.LocalDate;
import java.util.List;

public interface EncounterService
{
    Encounter createEncounter(LocalDate visitDate, Long patient);

    Encounter getEncounter(Long encounterId);

   // List<Encounter> getPatientEncounters(Long patientId);

    Encounter updateEncounter(Long encounterId, Encounter updatedEncounter);

    void deleteEncounter(Long encounterId);

    List<Encounter> getPatientEncounters(Long patientId);

}
