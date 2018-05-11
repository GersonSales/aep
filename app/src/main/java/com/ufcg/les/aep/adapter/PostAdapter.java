package com.ufcg.les.aep.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.behaviour.Observer;
import com.ufcg.les.aep.model.mock.Mocker;
import com.ufcg.les.aep.model.post.Post;
import com.ufcg.les.aep.model.post.PostViewHolder;

import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> implements Observer {


    private static PostAdapter instance;

    private PostAdapter() {
        Mocker.POST_MOCK.add(this);
        Mocker.POST_MOCK.add(new Post("Post")); //TODO remove this initial post creation
    }

    static {
        instance = new PostAdapter();
    }

    public static PostAdapter getInstance() {
        return instance;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view  = inflateView(parent, inflater);
        ButterKnife.bind(this, view);
        return new PostViewHolder(view);
    }

    private View inflateView(@NonNull ViewGroup parent, LayoutInflater inflater) {
        return inflater.inflate(R.layout.post_row, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(Mocker.POST_MOCK.getByPosition(position));
    }

    @Override
    public int getItemCount() {
        return Mocker.POST_MOCK.size();
    }

    public void advise() {
        notifyDataSetChanged();
    }
}
