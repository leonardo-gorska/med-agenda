package br.com.medagenda.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AppointmentRequestDTO(
        @NotNull(message = "Doctor ID is required") Long doctorId,
        @NotNull(message = "Patient ID is required") Long patientId,
        @NotNull(message = "Start time is required") @Future(message = "Start time must be in the future") LocalDateTime startTime,
        @NotNull(message = "End time is required") @Future(message = "End time must be in the future") LocalDateTime endTime
) {}
