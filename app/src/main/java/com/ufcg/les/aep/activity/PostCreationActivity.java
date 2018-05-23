package com.ufcg.les.aep.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;


import com.ufcg.les.aep.R;



import butterknife.BindView;
import butterknife.ButterKnife;

public class PostCreationActivity extends AppCompatActivity {
  
  
  @BindView(R.id.dateEdit_editText)
  EditText dateUser;
  
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);
        ButterKnife.bind(this);
    }
    
    
    
}
