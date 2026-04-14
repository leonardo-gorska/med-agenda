package br.com.medagenda.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "patients")
@Entity(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Patient(String name, String cpf, String phone, User user) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.user = user;
    }
}
