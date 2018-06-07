package com.ufcg.les.aep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.model.media.MediaFactory;
import com.ufcg.les.aep.model.mock.Mocker;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.MediaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ufcg.les.aep.model.media.MediaFactory.MediaKey.IMAGE;

public class PostCreationActivity extends AppCompatActivity {
  
  private static final String EMAIL_PATTERN =
     "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  private static final Pattern patternEmail = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
  @BindView(R.id.submitPost_button)
  FloatingActionButton submitPostBtn;
  @BindView(R.id.titlePostCreation_editText)
  EditText titlePost;
  @BindView(R.id.namePostCreation_editText)
  EditText nomePost;
  @BindView(R.id.phonePostCreation_editText)
  EditText numberPost;
  @BindView(R.id.emailPostCreation_editText)
  EditText emailPost;
  @BindView(R.id.descriptionPostCreation_editText)
  EditText descriptionPost;
  @BindView(R.id.captureImage_button)
  Button imageCapture;
  @BindView(R.id.videoCapture_button)
  Button videoCapture;
  @BindView(R.id.submitPost_Button)
  Button submit;
  private List<AbstractMedia> medias = new ArrayList<>(); //TODO
  private AbstractMedia media;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_post_creation);
    ButterKnife.bind(this);
    
    createSubmitPostBtn();
  }
  
  public void showToast(String text) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
  }
  
  private boolean setPostContact(Post post) {
    boolean result = false;
    String number = numberPost.getText().toString();
    String email = emailPost.getText().toString();
    Matcher matcher = patternEmail.matcher(email);
    
    if (!number.trim().equals("") && (number.matches(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}")
       || number.matches(".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}"))) {
      //post.setNumber(number);
      result = true;
    }
    
    if (!email.trim().equals("") && matcher.matches()) {
      //post.setEmail(email);
      result = true;
    }
    
    if (result == false) {
      showToast("Insira email ou numero para contato corretamente!");
    }
    
    return result;
  }
  
  
  private void createSubmitPostBtn() {
    submit.setOnClickListener(o -> createPost());
  }
  
  private void createPost() {
    Post newPost = new Post("", "", this.medias, new ArrayList<>());
    
    boolean titleValid = setPostTitle(newPost);
    boolean nameValid = setPostName(newPost);
    boolean contactValid = setPostContact(newPost);
    boolean descriptionValid = setPostDescription(newPost);
    
    if (titleValid && nameValid && contactValid && descriptionValid) {
      Mocker.POST_MOCK.add(newPost);
      finish();
    }
  }
  
  private boolean setPostDescription(Post post) {
    String description = descriptionPost.getText().toString();
    if (!description.trim().isEmpty() && (description.length() > 15)) {
      post.setDescription(description);
      return true;
    } else {
      showToast("Sua descrição deve conter mais de 15 caracteres!");
      return false;
    }
  }
  
  private boolean setPostName(Post post) {
    String name = nomePost.getText().toString();
    if (!name.trim().equals("") && nameValidation(name)) {
      //post.setName(name);
      return true;
    } else {
      showToast("Insira seu nome corretamente!");
      return false;
      
    }
  }
  
  
  public boolean setPostTitle(Post post) {
    String title = titlePost.getText().toString();
    if (!title.trim().equals("") && (title.length() > 5)) {
      post.setTitle(title);
      return true;
    } else {
      showToast("Seu título deve conter no mínimo 5 caracteres");
      return false;
    }
  }
  
  
  /**
   * That method will return true if the all paramameter string is alphabetic
   *
   * @param name
   * @return returnNameValidation
   */
  public boolean nameValidation(String name) {
    boolean returnNameValidation = false;
    for (int i = 0; i < name.length(); i++) {
      if (!Character.isDigit(name.charAt(i))) {
        returnNameValidation = true;
      } else {
        return false;
      }
    }
    return returnNameValidation;
  }
  
  @OnClick(R.id.captureImage_button)
  public void onCaptureImageClick() {
    this.media = MediaFactory.getMedia(this, IMAGE);
    final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,this.media.getUri());
    startActivityForResult(cameraIntent, MediaUtil.MEDIA_CAPTURE);
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
    if (resultCode == RESULT_OK) {
      this.medias.add(this.media);
    }
  }
  
}
