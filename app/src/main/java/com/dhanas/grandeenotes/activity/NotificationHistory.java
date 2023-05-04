package com.dhanas.grandeenotes.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dhanas.grandeenotes.Adapter.NotificationAdapter;
import com.dhanas.grandeenotes.Model.NotificationsModel.NotificationModel;
import com.dhanas.grandeenotes.Model.NotificationsModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.dhanas.grandeenotes.Webservice.AppAPI;
import com.dhanas.grandeenotes.Webservice.BaseURL;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationHistory extends AppCompatActivity {
    PrefManager prefManager;
    ProgressDialog progressDialog;

    List<Result> notificationList;
    RecyclerView rv_notification;
    NotificationAdapter notificationAdapter;
    TextView toolbar_title, txt_back;
    RelativeLayout rl_adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            //switch_theme.setChecked(true);
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_notification_history);
        PrefManager.forceRTLIfSupported(getWindow(), NotificationHistory.this);
        prefManager = new PrefManager(NotificationHistory.this);

        progressDialog = new ProgressDialog(NotificationHistory.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        rl_adView = findViewById(R.id.rl_adView);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        rv_notification =  findViewById(R.id.rv_notification);
        toolbar_title.setText("" + getResources().getString(R.string.notification));
        txt_back = (TextView) findViewById(R.id.txt_back);
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showNotification();

        if (prefManager.getValue("banner_ad").equalsIgnoreCase("yes")) {
            Admob();
            rl_adView.setVisibility(View.VISIBLE);
        } else {
            rl_adView.setVisibility(View.GONE);
        }

    }
    private void showNotification() {
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<NotificationModel> call = bookNPlayAPI.notificationList();
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if (response.code() == 200) {
                    notificationList = new ArrayList<>();
                    notificationList = response.body().getResult();
                    Log.e("NotificationModel", "" + notificationList.size());

                    notificationAdapter = new NotificationAdapter(NotificationHistory.this, notificationList);
                    rv_notification.setHasFixedSize(true);

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(NotificationHistory.this, 1, LinearLayoutManager.VERTICAL, false);
                    rv_notification.setLayoutManager(gridLayoutManager);
                    rv_notification.setItemAnimator(new DefaultItemAnimator());
                    rv_notification.setAdapter(notificationAdapter);
                    notificationAdapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void Admob() {

        try {
            AdView mAdView = new AdView(NotificationHistory.this);
            mAdView.setAdSize(AdSize.SMART_BANNER);
            mAdView.setAdUnitId(prefManager.getValue("banner_adid"));
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                }

                @Override
                public void onAdClosed() {
//                    Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    Log.e("errorcode", "" + errorCode);
//                    Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLeftApplication() {
//                    Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                }
            });
            mAdView.loadAd(adRequest);

            ((RelativeLayout) rl_adView).addView(mAdView);
        } catch (Exception e) {
            Log.e("Exception=>", "" + e.getMessage());
        }
    }

}