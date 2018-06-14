package com.ufcg.les.aep.model.post;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.ufcg.les.aep.util.MediaUtil;
import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.util.TagType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Post implements Serializable , Comparable<Post>{
  private static final long serialVersionUID = 690241872815232514L;
  
  private static int postId;
  
  private Date creationDate;
  private int id;
  private String title;
  private String description;
  private List<AbstractMedia> mediaList;
  private List<Tag> tags;
  private TagType tagType;

  
  public Post(String title, String description, List<AbstractMedia> mediaList, List<Tag> tags,TagType tagType) {
    this.creationDate = new Date();
    this.id = postId++;
    this.title = title;
    this.description = description;
    this.mediaList = mediaList;
    this.tags = tags;
    this.tagType = tagType;
  }
  
  public Date getCreationDate() {
    return creationDate;
  }

  public TagType getTagType() {
    return tagType;
  }

  public void setTagType(TagType tagType) {
    this.tagType = tagType;
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
