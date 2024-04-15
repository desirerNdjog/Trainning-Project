package com.bdd_test.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = " firstname is empty")
    private String firstName;
    @Column(nullable = false)
    @NotBlank(message = "lastname is empty")
    private String lastName;
    @Column(nullable = false)
    @NotBlank
    @Min(message = "minimum 9 characters", value = 9L)
    private String phoneNumber;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate birthDate;
}
