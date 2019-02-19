package com.ufcg.les.aep.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.model.post.PostViewHolder;
import com.ufcg.les.aep.util.service.PostBuffer;

import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> implements PostBuffer.PostBufferObserver {
  private static PostAdapter instance;
  
  static {
    instance = new PostAdapter();
  }
  
  private PostAdapter() {
    PostBuffer.getInstance().addObserver(this);
  }
  
  public static PostAdapter getInstance() {
    return instance;
  }
  
  @NonNull
  @Override
  public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    final View view = inflateView(parent, inflater);
    ButterKnife.bind(this, view);
    return new PostViewHolder(view);
    
  }
  
  private View inflateView(@NonNull ViewGroup parent, LayoutInflater inflater) {
    return inflater.inflate(R.layout.post_row, parent, false);
  }
  
  @Override
  public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
    holder.bind(getPostByPosition(position));
  }
  
  private Post getPostByPosition(final int position) {
    return PostBuffer.getInstance().getByPosition(position);
  }
  
  @Override
  public int getItemCount() {
    return PostBuffer.getInstance().size();
  }
  
  public void filterByNameThatContains(final String text) {
    PostBuffer.getInstance().filterByNameThatContains(text);
    notifyDataSetChanged();
  }
  
  public void restoreList() {
    PostBuffer.getInstance().filterByNameThatContains("");
    notifyDataSetChanged();
  }
}
