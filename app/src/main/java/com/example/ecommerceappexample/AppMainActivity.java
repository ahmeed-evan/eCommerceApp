package com.example.ecommerceappexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.ecommerceappexample.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class AppMainActivity extends AppCompatActivity {

    @BindView(R.id.joinButton)
    Button joinButton;
    @BindView(R.id.alreadyRegisteredButton)
    Button alreadyRegisteredButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.alreadyRegisteredButton)
    public void onAlreadyRegisteredButtonClicked(){
       startActivity(new Intent(AppMainActivity.this,Login_Activity.class));
    }

    @OnClick(R.id.joinButton)
    public void onJoinButtonClicked() {
        startActivity(new Intent(AppMainActivity.this, RegisterActivity.class));
    }
}
