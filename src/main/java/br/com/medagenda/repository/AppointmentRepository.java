package br.com.medagenda.repository;

import br.com.medagenda.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM appointments a " +
           "WHERE a.doctor.id = :doctorId AND a.status != 'CANCELED' " +
           "AND ((a.startTime < :endTime) AND (a.endTime > :startTime))")
    boolean existsOverlappingAppointmentForDoctor(@Param("doctorId") Long doctorId, 
                                                  @Param("startTime") LocalDateTime startTime, 
                                                  @Param("endTime") LocalDateTime endTime);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM appointments a " +
           "WHERE a.patient.id = :patientId AND a.status != 'CANCELED' " +
           "AND ((a.startTime < :endTime) AND (a.endTime > :startTime))")
    boolean existsOverlappingAppointmentForPatient(@Param("patientId") Long patientId, 
                                                   @Param("startTime") LocalDateTime startTime, 
                                                   @Param("endTime") LocalDateTime endTime);
}
