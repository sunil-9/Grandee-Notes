package com.dhanas.grandeenotes.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Adapter.SemesterAdapter;
import com.dhanas.grandeenotes.Model.SemesterModel.Result;
import com.dhanas.grandeenotes.Model.SemesterModel.SemesterModel;
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

public class SemesterAllActivity extends AppCompatActivity {

    PrefManager prefManager;
    ProgressDialog progressDialog;

    List<Result> semesterList;
    RecyclerView rv_semester;
    SemesterAdapter semesterAdapter;
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
        setContentView(R.layout.semester_activity);
        PrefManager.forceRTLIfSupported(getWindow(), SemesterAllActivity.this);
        rv_semester = (RecyclerView) findViewById(R.id.rv_semester);

        prefManager = new PrefManager(SemesterAllActivity.this);

        progressDialog = new ProgressDialog(SemesterAllActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        rl_adView = findViewById(R.id.rl_adView);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("" + getResources().getString(R.string.semester));
        txt_back = (TextView) findViewById(R.id.txt_back);
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        SemesterList();

        if (prefManager.getValue("banner_ad").equalsIgnoreCase("yes")) {
            Admob();
            rl_adView.setVisibility(View.VISIBLE);
        } else {
            rl_adView.setVisibility(View.GONE);
        }

    }

    private void SemesterList() {
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<SemesterModel> call = bookNPlayAPI.semesterlist();
        call.enqueue(new Callback<SemesterModel>() {
            @Override
            public void onResponse(Call<SemesterModel> call, Response<SemesterModel> response) {
                if (response.code() == 200) {

                    semesterList = new ArrayList<>();
                    semesterList = response.body().getResult();
                    Log.e("AuthorList", "" + semesterList.size());

                    semesterAdapter = new SemesterAdapter(SemesterAllActivity.this, semesterList);
                    rv_semester.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(SemesterAllActivity.this,
                            LinearLayoutManager.HORIZONTAL, false);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(SemesterAllActivity.this, 3, LinearLayoutManager.VERTICAL, false);
                    rv_semester.setLayoutManager(gridLayoutManager);
                    rv_semester.setItemAnimator(new DefaultItemAnimator());
                    rv_semester.setAdapter(semesterAdapter);
                    semesterAdapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SemesterModel> call, Throwable t) {
                progressDialog.dismiss();
            }

            });

    }




    public void Admob() {

        try {
            AdView mAdView = new AdView(SemesterAllActivity.this);
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
