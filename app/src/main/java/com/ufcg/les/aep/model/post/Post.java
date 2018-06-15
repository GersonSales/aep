package com.ufcg.les.aep.model.post;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.media.AbstractMedia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Post implements Serializable, Comparable<Post> {
  private static final long serialVersionUID = 690241872815232514L;
  
  private static int postId;
  
  private Date creationDate;
  private int id;
  private String title;
  private String description;
  private List<AbstractMedia> mediaList;
  private List<Tag> tags;
  private String tagType;
  private String nameContact;
  private String numberContact;
  private String emailContact;
  
  public Post(String title, String description, List<AbstractMedia> mediaList, List<Tag> tags, String tagType) {
    this.creationDate = new Date();
    this.id = postId++;
    this.title = title;
    this.description = description;
    this.mediaList = mediaList;
    this.tags = tags;
    this.tagType = tagType;
  }
  
  public String getNameContact() {
    return nameContact;
  }
  
  public void setNameContact(String nameContact) {
    this.nameContact = nameContact;
  }
  
  public String getNumberContact() {
    return numberContact;
  }
  
  public void setNumberContact(String numberContact) {
    this.numberContact = numberContact;
  }
  
  public String getEmailContact() {
    return emailContact;
  }
  
  public void setEmailContact(String emailContact) {
    this.emailContact = emailContact;
  }
  
  public Date getCreationDate() {
    return creationDate;
  }
  
  public String getTagType() {
    return tagType;
  }
  
  public void setTagType(String tagType) {
    this.tagType = tagType;
  }
  
  public int getTypeColor() {
    if (getTagType().equals(Tag.ACHADO)) {
      return R.color.foundTagColor;
    }
    return R.color.lostTagColor;
    
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public List<AbstractMedia> getMediaList() {
    return mediaList;
  }
  
  public List<Tag> getTags() {
    return tags;
  }
  
  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }
  
  @Override
  public int compareTo(@NonNull final Post post) {
    return post.getCreationDate().compareTo(getCreationDate());
  }
  
  public Bitmap getMainImage() {
    return mediaList.isEmpty() ? null : mediaList.get(0).getThumbnail();
  }
}
