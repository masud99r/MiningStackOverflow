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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mr5ba
 */
public class MiningStackOverflow {
    public static String pathxml =  "C:/Development/NetbeanProjects/data/miningstackoverflow/Posts.xml";
   // public static String pathxml =  "C:/Development/NetbeanProjects/data/miningstackoverflow/Posts_sample.xml";
    public static String writepath = "C:/Development/NetbeanProjects/data/miningstackoverflow/Android_Posts_title.txt";
    public static String rootpath = "C:/Development/NetbeanProjects/data/miningstackoverflow/";
    public static String readpath = "C:/Development/NetbeanProjects/data/miningstackoverflow/Android_Posts_title.txt";
    
    /**
     * @param args the command line arguments
     */
    HashMap<String, Integer> tags_map;
    
    
    public static boolean ASC = true;
    public static boolean DESC = false;
    
    //setting
    public static boolean answerOK=true;
    public static String targetTAG="android";
    public static boolean allTags=false;
    public static boolean allPosts=false;
    
    public MiningStackOverflow(){
       
        tags_map = new HashMap<>();
        
    }
public static void main(String[] args) {
     HashSet<Post>post_set;
     HashMap<String, ArrayList<String>> title_tags;
    // TODO code application logic here
    MiningStackOverflow mso = new MiningStackOverflow();
    title_tags = mso.loadTitles(readpath);
    //System.out.println("Title map size: "+title_tags.size());
    ArrayList<String> baseType1 = new ArrayList<>();
    baseType1.add("android-maps");
    baseType1.add("android-emulator");
    baseType1.add("android-layout");
    baseType1.add("android-studio");
    baseType1.add("android-debugging");

    double param = 0.5;
   
    mso.rankTitle(title_tags, baseType1, param);
    //mso.writePosts(post_set, "Title", "Android_Posts_title.txt");
     //post_set = mso.getPosts(pathxml,writepath);
}
private void rankTitle(HashMap<String, ArrayList<String>> titleMap, ArrayList<String> baseTagList,double wparam){
    HashMap<String, Double> ranks = new HashMap<>();
    for(String title:titleMap.keySet()){
        ArrayList<String> postTagList = titleMap.get(title);
        double score = calculateScore(postTagList, baseTagList, wparam);
        ranks.put(title, score);
    }
    ranks = sortByComparatorDouble(ranks, DESC);
    writeOnFile_doubleHash(ranks,"ranks_title.txt");
    
}
private double calculateScore(ArrayList<String> postTagList, ArrayList<String> baseTagList, double weightParam){
    if(postTagList.isEmpty() || baseTagList.isEmpty()){
        return 0;
    }
    double score=0;
    double simiScore=0;
    double ratioScore=0;
    List<String> common = new ArrayList<>(baseTagList);
    common.retainAll(postTagList);
    
    simiScore = common.size();
    ratioScore = simiScore/postTagList.size();
    score = simiScore + weightParam * ratioScore;
    score =score/(baseTagList.size()+1);//normalized over base length
    //System.out.println("Post:"+postTagList.toString()+"\n \t"+baseTagList.toString()+"\n \t"+common.toString());
    return score;//[0 to 1]
}
private HashMap<String, ArrayList<String>>loadTitles(String filepath){
    HashMap<String, ArrayList<String>> titletags = new HashMap<>();
   BufferedReader reader=null;
    try {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            String title = line;
            String tags;
            if((tags=reader.readLine()) !=null){//assuming no duplicate title
                if(tags.trim().startsWith("[")){
                    tags = tags.replaceAll("\\[", "");
                    tags = tags.replaceAll("]", "");
                    String[] tags_parts = tags.trim().split(",");
                    ArrayList<String> tagList = new ArrayList<>();
                    for (String tags_part : tags_parts) {
                        tagList.add(tags_part.trim());
                    }
                    
                    titletags.put(title, tagList);
                   // System.out.println("Title: "+title+"="+tagList.toString());
                }
            }
        }
        
    }catch(Exception e){
        e.printStackTrace();
    }
   finally{
        if(reader !=null){
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(MiningStackOverflow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
   }
    return titletags;
}
public void writeOnFile_doubleHash(HashMap<String,Double>wmap,String fileName)
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
private HashSet<Post> getPosts(String readfilepath, String writepath){
    HashSet<Post>postSet = new HashSet<>();
    BufferedReader reader=null;
    BufferedWriter bw = null;
    int skipped_count =0;
    try {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(readfilepath), "UTF-8"));
        FileWriter fw = new FileWriter(new File(writepath));
        bw = new BufferedWriter(fw);
        String line;
        int countpost=0;
        Post p= new Post();
    while ((line = reader.readLine()) != null) {
        try{
        line = line.trim();
        if(line.startsWith("<row")){
            p= new Post();
            String[] Id_parts = line.split("Id");
             if(Id_parts.length>1){
            String Id = Id_parts[1].trim().split("\"")[1].trim();
                p.setId(Id);
             }
            String[] PostTypeId_parts = line.split("PostTypeId");
            String PostTypeId="";
             if(PostTypeId_parts.length>1){
                PostTypeId = PostTypeId_parts[1].trim().split("\"")[1].trim();
                p.setPostTypeId(PostTypeId);
             }
            if(PostTypeId.equals("1") || allPosts){//question
               
               String[] tag_parts_line = line.split("Tags");
               
               String tags="";
               if(tag_parts_line.length>1){
               String[] tag_parts =tag_parts_line[1].split("\"");
                tags = tag_parts[1].trim().replaceAll("&lt", " ");
                tags = tags.replaceAll(";", " ");
                tags = tags.replaceAll("&gt", " ");
                tags = tags.replaceAll("\\s+", " ");//remove multiple space
               }
                String[] tag_list = tags.trim().split(" ");
                ArrayList<String> tags_arraylist = new ArrayList<>();
                
                tags_arraylist.addAll(Arrays.asList(tag_list));
             if(tags_arraylist.contains(targetTAG) || allTags){
               
                p.setTags(tags_arraylist);
               
               
               String[] ParentId_parts = line.split("ParentId");
                  if(ParentId_parts.length>1){
                    String ParentId = ParentId_parts[1].trim().split("\"")[1].trim();
                     p.setParentId(ParentId);
                   }
              String[] AcceptedAnswerId_parts = line.split("AcceptedAnswerId");
               if(AcceptedAnswerId_parts.length>1){
                String AcceptedAnswerId = AcceptedAnswerId_parts[1].trim().split("\"")[1].trim();
                p.setAcceptedAnswerId(AcceptedAnswerId);
               }
               String[] CreationDate_parts = line.split("CreationDate");
               if(CreationDate_parts.length>1){
               String CreationDate = CreationDate_parts[1].trim().split("\"")[1].trim();
                p.setCreationDate(CreationDate);
               }
               String[] Score_parts = line.split("Score");
               if(Score_parts.length>1){
                String Score = Score_parts[1].trim().split("\"")[1].trim();
                p.setScore(Integer.parseInt(Score));
               }
               
               String[] ViewCount_parts = line.split("ViewCount");
               if(ViewCount_parts.length>1){
                String ViewCount = ViewCount_parts[1].trim().split("\"")[1].trim();
                p.setViewCount(Integer.parseInt(ViewCount));
               }
               
               String[] Body_parts = line.split("Body");
                if(Body_parts.length>1){
                    String Body = Body_parts[1].trim().split("\"")[1].trim();
                    p.setBody(Body);
                }
               String[] OwnerUserId_parts = line.split("OwnerUserId");
               if(OwnerUserId_parts.length>1){
                String OwnerUserId = OwnerUserId_parts[1].trim().split("\"")[1].trim();
                p.setOwnerUserId(OwnerUserId);
               }
               String[] LastEditorUserId_parts = line.split("LastEditorUserId");
               if(LastEditorUserId_parts.length>1){
                    String LastEditorUserId = LastEditorUserId_parts[1].trim().split("\"")[1].trim();
                    p.setLastEditorUserId(LastEditorUserId);
               }
               String[] LastEditorDisplayName_parts = line.split("LastEditorDisplayName");
                if(LastEditorDisplayName_parts.length>1){
                    String LastEditorDisplayName = LastEditorDisplayName_parts[1].trim().split("\"")[1].trim();
                    p.setLastEditorDisplayName(LastEditorDisplayName);
                }
               String[] LastEditDate_parts = line.split("LastEditDate");
                if(LastEditDate_parts.length>1){
                    String LastEditDate = LastEditDate_parts[1].trim().split("\"")[1].trim();
                    p.setLastEditDate(LastEditDate);
                }
               
               String[] LastActivityDate_parts = line.split("LastActivityDate");
               if(LastActivityDate_parts.length>1){
                    String LastActivityDate = LastActivityDate_parts[1].trim().split("\"")[1].trim();
                    p.setLastActivityDate(LastActivityDate);
               }
               String[] Title_parts = line.split("Title");
                if(Title_parts.length>1){
                    String Title = Title_parts[1].trim().split("\"")[1].trim();
                    p.setTitle(Title);
                }
               String[] AnswerCount_parts = line.split("AnswerCount");
               if(AnswerCount_parts.length>1){
                    String AnswerCount = AnswerCount_parts[1].trim().split("\"")[1].trim();
                    p.setAnswerCount(Integer.parseInt(AnswerCount));
               }
               String[] CommentCount_parts = line.split("CommentCount");
               if(CommentCount_parts.length>1){
                    String CommentCount = CommentCount_parts[1].trim().split("\"")[1].trim();
                    p.setCommentCount(Integer.parseInt(CommentCount));
               }
               
               String[] FavoriteCount_parts = line.split("FavoriteCount");
               if(FavoriteCount_parts.length>1){
                String FavoriteCount = FavoriteCount_parts[1].trim().split("\"")[1].trim();
                p.setFavoriteCount(Integer.parseInt(FavoriteCount));
               }
               String[] CommunityOwnedDate_parts = line.split("CommunityOwnedDate");
               if(CommunityOwnedDate_parts.length>1){
                    String CommunityOwnedDate = CommunityOwnedDate_parts[1].trim().split("\"")[1].trim();
                    p.setCommunityOwnedDate(CommunityOwnedDate);
               }
               
            }
            }
            countpost++;
            if(countpost%10000==0){
                System.out.println("Written: "+countpost + " Skipped: "+skipped_count);
             }
            //postSet.add(p);
              bw.write(p.getTitle()+"\n"+p.getTags().toString()+"\n");      
            // System.out.println(p); 
            
       }
    }catch(Exception e){
        skipped_count++;
       // System.out.println(p); 
         //   System.out.println("Not well formatted, skipping ..");
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
    return postSet;
}
    private void getPostsTitle(String readfilepath, String writepath){
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
public void writePosts(HashSet<Post>postset,String field, String fileName)
{
   BufferedWriter bw = null;
   try {
    File file = new File(fileName);
     if (!file.exists()) {
        file.createNewFile();
     }
     FileWriter fw = new FileWriter(rootpath+file);
     bw = new BufferedWriter(fw);
    for (Post p: postset){
        if(field.equals("Title")){
            String title = p.getTitle();
            ArrayList<String> tagslist = p.getTags();
            if(title !=null){
                bw.write(title+"\n");
                bw.write(tagslist.toString()+"\n");
            }
        }
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
//for double sorting
    private static HashMap<String, Double> sortByComparatorDouble(HashMap<String, Double> unsortMap, final boolean order)
    {

        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>()
        {
            public int compare(Map.Entry<String, Double> o1,
                    Map.Entry<String, Double> o2)
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
        HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
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
