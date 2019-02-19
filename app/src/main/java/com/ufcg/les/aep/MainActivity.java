package com.ufcg.les.aep;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.ufcg.les.aep.util.service.PostBuffer;
import com.ufcg.les.aep.util.service.SessionManager;
import com.ufcg.les.aep.view.activity.FeedFragment;
import com.ufcg.les.aep.view.activity.SignActivity;
import com.ufcg.les.aep.view.fragment.StorageFragment;
import com.ufcg.les.aep.view.fragment.UserDetailsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ufcg.les.aep.util.Constant.APP_SECRET;

public class MainActivity extends AppCompatActivity implements  SessionManager.SignOutObserver, UserDetailsFragment.OnFragmentInteractionListener , StorageFragment.OnFragmentInteractionListener{
  
  private static final int FIRST = 0;
  @BindView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;
  
  @BindView(R.id.nav_view)
  NavigationView mNavigationView;
  
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  private FragmentManager mFragmentManager;
  
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    SessionManager.getInstance().addSignOutObserver(this);
    mFragmentManager = getSupportFragmentManager();
    
    commitFeedFragment();
    setupNavigationView();
    setupToolBar();
    initAppCenterAnalytics();
    bindUserInfo();
    PostBuffer.getInstance().synchronize(this);
  
  }
  
  private void bindUserInfo() {
    final String name = SessionManager.getInstance().getUserName();
    ((TextView)mNavigationView.getHeaderView(FIRST).findViewById(R.id.user_name)).setText(name);
  }
  
  private void commitFeedFragment() {
    mFragmentManager.beginTransaction().replace(R.id.content_frame_layout, new FeedFragment()).commit();
  }
  
  private void setupToolBar() {
    setSupportActionBar(toolbar);
    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
    }
  }
  
  private void setupNavigationView() {
    mNavigationView.setNavigationItemSelectedListener(item -> {
      item.setChecked(true);
      mDrawerLayout.closeDrawers();
      
      switch (item.getItemId()) {
        case R.id.home:
          commitFeedFragment();
          return true;
        case R.id.profile:
          commitUserFragment();
          return true;
        case R.id.user_storage:
          commitUserStorageFragment();
          return true;
        case R.id.sign_out:
          SessionManager.getInstance().logout();
          return true;
      }
      return false;
    });
  }
  
  private void commitUserStorageFragment() {
    mFragmentManager.beginTransaction().replace(R.id.content_frame_layout, StorageFragment.newInstance()).commit();
  
  }
  
  private void commitUserFragment() {
    mFragmentManager.beginTransaction().replace(R.id.content_frame_layout, UserDetailsFragment.newInstance()).commit();
  }
  
  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
  
  private void initAppCenterAnalytics() {
    AppCenter.start(getApplication(), APP_SECRET,
       Analytics.class, Crashes.class);
  }
  
  @Override
  public void notifySuccessfulSignOut() {
    startActivity(new Intent(this, SignActivity.class));
    finish();
  }
  
  @Override
  public void onFragmentInteraction() {
  
  
  }
}
