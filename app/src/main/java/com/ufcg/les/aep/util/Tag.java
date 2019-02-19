package com.ufcg.les.aep.util;

public class Tag {
  public static final String POST_INDEX = "post_index";
  public static final String POST = "post";
  public static final String DETAILS_OF = "Detalhes de";

  private String tagName;

  public Tag(String tagName) {
    this.tagName = tagName;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }
}
