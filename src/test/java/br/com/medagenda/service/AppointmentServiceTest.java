package br.com.medagenda.service;

import br.com.medagenda.domain.Appointment;
import br.com.medagenda.domain.Doctor;
import br.com.medagenda.domain.Patient;
import br.com.medagenda.domain.User;
import br.com.medagenda.domain.UserRole;
import br.com.medagenda.dto.AppointmentRequestDTO;
import br.com.medagenda.dto.AppointmentResponseDTO;
import br.com.medagenda.exception.ConflictException;
import br.com.medagenda.repository.AppointmentRepository;
import br.com.medagenda.repository.DoctorRepository;
import br.com.medagenda.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Doctor doctor;
    private Patient patient;
    private AppointmentRequestDTO requestDto;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setUp() {
        User docUser = new User("doc@doc.com", "123", UserRole.DOCTOR);
        doctor = new Doctor(1L, "House", "12345", "Cardio", docUser);

        User patUser = new User("pat@pat.com", "123", UserRole.PATIENT);
        patient = new Patient(1L, "Greg", "123456789", "9999", patUser);

        start = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        end = start.plusHours(1);

        requestDto = new AppointmentRequestDTO(1L, 1L, start, end);
    }

    @Test
    void shouldScheduleAppointmentSuccessfully() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.existsOverlappingAppointmentForDoctor(1L, start, end)).thenReturn(false);
        when(appointmentRepository.existsOverlappingAppointmentForPatient(1L, start, end)).thenReturn(false);

        Appointment mockAppointment = new Appointment(doctor, patient, start, end);
        mockAppointment.setId(100L);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(mockAppointment);

        AppointmentResponseDTO response = appointmentService.schedule(requestDto);

        assertNotNull(response);
        assertEquals(100L, response.id());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void shouldThrowConflictWhenDoctorIsBusy() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.existsOverlappingAppointmentForDoctor(1L, start, end)).thenReturn(true);

        assertThrows(ConflictException.class, () -> appointmentService.schedule(requestDto));

        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void shouldThrowConflictWhenPatientIsBusy() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.existsOverlappingAppointmentForDoctor(1L, start, end)).thenReturn(false);
        when(appointmentRepository.existsOverlappingAppointmentForPatient(1L, start, end)).thenReturn(true);

        assertThrows(ConflictException.class, () -> appointmentService.schedule(requestDto));

        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}
