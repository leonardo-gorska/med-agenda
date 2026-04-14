package br.com.medagenda.repository;

import br.com.medagenda.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByCrm(String crm);
    boolean existsByUserEmail(String email);
}
