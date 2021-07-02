package com.example.VideoCallApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;
    EditText emailBox,passwordBox,nameBox;
    Button loginBtn,signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        emailBox = findViewById(R.id.emailBox);
        nameBox = findViewById(R.id.nameBox);
        passwordBox = findViewById(R.id.passwordBox);

        loginBtn = findViewById(R.id.loginButton);
        signUpBtn = findViewById(R.id.createButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass,name;
                email = emailBox.getText().toString();
                pass = passwordBox.getText().toString();
                name = nameBox.getText().toString();
                if(email.equals("") || pass.equals("") || name.equals(""))
                {
                    Toast.makeText(SignupActivity.this,"Please fill out all the fields!",Toast.LENGTH_SHORT).show();
                }
//                else if(email.equals(""))
//                {
//                    Toast.makeText(SignupActivity.this,"Please Enter the Email!",Toast.LENGTH_SHORT).show();
//                }
//                else if(pass.equals(""))
//                {
//                    Toast.makeText(SignupActivity.this,"Please Enter the Password!",Toast.LENGTH_SHORT).show();
//                }
//                else if(name.equals(""))
//                {
//                    Toast.makeText(SignupActivity.this,"Please Enter the Username!",Toast.LENGTH_SHORT).show();
//                }
                else
                {
                    User user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setPass(pass);

                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                database.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                    }
                                });
                                Toast.makeText(SignupActivity.this,"Account is Created",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}