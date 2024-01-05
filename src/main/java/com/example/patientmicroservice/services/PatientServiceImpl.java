package com.example.patientmicroservice.services;


import com.example.patientmicroservice.dtos.ConditionDTO;
import com.example.patientmicroservice.dtos.EncounterDTO;
import com.example.patientmicroservice.dtos.ObservationDTO;
import com.example.patientmicroservice.dtos.PatientDetailsDTO;
import com.example.patientmicroservice.entities.Condition;
import com.example.patientmicroservice.entities.Encounter;
import com.example.patientmicroservice.entities.Observation;
import com.example.patientmicroservice.entities.Patient;
import com.example.patientmicroservice.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;


    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElse(null);
    }

    public Patient createPatient(String firstName, String lastName, int age, String userId) {
        Patient patient = new Patient(firstName, lastName, age, userId);
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Optional<Patient> existingPatient = patientRepository.findById(id);
        if (existingPatient.isPresent()) {
            updatedPatient.setId(id);
            return patientRepository.save(updatedPatient);
        } else {
            return null;
        }
    }

    public boolean deletePatient(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Patient> getPatientByFirstNameAndLastName(String firstName,String lastName)
    {
        return patientRepository.findByFirstNameAndLastName(firstName,lastName);
    }

    public Patient getPatientByUserId(String id)
    {
        return patientRepository.getPatientByUserId(id);
    }
    public PatientDetailsDTO getPatientDetailsById(Long id) {
        Patient patient = this.getPatientById(id);
        if (patient != null) {
            List<Condition> conditions = patient.getConditions();
            List<Observation> observations = patient.getObservations();
            List<Encounter> encounters = patient.getEncounters();
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

            return new PatientDetailsDTO(
                    patient.getId(),
                    conditionDTOs,
                    observationDTOs,
                    encounterDTOs,
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getAge()
            );
        }
        return null;
    }
}
