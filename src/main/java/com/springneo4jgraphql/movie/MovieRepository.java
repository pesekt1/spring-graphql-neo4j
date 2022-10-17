package com.springneo4jgraphql.movie;

import org.neo4j.cypherdsl.core.*;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.neo4j.repository.support.CypherdslConditionExecutor;
import org.springframework.data.neo4j.repository.support.CypherdslStatementExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;

@GraphQlRepository
public interface MovieRepository extends
        Neo4jRepository<Movie, String>,
        QuerydslPredicateExecutor<Movie>,
        CypherdslStatementExecutor<Movie>,
        CypherdslConditionExecutor<Movie> {

    Long deleteByTitle(String title);

    @Query("""
            MATCH (m:Movie {title: $title})
            MATCH (p:Person {name: $actorName})
            CREATE (p)-[r:ACTED_IN {roles:[$role]}]->(m)
            return m""")
    Movie addActor(
            @Param("title") String title,
            @Param("actorName") String actorName,
            @Param("role") String role);

    @Query("""
            MATCH (m:Movie {title: $title})
            MATCH (p:Person {name: $actorName})
            Match (p)-[r:ACTED_IN]->(m)
            DELETE r
            RETURN m""")
    List<Movie> removeActor(
            @Param("title") String title,
            @Param("actorName") String actorName);
}
