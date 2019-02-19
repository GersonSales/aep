package com.ufcg.les.aep.view.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.User.User;
import com.ufcg.les.aep.util.service.AccountManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpFragment extends Fragment implements AccountManager.AccountManagerObserver{

  @BindView(R.id.first_name)
  /*default*/ EditText firstName;
  
  @BindView(R.id.last_name)
  /*default*/ EditText lastName;
  
  @BindView(R.id.email)
  /*default*/ EditText email;

  @BindView(R.id.phone_number)
  /*default*/ EditText phoneNumber;

  @BindView(R.id.password)
  /*default*/ EditText password;
  
  @BindView(R.id.password_confirmation)
  /*default*/ EditText passwordConfirmation;
  
  @BindView(R.id.sign_up_progress)
  /*default*/ ProgressBar progressBar;
  
  @BindView(R.id.sign_up_form_constraint)
  /*default*/ View signUpForm;
  
  private OnFragmentInteractionListener mListener;
  
  private FirebaseAuth firebaseAuth;
  
  public SignUpFragment() {
    // Required empty public constructor
  }
  
  public static SignUpFragment newInstance() {
    return new SignUpFragment();
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    clearViewGroup(container);
    View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
    firebaseAuth = FirebaseAuth.getInstance();
    ButterKnife.bind(this, view);
    
    AccountManager.getInstance().addObserver(this);
    return view;
  }
  
  @OnClick(R.id.sign_up_button)
  public void onSignUpButton() {
    attemptToSignUp();
  }
  
  private void attemptToSignUp() {
    boolean isAValidAccount = true;

    if(isAValidAccount && !isNetworkAvailable()) {
      isAValidAccount = false;
      // TODO add mensagem para usuaário de conectar a rede!
    }

    if (isAValidAccount && !isEmailValid()) {
      isAValidAccount = false;
      email.setError("Invalid e-mail.");
    }
    
    if (isAValidAccount && !isPasswordValid()) {
      isAValidAccount = false;
      password.setError("Invalid password.");
    }
    
    if (isAValidAccount && !isValidPasswordConfirmation()) {
      isAValidAccount = false;
      passwordConfirmation.setError("Invalid Confirmation.");
    }

    if (isAValidAccount) {
      showProgress(true);
      final String firstName = this.firstName.getText().toString();
      final String lastName = this.lastName.getText().toString();
      final String email = this.email.getText().toString();
      final String phoneNumber = this.phoneNumber.getText().toString();
      final String password = this.password.getText().toString();
      
      final User user = new User(firstName, lastName, email, phoneNumber, password);
  
      AccountManager.getInstance().postUser(user);
      
  

//      firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
//        showProgress(false);
//        if (task.isSuccessful()) {
//          Toast.makeText(getContext(), "Account created.", Toast.LENGTH_SHORT).show();
//          final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//          if (firebaseUser != null) {
//            FirebaseClient.post("users", firebaseUser.getUid() , user);
//          }
//          mListener.onFragmentInteraction();
//        } else {
//          final Exception exception = task.getException();
//          if (exception != null) {
//            Log.e("FAILURE", exception.getMessage(), exception);
//            Toast.makeText(getContext(), exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//          }
//        }
//
//
//      });
      
    }
  }
  
  
  private void showProgress(final boolean show) {
    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    signUpForm.setAlpha(show ? 0.2f : 1);
  }
  
  private void clearViewGroup(final ViewGroup container) {
    if (container != null) {
      container.removeAllViews();
    }
  }
  
  @OnClick(R.id.sign_in_checkout_text_view)
  public void onCheckoutClick() {
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
  
  public boolean isEmailValid() {
    return this.email.getText().toString().contains("@");
  }
  
  public boolean isPasswordValid() {
    return this.password.getText().toString().length() > 6;
  }
  
  public boolean isValidPasswordConfirmation() {
    return passwordConfirmation.getText().toString().equals(this.password.getText().toString());
  }
  
  @Override
  public void notifySuccessfulAccountCreation() {
    showToast(getString(R.string.account_successful_created));
    mListener.onFragmentInteraction();
  }
  
  private void showToast(final String string) {
    Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    
  }
  
  public interface OnFragmentInteractionListener {
    void onFragmentInteraction();
  }

  private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager
            = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }

}
