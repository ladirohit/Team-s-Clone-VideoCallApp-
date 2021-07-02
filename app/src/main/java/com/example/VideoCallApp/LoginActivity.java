package com.example.VideoCallApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailBox,passwordBox;
    Button loginBtn,signUpBtn;

    FirebaseAuth auth;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait..");

        auth= FirebaseAuth.getInstance();

        setContentView(R.layout.activity_login);
        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);

        loginBtn = findViewById(R.id.loginButton);
        signUpBtn = findViewById(R.id.createButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass;
                email = emailBox.getText().toString();
                pass = passwordBox.getText().toString();
                if(email.equals("") || pass.equals(""))
                {
                    Toast.makeText(LoginActivity.this,"Please fill out all the fields!",Toast.LENGTH_SHORT).show();
//                    if(email.equals(""))
//                    {
//                        emailBox.setError("Please fill out Email!");
//                    }
//                    if(pass.equals(""))
//                    {
//                        passwordBox.setError("Please fill out Password!");
//                    }
                }
//                else if(email.equals(""))
//                {
//                    Toast.makeText(LoginActivity.this,"Please Enter the Email!",Toast.LENGTH_SHORT).show();
//                }
//                else if(pass.equals(""))
//                {
//                    Toast.makeText(LoginActivity.this,"Please Enter the Password!",Toast.LENGTH_SHORT).show();
//                }
                else {
                    dialog.show();
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if(task.isSuccessful()) {
                                //Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,DashBoardActivity.class));
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
    }
}