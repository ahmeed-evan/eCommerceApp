package com.example.ecommerceappexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ecommerceappexample.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Paper.init(SplashActivity.this);
                String userPhoneNumber = Paper.book().read(ConstrantKeys.USER_PHONE_NUMBER);
                String userPassword = Paper.book().read(ConstrantKeys.USER_PASSWORD);
                if (userPhoneNumber != null && userPassword != null) {
                    loginProcess(userPhoneNumber, userPassword);
                } else {
                    startActivity(new Intent(SplashActivity.this, AppMainActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void loginProcess(String userPhoneNumber, String userPassword) {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ConstrantKeys.USERS).child(ConstrantKeys.PHONE_NUMBER).exists()) {
                    User user = dataSnapshot.child(ConstrantKeys.USERS).child(ConstrantKeys.PHONE_NUMBER).getValue(User.class);
                    if (user.getUSER_PHONE_NUMBER().equals(userPhoneNumber)) {
                        if (user.getUSER_PASSWORD().equals(userPassword)) {
                            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
