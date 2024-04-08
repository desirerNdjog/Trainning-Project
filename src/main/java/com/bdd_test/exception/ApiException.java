package com.bdd_test.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiException {
    private int codeStatus;
    private String message;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm", shape = JsonFormat.Shape.STRING)
    private String dateTime;
}
