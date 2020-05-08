package com.example.ecommerceappexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
