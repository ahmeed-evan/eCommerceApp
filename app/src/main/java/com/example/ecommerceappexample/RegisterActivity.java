package com.example.ecommerceappexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private String DATABASE_USER_KIND="USERS";

    @BindView(R.id.userNameEditText)
    EditText userNameEditText;
    @BindView(R.id.regPhoneNumberEditText)
    EditText regPhoneNumberEditText;
    @BindView(R.id.regPasswordEditText)
    EditText regPasswordEditText;
    @BindView(R.id.registerButton)
    Button registerButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_registation);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
    }

    @OnClick(R.id.registerButton)
    public void onButtonClickeed() {
        createNewAccount();
    }

    private void createNewAccount() {
        String userName = userNameEditText.getText().toString();
        String userPhone = regPhoneNumberEditText.getText().toString();
        String userPassword = regPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Please Provide a Username..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPhone)) {
            Toast.makeText(this, "Please Provide a Phone Number ..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Please Provide a Password..", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Creating Account");
            progressDialog.setMessage("Please Wait When We Are Checking The Credintials.");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            registationProcess(userName, userPhone, userPassword);
        }
    }

    private void registationProcess(String userName, String userPhone, String userPassword) {
        final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(DATABASE_USER_KIND).child(userPhone).exists()) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put(ConstrantKeys.USER_NAME, userName);
                    userDataMap.put(ConstrantKeys.USER_PHONE_NUMBER, userPhone);
                    userDataMap.put(ConstrantKeys.USER_PASSWORD, userPassword);
                    rootReference.child(DATABASE_USER_KIND).child(userPhone).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"Registation Complete Successfully!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this,Login_Activity.class));
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"Something Went Wrong. Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "This Phone Number Is Already Used!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, AppMainActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
