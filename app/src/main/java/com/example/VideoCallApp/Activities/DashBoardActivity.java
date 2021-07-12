package com.example.VideoCallApp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.VideoCallApp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashBoardActivity extends AppCompatActivity {
    com.example.VideoCallApp.databinding.ActivityDashBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.VideoCallApp.databinding.ActivityDashBoardBinding.inflate(getLayoutInflater());
       // setContentView(R.layout.activity_dash_board);
        setContentView(binding.getRoot());
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.content, new VideoCallFragment());
        mTransaction.commit();

        //USED onNavigationItemSelected TO FIND WHICH IS BEEN CLICKED IN THE BOTTOM NAVIGATION BAR
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch(item.getItemId()){
                    case R.id.logOut:
                        transaction.replace(R.id.content, new LogOutFragment());
                        break;
                    case R.id.videoCall:
                        transaction.replace(R.id.content, new VideoCallFragment());
                        break;
                    case R.id.chat:
                        transaction.replace(R.id.content, new ChatFragment());
                        break;
                    case R.id.groupChat:
                        transaction.replace(R.id.content , new GroupChatFragment());
                        break;
                }
                transaction.commit();
                return true;
            }
        });
    }

    //PROPERLY NAVIGATES TO PREVIOUS DESTINATIONS WHEN THE WHEN THE BACK BUTTON IS PRESSED
    public void onBackPressed()
    {
        finishAffinity();
    }
}