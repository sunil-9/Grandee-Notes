package com.dhanas.grandeenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dhanas.grandeenotes.fragments.LoginFragment;
import com.dhanas.grandeenotes.fragments.SignupFragment;

public class MainActivity extends AppCompatActivity {
    ImageView login;
    ImageView signup;
    ImageView toc;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login =findViewById(R.id.btn_login);
        signup=findViewById(R.id.btn_register);
        toc= findViewById(R.id.toc);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginFragment();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupFragment();

            }
        });
        toc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "this is TAC", Toast.LENGTH_SHORT).show();

            }
        });
    }
    protected void openLoginFragment(){
        LoginFragment loginFragment = new LoginFragment();
        transaction.setCustomAnimations(R.anim.right_enter, R.anim.left_enter, R.anim.right_enter, R.anim.left_enter);
        transaction.replace(R.id.welcome_screen, loginFragment).addToBackStack(null).commit();
    }
    protected void openSignupFragment(){
        SignupFragment signupFragment = new SignupFragment();
        transaction.setCustomAnimations(R.anim.right_enter, R.anim.left_enter, R.anim.right_enter, R.anim.left_enter);
        transaction.replace(R.id.welcome_screen, signupFragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.welcome_screen);
        if(fragment != null){
            transaction.remove(fragment).addToBackStack(null).commit();;
        }
        else {
        super.onBackPressed();
        }
    }
}