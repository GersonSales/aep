package com.ufcg.les.aep.util.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ufcg.les.aep.model.User.User;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
  
  public static final String USERS = "/users/";
  private static final String TAG = "SESSION";
  
  
  public interface SignInObserver {
    void notifySuccessfulAttempt();
    void notifyUnsuccessfulAttempt(Exception exception);
  }
  
  public interface SignOutObserver {
    void notifySuccessfulSignOut();
  }
  
  private static SessionManager instance;
  
  public static SessionManager getInstance() {
    if (instance == null) {
      instance = new SessionManager();
    }
    instance.checkUserValues();
    return instance;
  }
  
  private void checkUserValues() {
    if (isUserLogged() && user instanceof NullUser) {
      bindUserValues();
    }
  }
  
  private final FirebaseAuth firebaseAuth;
  private final DatabaseReference databaseReference;
  private final List<SignInObserver> sigInObservers;
  private final List<SignOutObserver> signOutObservers;
  
  private FirebaseUser authUser;
  private User user;
  
  
  
  private SessionManager() {
    user = new NullUser();
    firebaseAuth = FirebaseAuth.getInstance();
    sigInObservers = new ArrayList<>();
    signOutObservers = new ArrayList<>();
    authUser = FirebaseAuth.getInstance().getCurrentUser();
    databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS);
  }
  
  
  public void login(final String email, final String password) {
    OnCompleteListener<AuthResult> onCompleteListener = task -> {
    if (task.isSuccessful()) {
      authUser = task.getResult().getUser();
      bindUserValues();
      notifySuccessfulLogin();
    } else {
      notifyUnsuccessfulLogin(task.getException());
    }
    };
    
    firebaseAuth.signInWithEmailAndPassword(email, password)
       .addOnCompleteListener(onCompleteListener);
  }
  
  private void bindUserValues() {
    Query query  = databaseReference.child(authUser.getUid());
    query.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        user = dataSnapshot.getValue(User.class);
        Log.i(TAG, "User successful logged in");
      }
  
      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
    
      }
    });
  }
  
  public void addSignInObserver(final SignInObserver observer) {
    sigInObservers.add(observer);
  }
  
  public void addSignOutObserver(final SignOutObserver signOutObserver) {
    signOutObservers.add(signOutObserver);
  }
  
  private void notifyUnsuccessfulLogin(final Exception exception) {
    for (SignInObserver signInObserver : sigInObservers) {
      signInObserver.notifyUnsuccessfulAttempt(exception);
    }
  }
  
  private void notifySuccessfulLogin() {
    for (SignInObserver signInObserver : sigInObservers) {
      signInObserver.notifySuccessfulAttempt();
    }
  }
  
  public void logout() {
    firebaseAuth.signOut();
    authUser = firebaseAuth.getCurrentUser();
    user = new NullUser();
    notifySuccessfulSignOut();
  }
  
  
  public boolean isUserLogged() {
    return authUser != null;
  }
  private void notifySuccessfulSignOut() {
    for (SignOutObserver signOutObserver : signOutObservers) {
      signOutObserver.notifySuccessfulSignOut();
    }
  }
  
  public String getUserName() {
    return user.getName();
  }
  
  
  public String getUserEmail() {
    return authUser.getEmail();
  }
  
  public String getUserPhone() {
    return user.getPhoneNumber();
  }
  
  
  public String getUserKey() {
    return authUser.getUid();
  }
  
  public User getUser() {
    return user;
  }
  
  
  
  
  
}
