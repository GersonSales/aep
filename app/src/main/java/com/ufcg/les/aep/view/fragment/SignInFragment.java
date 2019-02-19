package com.ufcg.les.aep.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.ufcg.les.aep.MainActivity;
import com.ufcg.les.aep.R;
import com.ufcg.les.aep.util.service.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInFragment extends Fragment implements SessionManager.SignInObserver {
  
  public static final int MIN_PASSWORD_LEN = 6;
  private final String TAG = "AUTHENTICATION";
  @BindView(R.id.email)
  /*default*/ AutoCompleteTextView mEmailView;
  @BindView(R.id.password)
  /*default*/ EditText mPasswordView;
  @BindView(R.id.login_progress)
  /*default*/ View mProgressView;
  @BindView(R.id.login_form)
  /*default*/ View mLoginFormView;
  private OnFragmentInteractionListener mListener;
  
  public SignInFragment() {
    // Required empty public constructor
  }
  
  public static SignInFragment newInstance() {
    return new SignInFragment();
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                           final Bundle savedInstanceState) {
    clearViewGroup(container);
    
    final View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
    ButterKnife.bind(this, view);
    
    SessionManager.getInstance().addSignInObserver(this);
    
    return view;
  }
  
  private void clearViewGroup(final ViewGroup container) {
    if (container != null) {
      container.removeAllViews();
    }
  }
  
  @OnClick(R.id.signUpCheckout_textView)
  public void onCheckoutClick() {
    if (mListener != null) {
      mListener.onFragmentInteraction();
    }
  }
  
  @OnClick(R.id.sign_in_button)
  public void onSignInClick() {
    attemptLogin();
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
  
  private void attemptLogin() {
    // Reset errors.
    mEmailView.setError(null);
    mPasswordView.setError(null);
    
    // Store values at the time of the login attempt.
    String email = mEmailView.getText().toString();
    String password = mPasswordView.getText().toString();
    
    boolean cancel = false;
    View focusView = null;
    
    // Check for a valid password, if the user entered one.
    if (!isPasswordValid(password)) {
      mPasswordView.setError(getString(R.string.error_invalid_password));
      focusView = mPasswordView;
      cancel = true;
    }
    
    // Check for a valid email address.
    if (TextUtils.isEmpty(email)) {
      mEmailView.setError(getString(R.string.error_field_required));
      focusView = mEmailView;
      cancel = true;
    } else if (!isEmailValid(email)) {
      mEmailView.setError(getString(R.string.error_invalid_email));
      focusView = mEmailView;
      cancel = true;
    }
    
    if (cancel) {
      // There was an error; don't attempt login and focus the first
      // form field with an error.
      focusView.requestFocus();
    } else {
      // Show a progress spinner, and kick off a background task to
      // perform the user login attempt.
      showProgress(true);
      attemptRemoteLogin(email, password);
    }
  }
  
  private void attemptRemoteLogin(final String email, final String password) {
    SessionManager.getInstance().login(email, password);
  }
  
  private void proceedWithSignInError(final String string) {
    showProgress(false);
    Log.e(TAG, string);
    if (string.equals(getString(R.string.identifier_error))) {
      showInvalidEmailDialog();
    } else if (string.equals(getString(R.string.password_error))) {
      mPasswordView.setError(getString(R.string.error_incorrect_password));
      mPasswordView.requestFocus();
    }
    
  }
  
  private void showInvalidEmailDialog() {
    final Activity activity = getActivity();
    if (activity != null) {
      AlertDialog.Builder builder = new AlertDialog.Builder(activity);
      builder.setTitle(R.string.invalid_email);
      builder.setMessage(R.string.not_registered_email_message);
      builder.setPositiveButton(R.string.register_email, (dialogInterface, i) -> {
        mListener.onFragmentInteraction();
      });
      
      builder.show();
    } else {
      showToast(getString(R.string.email_not_registered));
    }
  }
  
  private void showToast(final String string) {
    Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    
  }
  
  private void proceed() {
    showProgress(false);
    final Activity activity = getActivity();
    if (activity != null) {
      final Intent intent = new Intent(getActivity(), MainActivity.class);
      startActivity(intent);
      activity.finish();
    }
  }
  
  private boolean isEmailValid(String email) {
    return email.contains("@");
  }
  
  private boolean isPasswordValid(String password) {
    
    return !(TextUtils.isEmpty(password) || password.length() <= MIN_PASSWORD_LEN);
  }
  
  private void showProgress(final boolean show) {
    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    mLoginFormView.setAlpha(show ? 0.2f : 1);
  }
  
  @Override
  public void notifySuccessfulAttempt() {
    Log.d(TAG, "signInWithCustomToken:success");
    proceed();
  }
  
  @Override
  public void notifyUnsuccessfulAttempt(Exception exception) {
    proceedWithSignInError(exception.getLocalizedMessage());
    Log.d(TAG, "signInWithCustomToken:failure");
  }
  
  public interface OnFragmentInteractionListener {
    void onFragmentInteraction();
  }
  
}
