package com.dhanas.grandeenotes.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Adapter.SemesterBookAdapter;
import com.dhanas.grandeenotes.Model.BookModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.squareup.picasso.Picasso.Priority.HIGH;

public class SemesterBookList extends AppCompatActivity {

    RecyclerView rv_booklist;
    List<Result> BookList;
    SemesterBookAdapter semesterBookAdapter;

    PrefManager prefManager;
    ProgressDialog progressDialog;
    String s_id, s_name;

    TextView toolbar_title, txt_back, txt_course_book,txt_oldquestion, txt_syllabus,  txt_semester_name,txt_books_total;
    CircularImageView iv_thumb;
    TextView txt_view_book, txt_download_book;
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
        setContentView(R.layout.semester_book_list);
        PrefManager.forceRTLIfSupported(getWindow(), SemesterBookList.this);
        prefManager = new PrefManager(SemesterBookList.this);
        progressDialog = new ProgressDialog(SemesterBookList.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        rl_adView = findViewById(R.id.rl_adView);
        txt_course_book = (TextView) findViewById(R.id.txt_course_book);
        txt_semester_name = (TextView) findViewById(R.id.txt_sem_name);
//        txt_oldquestion = (TextView) findViewById(R.id.txt_oldquestion_book);
//        txt_syllabus = (TextView) findViewById(R.id.txt_syllabus_book);
        txt_books_total = (TextView) findViewById(R.id.txt_books_total);

        rv_booklist = (RecyclerView) findViewById(R.id.rv_booklist);
        iv_thumb = (CircularImageView) findViewById(R.id.image);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        txt_view_book = (TextView) findViewById(R.id.txt_view_book);
        txt_download_book = (TextView) findViewById(R.id.txt_download_book);

        txt_back = (TextView) findViewById(R.id.txt_back);

        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            s_id = bundle.getString("s_id");
            s_name = bundle.getString("s_name");

            Log.e("a_id", "" + s_id);

            toolbar_title.setText("" + s_name);
//            txt_semester_name.setText("" + s_name);
//            txt_course_book.setText("" + s_name + "'s course " + getResources().getString(R.string.Books));
//            txt_syllabus.setText("" + s_name + "'s syllabus " + getResources().getString(R.string.syllabus));
//            txt_oldquestion.setText("" + s_name + "'s course " + getResources().getString(R.string.oldquestion));

        }

    }

    }
