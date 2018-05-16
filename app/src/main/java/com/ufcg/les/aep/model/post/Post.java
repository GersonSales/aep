package com.ufcg.les.aep.model.post;

import android.graphics.Bitmap;
import java.io.Serializable;
import java.util.List;

public class Post implements Serializable {
  private static final long serialVersionUID = 690241872815232514L;
  
  private static int postId;
  
  private int id;
  private String title;
  private String description;
  private List<Bitmap> images;
  private List<Tag> tags;
  
  public Post(String title, String description, List<Bitmap> images, List<Tag> tags) {
    this.id = postId++;
    this.title = title;
    this.description = description;
    this.images = images;
    this.tags = tags;
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
  
  public List<Bitmap> getImages() {
    return images;
  }
  
  public void setImages(List<Bitmap> images) {
    this.images = images;
  }
  
  public List<Tag> getTags() {
    return tags;
  }
  
  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }
}
