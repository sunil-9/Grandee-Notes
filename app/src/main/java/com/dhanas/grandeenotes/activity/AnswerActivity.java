package com.dhanas.grandeenotes.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Adapter.AnswerAdapter;
import com.dhanas.grandeenotes.Model.AnswerModel.AnswerModel;
import com.dhanas.grandeenotes.Model.AnswerModel.Result;
import com.dhanas.grandeenotes.Model.SuccessModel.SuccessModel;
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
    private String q_id, question;
    private TextView tv_question, subbmit_answer, txt_back;
    ProgressDialog progressDialog;
    List<Result> answerList;
    AnswerAdapter answerAdapter;
    private RecyclerView rv_questionList;
    PrefManager prefManager;
    EditText et_answer;

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
        tv_question = findViewById(R.id.tv_question);
        et_answer = findViewById(R.id.et_answer);
        subbmit_answer = findViewById(R.id.subbmit_answer);
        subbmit_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_answer(et_answer.getText().toString().trim(), q_id);
            }
        });

        prefManager = new PrefManager(AnswerActivity.this);

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
        txt_back = findViewById(R.id.txt_back);
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
//        Toast.makeText(this, q_id, Toast.LENGTH_SHORT).show();

    }

    private void send_answer(String answer, String q_id) {
        if (!answer.isEmpty()) {
            //todo send data
            progressDialog.show();
            AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
            Call<SuccessModel> call = bookNPlayAPI.send_answer(answer,q_id,prefManager.getLoginId());
            call.enqueue(new Callback<SuccessModel>() {
                @Override
                public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                    if (response.body().getStatus() == 200) {
                        Toast.makeText(AnswerActivity.this, "submitted successfully " , Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(AnswerActivity.this, "Something went wrong please try again!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<SuccessModel> call, Throwable t) {
                    Log.e("TAG", "onFailure sending answer: "+t.toString() );
                    progressDialog.dismiss();

                }
            });

        } else {
            Toast.makeText(this, "nothing to send", Toast.LENGTH_SHORT).show();
        }
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
                Log.e("course List", "error occurred while call " + t.toString());
            }
        });

    }
}