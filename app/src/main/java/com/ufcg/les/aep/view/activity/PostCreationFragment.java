package com.ufcg.les.aep.view.activity;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.CapturedImageAdapter;
import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.model.media.MediaFactory;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.util.Enviroment;
import com.ufcg.les.aep.util.MediaUtil;
import com.ufcg.les.aep.util.service.PostSender;
import com.ufcg.les.aep.util.service.SessionManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ufcg.les.aep.model.media.MediaFactory.MediaKey.IMAGE;

public class PostCreationFragment extends Fragment implements AdapterView.OnItemSelectedListener {
  private static final int GALLERY_REQUEST = 3;
  
  @BindView(R.id.titlePostCreation_editText)
  EditText titlePost;
  @BindView(R.id.descriptionPostCreation_editText)
  EditText descriptionPost;
  @BindView(R.id.captureImage_button)
  FloatingActionButton imageCapture;
  @BindView(R.id.submitPost_Button)
  Button submit;
  @BindView(R.id.lost_n_found_dropdown)
  Spinner dropdown;
  @BindView(R.id.spinner_TagsId)
  Spinner tag_spinner;
  @BindView(R.id.captureImage_Gallery)
  FloatingActionButton captureGallery;

  
  @BindView(R.id.images_label)
  TextView imageLabel;
  
  
  @BindView(R.id.capturedImages_recyclerView)
  /*default*/ RecyclerView recyclerView;
  
  private List<AbstractMedia> medias = new ArrayList<>(); //TODO
  private AbstractMedia media;
  private CharSequence choosedType;
  
  public PostCreationFragment() {
    // Required empty public constructor
  }
  
  
  public static PostCreationFragment newInstance(AbstractMedia... abstractMedia) {
    final PostCreationFragment postCreationFragment = new PostCreationFragment();
    postCreationFragment.addMedia(abstractMedia);
    return postCreationFragment;
  }
  
  private void addMedia(AbstractMedia... abstractMedia) {
    this.medias.addAll(Arrays.asList(abstractMedia));
    CapturedImageAdapter.getInstance().addMedia(abstractMedia);
  }
  
