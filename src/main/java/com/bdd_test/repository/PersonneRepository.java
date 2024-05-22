package com.bdd_test.repository;

import com.bdd_test.domain.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneRepository extends JpaRepository<Person, Long> {
}
