package com.ufcg.les.aep.model.listener;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;

import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.model.media.MediaFactory;

import static com.ufcg.les.aep.model.media.MediaFactory.MediaKey.IMAGE;

public final class CameraButtonListener implements View.OnClickListener {

  
  @Override
  public void onClick(final View view) {
    final AbstractMedia image = MediaFactory.getMedia(view.getContext(), IMAGE);
    final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image.getUri());
    view.getContext().startActivity(cameraIntent);
  }
}
