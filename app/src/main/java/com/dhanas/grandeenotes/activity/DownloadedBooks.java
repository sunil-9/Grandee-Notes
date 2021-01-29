package com.dhanas.grandeenotes.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Adapter.MyDownloadBooksAdapter;
import com.dhanas.grandeenotes.Model.BookModel.BookModel;
import com.dhanas.grandeenotes.Model.BookModel.Result;
import com.dhanas.grandeenotes.Model.downloads.DownloadModel;
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

import static com.dhanas.grandeenotes.Utility.Constants.DATABASE_NAME;

public class DownloadedBooks extends AppCompatActivity {

    List<Result> MyDownloadBookList;
    RecyclerView rv_mydownloadbooks;
    MyDownloadBooksAdapter myDownloadBooksAdapter;

    PrefManager prefManager;
    ProgressDialog progressDialog;

    SQLiteDatabase mDatabase;
    List<DownloadModel> bookList;
    TextView toolbar_title, txt_back;
    LinearLayout ly_dataNotFound;
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
        setContentView(R.layout.downloadedbooks);
        bookList = new ArrayList<>();
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        ly_dataNotFound = findViewById(R.id.ly_dataNotFound);
        prefManager = new PrefManager(DownloadedBooks.this);

        progressDialog = new ProgressDialog(DownloadedBooks.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        rl_adView = findViewById(R.id.rl_adView);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("" + getResources().getString(R.string.my_downloaded_book));
        rv_mydownloadbooks = (RecyclerView) findViewById(R.id.rv_mydownloadbooks);

        txt_back = (TextView) findViewById(R.id.txt_back);
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        if (prefManager.getLoginId().equalsIgnoreCase("0")) {
//            startActivity(new Intent(DownloadedBooks.this, LoginActivity.class));
//        } else {
            downloadlist();
//        }

//        if (prefManager.getValue("banner_ad").equalsIgnoreCase("yes")) {
            Admob();
            rl_adView.setVisibility(View.VISIBLE);
//        } else {
//            rl_adView.setVisibility(View.GONE);
//        }

    }

    private void downloadlist() {
        progressDialog.show();
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorBooks = mDatabase.rawQuery("SELECT * FROM books", null);
        //if the cursor has some data
        if (cursorBooks.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                bookList.add(new DownloadModel(
                        cursorBooks.getInt(0),
                        cursorBooks.getString(1),
                        cursorBooks.getString(2)
                ));
            } while (cursorBooks.moveToNext());
        }
        //closing the cursor
        cursorBooks.close();

        if (bookList.size() > 0) {
            myDownloadBooksAdapter = new MyDownloadBooksAdapter(DownloadedBooks.this, bookList, mDatabase);
            rv_mydownloadbooks.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(DownloadedBooks.this,
                    LinearLayoutManager.HORIZONTAL, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(DownloadedBooks.this, 3,
                    LinearLayoutManager.VERTICAL, false);
            rv_mydownloadbooks.setLayoutManager(gridLayoutManager);
            rv_mydownloadbooks.setItemAnimator(new DefaultItemAnimator());
            rv_mydownloadbooks.setAdapter(myDownloadBooksAdapter);
            myDownloadBooksAdapter.notifyDataSetChanged();
        } else {
            ly_dataNotFound.setVisibility(View.VISIBLE);
        }
                progressDialog.dismiss();
    }

    public void Admob() {
        try {
            AdView mAdView = new AdView(DownloadedBooks.this);
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
