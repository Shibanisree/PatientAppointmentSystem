package com.clinic.PatientAppointmentSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Doctor {
    @Id
    private String id;
    private String name;
    private String specialty;
}