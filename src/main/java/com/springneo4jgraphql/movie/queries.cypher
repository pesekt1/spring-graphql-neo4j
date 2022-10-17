match (p:Person) where p.name = "Tomas Pesek"
return p;

match (m:Movie) where m.title = "The Matrix"
return m;

MATCH (m:Movie)<-[r:ACTED_IN]-(p:Person) WHERE p.name = "Tomas Pesek" RETURN m,p,r