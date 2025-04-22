package com.clinic.PatientAppointmentSystem.service;

import com.clinic.PatientAppointmentSystem.model.Doctor;
import com.clinic.PatientAppointmentSystem.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor saveDoctor(Doctor doctor) {
        if (doctor.getId() == null) {
            doctor.setId(UUID.randomUUID().toString());
        }
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