  @Override
  public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                           final Bundle savedInstanceState) {
    
    final View view = inflater.inflate(R.layout.fragment_post_creation, container, false);
    ButterKnife.bind(this, view);
  
  
    setHasOptionsMenu(true);
  
    clearViewGroup(container);
    createTypeDropdown();
    createDropdownTags();
    initRecyclerView();
    
    return view;
  }
  
  private void clearViewGroup(final ViewGroup container) {
    if (container != null) {
      container.removeAllViews();
    }
  }
  
  private void initRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
       LinearLayoutManager.HORIZONTAL, false));
    recyclerView.setAdapter(CapturedImageAdapter.getInstance());
  }
  
  public void showToast(String text) {
    Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
  }
  @OnClick(R.id.captureImage)
  public void onImageCaptureClick() {
    if (captureGallery.getVisibility() == VISIBLE) {
      captureGallery.setVisibility(GONE);
      imageCapture.setVisibility(GONE);
    }else {
      captureGallery.setVisibility(VISIBLE);
      imageCapture.setVisibility(VISIBLE);
    }
  }
  
  @OnClick(R.id.submitPost_Button)
  public void onSubmitClick() {
    boolean canCreate = true;
    
    if (!isTitleValid()) {
      titlePost.setError(getString(R.string.title_error_message));
      canCreate = false;
    }
    
    if (!isDescriptionValid()) {
      descriptionPost.setError(getString(R.string.description_error_message));
      canCreate = false;
    }
    
    
    if (!isImageValid()) {
      imageLabel.setError(getString(R.string.image_error_message));
      showToast(getString(R.string.image_error_message));
      canCreate = false;
    }
    
    if (canCreate) {
      createAndSendPost();
      CapturedImageAdapter.getInstance().clear();
      finish();
    } else {
      submit.setError("");
    }
  }
  
  private void finish() {
    final FragmentManager fragmentManager = getFragmentManager();
    if (fragmentManager != null) {
      fragmentManager
         .beginTransaction()
         .replace(R.id.content_frame_layout, new FeedFragment())
         .detach(this)
         .commit();
    }
  }
  
  @Override
  public void onDetach() {
    CapturedImageAdapter.getInstance().clear();
    super.onDetach();
  }
  
  private void createAndSendPost() {
    final String title = titlePost.getText().toString();
    final String description = descriptionPost.getText().toString();
    final Post newPost = new Post(title, description, this.medias, "", choosedType.toString(), SessionManager.getInstance().getUser());
    PostSender.getInstance().sendPost(newPost);
  }
  
  @RequiresApi(api = Build.VERSION_CODES.M)
  @OnClick(R.id.captureImage_Gallery)
  public void onCaptureGalleryPhoto() {
    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    if (Enviroment.requestStoragePermission(getActivity())) {
      startActivityForResult(intent, GALLERY_REQUEST);
    }
  }
  
  @RequiresApi(api = Build.VERSION_CODES.M)
  @OnClick(R.id.captureImage_button)
  public void onCaptureImageClick() {
    this.media = MediaFactory.getMedia(getContext(), IMAGE);
    final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, this.media.getUri());
    if (Enviroment.requestCameraPermission(getActivity())) {
      startActivityForResult(cameraIntent, MediaUtil.MEDIA_CAPTURE);
    }
  }
  
  public String getPathFromUri(Uri contentUri) {
    String res = null;
    String[] proj = { MediaStore.Images.Media.DATA };
    Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
    if (cursor.moveToFirst()) {
      int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      res = cursor.getString(column_index);
    }
    cursor.close();
    return res;
  }
  
  @Override
  public void onActivityResult(int requestCode, int resultCode, final Intent data) {
    if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST) {
      if (data == null) {
        showToast("Não existe imagem");
        return;
      } else {
        try {
          final Uri uri = data.getData();
          final String path = getPathFromUri(uri);
          if (path != null) {
            AbstractMedia abstractMedia = new AbstractMedia(uri);
            this.medias.add(abstractMedia);
            CapturedImageAdapter.getInstance().addMedia(abstractMedia);
          }
        } catch (Exception e) {
          Log.e("FileSelectorActivity", "File select error", e);
        }
      }
    } else {
      this.medias.add(this.media);
      CapturedImageAdapter.getInstance().addMedia(this.media);
    }
  }
  
  private void createDropdownTags() {
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.lost_mutiples_tags, android.R.layout.simple_spinner_dropdown_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    tag_spinner.setAdapter(adapter);
    tag_spinner.setOnItemSelectedListener(this);
    
  }
  
  private void createTypeDropdown() {
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.lost_n_found_tags, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    dropdown.setAdapter(adapter);
    dropdown.setOnItemSelectedListener(this);
  }
  
  @Override
  public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
    inflater.inflate(R.menu.post_creation_menu, menu);
    super.onPrepareOptionsMenu(menu);
  }
  
  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case R.id.post_creation_cancel:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
  
  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    Spinner spinner1 = (Spinner) parent;
    if (spinner1.getId() == R.id.spinner_TagsId) {
//      choosedOption1 = (CharSequence) parent.getItemAtPosition(position);
    }
    if (spinner1.getId() == R.id.lost_n_found_dropdown) {
      choosedType = (CharSequence) parent.getItemAtPosition(position);
    }
  }
  
  @Override
  public void onNothingSelected(AdapterView<?> parent) {
//    choosedOption = (CharSequence) parent.getItemAtPosition(0);
  }
  
  public boolean isTitleValid() {
    final String title = titlePost.getText().toString();
    return !title.trim().equals("") && (title.length() >= 2);
  }
  
  public boolean isDescriptionValid() {
    final String description = descriptionPost.getText().toString();
    return !description.trim().isEmpty() && (description.length() >= 6);
  }
  
  public boolean isImageValid() {
    return CapturedImageAdapter.getInstance().getItemCount() > 0;
  }
  
  
}
