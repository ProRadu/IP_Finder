package com.example.ipfinder;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private Button button;
    private TextView textView;
    private FirebaseUser user;

    public ProfileFragment(){super(R.layout.fragment_profile);}


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        button=view.findViewById(R.id.logout);
        textView=view.findViewById(R.id.user_details);
        user=auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }
        else {
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}