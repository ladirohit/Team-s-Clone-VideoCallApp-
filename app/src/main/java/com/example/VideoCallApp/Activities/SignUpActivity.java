package com.example.VideoCallApp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.VideoCallApp.R;
import com.example.VideoCallApp.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseDatabase mDatabase;
    EditText emailBox,passwordBox,nameBox;
    Button loginBtn,signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //CREATED INSTANCE OF FIREBASEFIRESTORE ,  FIREBASEAUTH , FIREBASEDATABASE
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        emailBox = findViewById(R.id.emailBox);
        nameBox = findViewById(R.id.nameBox);
        passwordBox = findViewById(R.id.passwordBox);

        loginBtn = findViewById(R.id.loginButton);
        signUpBtn = findViewById(R.id.createButton);

        //THE onClick FUNCTION INSIDE THE setOnClickListener EXECUTES WHEN THE LOGIN BUTTON IS CLICKED
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IT WILL CHANGE THE ACTIVITY FROM SIGN UP TO LOGIN
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });

        //THE onClick FUNCTION INSIDE THE setOnClickListener EXECUTES WHEN THE SIGN UP BUTTON IS CLICKED
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass,name;
                email = emailBox.getText().toString();
                pass = passwordBox.getText().toString();
                name = nameBox.getText().toString();
                if(email.equals("") || pass.equals("") || name.equals(""))
                {
                    Toast.makeText(SignUpActivity.this,"Please fill out all the fields!",Toast.LENGTH_SHORT).show();
                }
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
                                user.setUid(auth.getUid());
                                database.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //IT WILL CHANGE THE ACTIVITY FROM SIGN UP TO LOGIN
                                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                    }
                                });

                                mDatabase.getReference().child("Users")
                                        .child(auth.getUid())
                                        .setValue(user);

                                Toast.makeText(SignUpActivity.this,"Account is Created",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(SignUpActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}