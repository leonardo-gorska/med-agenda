package br.com.medagenda.controller;

import br.com.medagenda.domain.Doctor;
import br.com.medagenda.domain.Patient;
import br.com.medagenda.domain.User;
import br.com.medagenda.domain.UserRole;
import br.com.medagenda.dto.auth.AuthenticationDTO;
import br.com.medagenda.dto.auth.LoginResponseDTO;
import br.com.medagenda.dto.auth.RegisterDoctorDTO;
import br.com.medagenda.dto.auth.RegisterPatientDTO;
import br.com.medagenda.repository.DoctorRepository;
import br.com.medagenda.repository.PatientRepository;
import br.com.medagenda.repository.UserRepository;
import br.com.medagenda.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody @Valid RegisterDoctorDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Email is already registered.");
        }
        if (this.doctorRepository.existsByCrm(data.crm())) {
            return ResponseEntity.badRequest().body("CRM is already registered.");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.email(), encryptedPassword, UserRole.DOCTOR);
        
        Doctor doctor = new Doctor(data.name(), data.crm(), data.specialty(), newUser);
        this.doctorRepository.save(doctor);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/patient")
    public ResponseEntity<?> registerPatient(@RequestBody @Valid RegisterPatientDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Email is already registered.");
        }
        if (this.patientRepository.existsByCpf(data.cpf())) {
            return ResponseEntity.badRequest().body("CPF is already registered.");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.email(), encryptedPassword, UserRole.PATIENT);
        
        Patient patient = new Patient(data.name(), data.cpf(), data.phone(), newUser);
        this.patientRepository.save(patient);

        return ResponseEntity.ok().build();
    }
}
