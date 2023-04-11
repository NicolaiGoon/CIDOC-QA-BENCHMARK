/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cidocqa_benchmark;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;


public class CIDOCQA_Benchmark {

    
    static String birthActor="PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/>\n" +
"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
"SELECT distinct ?actor ?la1 ?place WHERE {\n" +
"?actor rdfs:label ?la1 . \n" +
"?actor <http://www.cidoc-crm.org/cidoc-crm/P92i_was_brought_into_existence_by> ?x . \n" +
"?x <http://www.cidoc-crm.org/cidoc-crm/P7_took_place_at> ?z .\n" +
"?z rdfs:label ?place .\n" +
"} limit 1";
    
    static String typeQuery = "PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/>\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?d ?l WHERE {\n"
            + "?d rdfs:label ?lab .\n"
            + "?d cidoc:P2_has_type ?o . ?o rdfs:label ?l\n"
            + "}";

    static String giftQuery = "PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/>\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?d  ?o1 WHERE {\n"
            + "?d rdfs:label ?lab .\n"
            + "?d <http://www.cidoc-crm.org/cidoc-crm/P67i_is_referred_to_by> ?pr . ?pr rdf:value ?o1  .FILTER(REGEX(STR(?o1) , \"Gift of\" ) || REGEX(STR(?o1) , \"Bequest of\" )) \n"
            + "} ";

    static String startedQuery = "PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/>\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?d ?o1 WHERE {\n"
            + "?d rdfs:label ?lab .\n"
            + "?d cidoc:P108i_was_produced_by ?pr \n"
            + ". ?pr cidoc:P4_has_time-span ?sp \n"
            + ". ?sp cidoc:P82a_begin_of_the_begin ?o1\n"
            + "} ";
    
    static String endedQuery = "PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/>\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?d ?o1 WHERE {\n"
            + "?d rdfs:label ?lab .\n"
            + "?d cidoc:P108i_was_produced_by ?pr \n"
            + ". ?pr cidoc:P4_has_time-span ?sp \n"
            + ". ?sp cidoc:P82b_end_of_the_end ?o1\n"
            + "} ";

    static String nationalityQuery = "PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/>\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?d ?value WHERE {\n"
            + "?d rdfs:label ?lab .\n"
            + "?d <http://www.cidoc-crm.org/cidoc-crm/P108i_was_produced_by> ?prod . \n"
            + "?prod <http://www.cidoc-crm.org/cidoc-crm/P14_carried_out_by> ?actor . \n"
            + "?actor <http://www.cidoc-crm.org/cidoc-crm/P107i_is_current_or_former_member_of> ?x . \n"
            + "?x rdfs:label ?value\n"
            + "} ";

    static String birthPlace = "PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/>\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?d ?place WHERE {\n"
            + "?d rdfs:label ?lab .\n"
            + "?d <http://www.cidoc-crm.org/cidoc-crm/P108i_was_produced_by> ?prod . \n"
            + "?prod <http://www.cidoc-crm.org/cidoc-crm/P14_carried_out_by> ?actor . \n"
            + "?actor <http://www.cidoc-crm.org/cidoc-crm/P92i_was_brought_into_existence_by> ?x . \n"
            + "?x <http://www.cidoc-crm.org/cidoc-crm/P7_took_place_at> ?z .\n"
            + "?z rdfs:label ?place .\n"
            + "} ";
    
    
        static String deathYear = "PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/>\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?d ?z WHERE {\n"
            + "?d rdfs:label ?lab .\n"
            + "?d <http://www.cidoc-crm.org/cidoc-crm/P108i_was_produced_by> ?prod . \n"
            + "?prod <http://www.cidoc-crm.org/cidoc-crm/P14_carried_out_by> ?actor . \n"
            + "?actor <http://www.cidoc-crm.org/cidoc-crm/P93i_was_taken_out_of_existence_by> ?x . \n"
            + "?x <http://www.cidoc-crm.org/cidoc-crm/P4_has_time-span> ?ts . \n"
            + "?ts rdfs:label ?z .\n"
            + "} ";

