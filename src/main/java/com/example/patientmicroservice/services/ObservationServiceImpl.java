package com.example.patientmicroservice.services;

import com.example.patientmicroservice.entities.Observation;
import com.example.patientmicroservice.repositories.ObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObservationServiceImpl implements ObservationService {
    @Autowired
    private ObservationRepository observationRepository;

    public List<Observation> getAllObservations() {
        return observationRepository.findAll();
    }

    public Observation getObservationById(Long id) {
        Optional<Observation> observation = observationRepository.findById(id);
        return observation.orElse(null);
    }

    public Observation createObservation(Observation observation) {
        return observationRepository.save(observation);
    }

    public Observation updateObservation(Long id, Observation updatedObservation) {
        Optional<Observation> existingObservation = observationRepository.findById(id);
        if (existingObservation.isPresent()) {
            updatedObservation.setId(id);
            return observationRepository.save(updatedObservation);
        } else {
            return null;
        }
    }

    public boolean deleteObservation(Long id) {
        Optional<Observation> observation = observationRepository.findById(id);
        if (observation.isPresent()) {
            observationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Observation> getObservationByEncounterId(Long id) {
        return observationRepository.findByEncounterId(id);
    }
}
