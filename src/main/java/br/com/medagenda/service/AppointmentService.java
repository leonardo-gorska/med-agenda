package br.com.medagenda.service;

import br.com.medagenda.domain.Appointment;
import br.com.medagenda.domain.Doctor;
import br.com.medagenda.domain.Patient;
import br.com.medagenda.dto.AppointmentRequestDTO;
import br.com.medagenda.dto.AppointmentResponseDTO;
import br.com.medagenda.exception.ConflictException;
import br.com.medagenda.repository.AppointmentRepository;
import br.com.medagenda.repository.DoctorRepository;
import br.com.medagenda.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public AppointmentResponseDTO schedule(AppointmentRequestDTO request) {
        if (!request.endTime().isAfter(request.startTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        // Anti-conflict verification
        boolean doctorConflict = appointmentRepository.existsOverlappingAppointmentForDoctor(doctor.getId(), request.startTime(), request.endTime());
        if (doctorConflict) {
            throw new ConflictException("Doctor already has an appointment in the selected time range.");
        }

        boolean patientConflict = appointmentRepository.existsOverlappingAppointmentForPatient(patient.getId(), request.startTime(), request.endTime());
        if (patientConflict) {
            throw new ConflictException("Patient already has an appointment in the selected time range.");
        }

        Appointment appointment = new Appointment(doctor, patient, request.startTime(), request.endTime());
        appointment = appointmentRepository.save(appointment);

        return new AppointmentResponseDTO(appointment);
    }

    public Page<AppointmentResponseDTO> listAll(Pageable pagination) {
        return appointmentRepository.findAll(pagination).map(AppointmentResponseDTO::new);
    }
}
