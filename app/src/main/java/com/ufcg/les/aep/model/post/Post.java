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
  
  public Post(String description, List<Bitmap> images) {
    id = postId++;
    this.description = description;
    this.images = images;
  }
  
  
  
  
  

}
