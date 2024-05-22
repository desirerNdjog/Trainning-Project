package com.bdd_test.mapper;

import com.bdd_test.dto.PersonneDTO;
import com.bdd_test.domain.models.Person;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper {
   PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

   @Mapping(source = "date", target = "birthDate", dateFormat = "dd/MM/yyyy")
   Person fromPersonDTOToPerson(PersonneDTO personneDTO);

   @InheritInverseConfiguration
   PersonneDTO fromPersonToPersonDTO(Person person);
}
