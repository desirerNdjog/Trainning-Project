package com.bdd_test.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Builder
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
