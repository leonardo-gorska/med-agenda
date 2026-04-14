package br.com.medagenda.repository;

import br.com.medagenda.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByUserEmail(String email);
}
