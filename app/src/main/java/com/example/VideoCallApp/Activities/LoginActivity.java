package com.example.VideoCallApp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.VideoCallApp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailBox,passwordBox;
    Button loginBtn,signUpBtn;

    TextView forgotPassword;

    FirebaseAuth mAuth;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C5373")));

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait..");

        //CREATED INSTANCE OF FIREBASEAUTH
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null)
        {
            Intent intent = new Intent(LoginActivity.this,DashBoardActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);
        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);

        loginBtn = findViewById(R.id.loginButton);
        signUpBtn = findViewById(R.id.createButton);

        forgotPassword = findViewById(R.id.forgotPassword);

        //THE onClick FUNCTION INSIDE THE setOnClickListener EXECUTES WHEN THE LOGIN BUTTON IS CLICKED
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass;
                email = emailBox.getText().toString();
                pass = passwordBox.getText().toString();
                if(email.equals("") || pass.equals(""))
                {
                    Toast.makeText(LoginActivity.this,"Please fill out all the fields!",Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.show();
                    //FOR AUTHORISING THE EMAIL AND PASSWORD FROM THE DATABASE
                    mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if(task.isSuccessful()) {
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
        //THE onClick FUNCTION INSIDE THE setOnClickListener EXECUTES WHEN THE SIGN UP BUTTON IS CLICKED
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IT WILL CHANGE THE ACTIVITY FROM LOGIN TO SIGN UP
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        //THE onClick FUNCTION INSIDE THE setOnClickListener EXECUTES WHEN THE FORGOT PASSWORD TEXT FIELD IS CLICKED
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailBox.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Please Enter the Registered Email", Toast.LENGTH_SHORT).show();
                }
                else {
                    //IT WILL SEND THE RESET PASSWORD EMAIL TO THE REGISTERED EMAIL
                    mAuth.getInstance().sendPasswordResetEmail(emailBox.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Reset Password is sent to your Email...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });



    }

    //PROPERLY NAVIGATES TO PREVIOUS DESTINATIONS WHEN THE WHEN THE BACK BUTTON IS PRESSED
    public void onBackPressed()
    {
        finishAffinity();
    }
}