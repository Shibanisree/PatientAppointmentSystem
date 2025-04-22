document.addEventListener('DOMContentLoaded', () => {
    const doctorSelect = document.getElementById('doctorId');
    fetch('/api/public/doctors')
        .then(response => response.json())
        .then(doctors => {
            doctors.forEach(doctor => {
                const option = document.createElement('option');
                option.value = doctor.id;
                option.textContent = `${doctor.name} (${doctor.specialty})`;
                doctorSelect.appendChild(option);
            });
        });

    document.getElementById('appointmentForm').addEventListener('submit', (e) => {
        e.preventDefault();
        const patientName = document.getElementById('patientName').value;
        const patientEmail = document.getElementById('patientEmail').value;
        const doctorId = document.getElementById('doctorId').value;
        const appointmentTime = document.getElementById('appointmentTime').value;
        const reason = document.getElementById('reason').value;

        fetch('/api/public/book', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ patientName, patientEmail, doctorId, appointmentTime, reason })
        })
            .then(response => {
                if (response.ok) {
                    document.getElementById('success').style.display = 'block';
                    document.getElementById('appointmentForm').reset();
                } else {
                    alert('Failed to book appointment');
                }
            });
    });
});