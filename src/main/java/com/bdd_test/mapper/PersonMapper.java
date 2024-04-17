package com.bdd_test.mapper;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.models.Person;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Mapper(componentModel = "spring")
@Configuration
public interface PersonMapper {
   PersonMapper MAPPER = Mappers.getMapper(PersonMapper.class);

   @Bean
   @Mapping(source = "birthDate", target = "date")
   Person fromPersonDTOToPerson(PersonneDTO personneDTO);
   @Bean
   @InheritInverseConfiguration
   PersonneDTO fromPersonToPersonDTO(Person person);
}
