package com.ufcg.les.aep.model.post;

import java.io.Serializable;

public class Post implements Serializable {
  private static final long serialVersionUID = 690241872815232514L;
  
  private String name;
  
  public Post(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }

}
