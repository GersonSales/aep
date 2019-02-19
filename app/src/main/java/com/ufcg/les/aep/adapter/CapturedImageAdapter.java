package com.ufcg.les.aep.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.model.media.ImageViewHolder;
import com.ufcg.les.aep.model.post.PostViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

public class CapturedImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {
  
  private static CapturedImageAdapter instance;
  
  static  {
    instance = new CapturedImageAdapter();
  }
  
  public static CapturedImageAdapter getInstance() {
    return instance;
  }
  
  private CapturedImageAdapter() {mediaList = new ArrayList<>(); }
  
  private List<AbstractMedia> mediaList;
  
  @NonNull
  @Override
  public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    final View view = inflateView(parent, inflater);
    ButterKnife.bind(this, view);
    return new ImageViewHolder(view);
  }
  
  private View inflateView(@NonNull ViewGroup parent, LayoutInflater inflater) {
    return inflater.inflate(R.layout.captured_image_row, parent, false);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
    holder.bind(mediaList.get(position));
  }
  
  @Override
  public int getItemCount() {
    return mediaList.size();
  }
  
  public void addMedia(final AbstractMedia... media) {
    mediaList.addAll(Arrays.asList(media));
    notifyDataSetChanged();
  }
  
  
  public void clear() {
    mediaList.clear();
  }
  
  public void addAll(final List<AbstractMedia> medias) {
    this.mediaList.addAll(medias);
  }
}
