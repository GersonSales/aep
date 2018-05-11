package com.ufcg.les.aep.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.PostAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedActivity extends AppCompatActivity {

    @BindView(R.id.feed_recyclerView)
    RecyclerView feedRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);

        feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecyclerView.setAdapter(PostAdapter.getInstance());
    }
}
