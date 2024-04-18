package com.bdd_test.mapper;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.models.Person;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-18T15:49:04+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public Person fromPersonDTOToPerson(PersonneDTO personneDTO) {
        if ( personneDTO == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.birthDate( personneDTO.getDate() );
        person.id( personneDTO.getId() );
        person.email( personneDTO.getEmail() );
        person.firstName( personneDTO.getFirstName() );
        person.lastName( personneDTO.getLastName() );
        person.phoneNumber( personneDTO.getPhoneNumber() );

        return person.build();
    }

    @Override
    public PersonneDTO fromPersonToPersonDTO(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonneDTO.PersonneDTOBuilder personneDTO = PersonneDTO.builder();

        personneDTO.date( person.getBirthDate() );
        personneDTO.id( person.getId() );
        personneDTO.firstName( person.getFirstName() );
        personneDTO.lastName( person.getLastName() );
        personneDTO.email( person.getEmail() );
        personneDTO.phoneNumber( person.getPhoneNumber() );

        return personneDTO.build();
    }
}
