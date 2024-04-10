package com.bdd_test.dao;

import com.bdd_test.models.Person;
import com.bdd_test.service.PersonDAOService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@AllArgsConstructor
public class PersonDAO implements PersonDAOService {
    private final EntityManager entityManager;

    @Override
    public List<Person> findAllPerson() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root  = criteriaQuery.from(Person.class);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<Person> list = entityManager.createQuery(criteriaQuery).getResultList();
        return list;
    }
}
