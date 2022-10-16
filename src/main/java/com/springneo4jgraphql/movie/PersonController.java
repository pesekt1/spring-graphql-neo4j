package com.springneo4jgraphql.movie;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PersonController {
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @MutationMapping
    public Person addPerson(
            @Argument String name,
            @Argument Integer yearOfBirth)
 {
        System.out.println("Saving person: " + name);
        Person person = new Person(name, yearOfBirth);
        return personRepository.save(person);
    }
}
