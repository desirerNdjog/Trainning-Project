package com.bdd_test.config;

import com.bdd_test.models.Person;
import jakarta.validation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
public class GenericValidation {
    private ValidatorFactory factory;
    private Validator  validator;

    public GenericValidation(){
        this.factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public <T> List<String> errors(T person){
        List<String> errors = new ArrayList<>();
        Set<ConstraintViolation<T>> violations = validator.validate(person);
        if(violations.isEmpty()){
            return Collections.emptyList();
        }else {
            try {
                violations.forEach(x -> errors.add(x.getMessage()));
            }catch (NullPointerException e){
                errors.add(e.getMessage());
            }
            return errors;
        }
    }
}
