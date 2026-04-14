package br.com.medagenda.dto;

import br.com.medagenda.domain.Appointment;
import java.time.LocalDateTime;

public record AppointmentResponseDTO(
        Long id,
        Long doctorId,
        String doctorName,
        Long patientId,
        String patientName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String status
) {
    public AppointmentResponseDTO(Appointment appointment) {
        this(
            appointment.getId(),
            appointment.getDoctor().getId(),
            appointment.getDoctor().getName(),
            appointment.getPatient().getId(),
            appointment.getPatient().getName(),
            appointment.getStartTime(),
            appointment.getEndTime(),
            appointment.getStatus().name()
        );
    }
}
