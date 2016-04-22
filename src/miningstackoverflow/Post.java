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
    private String ParentID;
    private String AcceptedAnswerId;
    private String CreationDate;
    private String Score;
    private String ViewCount;
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
    private int AnswerCount;
    private int CommentCount;
    private int FavoriteCount;
    
    public String getId(){
        return this.Id;
    }
    public String getPostTypeId(){
        return this.PostTypeId;
    }
    public String getParentID(){
        return this.ParentID;  
    }
    public String getAcceptedAnswerId(){
        return this.AcceptedAnswerId;  
    }
    public String getCreationDate(){
        return this.CreationDate;  
    }
    public String getScore(){
        return this.Score;  
    }
    public String getViewCount(){
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
    public void setParentID(String ParentID){
        this.ParentID=ParentID;  
    }
    public void setAcceptedAnswerId(String AcceptedAnswerId){
        this.AcceptedAnswerId=AcceptedAnswerId;  
    }
    public void setCreationDate(String CreationDate){
        this.CreationDate=CreationDate;  
    }
    public void setScore(String Score){
        this.Score=Score;  
    }
    public void setViewCount(String ViewCount){
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
}
