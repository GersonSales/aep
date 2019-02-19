package com.ufcg.les.aep.util.service;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ufcg.les.aep.model.User.User;
import com.ufcg.les.aep.view.fragment.SignUpFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountManager {
  

  
  public interface AccountManagerObserver {
    void notifySuccessfulAccountCreation();
  }
  
  public static final String USERS = "/users/";
  private static AccountManager instance;
  private final FirebaseAuth firebaseAuth;
  private final DatabaseReference databaseReference;
  private final List<AccountManagerObserver> observers;
  private AccountManager() {
    firebaseAuth = FirebaseAuth.getInstance();
    databaseReference = FirebaseDatabase.getInstance().getReference();
    observers = new ArrayList<>();
  }
  
  public static AccountManager getInstance() {
    if (instance == null) {
      instance = new AccountManager();
    }
    
    return instance;
  }
  
  public void postUser(final User user) {
    firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
       .addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
          final String key = task.getResult().getUser().getUid();
          postUserValues(key, user);
          notifyObservers();
        }
        
    });
  }
  
  private void notifyObservers() {
    for (final AccountManagerObserver observer : observers) {
      observer.notifySuccessfulAccountCreation();
    }
  }
  
  private void postUserValues(final String key, final User user) {
    Map<String, Object> userValues = user.toMap();
    
    Map<String, Object> childUpdates = new HashMap<>();
    childUpdates.put(USERS + key, userValues);
    
    databaseReference.updateChildren(childUpdates);
  }
  
  public void addObserver(final AccountManagerObserver observer) {
    observers.add(observer);
  }
}
