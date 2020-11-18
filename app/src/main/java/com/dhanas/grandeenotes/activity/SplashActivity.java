package com.dhanas.grandeenotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.dhanas.grandeenotes.MainActivity;
import com.dhanas.grandeenotes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    ProgressBar mProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);

        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork() {
        for (int progress = 0; progress < 100; progress += 10) {
            try {
                Thread.sleep(100);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private void startApp() {
//        if (check == 1) {
//            finish();
//            System.exit(0);
//        } else {
//        if (mAuth.getCurrentUser() != null) {
//
//        }
//        else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

//        }

//        }
    }


}
