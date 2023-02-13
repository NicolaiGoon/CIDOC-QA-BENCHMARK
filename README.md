# CIDOC-QA Evaluation Benchmark

This page contains the evaluation collection for Question Answering over CIDOC-CRM. 
In particular 5,000 questions are provided based on 10 query templates (of different depths), 
which were produced by using the  Smithsonian American Art Museum (SAAM) KG.

**The evaluation collection (grouped by each depth) is provided through the folder Benchmark.**

<h1> SPARQL Queries for Creating the Questions and Answers</h1>
Below, we provide the different SPARQL queries that we sent to https://triplydb.com/smithsonian/american-art-museum/sparql/american-art-museum, 
for creating the 5,000 questions. <br>


PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/> <br>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> <br>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> <br> <br>

<h2>Question Templates of Depth 1</h2>

**Q1. Which is the art type of {Art Work}?**

SELECT ?artwork ?label ?typeLabel WHERE { <br>
?artwork rdfs:label ?label . <br>
?artwork **cidoc:P2_has_type** ?type .  <br>
?type rdfs:label ?typeLabel <br>
} <br>

**Q2. What material used for creating the {Art Work}?**

SELECT ?artwork ?label  ?material WHERE { <br>
?artwork rdfs:label ?label .  <br>
?artwork **cidoc:P67i_is_referred_to_by** ?ref . <br>
?ref rdf:value ?material <br>
.filter(STRENDS(str(?ref),'medium')) <br>
} <br>

**Q3. Who gave the {Art Work} to the museum?**

SELECT ?artwork ?label ?value WHERE { <br>
?artwork rdfs:label ?label . <br>
?artwork **cidoc:P67i_is_referred_to_by** ?ref .  <br>
?ref rdf:value ?value <br>
.FILTER(REGEX(STR(?value) , "Gift of" ) || REGEX(STR(?value) , "Bequest of" ))  <br>
} <br>

<h2>Question Templates of Depth 2</h2>

**Q4. Who is the creator of {Art Work}?**

SELECT ?artwork ?label ?value WHERE {  <br>
?artwork rdfs:label ?label .  <br>
?artwork **cidoc:P108i_was_produced_by** ?prod .  <br> 
?prod **cidoc:P14_carried_out_by** ?actor .  <br>
?actor rdfs:label ?value  <br>
}  <br>

**Q5. Which is the birth place of {Artist}?**

SELECT distinct ?actor ?label ?placeLabel WHERE { <br>
?actor rdfs:label ?label .  <br>
?actor **cidoc:P92i_was_brought_into_existence_by ?existence** .  <br>
?existence **cidoc:P7_took_place_at** ?place . <br>
?place rdfs:label ?placeLabel . <br>
} <br>

<h2>Question Templates of Depth 3</h2>

**Q6. When the {Art Work} started?** 

SELECT ?artwork ?label ?date WHERE { <br>
?artwork rdfs:label ?label . <br>
?artwork **cidoc:P108i_was_produced_by** ?prod .  <br>
?prod **cidoc:P4_has_time-span** ?tsp .  <br>
?tsp **cidoc:P82a_begin_of_the_begin** ?date <br>
}  <br>

**Q7. When the production of {Art Work} ended?** 

SELECT ?artwork ?label ?endDate WHERE { <br>
?artwork rdfs:label ?label . <br>
?artwork **cidoc:P108i_was_produced_by** ?prod .  <br>
?prod **cidoc:P4_has_time-span** ?tsp .  <br>
?tsp **cidoc:P82b_end_of_the_end** ?endDate <br>
}  <br>

**Q8. Which is the nationality of the creator of {Art Work}?** 

SELECT ?artwork ?label ?nationality WHERE { <br>
?artwork rdfs:label ?label .   <br>
?artwork **cidoc:P108i_was_produced_by** ?prod .    <br>
?prod **cidoc:P14_carried_out_by** ?actor .    <br>
?actor **cidoc:P107i_is_current_or_former_member_of** ?country .    <br>
?country rdfs:label ?nationality   <br>
}  <br>

<h2>Question Templates of Depth 4 </h2>

**Q9. Which is the birth place of the creator of {Art Work}?** 

SELECT ?artwork ?label ?place WHERE { <br>
?artwork rdfs:label ?label . <br>
?artwork **cidoc:P108i_was_produced_by** ?prod .  <br>
?prod **cidoc:P14_carried_out_by** ?actor .  <br>
?actor **cidoc:P92i_was_brought_into_existence_by** ?existence .  <br>
?existence **cidoc:P7_took_place_at** ?placeLabel . <br>
?place rdfs:label ?placeLabel . <br>
}  <br>



**Q10. Which year died the creator of {Art Work}?** 

SELECT ?artwork ?label ?deathYear WHERE { <br>
?artwork rdfs:label ?label . <br>
?artwork **cidoc:P108i_was_produced_by** ?prod .  <br>
?prod **cidoc:P14_carried_out_by** ?actor .  <br>
?actor **cidoc:P93i_was_taken_out_of_existence_by** ?out .    <br>
?out **cidoc:P4_has_time-span** ?ts .   <br>
?ts rdfs:label ?deathYear   <br>
}  <br>



<h1>Code for Creating the Evaluation Benchmark</h1>
The JAVA code for creating the evaluation benchmark can be found inside the folder  **Code_for_creating_the_benchmark**.


<h1>SPARQL Queries for Creating Subgraphs through Graph DB</h1>

