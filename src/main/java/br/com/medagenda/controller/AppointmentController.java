package br.com.medagenda.controller;

import br.com.medagenda.dto.AppointmentRequestDTO;
import br.com.medagenda.dto.AppointmentResponseDTO;
import br.com.medagenda.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> scheduleAppointment(@RequestBody @Valid AppointmentRequestDTO data) {
        AppointmentResponseDTO response = appointmentService.schedule(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentResponseDTO>> listAppointments(@PageableDefault(size = 10, sort = {"startTime"}) Pageable pagination) {
        Page<AppointmentResponseDTO> page = appointmentService.listAll(pagination);
        return ResponseEntity.ok(page);
    }
}
