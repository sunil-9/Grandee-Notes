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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.dhanas.grandeenotes.Adapter.SemesterBookAdapter;
import com.dhanas.grandeenotes.Adapter.ViewPagerAdapter;
import com.dhanas.grandeenotes.Model.BookModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.squareup.picasso.Picasso.Priority.HIGH;

public class SemesterBookList extends AppCompatActivity {
    PrefManager prefManager;
    ProgressDialog progressDialog;
    public String s_id, s_name;
    private static final int NUM_PAGES = 3;
    private String[] titles = new String[]{"Course", "Old Questions", "Syllabus"};

    TextView toolbar_title, txt_back, txt_course_book, txt_oldquestion, txt_syllabus, txt_semester_name, txt_books_total;
    CircularImageView iv_thumb;
    TextView txt_view_book, txt_download_book;
    RelativeLayout rl_adView;

    TabLayout tabLayout;
    TabItem tab_course, tab_oldquestion, tab_syllabus;
    ViewPager2 viewPager;
    ViewPagerAdapter pagerAdapter;

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
        setContentView(R.layout.semester_book_list);
        PrefManager.forceRTLIfSupported(getWindow(), SemesterBookList.this);
        prefManager = new PrefManager(SemesterBookList.this);
        progressDialog = new ProgressDialog(SemesterBookList.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        getBundle();


        rl_adView = findViewById(R.id.rl_adView);
        txt_back = (TextView) findViewById(R.id.txt_back);

        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.mypager);
        pagerAdapter = new ViewPagerAdapter(this,NUM_PAGES,s_id);
        viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager,  (tab, position) -> tab.setText(titles[position])).attach();


    }
   private void getBundle(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            s_id = bundle.getString("s_id");
            s_name = bundle.getString("s_name");

            Log.e("a_id", "" + s_id);

            toolbar_title.setText("" + s_name);

        }
    }

}
