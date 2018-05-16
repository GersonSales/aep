package com.ufcg.les.aep.model.mock;

import com.ufcg.les.aep.behaviour.Observable;
import com.ufcg.les.aep.behaviour.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mock<T extends Comparable<T>> implements Observable {
  
  private List<Observer> observers;
  private List<T> list;
  
  public Mock() {
    list = new ArrayList<>();
    observers = new ArrayList<>();
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
  
  private void notifyAllObservers() {
    for (Observer observer : this.observers) {
      observer.advise();
    }
  }
  
  
}
