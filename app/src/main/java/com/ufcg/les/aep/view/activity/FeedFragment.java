/*
 * FeedActivity
 * v1.0
 *
 * Copyright (C) 2018 LES-UFCG.
 * All rights reserved.
 * */

package com.ufcg.les.aep.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.PostAdapter;
import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.model.media.MediaFactory;
import com.ufcg.les.aep.util.Enviroment;
import com.ufcg.les.aep.util.service.PostBuffer;
import com.ufcg.les.aep.util.service.UserImageStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

import static com.ufcg.les.aep.model.media.MediaFactory.MediaKey.IMAGE;
import static com.ufcg.les.aep.util.MediaUtil.MEDIA_CAPTURE;


/**
 * This class is an {@link android.app.Activity} that provides the listing of the
 * {@link com.ufcg.les.aep.model.post.Post}.
 */
public class FeedFragment extends Fragment implements PostBuffer.onSynchronizedBufferListener {
  private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
  /**
   * This RecyclerView is responsible to list the {@link com.ufcg.les.aep.model.post.Post} and
   * show then in the {@link FeedFragment} layout. It was bound by the {@link ButterKnife}.
   */
  @BindView(R.id.feed_recyclerView)
  RecyclerView feedRecyclerView;
  /**
   * This field is the representation of the {@link SwipeRefreshLayout} that's responsible o create
   * a refresh interaction with the feed_recyclerView layout.
   */
  @BindView(R.id.feed_swipeRefresh)
  SwipeRefreshLayout feedRefresher;
  @BindView(R.id.social_floating_menu)
  com.github.clans.fab.FloatingActionMenu postMenu;
  @BindView(R.id.floating_post_id)
  com.github.clans.fab.FloatingActionButton postCreationBtn;
  @BindView(R.id.floating_save_id)
  com.github.clans.fab.FloatingActionButton savePictureBtn;
  @BindView(R.id.floating_date_filter)
  com.github.clans.fab.FloatingActionButton dateFilterBtn;
  private AbstractMedia capturedMedia;
  
  @Override
  public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                           final Bundle savedInstanceState) {
    clearViewGroup(container);
    
    final View view = inflater.inflate(R.layout.fragment_feed, container, false);
    ButterKnife.bind(this, view);
    initRecyclerView();
    setHasOptionsMenu(true);
    PostBuffer.getInstance().addOnSynchronizedListener(this);//TODO improve  this logic
    
    return view;
  }
  
  private void clearViewGroup(final ViewGroup container) {
    if (container != null) {
      container.removeAllViews();
    }
  }
  
  @OnClick(R.id.floating_post_id)
  public void onPostCreationClick() {
    final FragmentManager fragmentManager = getFragmentManager();
    if (fragmentManager != null) {
      fragmentManager
         .beginTransaction()
         .replace(R.id.content_frame_layout, PostCreationFragment.newInstance())
         .commit();
    }
  }
  
  /**
   * This method ensures that the {@link FeedFragment}'s {@link RecyclerView} is create correctly.
   */
  private void initRecyclerView() {
    initFeedRefresher();
    feedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    feedRecyclerView.setAdapter(PostAdapter.getInstance());
  }
  
  private void initFeedRefresher() {
    this.feedRefresher.setOnRefreshListener(this :: refreshFeed);
  }
  
  private void refreshFeed() {
    this.feedRefresher.setRefreshing(true);
    PostBuffer.getInstance().synchronize(getContext());
  }
  
  @Override
  public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
    inflater.inflate(R.menu.menu_main, menu);
    MenuItem menuItem = menu.findItem(R.id.post_search);
    
    SearchView searchView = (SearchView) menuItem.getActionView();
    initSearchBehavior(searchView);
    
    
    super.onPrepareOptionsMenu(menu);
  }
  
  private void initSearchBehavior(final SearchView searchView) {
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        PostAdapter.getInstance().filterByNameThatContains(query);
        return false;
      }
      
      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    
    searchView.setOnCloseListener(() -> {
      PostAdapter.getInstance().restoreList();
      return false;
    });
  }
  
  @Override
  public void notifySynchronizedBuffer() {
    this.feedRefresher.setRefreshing(false);
  }
  
  @RequiresApi(api = Build.VERSION_CODES.M)
  @OnClick(R.id.floating_save_id)
  public void savePictureOnClick() {
    capturedMedia = MediaFactory.getMedia(getContext(), IMAGE);
    final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedMedia.getUri());
    if (Enviroment.requestCameraPermission(getActivity())) {
      startActivityForResult(cameraIntent, MEDIA_CAPTURE);
    }
  }
  
  @Override
  public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == MEDIA_CAPTURE) {
        UserImageStorage.getInstance().add(capturedMedia);
      }
    }
  }
}