package com.clinic.PatientAppointmentSystem.repository;

import com.clinic.PatientAppointmentSystem.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
}