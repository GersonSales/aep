/*
 * FeedActivity
 * v1.0
 *
 * Copyright (C) 2018 LES-UFCG.
 * All rights reserved.
 * */

package com.ufcg.les.aep.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.PostAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * This class is an {@link android.app.Activity} that provides the listing of the
 * {@link com.ufcg.les.aep.model.post.Post}.
 */
public class FeedActivity extends AppCompatActivity {
  
  /**
   * This RecyclerView is responsible to list the {@link com.ufcg.les.aep.model.post.Post} and
   * show then in the {@link FeedActivity} layout. It was bound by the {@link ButterKnife}.
   */
  @BindView(R.id.feed_recyclerView)
  RecyclerView feedRecyclerView;
  
  
  /**
   * This method is responsible to generate all {@link FeedActivity} behaviour when it's created
   * by the Android life cycle.
   *
   * @param savedInstanceState The instance of the {@link android.content.Context} and its
   *                           attributes.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_feed);
    ButterKnife.bind(this);
    
    initRecyclerView();
  }
  
  /**
   * This method ensures that the {@link FeedActivity}'s {@link RecyclerView} is create correctly.
   */
  private void initRecyclerView() {
    feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    feedRecyclerView.setAdapter(PostAdapter.getInstance());
  }
}
