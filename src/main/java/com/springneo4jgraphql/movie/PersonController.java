package com.springneo4jgraphql.movie;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @MutationMapping
    public Person addPerson(
            @Argument String name,
            @Argument Integer yearOfBirth)
 {
        System.out.println("Saving person: " + name);
        return personService.save(new Person(name, yearOfBirth));
    }
}
