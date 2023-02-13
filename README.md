# CIDOC-QA Evaluation Benchmark

This page contains the evaluation collection for Question Answering over CIDOC-CRM. 
In particular 5,000 questions are provided based on 10 query templates (of different depths), 
which were produced by using the  Smithsonian American Art Museum (SAAM) KG.


<h1> SPARQL Queries for Creating the Templates </h1>
Below, we provide the different SPARQL queries that we sent to https://triplydb.com/smithsonian/american-art-museum/sparql/american-art-museum, 
for creating the 5,000 questions.


PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/> <br>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> <br>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> <br> <br>

<h2>Questions of Depth 1</h2>

**Q1. Which is the art type of {Art Work}?**

SELECT ?artwork ?label ?typeLabel WHERE { <br>
?artwork rdfs:label ?label . <br>
?artwork cidoc:P2_has_type ?type .  <br>
?type rdfs:label ?typeLabel <br>
} <br>

**Q2. What material used for creating the {Art Work}?**

SELECT ?artwork ?label  ?material WHERE { <br>
?artwork rdfs:label ?label .  <br>
?artwork cidoc:P67i_is_referred_to_by ?ref . <br>
?ref rdf:value ?material <br>
.filter(STRENDS(str(?ref),'medium')) <br>
} <br>

**Q3. Who gave the {Art Work} to the museum?**

SELECT ?artwork ?label ?value WHERE { <br>
?artwork rdfs:label ?label . <br>
?artwork cidoc:P67i_is_referred_to_by ?ref .  <br>
?ref rdf:value ?value <br>
.FILTER(REGEX(STR(?value) , "Gift of" ) || REGEX(STR(?value) , "Bequest of" ))  <br>
} <br>

<h2>Questions of Depth 2</h2>
**Q4. Who is the creator of {Art Work}?**

SELECT ?artwork ?label ?value WHERE {  <br>
?artwork rdfs:label ?label .  <br>
?artwork cidoc:P108i_was_produced_by ?prod .  <br> 
?prod cidoc:P14_carried_out_by ?actor .  <br>
?actor rdfs:label ?value  <br>
}  <br>

**Q5. Which is the birth place of {Artist}?**

SELECT distinct ?actor ?label ?placeLabel WHERE { <br>
?actor rdfs:label ?label .  <br>
?actor cidoc:P92i_was_brought_into_existence_by ?existence .  <br>
?existence cidoc:P7_took_place_at ?place . <br>
?place rdfs:label ?placeLabel . <br>
} <br>


<h1>Code for Creating the Evaluation Benchmark</h1>



<h1>SPARQL Queries for Creating Subgraphs through Graph DB</h1>

