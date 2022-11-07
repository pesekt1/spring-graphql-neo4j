create (p:Person {name: "Tomas Pesek", yearOfBirth: 1984})

match (p:Person) where p.name = "Tomas Pesek"
return p;

match (m:Movie) where m.title = "The Matrix"
return m;

MATCH (m:Movie {title: "The Matrix"})
MATCH (p:Person {name: "Tomas Pesek"})
CREATE (p)-[r:ACTED_IN {roles:["Test role"]}]->(m)
return m

MATCH (m:Movie)<-[r:ACTED_IN]-(p:Person) WHERE p.name = "Tomas Pesek" RETURN m,p,r

MATCH (m:Movie {title: "The Matrix"})
MATCH (p:Person)
Match (p)-[r:ACTED_IN]->(m)
return p

MATCH (m:Movie {title: "The Matrix"})
MATCH (p:Person {name: "Tomas Pesek"})
Match (p)-[r:ACTED_IN]->(m)
return r.roles

MATCH (m:Movie {title: "The Matrix"})
MATCH (p:Person)
Match (p)-[r:ACTED_IN]->(m)
return p,r.roles