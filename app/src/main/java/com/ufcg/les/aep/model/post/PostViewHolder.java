package com.ufcg.les.aep.model.post;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.mock.Mocker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.postId_textView)
    TextView postId;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Post post) {
        postId.setText(post.getName());
    }

    @OnClick
    public void onClick() {
        Mocker.POST_MOCK.add(new Post("Name"));
//        this.context.startActivity(new Intent(this.context, PostDetailsActivity.class));//TODO Start the specific activity of the current post.
    }
}
