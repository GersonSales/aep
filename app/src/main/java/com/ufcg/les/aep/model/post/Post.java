package com.ufcg.les.aep.model.post;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;
import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.User.PostAssignable;
import com.ufcg.les.aep.model.media.AbstractMedia;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@IgnoreExtraProperties
public class Post implements Serializable, Comparable<Post> {
  private static final long serialVersionUID = 690241872815232514L;
  
  private static int postId;
  private String ownerPhoneNumber;
  private String ownerEmail;
  private String ownerName;
  
  private String creationDate;
  private int id;
  private String title;
  private String description;
  private List<AbstractMedia> mediaList;
  private String categoryTag;
  private String type;
  private String nameContact;
  private String numberContact;
  private String emailContact;
  private String key;
  private List<String> mediaPaths;
  
  public Post() {
    mediaList = new ArrayList<>();
    mediaPaths = new ArrayList<>();
  
  }
  
  public Post(String title, String description, List<AbstractMedia> mediaList, String categoryTag, String type, final PostAssignable postAssignable) {
    this();
    this.creationDate = datePost();
    this.id = postId++;
    this.title = title;
    this.description = description;
    this.mediaList = mediaList;
    this.categoryTag = categoryTag;
    this.type = type;
    this.ownerName = postAssignable.getName();
    this.ownerEmail = postAssignable.getEmail();
    this.ownerPhoneNumber = postAssignable.getPhoneNumber();
  }
  
  
  private String datePost() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    String timestamp = sdf.format(new Date());
    return timestamp;
    
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
  
  public String getCreationDate() {
    return creationDate;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public int getTypeColor() {
    if (getType().equals(Tag.FOUND)) {
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
  
  public boolean hasAllMedias() {
    return mediaList.size() == mediaPaths.size();
  }
  
  public void addMedia(final AbstractMedia media) {
    mediaList.add(media);
  }
  
  public String getCategoryTag() {
    return categoryTag;
  }
  
  public void setCategoryTag(String categoryTag) {
    this.categoryTag = categoryTag;
  }
  
  @Override
  public int compareTo(@NonNull final Post post) {
    return post.getCreationDate().compareTo(getCreationDate());
  }
  
  public Bitmap getMainImage() {
    return mediaList.isEmpty() ? null : mediaList.get(0).getThumbnail();
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Post)) return false;
    Post post = (Post) o;
    return Objects.equals(getCreationDate(), post.getCreationDate()) &&
       Objects.equals(getTitle(), post.getTitle()) &&
       Objects.equals(getDescription(), post.getDescription());
  }
  
  @Override
  public int hashCode() {
    
    return Objects.hash(getCreationDate(), getTitle(), getDescription());
  }
  
  @Override
  public String toString() {
    return "Post{" +
       "id=" + id +
       ", title='" + title + '\'' +
       ", description='" + description + '\'' +
       '}';
  }
  
  public Map<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("title", getTitle());
    result.put("description", getDescription());
    result.put("creationDate", getCreationDate());
    result.put("type", getType());
    result.put("mediaPaths", mediaPaths);
    result.put("key", getKey());
    result.put("ownerName", getOwnerName());
    result.put("ownerEmail", getOwnerEmail());
    result.put("ownerPhoneNumber", getOwnerPhoneNumber());
    return result;
  }
  
  public void setKey(final String key) {
    this.key = key;
  }
  
  public String getKey() {
    return this.key;
  }
  
  public void addMediaPath(List<String> strings) {
    this.mediaPaths.addAll(strings);
  }
  
  public List<String> getMediaPaths() {
    return mediaPaths;
  }
  
  public void setMediaPaths(final List<String> mediaPaths) {
    this.mediaPaths = mediaPaths;
  }
  
  public String getOwnerName() {
    return ownerName;
  }
  
  public String getOwnerEmail() {
    return ownerEmail;
  }
  
  public String getOwnerPhoneNumber() {
    return ownerPhoneNumber;
  }
}
