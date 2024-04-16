package com.bdd_test.controller;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.models.Person;
import com.bdd_test.service.PersonDAOService;
import com.bdd_test.service.PersonneService;
import com.bdd_test.utils.HttpResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/person")
@AllArgsConstructor
public class PersonnController{
    private final PersonDAOService dao;
    private final PersonneService service;
    @GetMapping()
    public ResponseEntity<HttpResponse> findAllNoPaginate() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .dateTime(LocalDateTime.now().toString())
                        .message("list person")
                        .codeStatus(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .datas(Map.of("data", dao.findAllPerson()))
                        .build()
        );
    }

    @PostMapping()
    public ResponseEntity<HttpResponse> create(@RequestBody @Valid Person person){
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .dateTime(LocalDateTime.now().toString())
                        .message("created")
                        .codeStatus(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .datas(Map.of("data", service.create(person)))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> findById(@PathVariable("id") Long id){
        Optional<PersonneDTO> person = service.findPersonById(id);
        if (person.isPresent()){
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .dateTime(LocalDateTime.now().toString())
                            .message("find")
                            .codeStatus(HttpStatus.OK.value())
                            .httpStatus(HttpStatus.OK)
                            .datas(Map.of("data", service.findPersonById(id)))
                            .build()
            );
        }else{
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .dateTime(LocalDateTime.now().toString())
                            .message("person not found")
                            .codeStatus(HttpStatus.NOT_FOUND.value())
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .build()
            );
        }

    }

}
