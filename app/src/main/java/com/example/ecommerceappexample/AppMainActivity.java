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
        Paper.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String userPhoneNumber=Paper.book().read(ConstrantKeys.USER_PHONE_NUMBER);
        String userPassword=Paper.book().read(ConstrantKeys.USER_PASSWORD);
        if (userPhoneNumber!= "" && userPassword != ""){
            loginProcess(userPhoneNumber,userPassword);
        }
    }

    private void loginProcess(String userPhoneNumber, String userPassword) {

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ConstrantKeys.USERS).child(ConstrantKeys.PHONE_NUMBER).exists()){
                    User user=dataSnapshot.child(ConstrantKeys.USERS).child(ConstrantKeys.PHONE_NUMBER).getValue(User.class);
                    if (user.getUSER_PHONE_NUMBER().equals(userPhoneNumber)){
                        if (user.getUSER_PASSWORD().equals(userPassword)){
                            startActivity(new Intent(AppMainActivity.this,HomeActivity.class));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
