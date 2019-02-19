package com.ufcg.les.aep.util.service;

import com.ufcg.les.aep.model.media.AbstractMedia;

import java.util.ArrayList;
import java.util.List;

public class UserImageStorage {
  private static UserImageStorage instance;
  
  public static UserImageStorage getInstance() {
    if (instance == null) {
      instance = new UserImageStorage();
    }
    return instance;
  }
  
  private List<AbstractMedia> mediaList;
   private UserImageStorage() {
     this.mediaList = new ArrayList<>();
   }
   
   public AbstractMedia getByPosition(final int position) {
     return mediaList.get(position);
   }
   
   public int getItemCount() {
     return mediaList.size();
   }
  
  
  public void add(final AbstractMedia abstractMedia) {
    this.mediaList.add(abstractMedia);
  }
}
