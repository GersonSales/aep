package com.ufcg.les.aep.model.mock;

import android.content.Context;
import android.util.Log;

import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.LogTag;
import com.ufcg.les.aep.util.MediaUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class Mocker implements Serializable {
  public static final Mock<Post> POST_MOCK = new Mock<>();
  private static final long serialVersionUID = 3540825038521615158L;
  
  public static void update() {
    Log.i(LogTag.UPDATE, "Initializing the Mockers update.");
//    POST_MOCK.add(new Post("Computer", "I found a computer", getBitmapListFromURL("https://goo.gl/gvu2P8", "https://goo.gl/KTNkUi"), new ArrayList<>()));
    Log.i(LogTag.UPDATE, "Mocker is up to date.");
  }
}
