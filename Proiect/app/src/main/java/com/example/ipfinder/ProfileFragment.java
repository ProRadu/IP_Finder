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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;


public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private Button button;
    private TextView textViewEmail, textViewUserProfile;
    private FirebaseUser user;
    private String userID;

    public ProfileFragment(){super(R.layout.fragment_profile);}


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        button=view.findViewById(R.id.logout);
        textViewEmail=view.findViewById(R.id.user_details);
        textViewUserProfile=view.findViewById(R.id.user_profile);
        user=auth.getCurrentUser();







        if(user == null){
            Intent intent = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }
        else {
            fStore = FirebaseFirestore.getInstance();
            userID=user.getUid();
            DocumentReference documentReference = fStore.collection("users").document(userID);
            documentReference.addSnapshotListener( new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    if (documentSnapshot != null) {
                        textViewEmail.setText(documentSnapshot.getString("email"));
                    }
                }
            });
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