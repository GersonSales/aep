package com.ufcg.les.aep.model.mock;

import android.util.Log;

import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.LogTag;

public class Mocker {
    public static final Mock<Post> POST_MOCK = new Mock<>();
  
  public static void update() {
    Log.i(LogTag.UPDATE, "Updating the Mocker.");
  }
}
