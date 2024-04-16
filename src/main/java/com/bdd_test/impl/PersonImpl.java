package com.bdd_test.impl;

import com.bdd_test.config.GenericValidation;
import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.exception.ValidationException;
import com.bdd_test.mapper.PersonDTOMapper;
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
    private final PersonDTOMapper mapper;
    @Override
    public PersonneDTO create(Person person) {
        List<String> checkErrors = validation.errors(person);
        if (checkErrors.isEmpty()){
            return mapper.apply(repository.save(person));
        }else{
            var message = checkErrors.get(0);
            throw new ValidationException(message);
        }
    }

    @Override
    public PersonneDTO update(Person person) {
        List<String> checkErrors = validation.errors(person);
        if (checkErrors.isEmpty()){
            return mapper.apply(repository.save(person));
        }else{
            String message = checkErrors.get(0);
            throw new ValidationException(message);
        }
    }

    @Override
    public Optional<PersonneDTO> findPersonById(Long id) {
        var response = repository.findById(id);
        return response.map(mapper);
    }

}
