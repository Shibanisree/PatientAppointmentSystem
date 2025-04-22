package com.clinic.PatientAppointmentSystem.service;

import com.clinic.PatientAppointmentSystem.model.Patient;
import com.clinic.PatientAppointmentSystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient savePatient(Patient patient) {
        if (patient.getId() == null) {
            patient.setId(UUID.randomUUID().toString());
        }
        return patientRepository.save(patient);
    }

    public Patient getPatientById(String id) {
        return patientRepository.findById(id).orElse(null);
    }

    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }
}
