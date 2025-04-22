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
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/doctors")
    public List<Doctor> getDoctors() {
        return doctorService.getAllDoctors();
    }

    @PostMapping("/doctors")
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    @GetMapping("/appointments")
    public List<Appointment> getAppointments(@RequestParam String doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    @PostMapping("/appointments")
    public Appointment addAppointment(@RequestBody AppointmentRequest request) {
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

    @PutMapping("/appointments/{id}")
    public Appointment updateAppointment(@PathVariable String id, @RequestBody AppointmentRequest request) {
        Long appointmentId;
        try {
            appointmentId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid appointment ID format", e);
        }
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new RuntimeException("Appointment not found");
        }
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        return appointmentService.saveAppointment(appointment);
    }

    @DeleteMapping("/appointments/{id}")
    public void deleteAppointment(@PathVariable String id) {
        Long appointmentId;
        try {
            appointmentId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid appointment ID format", e);
        }
        appointmentService.deleteAppointment(appointmentId);
    }

    @GetMapping("/patients/{id}")
    public Patient getPatient(@PathVariable String id) {
        return patientService.getPatientById(id);
    }

    @PutMapping("/patients/{id}")
    public Patient updatePatient(@PathVariable String id, @RequestBody Patient patient) {
        patient.setId(id);
        return patientService.savePatient(patient);
    }

    @DeleteMapping("/patients/{id}")
    public void deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
    }
}