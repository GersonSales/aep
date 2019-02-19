package com.ufcg.les.aep.model.User;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User implements PostAssignable{
  
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String password;
  private String key;
  
  public User() {
  }
  
  public User(final String firstName, final String lastName, final String email, final String phoneNumber, final String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
  }
  public String getName() {
    return getFirstName().concat(" ").concat(getLastName());
  }
  public String getFirstName() {
    return firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public String getEmail() {
    return email;
  }
  
  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  public String getPassword() {
    return password;
  }
  
  public Map<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("firstName", getFirstName());
    result.put("lastName", getLastName());
    result.put("email", getEmail());
    result.put("phoneNumber", getPhoneNumber());
    result.put("password", getPassword());
    return result;
  }
  
  
}
