package com.bdd_test.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(value = JsonInclude.Include.ALWAYS)
public class HttpResponse {
    private int codeStatus;
    private String message;
    private HttpStatus httpStatus;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm", shape = JsonFormat.Shape.STRING)
    private String dateTime;
    private Map<?, ?> datas;
}
