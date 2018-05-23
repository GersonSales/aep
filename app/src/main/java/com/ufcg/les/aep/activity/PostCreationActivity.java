package com.ufcg.les.aep.activity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.provider.MediaStore;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ufcg.les.aep.model.media.MediaFactory.MediaKey.IMAGE;

public class PostCreationActivity extends AppCompatActivity {
  
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

//  @BindView(R.id.submitPost_button)
//  FloatingActionButton submitPostBtn;
  
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_post_creation);
    ButterKnife.bind(this);
    
    createSubmitPostBtn();
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
  
  private boolean setPostName(Post post) {
    String name = nomePost.getText().toString();
    if (!name.trim().equals("")) {
      post.setTitle(name);
      return true;
    } else {
      showToast("Insira seu nome!");
      return false;
    }
  }
  
  private boolean setPostDescription(Post post) {
    String description = descriptionPost.getText().toString();
    if (!description.trim().isEmpty()) {
      post.setDescription(description);
      return true;
    } else {
      showToast("Insira a descrição do item");
      return false;
    }
  }
  
  private boolean setPostContact(Post post) {
    boolean result = false;
    String number = numberPost.getText().toString();
    String email = emailPost.getText().toString();
    
    if (!number.trim().equals("")) {
      //post.setNumber(number);
      result = true;
    }
    
    if (!email.trim().equals("")) {
      //post.setEmail(email);
      result = true;
    }
    
    if (result == false) {
      showToast("Insira email ou numero para contato!");
    }
    
    return result;
  }
  
  public boolean setPostTitle(Post post) {
    String title = titlePost.getText().toString();
    if (!title.trim().equals("")) {
      post.setTitle(title);
      return true;
    } else {
      showToast("Insira o titulo do objeto!");
      return false;
    }
  }
  
  public void showToast(String text) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
  }
  
  
  @OnClick(R.id.captureImage_button)
  public void onCaptureImageClick() {
    this.media = MediaFactory.getMedia(this, IMAGE);
    final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, this.media.getUri());
    startActivityForResult(cameraIntent, MediaUtil.MEDIA_CAPTURE);
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      this.medias.add(this.media);
    }
  }
}
