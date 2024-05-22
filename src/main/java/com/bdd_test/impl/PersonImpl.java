package com.bdd_test.impl;

import com.bdd_test.config.GenericValidation;
import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.exception.ValidationException;
import com.bdd_test.mapper.PersonMapper;
import com.bdd_test.domain.models.Person;
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
    private final PersonMapper mapper;

    @Override
    public PersonneDTO create(PersonneDTO personneDTO) {
        List<String> checkErrors = validation.errors(mapper.fromPersonDTOToPerson(personneDTO));
        if (checkErrors.isEmpty()){
            return mapper.fromPersonToPersonDTO(
                    repository.save(mapper.fromPersonDTOToPerson(personneDTO))
            );
        }else{
            var message = checkErrors.get(0);
            throw new ValidationException(message);
        }
    }

    @Override
    public PersonneDTO update(PersonneDTO personneDTO) {
        List<String> checkErrors = validation.errors(personneDTO);
        if (checkErrors.isEmpty()){
            return mapper.fromPersonToPersonDTO(
                    repository.save(mapper.fromPersonDTOToPerson(personneDTO))
            );
        }else{
            String message = checkErrors.get(0);
            throw new ValidationException(message);
        }
    }

    @Override
    public Optional<PersonneDTO> findPersonById(Long id) {
        Optional<Person> response = repository.findById(id);
        return response.map(mapper::fromPersonToPersonDTO);
    }

}
