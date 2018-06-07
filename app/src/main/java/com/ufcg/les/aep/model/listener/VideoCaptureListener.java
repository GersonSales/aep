package com.ufcg.les.aep.model.listener;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;

import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.model.media.MediaFactory;
import com.ufcg.les.aep.util.MediaUtil;

import static com.ufcg.les.aep.model.media.MediaFactory.MediaKey.VIDEO;

public final class VideoCaptureListener implements View.OnClickListener {
  
  @Override
  public void onClick(final View view) {
    final AbstractMedia video = MediaFactory.getMedia(view.getContext(), VIDEO);
    final Intent videoCaptureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    videoCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, video.getUri());
    videoCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, MediaUtil.HIGH_QUALITY);
    view.getContext().startActivity(videoCaptureIntent);
  }
}
