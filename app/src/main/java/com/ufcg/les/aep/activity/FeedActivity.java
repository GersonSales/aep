/*
 * FeedActivity
 * v1.0
 *
 * Copyright (C) 2018 LES-UFCG.
 * All rights reserved.
 * */

package com.ufcg.les.aep.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SearchView;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.PostAdapter;
import com.ufcg.les.aep.model.mock.Mocker;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ufcg.les.aep.util.Constant.APP_SECRET;


/**
 * This class is an {@link android.app.Activity} that provides the listing of the
 * {@link com.ufcg.les.aep.model.post.Post}.
 */
public class FeedActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSearch;
    private List<Post> backupMock;



  /**
   * This RecyclerView is responsible to list the {@link com.ufcg.les.aep.model.post.Post} and
   * show then in the {@link FeedActivity} layout. It was bound by the {@link ButterKnife}.
   */
  @BindView(R.id.feed_recyclerView)
  RecyclerView feedRecyclerView;

  /**
   * This field is the representation of the {@link SwipeRefreshLayout} that's responsible o create
   * a refresh interaction with the feed_recyclerView layout.
   */
  @BindView(R.id.feed_swipeRefresh)
  SwipeRefreshLayout feedRefresher;


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
    mToolbar = findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);


    initRecyclerView();
    initAppCenterAnalytics();
  }

  private void initAppCenterAnalytics() {
    AppCenter.start(getApplication(), APP_SECRET,
       Analytics.class, Crashes.class);
  }

  /**
   * This method ensures that the {@link FeedActivity}'s {@link RecyclerView} is create correctly.
   */
  private void initRecyclerView() {
    initFeedRefresher();
    feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    feedRecyclerView.setAdapter(PostAdapter.getInstance());
  }

  private void initFeedRefresher() {
    this.feedRefresher.setOnRefreshListener(this :: refreshFeed);
  }

  private void refreshFeed() {
    Mocker.update();
    this.feedRefresher.setRefreshing(false);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu_main, menu);

      SearchManager searchManager =
              (SearchManager) getSystemService(Context.SEARCH_SERVICE);
      SearchView searchView =
              (SearchView) menu.findItem(R.id.search).getActionView();
      searchView.setSearchableInfo(
              searchManager.getSearchableInfo(getComponentName()));

      searchView.setOnQueryTextListener(this);

      return true;
  }

    @Override
    public boolean onQueryTextSubmit(String query) {
        copyToFrom(Mocker.POST_MOCK.getList(), backupMock);
        backupMock = null;

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(!newText.isEmpty()) {
            System.out.println("Porra Junio");
            System.out.println("Porra Junio");
            System.out.println("Porra Junio");
            if(backupMock == null) {
                backupMock = new ArrayList<>();
                copyToFrom(backupMock, Mocker.POST_MOCK.getList());
            }

            List<Post> newMock = new ArrayList<>();
            System.out.println(newText);
            for(Post post: Mocker.POST_MOCK.getList()) {
                if(post.getTitle().charAt(newText.length()-1) == (newText.charAt(newText.length()-1))) {
                    newMock.add(post);
                }
            }

            Mocker.POST_MOCK.setList(newMock);
            return true;

        } else {
            copyToFrom(Mocker.POST_MOCK.getList(), backupMock);
            backupMock = null;
            System.out.println("Otaro nem entrei");

            return false;
        }
    }

    private void copyToFrom(List to, List<Post> from) {
        for(Post p: from) {
            to.add(p);
        }
    }
}
