package com.example.ecommerceappexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceappexample.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.paperdb.Paper;

public class Login_Activity extends AppCompatActivity {

    private String DATABASE_USER_KIND = "USERS";
    private ProgressDialog progressDialog;

    @BindView(R.id.regPhoneNumberEditText)
    EditText regPhoneNumberEditText;
    @BindView(R.id.regPasswordEditText)
    EditText regPasswordEditText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.rememberMeCheckBox)
    CheckBox rememberMeCheckBox;
    @BindView(R.id.adminTextView)
    TextView adminTextView;
    @BindView(R.id.notAdminTextView)
    TextView notAdminTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        Paper.init(this);
    }


    @OnClick(R.id.loginButton)
    public void onLoginButtonClicked() {
        String phoneNumber = regPhoneNumberEditText.getText().toString();
        String password = regPasswordEditText.getText().toString();

        if (rememberMeCheckBox.isChecked()) {
            Paper.book().write(ConstrantKeys.USER_PHONE_NUMBER, phoneNumber);
            Paper.book().write(ConstrantKeys.USER_PASSWORD, password);
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "Please Provide A Phone Number To Login! ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Provide A Password To Login! ", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Please Wait, We Are Checking Credentials");
            progressDialog.show();
            loginProcess(phoneNumber, password);
        }
    }

    @OnClick(R.id.adminTextView)
    public void onAdminTextClicked() {
        loginButton.setText("ADMIN LOGIN");
        rememberMeCheckBox.setVisibility(View.GONE);
        adminTextView.setVisibility(View.GONE);
        notAdminTextView.setVisibility(View.VISIBLE);
        this.DATABASE_USER_KIND = "ADMINS";
    }

    @OnClick(R.id.notAdminTextView)
    public void onNotAdminTextClicked() {
        loginButton.setText("LOGIN");
        rememberMeCheckBox.setVisibility(View.VISIBLE);
        adminTextView.setVisibility(View.VISIBLE);
        notAdminTextView.setVisibility(View.GONE);
        this.DATABASE_USER_KIND = "USERS";
    }

    private void loginProcess(String phoneNumber, String password) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(DATABASE_USER_KIND).child(phoneNumber).exists()) {
                    if (DATABASE_USER_KIND.equals("ADMINS")) {
                        User user = dataSnapshot.child(DATABASE_USER_KIND).child(phoneNumber).getValue(User.class);
                        if (user.getUSER_PHONE_NUMBER().equals(phoneNumber)) {
                            if (user.getUSER_PASSWORD().equals(password)) {
                                progressDialog.dismiss();
                                startActivity(new Intent(Login_Activity.this, AdminActivity.class));
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Login_Activity.this, "Phone Number or Password Is Incorrect! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (DATABASE_USER_KIND.equals("USERS")) {
                        User user = dataSnapshot.child(DATABASE_USER_KIND).child(phoneNumber).getValue(User.class);
                        if (user.getUSER_PHONE_NUMBER().equals(phoneNumber)) {
                            if (user.getUSER_PASSWORD().equals(password)) {
                                progressDialog.dismiss();
                                startActivity(new Intent(Login_Activity.this, HomeActivity.class));
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Login_Activity.this, "Phone Number or Password Is Incorrect! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Login_Activity.this, "Something Went Wrong. Please Check Your Internet Connection! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
