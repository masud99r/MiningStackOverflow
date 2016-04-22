/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miningstackoverflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author mr5ba
 */
public class MiningStackOverflow {
    public static String pathxml =  "C:/Development/NetbeanProjects/data/miningstackoverflow/Posts.xml";
   // public static String pathxml =  "C:/Development/NetbeanProjects/data/miningstackoverflow/Posts_sample.xml";
    public static String writepath = "C:/Development/NetbeanProjects/data/miningstackoverflow/post_title_java.txt";
    public static String rootpath = "C:/Development/NetbeanProjects/data/miningstackoverflow/";
    /**
     * @param args the command line arguments
     */
    HashMap<String, Integer> tags_map;
    public static boolean ASC = true;
    public static boolean DESC = false;
    
    public MiningStackOverflow(){
        tags_map = new HashMap<>();
    }
    public static void main(String[] args) {
        // TODO code application logic here
        MiningStackOverflow mso = new MiningStackOverflow();
        mso.getPosts(pathxml,writepath);
    }
private void getPosts(String readfilepath, String writepath){
    BufferedReader reader=null;
    BufferedWriter bw = null;
    int skipped_count =0;
    try {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(readfilepath), "UTF-8"));
        FileWriter fw = new FileWriter(new File(writepath));
        bw = new BufferedWriter(fw);
        String line;
        int countpost=0;
    while ((line = reader.readLine()) != null) {
        try{
        line = line.trim();
        if(line.startsWith("<row")){
            String[] postid_parts = line.split("PostTypeId");
            String postid = postid_parts[1].trim().split("\"")[1].trim();
            if(postid.equals("1")){//for question
                String[] tag_parts = line.split("Tags")[1].split("\"");
                String tags = tag_parts[1].trim().replaceAll("&lt", " ");
                tags = tags.replaceAll(";", " ");
                tags = tags.replaceAll("&gt", " ");
                tags = tags.replaceAll("\\s+", " ");//remove multiple space
                String[] tag_list = tags.trim().split(" ");
                ArrayList<String> tags_arraylist = new ArrayList<String>();
                
                for(int i=0;i<tag_list.length;i++){
                    String tag_key=tag_list[i];
                    tags_arraylist.add(tag_key);  
                }
                if(tags_arraylist.contains("java")){
                    //System.out.println("*******************************");
                    String[] line_tokens = line.split("Title");
                    String title = line_tokens[1].trim().split("\"")[1].trim();
                    bw.write(title+"\n");
                    countpost++;
                    //add tag to hashmap
                    for(int j = 0;j<tags_arraylist.size();j++){
                        String tag_java = tags_arraylist.get(j);
                        Integer n = tags_map.get(tag_java);
                        if(n==null){
                            tags_map.put(tag_java, 1);
                        }else{
                            n=n+1;
                            tags_map.put(tag_java, n);
                        }
                    }
                    if(countpost%100000==0){
                        System.out.println("Written: "+countpost + " Skipped: "+skipped_count);
                    }
                    //System.out.println("Title= "+title);
                }
            }
       }
    }catch(Exception e){
        skipped_count++;
         //   System.out.println("Not well formatted, skipping ..");
         ///e.printStackTrace();
    }
   }
  } catch(IOException e){
        System.err.format("[Error]Failed to open file %s!!", readfilepath);
   }
   finally{
        if(reader !=null){
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(MiningStackOverflow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(bw!=null)
            try {
                bw.close();
        } catch (IOException ex) {
            Logger.getLogger(MiningStackOverflow.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    tags_map = sortByComparator(tags_map, DESC);
    writeMapOnFile(tags_map, "tags_java.txt");
}
    private void getPost(String readfilepath, String writepath){
        BufferedWriter bw = null;
        try {
      File inputFile = new File(readfilepath);
      DocumentBuilderFactory dbFactory 
         = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputFile);
      doc.getDocumentElement().normalize();
      
      
  
    
     FileWriter fw = new FileWriter(new File(writepath));
     bw = new BufferedWriter(fw);
 // System.out.println("Root element :"+ doc.getDocumentElement().getNodeName());
    NodeList nList = doc.getElementsByTagName("row");
    int countpost=0;
    for (int temp = 0; temp < nList.getLength(); temp++) {
         Node nNode = nList.item(temp);
        // System.out.println("\nCurrent Element :" 
         //   + nNode.getNodeName());
         if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            //System.out.println("table elements : "+ eElement.getAttribute("column"));
          //  String column0r = eElement.getElementsByTagName("title").item(0).getTextContent();
            // System.out.println("Question: "+eElement.getAttribute("PostTypeId"));
             int post_type = Integer.parseInt(eElement.getAttribute("PostTypeId"));   
             if(post_type==1){//question
               // System.out.println("Title: "+eElement.getAttribute("Title"));
                bw.write(eElement.getAttribute("Title")+"\n");
                countpost++;
                if(countpost%10000==0){
                    System.out.println("Questions title written: "+countpost);
                }
             }
           }
        }
    }catch (Exception e) {
        e.printStackTrace();
   }
        finally
   { 
      try{
         if(bw!=null)
            bw.close();
      }catch(Exception ex){
          System.out.println("Error in closing the BufferedWriter"+ex);
       }

   }
  }
public void writeMapOnFile(HashMap<String,Integer>wmap,String fileName)
{
   BufferedWriter bw = null;
   try {
    File file = new File(fileName);
     if (!file.exists()) {
        file.createNewFile();
     }
     FileWriter fw = new FileWriter(rootpath+file);
     bw = new BufferedWriter(fw);
    for (String name: wmap.keySet()){
       String key =name;
       String value = wmap.get(name).toString();
       bw.write(key+" "+value+"\n");
    }
    System.out.println("Write Successfull: "+fileName);

 } catch (IOException ioe) {
      ioe.printStackTrace();
   }
   finally
   { 
      try{
         if(bw!=null)
            bw.close();
      }catch(Exception ex){
          System.out.println("Error in closing the BufferedWriter"+ex);
       }

   }

}
 private static HashMap<String, Integer> sortByComparator(HashMap<String, Integer> unsortMap, final boolean order)
{

    List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

    // Sorting the list based on values
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
    {
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2)
        {
            if (order)
            {
                return o1.getValue().compareTo(o2.getValue());
            }
            else
            {
                return o2.getValue().compareTo(o1.getValue());

            }
        }
    });

    // Maintaining insertion order with the help of LinkedList
    HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
    for (Map.Entry<String, Integer> entry : list)
    {
        sortedMap.put(entry.getKey(), entry.getValue());
    }

    return sortedMap;
}   
}
