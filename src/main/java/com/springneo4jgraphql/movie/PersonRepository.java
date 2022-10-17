package com.springneo4jgraphql.movie;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface PersonRepository extends Neo4jRepository<Person, String>{
    Long deleteByName(String name);
}
