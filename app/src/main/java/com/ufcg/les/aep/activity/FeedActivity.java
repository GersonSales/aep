/*
 * FeedActivity
 * v1.0
 *
 * Copyright (C) 2018 LES-UFCG.
 * All rights reserved.
 * */

package com.ufcg.les.aep.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.PostAdapter;
import com.ufcg.les.aep.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ufcg.les.aep.util.Constant.APP_SECRET;


/**
 * This class is an {@link android.app.Activity} that provides the listing of the
 * {@link com.ufcg.les.aep.model.post.Post}.
 */
public class FeedActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSearch;


  
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
    feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    feedRecyclerView.setAdapter(PostAdapter.getInstance());
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar();

        if(isSearchOpened){
            action.setDisplayShowCustomEnabled(false);
            action.setDisplayShowTitleEnabled(true);
            
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

            //mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_open_search)); onde mudar o icone TODO

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true);
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);
            action.setDisplayShowTitleEnabled(false);

            edtSearch = action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            edtSearch.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //doSearch(); onde fazer a pesquisa TODO
                    return true;
                }
                return false;
            });


            edtSearch.requestFocus();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);

            //mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close_search));TODO
            isSearchOpened = true;
        }
    }
}
