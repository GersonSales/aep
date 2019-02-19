package com.ufcg.les.aep.util.service;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.model.post.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostBuffer {
  
  
  private static final String POSTS = "posts";
  private static final String CREATION_DATE = "creationDate";
  private static final int AT_BEGIN = 0;
  
  private static PostBuffer instance;
  private final List<Post> postList;
  private final List<Post> postListBackup;
  
  private final Map<String, Post> postCache;
  private final List<PostBufferObserver> observers;
  private final List<PostBuffer.onSynchronizedBufferListener> synchronizedBufferListeners;
  private final DatabaseReference reference;
  
  private PostBuffer() {
    postList = new ArrayList<>();
    postListBackup = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    reference = database.getReference();
    observers = new ArrayList<>();
    postCache = new HashMap<>();
    synchronizedBufferListeners = new ArrayList<>();
  }
  
  public static PostBuffer getInstance() {
    if (instance == null) {
      instance = new PostBuffer();
    }
    return instance;
  }
  
  public void synchronize(final Context context) {
    Query query;
    
    if (postList.isEmpty()) {
      query = reference.child(POSTS).orderByChild(CREATION_DATE);
    } else {
      final String last = postList.get(postList.size() - 1).getCreationDate();
      query = reference.child(POSTS).orderByChild(CREATION_DATE).startAt(last);
    }
    
    feedPostListWithBackup();
    query.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(final DataSnapshot dataSnapshot) {
        for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
          final Post post = snapshot.getValue(Post.class);
          if (post != null && !postList.contains(post) && !postListBackup.contains(post)) {
            for (final String mediaPath : post.getMediaPaths()) {
              MediaSender.getInstance().downloadMedia(mediaPath, context, post.getKey());
              Log.i("POST", post.getMediaList().toString());
            }
            postCache.put(post.getKey(), post);
          }
        }
        
        notifyObservers();
        notifyBufferSynchronized();
        
      }
      
      @Override
      public void onCancelled(DatabaseError databaseError) {
      
      }
    });
  }
  
  private void feedPostListWithBackup() {
    if (!postListBackup.isEmpty()) {
      postList.addAll(postListBackup);
      postListBackup.clear();
      Collections.sort(postList);
    }
  }
  
  public void attachMedia(final String postKey, final AbstractMedia media) {
    if (postCache.containsKey(postKey)) {
      final Post post = postCache.get(postKey);
      post.addMedia(media);
      if (post.hasAllMedias()) {
        postList.add(post);
        Collections.sort(postList);
        postCache.remove(postKey);
      }
    }
    notifyBufferSynchronized();
  }
  
  private void notifyBufferSynchronized() {
    if (postCache.isEmpty()) {
      for (final onSynchronizedBufferListener listener : synchronizedBufferListeners) {
        listener.notifySynchronizedBuffer();
      }
    }
  }
  
  private void notifyObservers() {
    for (final PostBufferObserver observer : observers) {
      observer.notifyDataSetChanged();
    }
  }
  
  public void addObserver(final PostBufferObserver observer) {
    observers.add(observer);
  }
  
  public int size() {
    return postList.size();
  }
  
  public Post getByPosition(int position) {
    Post result = null;
    if (position < size()) {
      result = postList.get(position);
    }
    return result;
  }
  
  public void addOnSynchronizedListener(final onSynchronizedBufferListener listener) {
    synchronizedBufferListeners.add(listener);
  }
  
  
  public void filterByNameThatContains(final String text) {
    for (int i = postList.size() - 1; i >= 0; i--) {
      final Post post = postList.get(i);
      if (!post.getTitle().toLowerCase().contains(text)) {
        postListBackup.add(post);
        postList.remove(i);
      }
    }
    
    for (int i = postListBackup.size() - 1; i >= 0; i--) {
      final Post post = postListBackup.get(i);
      if (post.getTitle().toLowerCase().contains(text)) {
        postList.add(post);
        postListBackup.remove(i);
      }
    }
    
    Collections.sort(postList);
  }
  
  public interface PostBufferObserver {
    void notifyDataSetChanged();
  }
  
  public interface onSynchronizedBufferListener {
    void notifySynchronizedBuffer();
  }
  
  
}
