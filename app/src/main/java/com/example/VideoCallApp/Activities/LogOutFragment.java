package com.example.VideoCallApp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.VideoCallApp.R;
import com.google.firebase.auth.FirebaseAuth;


public class LogOutFragment extends Fragment {
    public LogOutFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_call, container, false);
        //startActivity(new Intent(container.getContext() , MainActivity.class));
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(container.getContext() , MainActivity.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        startActivity(new Intent(container.getContext() , DashBoardActivity.class));
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
        builder.setMessage("Do you want to logout?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
        return view;
    }
}