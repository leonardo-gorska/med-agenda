package br.com.medagenda.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "doctors")
@Entity(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String crm;

    @Column(nullable = false)
    private String specialty;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Doctor(String name, String crm, String specialty, User user) {
        this.name = name;
        this.crm = crm;
        this.specialty = specialty;
        this.user = user;
    }
}
