package com.bdd_test.models;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BasicEntity implements Serializable {
    private LocalDateTime created;
    private LocalDateTime updated;
    private String login;
}
