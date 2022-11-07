package com.springneo4jgraphql.movie;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;

@GraphQlRepository
public interface PersonRepository extends Neo4jRepository<Person, String>{
    Long deleteByName(String name);

    @Query("""
            MATCH (m:Movie {title: $title})
            MATCH (p:Person)
            Match (p)-[r:ACTED_IN]->(m)
            return p,r.roles""")
    List<Person> actorByMovie(@Param("title") String title);

    @Query("""
            MATCH (m:Movie {title: $title})
            MATCH (p:Person)
            Match (p)-[r:DIRECTED]->(m)
            return p""")
    List<Person> directorByMovie(@Param("title") String title);

    @Query("""
            MATCH (m:Movie {title: $title})
            MATCH (p:Person {name: $name})
            Match (p)-[r:ACTED_IN]->(m)
            return r.roles""")
    Roles getRoles(@Param("title") String title, @Param("name") String name);
}
