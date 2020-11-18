package com.dhanas.grandeenotes.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dhanas.grandeenotes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class SignupFragment extends Fragment {
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_signup, container, false);

        final EditText email_address = view.findViewById(R.id.input_email);
        final EditText password_data = view.findViewById(R.id.input_password);
        ImageView back = view.findViewById(R.id.back);
        ImageView register = view.findViewById(R.id.register);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: back arow work here

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_to_login= email_address.getText().toString().toLowerCase().trim();
                String password= password_data.getText().toString().trim();
                String first_name= password_data.getText().toString().trim();
                String last_name= password_data.getText().toString().trim();
                mAuth = FirebaseAuth.getInstance();


                mAuth.createUserWithEmailAndPassword(email_to_login, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
                                    success(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });

        return view;
    }

    private void success(FirebaseUser user) {
        Toast.makeText(getActivity(), "Authentication success.",
                Toast.LENGTH_SHORT).show();
    }
}