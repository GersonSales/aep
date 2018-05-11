package com.ufcg.les.aep.model.post;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ufcg.les.aep.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Post extends RecyclerView.ViewHolder {

    private static int id;

    @BindView(R.id.postId_textView)
    TextView postId;

    public Post(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind() {
        postId.setText(String.valueOf(id++));
    }
}
