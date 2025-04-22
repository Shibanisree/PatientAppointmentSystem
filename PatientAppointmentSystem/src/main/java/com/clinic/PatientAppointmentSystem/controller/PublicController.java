package com.clinic.PatientAppointmentSystem.controller;

import com.clinic.PatientAppointmentSystem.model.Appointment;
import com.clinic.PatientAppointmentSystem.model.AppointmentRequest;
import com.clinic.PatientAppointmentSystem.model.Doctor;
import com.clinic.PatientAppointmentSystem.model.Patient;
import com.clinic.PatientAppointmentSystem.service.AppointmentService;
import com.clinic.PatientAppointmentSystem.service.DoctorService;
import com.clinic.PatientAppointmentSystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/doctors")
    public List<Doctor> getDoctors() {
        return doctorService.getAllDoctors();
    }

    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody AppointmentRequest request) {
        Patient patient = new Patient();
        patient.setName(request.getPatientName());
        patient.setEmail(request.getPatientEmail());
        patient = patientService.savePatient(patient);

        Appointment appointment = new Appointment();
        appointment.setPatientId(patient.getId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        return appointmentService.saveAppointment(appointment);
    }
}