    static String creatorOf = "PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/>\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?d  ?val WHERE {\n"
            + "?d rdfs:label ?lab .\n"
            + "?d <http://www.cidoc-crm.org/cidoc-crm/P108i_was_produced_by> ?prod \n"
            + ". ?prod <http://www.cidoc-crm.org/cidoc-crm/P14_carried_out_by> ?actor \n"
            + ". ?actor rdfs:label ?val\n"
            + "} ";
    
    
    static String materialUsed = "PREFIX cidoc: <http://www.cidoc-crm.org/cidoc-crm/>\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "SELECT ?d  ?val WHERE {\n"
            + "?d rdfs:label ?lab .\n"
            + "?d <http://www.cidoc-crm.org/cidoc-crm/P67i_is_referred_to_by> ?pr .\n"
            + "?pr rdf:value ?val\n"
            + " .filter(STRENDS(str(?pr),'medium'))} ";
    
    

    public static void main(String[] args) throws MalformedURLException, IOException {
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        ArrayList<String> works = BENCH.retrieveLabels("SELECTED_ENTITIES.txt");
        String endpoint = "https://triplydb.com/smithsonian/american-art-museum/sparql/american-art-museum";
        //String label="\"Marguerites\"";
        //BENCH.createQuestionsForCreatorOf(works, endpoint);
       //BENCH.createQuestionsForTypeQuery(works, endpoint);
      // BENCH.createQuestionsForBeginning(works, endpoint);
      // BENCH.createQuestionsForNationalityOfCreator(works, endpoint);
     // BENCH.createQuestionsForBirthPlaceOfCreator(works, endpoint);
    // BENCH.createQuestionsForGiftORBequest(works, endpoint);
   // BENCH.createQuestionsForBirthActor(works, endpoint);
   // BENCH.createQuestionsForCreatorDeath(works, endpoint);
//   BENCH.createQuestionsForMaterialUsed(works, endpoint);
BENCH.createQuestionsForEnd(works, endpoint);
    }
    
    
 public void createQuestionsForMaterialUsed(ArrayList<String> works,String endpoint) throws MalformedURLException, IOException{
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        int id=1;
         System.out.println("[");
         for (String label : works) {
            String lab=label.replaceAll("\"", "");
            String result=BENCH.getJSON(CIDOCQA_Benchmark.materialUsed, "What material used for creating "+lab+"?", "is_referred_to_by", endpoint, label, id);
            if(result!=null){
                id=id+1;
                System.out.println(result+",");
            }
        }
        System.out.println("]");
    }    
    
       public void createQuestionsForCreatorDeath(ArrayList<String> works,String endpoint) throws MalformedURLException, IOException{
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        int id=1;
         System.out.println("[");
         for (String label : works) {
            String lab=label.replaceAll("\"", "");
            String result=BENCH.getJSON(CIDOCQA_Benchmark.deathYear, "Which year died the  creator of "+lab+"?", "hastimespan", endpoint, label, id);
            if(result!=null){
                id=id+1;
                System.out.println(result+",");
            }
        }
        System.out.println("]");
    }
    
    
     public void createQuestionsForBirthActor(ArrayList<String> works,String endpoint) throws MalformedURLException, IOException{
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        int id=1;
         System.out.println("[");
         for (int i=0;i<1900;i++) {
            String result=BENCH.getJSON(CIDOCQA_Benchmark.birthActor+" offset "+i, "Which is the birth place of #X#?", "took_place_at", endpoint, "", id);
            if(result!=null){
                id=id+1;
                System.out.println(result+",");
            }
        }
        System.out.println("]");
    }    
    
    
 public void createQuestionsForGiftORBequest(ArrayList<String> works,String endpoint) throws MalformedURLException, IOException{
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        int id=1;
         System.out.println("[");
         for (String label : works) {
            String lab=label.replaceAll("\"", "");
            String result=BENCH.getJSON(CIDOCQA_Benchmark.giftQuery, "Which gave "+lab+" to the museum?", "is_referred_to_by", endpoint, label, id);
            if(result!=null){
                id=id+1;
                System.out.println(result+",");
            }
        }
        System.out.println("]");
    }    
    
    
    public void createQuestionsForBirthPlaceOfCreator(ArrayList<String> works,String endpoint) throws MalformedURLException, IOException{
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        int id=1;
         System.out.println("[");
         for (String label : works) {
            String lab=label.replaceAll("\"", "");
            String result=BENCH.getJSON(CIDOCQA_Benchmark.birthPlace, "Which is the birth place of the creator of "+lab+"?", "tookPlaceAt", endpoint, label, id);
            if(result!=null){
                id=id+1;
                System.out.println(result+",");
            }
        }
        System.out.println("]");
    }
    
    public void createQuestionsForNationalityOfCreator(ArrayList<String> works,String endpoint) throws MalformedURLException, IOException{
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        int id=1;
         System.out.println("[");
         for (String label : works) {
            String lab=label.replaceAll("\"", "");
            String result=BENCH.getJSON(CIDOCQA_Benchmark.nationalityQuery, "Which is the nationality of the creator of "+lab+"?", "is_former_or_current_member_of", endpoint, label, id);
            if(result!=null){
                id=id+1;
                System.out.println(result+",");
            }
        }
        System.out.println("]");
    }
    
      public void createQuestionsForEnd(ArrayList<String> works,String endpoint) throws MalformedURLException, IOException{
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        int id=1;
         System.out.println("[");
         for (String label : works) {
            String lab=label.replaceAll("\"", "");
            String result=BENCH.getJSON(CIDOCQA_Benchmark.endedQuery, "When the production of "+lab+" ended?", "end_of_the_end", endpoint, label, id);
            if(result!=null){
                id=id+1;
                System.out.println(result+",");
            }
        }
        System.out.println("]");
    }

    
    
      public void createQuestionsForBeginning(ArrayList<String> works,String endpoint) throws MalformedURLException, IOException{
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        int id=1;
         System.out.println("[");
         for (String label : works) {
            String lab=label.replaceAll("\"", "");
            String result=BENCH.getJSON(CIDOCQA_Benchmark.startedQuery, "When "+lab+" started?", "begin_of_the_begin", endpoint, label, id);
            if(result!=null){
                id=id+1;
                System.out.println(result+",");
            }
        }
        System.out.println("]");
    }

    
    public void createQuestionsForTypeQuery(ArrayList<String> works,String endpoint) throws MalformedURLException, IOException{
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        int id=1;
         System.out.println("[");
         for (String label : works) {
            String lab=label.replaceAll("\"", "");
            String result=BENCH.getJSON(CIDOCQA_Benchmark.typeQuery, "Which is the art type of " + lab + "?", "has_type", endpoint, label, id);
            if(result!=null){
                id=id+1;
                System.out.println(result+",");
            }
        }
        System.out.println("]");
    }
    
    
    
    public void createQuestionsForCreatorOf(ArrayList<String> works,String endpoint) throws MalformedURLException, IOException{
        CIDOCQA_Benchmark BENCH = new CIDOCQA_Benchmark();
        int id=1;
         System.out.println("[");
         for (String label : works) {
            String lab=label.replaceAll("\"", "");
            String result=BENCH.getJSON(CIDOCQA_Benchmark.creatorOf, "Who is the creator of " + lab + "?", "carried_out_by", endpoint, label, id);
            if(result!=null){
                id=id+1;
                System.out.println(result+",");
            }
        }
        System.out.println("]");
    }
    
    
    public ArrayList<String> retrieveLabels(String file) {
        ArrayList<String> works = new ArrayList<String>();
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
               // System.out.println(data);
                works.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return works;
    }

    public String getJSON(String qType, String question, String CIDOCtype, String endpoint, String label, int id) throws UnsupportedEncodingException, MalformedURLException, IOException {
        String query = qType.replace("?lab", label);
        String sparqlQueryURL = endpoint + "?query=" + URLEncoder.encode(query, "utf8");
        URL url = new URL(sparqlQueryURL);
        URLConnection con = url.openConnection();
        String type = "text/tab-separated-values";
        con.setRequestProperty("ACCEPT", type);
        String template = "{\n    \"id\":" + id + ",\n"
                + "    \"question\": \"" + question + "\",\n"
                + "    \"entity\": ***,\n"
                + "    \"answers\": [],\n"
                + "    \"type\": \"" + CIDOCtype + "\"\n}";
        InputStream is = con.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "utf8");
        BufferedReader in = new BufferedReader(isr);

        String input;
        String resultsString = "";
        int count = 0;
        in.readLine();
        while ((input = in.readLine()) != null) {
            if (input.endsWith("?")) {
                continue;
            } else{
                String[] split = input.split("\t");
                if(split.length==2){
                template = template.replace("***", "\"" + split[0] + "\"");
                template = template.replace("[]", "[" + split[1] + "]");
                }
                else  if(split.length==3){
                template = template.replace("***", "\"" + split[0] + "\"");
                template = template.replace("#X#", "" + split[1].replace("\"","") + "");
                template = template.replace("[]", "[" + split[2] + "]");
                }
                
                count = 1;
            }
        }
        in.close();
        isr.close();
        is.close();
        if (count == 1) {
           // System.out.println(template);
            return template;
        } else {
            return null;
        }

    }

}
