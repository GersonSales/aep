package com.ufcg.les.aep.model.post;

import java.io.Serializable;

public final class Tag implements Serializable{
  private static final long serialVersionUID = 3899416338348004627L;
  public static final String ACHADO = "achado";
  public static final String PERDIDO = "perdido";
  
  private final String name;
  
  public Tag(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
}
