package com.ufcg.les.aep.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ufcg.les.aep.MainActivity;
import com.ufcg.les.aep.R;
import com.ufcg.les.aep.util.service.SessionManager;
import com.ufcg.les.aep.view.fragment.SignInFragment;
import com.ufcg.les.aep.view.fragment.SignUpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignActivity extends AppCompatActivity implements SignUpFragment.OnFragmentInteractionListener, SignInFragment.OnFragmentInteractionListener{
  
  private static final String EMAIL_PATTERN =
     "^[_.%+A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  
  @BindView(R.id.sign_frameLayout)
  /*default*/ FrameLayout frameLayout;
  
  private FragmentManager fragmentManager;
  private Fragment actualFragment;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign);
    fragmentManager = getSupportFragmentManager();
    
    ButterKnife.bind(this);
    beginTransaction();
    
  }
  
  private void beginTransaction() {
    if(SessionManager.getInstance().isUserLogged()) {
      final Intent intent = new Intent(this, MainActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      startActivity(intent);
      finish();
    } else {
      actualFragment = SignInFragment.newInstance();
      fragmentManager.beginTransaction().add(R.id.sign_frameLayout, actualFragment).commit();
    }
  }
  
  @Override
  public void onFragmentInteraction() {
    if (actualFragment instanceof SignInFragment) {
      actualFragment = SignUpFragment.newInstance();
    } else if (actualFragment instanceof SignUpFragment) {
      actualFragment = SignInFragment.newInstance();
    }
    fragmentManager.beginTransaction().replace(R.id.sign_frameLayout, actualFragment).commit();
  }
}
