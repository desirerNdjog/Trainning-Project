package com.bdd_test.controller;

import com.bdd_test.dao.PersonDAO;
import com.bdd_test.models.Person;
import com.bdd_test.service.PersonneService;
import com.bdd_test.utils.HttpResponse;
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
    private final PersonDAO dao;
    private final PersonneService service;
    @GetMapping()
    public ResponseEntity<HttpResponse> findAllNoPaginate() {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .dateTime(LocalDateTime.now().toString())
                        .httpStatus(HttpStatus.OK)
                        .codeStatus(HttpStatus.OK.value())
                        .datas(Map.of("data", dao.findAllPerson()))
                        .build()
        );
    }

    @PostMapping()
    public ResponseEntity<HttpResponse> create(@RequestBody Person person){
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .dateTime(LocalDateTime.now().toString())
                            .httpStatus(HttpStatus.OK)
                            .codeStatus(HttpStatus.OK.value())
                            .message("create")
                            .datas(Map.of("data", service.create(person)))
                            .build()
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> update(@PathVariable("id") int id, @RequestBody Person person){
        Optional<Person> personn = service.findPersonById(Long.valueOf(id));
        if (personn.isPresent()){
            personn.get().setFirstName(person.getFirstName());
            personn.get().setLastName(person.getLastName());
            personn.get().setBirthDate(person.getBirthDate());
            personn.get().setPhoneNumber(person.getPhoneNumber());
            Person person1 = service.update(person);
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .dateTime(LocalDateTime.now().toString())
                            .httpStatus(HttpStatus.OK)
                            .codeStatus(HttpStatus.OK.value())
                            .message("person update")
                            .datas(Map.of("data", person1))
                            .build()
            );
        }else{
            return ResponseEntity.ok().body(
                    HttpResponse.builder()
                            .dateTime(LocalDateTime.now().toString())
                            .message("person doesn't exist")
                            .httpStatus(HttpStatus.NOT_FOUND)
                            .codeStatus(HttpStatus.NOT_FOUND.value())
                            .build()
            );
        }
    }
}
