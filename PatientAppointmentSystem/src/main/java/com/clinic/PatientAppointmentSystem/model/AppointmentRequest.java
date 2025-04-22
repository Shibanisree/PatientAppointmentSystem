package com.clinic.PatientAppointmentSystem.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private String patientName;
    private String patientEmail;
    private String doctorId;
    private LocalDateTime appointmentTime;
    private String reason;
}
