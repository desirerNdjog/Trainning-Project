package com.bdd_test.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {
    private int codeStatus;
    private String message;
    private HttpStatus httpStatus;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm", shape = JsonFormat.Shape.STRING)
    private String dateTime;
    private Map<?, ?> datas;
}
