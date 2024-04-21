package com.bdd_test.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "email not conformed")
    @NotEmpty(message = "email is empty")
    @NotBlank(message = "email is blanck")
    private String email;
    @Column(nullable = false)
    @NotEmpty(message = "firstname is empty")
    @NotBlank(message = "firstname is blanck")
    private String firstName;
    @Column(nullable = false)
    @NotEmpty(message = "last is empty")
    @NotBlank(message = "lastname is empty")
    private String lastName;
    @Column(nullable = false)
    @NotBlank(message = "phoneNumber is empty")
    @Min(message = "minimum 9 characters", value = 9L)
    private String phoneNumber;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate birthDate;

}
