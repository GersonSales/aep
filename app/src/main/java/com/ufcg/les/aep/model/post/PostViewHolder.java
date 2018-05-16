package com.ufcg.les.aep.model.post;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.activity.PostDetailsActivity;
import com.ufcg.les.aep.util.Tag;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ufcg.les.aep.util.Tag.POST;

public class PostViewHolder extends RecyclerView.ViewHolder {
  
  private final Context context;
  @BindView(R.id.postId_textView)
  TextView postId;
  @BindView(R.id.imageId_imageObject)
  ImageView imagePost;
  @BindView(R.id.postid_titulo)
  TextView textTitulo;
  private Post post;
  
  public PostViewHolder(View itemView) {
    super(itemView);
    this.context = itemView.getContext();
    ButterKnife.bind(this, itemView);
  }
  
  public void bind(Post post) {
    this.post = post;
    postId.setText(post.getName());
    //imagePost.setImageBitmap(post.getImage()); TODO (when the attribute in POST has been created)
    //textTitulo.setText(post.getTitulo()); TODO (when the attribute in POST has been created)
  }
  
  @OnClick
  public void onClick() {
    final Intent detailsIntend = new Intent(this.context, PostDetailsActivity.class);
    detailsIntend.putExtra(POST, this.post);
    this.context.startActivity(detailsIntend);//TODO Start the specific activity of the current post.
  }
}
