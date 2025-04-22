document.addEventListener('DOMContentLoaded', () => {
    const doctorSelect = document.getElementById('adminDoctorId');
    const doctorFilter = document.getElementById('doctorFilter');
    const appointmentsTable = document.getElementById('appointments');
    const doctorForm = document.getElementById('doctorForm');
    const appointmentForm = document.getElementById('adminAppointmentForm');

    // Fetch and populate doctors
    fetch('/api/admin/doctors')
        .then(response => response.json())
        .then(doctors => {
            doctors.forEach(doctor => {
                const option1 = document.createElement('option');
                option1.value = doctor.id;
                option1.textContent = doctor.name + ' (' + doctor.specialty + ')';
                doctorSelect.appendChild(option1);

                const option2 = document.createElement('option');
                option2.value = doctor.id;
                option2.textContent = doctor.name + ' (' + doctor.specialty + ')';
                doctorFilter.appendChild(option2);
            });
        });

    // Add Doctor
    doctorForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const doctor = {
            name: document.getElementById('doctorName').value,
            specialty: document.getElementById('specialty').value
        };
        fetch('/api/admin/doctors', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(doctor)
        }).then(() => location.reload());
    });

    // Add Appointment
    appointmentForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const appointment = {
            patientName: document.getElementById('adminPatientName').value,
            patientEmail: document.getElementById('adminPatientEmail').value,
            doctorId: document.getElementById('adminDoctorId').value,
            appointmentTime: document.getElementById('adminAppointmentTime').value,
            reason: document.getElementById('adminReason').value
        };
        fetch('/api/admin/appointments', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(appointment)
        }).then(() => location.reload());
    });

    // Load and filter appointments
    function loadAppointments(doctorId = '') {
        fetch(`/api/admin/appointments?doctorId=${doctorId}`)
            .then(response => response.json())
            .then(appointments => {
                appointmentsTable.innerHTML = '';
                appointments.forEach(appointment => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${appointment.patientName}</td>
                        <td>${appointment.doctorId}</td>
                        <td>${new Date(appointment.appointmentTime).toLocaleString()}</td>
                        <td>${appointment.reason}</td>
                        <td><button onclick="deleteAppointment(${appointment.id})">Delete</button></td>
                    `;
                    appointmentsTable.appendChild(row);
                });
            });
    }

    doctorFilter.addEventListener('change', (e) => loadAppointments(e.target.value));
    loadAppointments();

    window.deleteAppointment = (id) => {
        if (confirm('Are you sure?')) {
            fetch(`/api/admin/appointments/${id}`, { method: 'DELETE' })
                .then(() => loadAppointments());
        }
    };
});