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
import android.widget.TextView;
import com.dhanas.grandeenotes.Adapter.AnswerAdapter;
import com.dhanas.grandeenotes.Model.AnswerModel.AnswerModel;
import com.dhanas.grandeenotes.Model.AnswerModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.dhanas.grandeenotes.Webservice.AppAPI;
import com.dhanas.grandeenotes.Webservice.BaseURL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dhanas.grandeenotes.Utility.Constants.please_wait;

public class AnswerActivity extends AppCompatActivity {
    private String q_id,question;
    private TextView tv_question;
    ProgressDialog progressDialog;
    List<Result> answerList;
    AnswerAdapter answerAdapter;
    private RecyclerView rv_questionList;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            //switch_theme.setChecked(true);
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        tv_question =findViewById(R.id.tv_question);

        prefManager =new PrefManager(AnswerActivity.this);

        progressDialog = new ProgressDialog(AnswerActivity.this);
        progressDialog.setMessage(please_wait);
        progressDialog.setCanceledOnTouchOutside(false);
        rv_questionList = (RecyclerView) findViewById(R.id.recycleView);


// getting the bundle back from the android
        Bundle bundle = getIntent().getExtras();

// getting the string back
         q_id = bundle.getString("q_id", "");
         question = bundle.getString("question", "");
        tv_question.setText(question);
         list_answer(q_id);
//        Toast.makeText(this, q_id, Toast.LENGTH_SHORT).show();

    }

    private void list_answer(String q_id) {

        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<AnswerModel> call = bookNPlayAPI.all_answer(q_id);
        call.enqueue(new Callback<AnswerModel>() {
            @Override
            public void onResponse(Call<AnswerModel> call, Response<AnswerModel> response) {
                if (response.code() == 200) {
                    answerList = new ArrayList<>();
                    answerList = response.body().getResult();
                    answerAdapter = new AnswerAdapter(AnswerActivity.this, answerList);
                    rv_questionList.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(AnswerActivity.this,
                            LinearLayoutManager.HORIZONTAL, false);
//                    rv_booklist.setLayoutManager(mLayoutManager3);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(AnswerActivity.this, 1, LinearLayoutManager.VERTICAL, false);
                    rv_questionList.setLayoutManager(gridLayoutManager);
                    rv_questionList.setItemAnimator(new DefaultItemAnimator());
                    rv_questionList.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();
                }
                Log.e("course List", "error occured");
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<AnswerModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("course List", "error occurred while call "+t.toString());
            }
        });

    }
}