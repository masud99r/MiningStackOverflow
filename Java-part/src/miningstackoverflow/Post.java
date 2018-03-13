/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miningstackoverflow;

import java.util.ArrayList;

/**
 *
 * @author mr5ba
 */
public class Post {
    private String Id;
    private String PostTypeId;
    private String ParentId;
    private String AcceptedAnswerId;
    private String CreationDate;
    private int Score=0;
    private int ViewCount=0;
    private String Body;
    private String OwnerUserId;
    private String LastEditorUserId;
    private String LastEditorDisplayName;
    private String LastEditDate;
    private String LastActivityDate;
    private String CommunityOwnedDate;
    private String ClosedDate;
    private String Title;
    private ArrayList<String> Tags;
    private int AnswerCount=0;
    private int CommentCount=0;
    private int FavoriteCount=0;
    
    public String getId(){
        return this.Id;
    }
    public String getPostTypeId(){
        return this.PostTypeId;
    }
    public String getParentId(){
        return this.ParentId;  
    }
    public String getAcceptedAnswerId(){
        return this.AcceptedAnswerId;  
    }
    public String getCreationDate(){
        return this.CreationDate;  
    }
    public int getScore(){
        return this.Score;  
    }
    public int getViewCount(){
        return this.ViewCount;  
    }
    public String getBody(){
        return this.Body; 
    }
    public String getOwnerUserId(){
        return this.OwnerUserId;  
    }
    public String getLastEditorUserId(){
        return this.LastEditorUserId;  
    }
    public String getLastEditorDisplayName(){
        return this.LastEditorDisplayName;  
    }
    public String getLastEditDate(){
        return this.LastEditDate;  
    }
    public String getLastActivityDate(){
        return this.LastActivityDate;  
    }
    public String getCommunityOwnedDate(){
        return this.CommunityOwnedDate;  
    }
    public String getClosedDate(){
        return this.ClosedDate;  
    }
    public String getTitle(){
        return this.Title;  
    }
    public ArrayList<String> getTags(){
        return this.Tags;  
    }
    public int getAnswerCount(){
        return this.AnswerCount;  
    }
    public int getCommentCount(){
         return this.CommentCount; 
    }
    public int getFavoriteCount(){
         return this.FavoriteCount; 
    }
    
    public void setId(String Id){
     this.Id=Id;
    }
    public void setPostTypeId(String PostTypeId){
        this.PostTypeId=PostTypeId;
    }
    public void setParentId(String ParentId){
        this.ParentId=ParentId;  
    }
    public void setAcceptedAnswerId(String AcceptedAnswerId){
        this.AcceptedAnswerId=AcceptedAnswerId;  
    }
    public void setCreationDate(String CreationDate){
        this.CreationDate=CreationDate;  
    }
    public void setScore(int Score){
        this.Score=Score;  
    }
    public void setViewCount(int ViewCount){
        this.ViewCount=ViewCount;  
    }
    public void setBody(String Body){
        this.Body=Body; 
    }
    public void setOwnerUserId(String OwnerUserId){
        this.OwnerUserId=OwnerUserId;  
    }
    public void setLastEditorUserId(String LastEditorUserId){
        this.LastEditorUserId=LastEditorUserId;  
    }
    public void setLastEditorDisplayName(String LastEditorDisplayName){
        this.LastEditorDisplayName=LastEditorDisplayName;  
    }
    public void setLastEditDate(String LastEditDate){
        this.LastEditDate=LastEditDate;  
    }
    public void setLastActivityDate(String LastActivityDate){
        this.LastActivityDate=LastActivityDate;  
    }
    public void setCommunityOwnedDate(String CommunityOwnedDate){
        this.CommunityOwnedDate=CommunityOwnedDate;  
    }
    public void setClosedDate(String ClosedDate){
        this.ClosedDate=ClosedDate;  
    }
    public void setTitle(String Title){
        this.Title=Title;  
    }
    public void setTags(ArrayList<String> tags){
        this.Tags=tags;  
    }
    public void setAnswerCount(int acount){
        this.AnswerCount=acount;  
    }
    public void setCommentCount(int comcount){
        this.CommentCount=comcount; 
    }
    public void setFavoriteCount(int favcount){
        this.FavoriteCount=favcount; 
    }
    @Override
    public String toString(){
        String tag_list="";
        if(Tags !=null){
            tag_list = Tags.toString();
        }
       return "Post: \n"
               + "Id: "+Id+"\n"
               + "PostTypeId: "+PostTypeId+"\n"
               + "ParentId: "+ParentId+"\n"
               + "AcceptedAnswerId: "+AcceptedAnswerId+"\n"
               + "CreationDate: "+CreationDate+"\n"
               + "ViewCount: "+ViewCount+"\n"
               + "Body: "+Body+"\n"
               + "OwnerUserId: "+OwnerUserId+"\n"
               + "LastEditorUserId: "+LastEditorUserId+"\n"
               + "LastEditorDisplayName: "+LastEditorDisplayName+"\n"
               + "LastEditDate: "+LastEditDate+"\n"
               + "Title: "+Title+"\n"
               + "Tags: "+tag_list+"\n"
               + "AnswerCount: "+AnswerCount+"\n"
               + "CommentCount: "+CommentCount+"\n"
               + "FavoriteCount: "+FavoriteCount+"\n"
               + "CommunityOwnedDate: "+CommunityOwnedDate+"\n";
               
    }
}
