package com.dhanas.grandeenotes.Fragment;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dhanas.grandeenotes.Adapter.QuestionListAdapter;

import com.dhanas.grandeenotes.Model.Question.QuestionModel;
import com.dhanas.grandeenotes.Model.Question.Result;
import com.dhanas.grandeenotes.Model.SuccessModel.SuccessModel;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.dhanas.grandeenotes.Webservice.AppAPI;
import com.dhanas.grandeenotes.Webservice.BaseURL;
import android.app.ProgressDialog;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dhanas.grandeenotes.Utility.Constants.please_wait;

public class ForumFragment extends Fragment {
    View view;
    private FloatingActionButton create;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    private RecyclerView rv_questionList;
    QuestionListAdapter questionListAdapter;
    List<Result> questionList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            //switch_theme.setChecked(true);
            getActivity().setTheme(R.style.darktheme);
        } else {
            getActivity().setTheme(R.style.AppTheme);
        }
        view= inflater.inflate(R.layout.fragment_forum, container, false);
        create = view.findViewById(R.id.float_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View myView = inflater.inflate(R.layout.ask_question, null);
                myDialog.setView(myView);
                final AlertDialog dialog = myDialog.create();
                final EditText asked_question = myView.findViewById(R.id.et_question);
                Button save_question = myView.findViewById(R.id.save_question);
                dialog.show();
                save_question.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String question = asked_question.getText().toString().trim();
                        if(question.equals("")){
                            Toast.makeText(getActivity(), "Please enter your question first", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "you asked "+question, Toast.LENGTH_SHORT).show();

                            prefManager =new PrefManager(getActivity());
                            String  user_id = prefManager.getLoginId();
                            addQuestion(user_id,question);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        //TODO: add adaptor for recycle view
        prefManager =new PrefManager(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        rv_questionList = (RecyclerView) view.findViewById(R.id.recycleView);

        list_question();

        return view;
    }

    private void list_question() {
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<QuestionModel> call = bookNPlayAPI.all_question();
        call.enqueue(new Callback<QuestionModel>() {
            @Override
            public void onResponse(Call<QuestionModel> call, Response<QuestionModel> response) {
                if (response.code() == 200) {
                    questionList = new ArrayList<>();
                    questionList = response.body().getResult();
                    questionListAdapter = new QuestionListAdapter(getActivity(), questionList);
                    rv_questionList.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
//                    rv_booklist.setLayoutManager(mLayoutManager3);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                    rv_questionList.setLayoutManager(gridLayoutManager);
                    rv_questionList.setItemAnimator(new DefaultItemAnimator());
                    rv_questionList.setAdapter(questionListAdapter);
                    questionListAdapter.notifyDataSetChanged();
                }
                Log.e("course List", "error occured");

                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<QuestionModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("course List", "error occurred while call "+t.toString());
            }
        });
    }

    public void addQuestion(String user_id,String Questions) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(please_wait);
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<SuccessModel> call = bookNPlayAPI.add_question(user_id,Questions);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    if (Build.VERSION.SDK_INT >= 26) {
                        ft.setReorderingAllowed(false);
                    }
                    ft.detach(new ForumFragment()).attach(new ForumFragment()).commit();
                }
            }
            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}