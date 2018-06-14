package com.ufcg.les.aep.model.mock;

import com.ufcg.les.aep.behaviour.Observable;
import com.ufcg.les.aep.behaviour.Observer;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.model.post.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mock<T extends Comparable<T>> implements Observable, Serializable{
  
  private static final long serialVersionUID = -358346662188309747L;
  private List<Observer> observers;
  private List<T> list;
  private Map posts;

  public Mock() {
    list = new ArrayList<>();
    posts = new HashMap<String,ArrayList<Post>>();
    observers = new ArrayList<>();
  }

  public void addMapTagType(Post post){
    if(post.getTagType().equals(Tag.ACHADO)){
      if(posts.containsKey(Tag.ACHADO)){
        ArrayList<Post> aux = (ArrayList<Post>) posts.get(Tag.ACHADO);

        posts.put(Tag.ACHADO,aux.add(post));


      }else{
        ArrayList arrayListAux = new ArrayList<Post>();
        arrayListAux.add(post);
        posts.put(Tag.ACHADO,arrayListAux);

      }
    } else if (post.getTagType().equals(Tag.PERDIDO)) {
      if(posts.containsKey(Tag.PERDIDO)){
        ArrayList<Post> aux = (ArrayList<Post>) posts.get(Tag.PERDIDO);

        posts.put(Tag.PERDIDO,aux.add(post));

      }else{
        ArrayList arrayListAux = new ArrayList<Post>();
        arrayListAux.add(post);
        posts.put(Tag.PERDIDO,arrayListAux);

      }
    }
  }
  public void add(T t) {
    list.add(t);
    Collections.sort(this.list);
    notifyAllObservers();
  }
  
  @Override
  public void attachObserver(Observer observer) {
    observers.add(observer);
  }
  
  public void remove(T t) {
    list.remove(t);
    notifyAllObservers();
  }
  
  public void remove(int position) {
    list.remove(position);
  }
  
  public T getByPosition(int position) {
    return list.get(position);
  }
  
  public int size() {
    return list.size();
  }

  public void setList(List<T> list) {
      this.list = list;
      notifyAllObservers();
  }

  public Map getPosts() {
    return posts;
  }

  public void setPosts(Map posts) {
    this.posts = posts;
  }

  public List<T> getList() { return this.list;  }
  
  private void notifyAllObservers() {
    for (Observer observer : this.observers) {
      observer.advise();
    }
  }
  
  
  public void addAll(List<T> tList) {
    list.addAll(tList);
  }
}
