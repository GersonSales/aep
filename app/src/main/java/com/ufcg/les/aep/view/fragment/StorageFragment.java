package com.ufcg.les.aep.view.fragment;

import android.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ufcg.les.aep.R;
import com.ufcg.les.aep.adapter.ImageStorageAdapter;
import com.ufcg.les.aep.model.media.AbstractMedia;
import com.ufcg.les.aep.view.activity.PostCreationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class StorageFragment extends Fragment {
  
  
  @BindView(R.id.storage_grid)
  /*default*/ GridView gridLayout;
  
  private OnFragmentInteractionListener mListener;
  
  public StorageFragment() {
    // Required empty public constructor
  }
  
  public static StorageFragment newInstance() {
    return new StorageFragment();
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    clearViewGroup(container);
    final View view = inflater.inflate(R.layout.fragment_storage, container, false);
    ButterKnife.bind(this, view);
    initGridLayout();
    return view;
  }
  
  private void clearViewGroup(final ViewGroup container) {
    if (container != null) {
      container.removeAllViews();
    }
  }
  
  private void initGridLayout() {
    this.gridLayout.setAdapter(ImageStorageAdapter.getInstance(getContext()));
  }
  
  @OnItemClick(R.id.storage_grid)
  public void onItemClick(final int position) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle(R.string.new_post)
       .setMessage(R.string.would_you_like_to_post_this_image)
       .setPositiveButton(R.string.yes, (dialogInterface, i) ->
          goToPostCreationFragment(ImageStorageAdapter.getInstance(getContext()).getItem(position)))
       .show();
    
  }
  
  private void goToPostCreationFragment(final AbstractMedia media) {
    final FragmentManager fragmentManager = getFragmentManager();
    if (fragmentManager != null) {
    fragmentManager
       .beginTransaction()
       .replace(R.id.content_frame_layout, PostCreationFragment.newInstance(media))
       .detach(this)
       .commit();
    }
  }
  
  public void onButtonPressed() {
    if (mListener != null) {
      mListener.onFragmentInteraction();
    }
  }
  
  @Override
  public void onAttach(final Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
         + " must implement OnFragmentInteractionListener");
    }
  }
  
  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }
  
  public interface OnFragmentInteractionListener {
    void onFragmentInteraction();
  }
}
