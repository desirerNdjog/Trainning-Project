package com.bdd_test.mapper;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.models.Person;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-17T00:52:15+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    private final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_0650712384 = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );

    @Override
    public Person fromPersonDTOToPerson(PersonneDTO personneDTO) {
        if ( personneDTO == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        if ( personneDTO.getDate() != null ) {
            person.birthDate( LocalDate.parse( personneDTO.getDate(), dateTimeFormatter_dd_MM_yyyy_0650712384 ) );
        }
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

        if ( person.getBirthDate() != null ) {
            personneDTO.date( dateTimeFormatter_dd_MM_yyyy_0650712384.format( person.getBirthDate() ) );
        }
        personneDTO.id( person.getId() );
        personneDTO.firstName( person.getFirstName() );
        personneDTO.lastName( person.getLastName() );
        personneDTO.email( person.getEmail() );
        personneDTO.phoneNumber( person.getPhoneNumber() );

        return personneDTO.build();
    }
}
