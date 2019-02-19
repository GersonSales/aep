package com.ufcg.les.aep.util.service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ufcg.les.aep.model.post.Post;

import java.util.HashMap;
import java.util.Map;

public class PostSender {
  private static final String POSTS = "/posts/";
  private static final String USER_POSTS = "/user-posts/";
  private static PostSender instance;
  private final Map<String, Map<String, Object>> postQueue;
  private DatabaseReference databaseReference;
  
  private PostSender() {
    databaseReference = FirebaseDatabase.getInstance().getReference();
    postQueue = new HashMap<>();
  }
  
  public static PostSender getInstance() {
    if (instance == null) {
      instance = new PostSender();
    }
    return instance;
  }
  
  public void sendPost(final Post post) {
    final String userKey = SessionManager.getInstance().getUserKey();
    
    final String key = databaseReference.child("posts").push().getKey();
    post.addMediaPath(MediaSender.getInstance().uploadMedia(key, post.getMediaList()));
    post.setKey(key);
    
    
    Map<String, Object> postValues = post.toMap();
    
    Map<String, Object> childUpdates = new HashMap<>();
    childUpdates.put(POSTS + key, postValues);
    childUpdates.put(USER_POSTS + userKey + "/" + key, postValues);
    
    postQueue.put(key, childUpdates);
  }
  
  public void sendPost(final String key) {
    final Map<String, Object> childUpdates = postQueue.get(key);
    if (childUpdates != null) {
      databaseReference.updateChildren(childUpdates)
         .addOnCompleteListener(task -> postQueue.remove(key));
    }
  }
}
