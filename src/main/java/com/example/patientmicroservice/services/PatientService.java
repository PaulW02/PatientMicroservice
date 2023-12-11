package com.example.patientmicroservice.services;

import com.example.patientmicroservice.dtos.PatientDetailsDTO;
import com.example.patientmicroservice.entities.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();

    Patient getPatientById(Long id);

    Patient createPatient(String firstName, String lastName, int age, Long userId);

    Patient updatePatient(Long id, Patient updatedPatient);

    boolean deletePatient(Long id);

    List<Patient> getPatientByFirstNameAndLastName(String firstName, String lastName);

    PatientDetailsDTO getPatientDetailsById(Long id);
    Patient getPatientByUserId(Long id);



}


