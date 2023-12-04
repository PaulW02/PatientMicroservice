package com.example.patientmicroservice.services;


import com.example.patientmicroservice.entities.Observation;

import java.util.List;

public interface ObservationService {
    List<Observation> getAllObservations();
    Observation getObservationById(Long id);
    Observation createObservation(Observation observation);
    Observation updateObservation(Long id, Observation updatedObservation);
    boolean deleteObservation(Long id);
    List<Observation> getObservationByEncounterId(Long id);


}
