# CIDOC-QA Evaluation Benchmark

This page contains the evaluation collection for Question Answering over CIDOC-CRM.
In particular 5,000 questions are provided based on 10 query templates (of different depths),
which were produced by using the  Smithsonian American Art Museum (SAAM) KG.

The evaluation collection (grouped by each depth) is provided through the folder Benchmark.

## A. SPARQL Queries for Creating the Questions and Answers

Below, we provide the different SPARQL queries that we sent to <https://triplydb.com/smithsonian/american-art-museum/sparql/american-art-museum>,
for creating the 5,000 questions.

```sparql
PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/> 
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>  
```

### Question Templates of Depth 1

Q1. Which is the art type of {Art Work}?

```sparql
SELECT ?artwork ?label ?typeLabel WHERE { 
?artwork rdfs:label ?label . 
?artwork cidoc:P2_has_type ?type .  
?type rdfs:label ?typeLabel 
} 
```

Q2. What material used for creating the {Art Work}?

```sparql
SELECT ?artwork ?label  ?material WHERE { 
?artwork rdfs:label ?label .  
?artwork cidoc:P67i_is_referred_to_by ?ref . 
?ref rdf:value ?material 
.filter(STRENDS(str(?ref),'medium')) 
} 
```

Q3. Who gave the {Art Work} to the museum?

```sparql
SELECT ?artwork ?label ?value WHERE { 
?artwork rdfs:label ?label . 
?artwork cidoc:P67i_is_referred_to_by ?ref .  
?ref rdf:value ?value 
.FILTER(REGEX(STR(?value) , "Gift of" ) || REGEX(STR(?value) , "Bequest of" ))  
} 
```

### Question Templates of Depth 2

Q4. Who is the creator of {Art Work}?

```sparql
SELECT ?artwork ?label ?value WHERE {  
?artwork rdfs:label ?label .  
?artwork cidoc:P108i_was_produced_by ?prod .   
?prod cidoc:P14_carried_out_by ?actor .  
?actor rdfs:label ?value  
} 
```

Q5. Which is the birth place of {Artist}?

```sparql
SELECT distinct ?actor ?label ?placeLabel WHERE { 
?actor rdfs:label ?label .  
?actor cidoc:P92i_was_brought_into_existence_by ?existence .  
?existence cidoc:P7_took_place_at ?place . 
?place rdfs:label ?placeLabel . 
} 
```

### Question Templates of Depth 3

Q6. When the {Art Work} started?

```sparql
SELECT ?artwork ?label ?date WHERE { 
?artwork rdfs:label ?label . 
?artwork cidoc:P108i_was_produced_by ?prod .  
?prod cidoc:P4_has_time-span ?tsp .  
?tsp cidoc:P82a_begin_of_the_begin ?date 
} 
```

Q7. When the production of {Art Work} ended?

```sparql
SELECT ?artwork ?label ?endDate WHERE { 
?artwork rdfs:label ?label . 
?artwork cidoc:P108i_was_produced_by ?prod .  
?prod cidoc:P4_has_time-span ?tsp .  
?tsp cidoc:P82b_end_of_the_end ?endDate 
}  
```

Q8. Which is the nationality of the creator of {Art Work}?

```sparql
SELECT ?artwork ?label ?nationality WHERE { 
?artwork rdfs:label ?label .   
?artwork cidoc:P108i_was_produced_by ?prod .    
?prod cidoc:P14_carried_out_by ?actor .    
?actor cidoc:P107i_is_current_or_former_member_of ?country .    
?country rdfs:label ?nationality   
}  
```

### Question Templates of Depth 4

Q9. Which is the birth place of the creator of {Art Work}?

```sparql
SELECT ?artwork ?label ?place WHERE { 
?artwork rdfs:label ?label . 
?artwork cidoc:P108i_was_produced_by ?prod .  
?prod cidoc:P14_carried_out_by ?actor .  
?actor cidoc:P92i_was_brought_into_existence_by ?existence .  
?existence cidoc:P7_took_place_at ?placeLabel . 
?place rdfs:label ?placeLabel . 
} 
```

Q10. Which year died the creator of {Art Work}?

```sparql
SELECT ?artwork ?label ?deathYear WHERE { 
?artwork rdfs:label ?label . 
?artwork cidoc:P108i_was_produced_by ?prod .  
?prod cidoc:P14_carried_out_by ?actor .  
?actor cidoc:P93i_was_taken_out_of_existence_by ?out .    
?out cidoc:P4_has_time-span ?ts .   
?ts rdfs:label ?deathYear   
}  
```

## B. Code for Creating the Evaluation Benchmark

The JAVA code for creating the evaluation benchmark can be found inside the folder  Code_for_creating_the_benchmark.

## C. SPARQL Queries for Creating Subgraphs via GraphDB

```sparql
SELECT DISTINCT (?start as ?s) (?property as ?p) (?end as ?o) (?startLabel as ?sLabel) 
(?endLabel as ?oLabel) (?startValue as ?sValue) (?endValue as ?oValue) (?index as ?depth) WHERE {
{
 SELECT ?start ?property ?end ?index 
    WHERE {
    VALUES (?src) {
        (<entity.uri>)
    }
    SERVICE path:search {
        <urn:path> path:findPath path:allPaths ;
        path:sourceNode ?src ;
        path:destinationNode ?dst ;
        path:minPathLength 1 ;
        path:maxPathLength {1} ;
        path:startNode ?start;
        path:propertyBinding ?property ;
        path:endNode ?end;
        path:resultBindingIndex ?index ;
        path:pathIndex ?path .
        }
    }
}
OPTIONAL {
    ?start rdfs:label ?startLabel .
    FILTER(lang(?startLabel) = "en" || lang(?startLabel) = "")
}
OPTIONAL {
    ?start rdf:value ?startValue
}
 OPTIONAL {
     ?end rdfs:label ?endLabel .
     FILTER(lang(?endLabel) = "en" || lang(?endLabel) = "")
}
OPTIONAL {
    ?end rdf:value ?endValue
}
FILTER(REGEX(STR(?property), "http://www.cidoc-crm.org/cidoc-crm")) .
} ORDERBY DESC(?index)
```
