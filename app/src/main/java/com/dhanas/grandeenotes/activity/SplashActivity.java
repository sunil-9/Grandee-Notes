package com.dhanas.grandeenotes.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.dhanas.grandeenotes.Model.GeneralSettings.GeneralSettings;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.MutedVideoView;
import com.dhanas.grandeenotes.Utility.MyApp;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.dhanas.grandeenotes.Webservice.AppAPI;
import com.dhanas.grandeenotes.Webservice.BaseURL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private PrefManager prefManager;
    Intent mainIntent;
    private boolean ispaused = false;
    private boolean connection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyApp.getInstance().initAppLanguage(this);
        setContentView(R.layout.splash);
        PrefManager.forceRTLIfSupported(getWindow(), SplashActivity.this);
        prefManager = new PrefManager(SplashActivity.this);


        MutedVideoView vView = (MutedVideoView) findViewById(R.id.video_view);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.splash);
        if (vView != null) {
            vView.setVideoURI(video);
            vView.setZOrderOnTop(true);
            vView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                }

            });

            vView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {

                    return false;
                }
            });

            vView.start();
            general_settings();
        } else {
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        if (ispaused) {
            general_settings();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        ispaused = true;
    }

    private void general_settings() {
        connection = getConnectivityStatus(SplashActivity.this);
        if (!connection) {

            Toast.makeText(SplashActivity.this, "No internet Connection view downloaded content", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashActivity.this,DownloadedBooks.class));
            finish();
        }
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<GeneralSettings> call = bookNPlayAPI.general_settings();
        call.enqueue(new Callback<GeneralSettings>() {
            @Override
            public void onResponse(Call<GeneralSettings> call, Response<GeneralSettings> response) {
                if (response.code() == 200) {

                    prefManager = new PrefManager(SplashActivity.this);
                    Boolean night_mode = prefManager.isNightModeEnabled();
                    if (night_mode)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        Log.e("==>", "" + response.body().getResult().get(i).getKey());
                        Log.e("==>", "" + response.body().getResult().get(i).getValue());
                        prefManager.setValue(response.body().getResult().get(i).getKey(), response.body().getResult().get(i).getValue());
                    }
                    jump();
                } else {
                    Toast.makeText(SplashActivity.this, "Unable to fetch data please try again letter", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralSettings> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Server error please try again letter", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void jump() {
        if (!prefManager.isFirstTimeLaunch()) {
            if (prefManager.getLoginId().equalsIgnoreCase("0"))
                mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
            else
                mainIntent = new Intent(SplashActivity.this, MainActivity.class);

        } else {
             mainIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
        }
        startActivity(mainIntent);
        finish();
    }

    public static boolean getConnectivityStatus(Context context) {
        boolean status = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            status = true;
        } else {
            status = false;
        }
        return status;
    }
}
