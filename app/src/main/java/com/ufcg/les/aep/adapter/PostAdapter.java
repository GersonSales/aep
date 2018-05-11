package com.ufcg.les.aep.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.model.post.Post;

import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<Post> {

    private static PostAdapter instance;

    private PostAdapter() { }

    static {
        instance = new PostAdapter();
    }

    public static PostAdapter getInstance() {
        return instance;
    }


    @NonNull
    @Override
    public Post onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view  = inflateView(parent, inflater);
        ButterKnife.bind(this, view);
        return new Post(view);
    }

    private View inflateView(@NonNull ViewGroup parent, LayoutInflater inflater) {
        return inflater.inflate(R.layout.post_row, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull Post holder, int position) {
        holder.bind();

    }

    @Override
    public int getItemCount() {
        return 10;//TODO Create a mock
    }
}
