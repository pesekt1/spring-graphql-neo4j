package com.springneo4jgraphql.movie;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person){
        return personRepository.save(person);
    }

    public Long deleteByName(String name){
        return personRepository.deleteAllByName(name);
    }
}
