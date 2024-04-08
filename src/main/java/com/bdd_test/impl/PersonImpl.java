package com.bdd_test.impl;

import com.bdd_test.config.GenericValidation;
import com.bdd_test.exception.ValidationException;
import com.bdd_test.models.Person;
import com.bdd_test.repository.PersonneRepository;
import com.bdd_test.service.PersonneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonImpl implements PersonneService {
    private final PersonneRepository repository;
    private final GenericValidation validation;
    @Override
    public Person create(Person person) {
        List<String> checkErrors = validation.errors(person);
        if (checkErrors.isEmpty()){
            return repository.save(person);
        }else{
            String message = checkErrors.get(0);
            throw new ValidationException(message);
        }
    }

    @Override
    public Person update(Person person) {
        List<String> checkErrors = validation.errors(person);
        if (checkErrors.isEmpty()){
            return repository.save(person);
        }else{
            String message = checkErrors.get(0);
            throw new ValidationException(message);
        }
    }

    @Override
    public Optional<Person> findPersonById(Long id) {
        return Optional.of(repository.findById(id).orElseThrow());
    }

}
