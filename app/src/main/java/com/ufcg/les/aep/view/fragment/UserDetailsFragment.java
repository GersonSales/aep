package com.ufcg.les.aep.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.util.service.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailsFragment extends Fragment {
  
  
  @BindView(R.id.user_name_tv)
  /*default*/ TextView userName;
  
  @BindView(R.id.user_email_tv)
  /*default*/ TextView userEmail;
  
  @BindView(R.id.user_phone_tv)
  /*default*/ TextView userPhone;
  
  private OnFragmentInteractionListener mListener;
  
  public UserDetailsFragment() {
    // Required empty public constructor
  }
  
  public static UserDetailsFragment newInstance() {
    return new UserDetailsFragment();
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_user_details, container, false);
    ButterKnife.bind(this, view);
    bindView();
    
    return view;
  }
  
  private void bindView() {
    userName.setText(SessionManager.getInstance().getUserName());
    userEmail.setText(SessionManager.getInstance().getUserEmail());
    userPhone.setText(SessionManager.getInstance().getUserPhone());
  }
  
  public void onButtonPressed() {
    if (mListener != null) {
      mListener.onFragmentInteraction();
    }
  }
  
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
         + " must implement OnFragmentInteractionListener");
    }
  }
  
  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }
  
  public interface OnFragmentInteractionListener {
    void onFragmentInteraction();
  }
}
