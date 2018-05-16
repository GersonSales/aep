package com.ufcg.les.aep.model.mock;

import android.util.Log;

import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.LogTag;

import java.util.ArrayList;

public class Mocker {
    public static final Mock<Post> POST_MOCK = new Mock<>();
  
  public static void update() {
    Log.i(LogTag.UPDATE, "Initializing the Mockers update.");
    POST_MOCK.add(new Post("Computer", "I found a computer", new ArrayList<>(), new ArrayList<>()));
    Log.i(LogTag.UPDATE, "Mocker is up to date.");
  }
}
