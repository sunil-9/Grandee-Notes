package com.dhanas.grandeenotes.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Activity.AnswerActivity;
import com.dhanas.grandeenotes.Model.Question.Result;
import com.dhanas.grandeenotes.Model.SuccessModel.SuccessModel;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.dhanas.grandeenotes.Webservice.AppAPI;
import com.dhanas.grandeenotes.Webservice.BaseURL;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dhanas.grandeenotes.Utility.Constants.please_wait;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder> {

    private List<Result> QuestionList;
    Activity mcontext;
    PrefManager prefManager;
    ProgressDialog progressDialog;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Question, name;
        ImageView status_pic;

        public MyViewHolder(View view) {
            super(view);
            Question = (TextView) view.findViewById(R.id.Question);
            name = (TextView) view.findViewById(R.id.name);
            status_pic = view.findViewById(R.id.status_pic);
        }
    }

    public QuestionListAdapter(Activity context, List<Result> QuestionList) {
        this.QuestionList = QuestionList;
        this.mcontext = context;
        prefManager = new PrefManager(mcontext);
    }

    @Override
    public QuestionListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_question, parent, false);

        return new QuestionListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final QuestionListAdapter.MyViewHolder holder, final int position) {
        Log.e("onBind(QuestionList): ", QuestionList.get(position).getQ_id());
        String question_asked_each_position = QuestionList.get(position).getQuestion();

        String user_id =QuestionList.get(position).getId();
        if( user_id.equals(prefManager.getLoginId())){
            holder.name.setText("You ");
        }
        else {
            holder.name.setText(QuestionList.get(position).getFullname());
        }

        holder.Question.setText("" + question_asked_each_position);
        String uid = prefManager.getLoginId();
        String fu_id = QuestionList.get(position).getUser_id();
        if (uid.equals(fu_id)) {
            holder.status_pic.setImageResource(R.drawable.ic_edit);
            holder.status_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AlertDialog.Builder myDialog1 = new AlertDialog.Builder(mcontext);
                        LayoutInflater inflater1 = LayoutInflater.from(mcontext);
                        View myView1 = inflater1.inflate(R.layout.update_question, null);
                        myDialog1.setView(myView1);
                        final AlertDialog dialog1 = myDialog1.create();
                        final EditText asked_question = myView1.findViewById(R.id.et_question);
                        asked_question.setText(question_asked_each_position);
                        Button et_question_update = myView1.findViewById(R.id.update_question);
                        Button et_question_delete = myView1.findViewById(R.id.delete_question);
                        dialog1.show();
                        et_question_update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                                updateQuestion(asked_question.getText().toString().trim(), QuestionList.get(position).getQ_id());
                            }
                        });
                        et_question_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                                DeleteQuestion(QuestionList.get(position).getQ_id());
                            }
                        });


                    } catch (Exception e) {
                        Log.e("TAG", "onClick: what is the problem " + e.toString());
                    }
                }
            });
        }

        holder.Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("click", "call");

                Toast.makeText(mcontext, "clicked this question " + QuestionList.get(position).getQuestion(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mcontext, AnswerActivity.class);
                intent.putExtra("q_id", QuestionList.get(position).getQ_id());
                intent.putExtra("question", QuestionList.get(position).getQuestion());

                mcontext.startActivity(intent);
            }
        });
    }

    private void DeleteQuestion(String q_id) {
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setTitle(please_wait);
        progressDialog.show();
//        todo delete question
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<SuccessModel> call = bookNPlayAPI.delete_question(q_id);
        Log.e("TAG", "DeleteQuestion: deleting" + q_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                if (response.code() == 200) {
                    Toast.makeText(mcontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(mcontext, "" + t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void updateQuestion(String Questions, String q_id) {
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setTitle(please_wait);
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<SuccessModel> call = bookNPlayAPI.update_question(Questions, q_id);
        Log.e("TAG", "updateQuestion: " + q_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                if (response.code() == 200) {
                    Toast.makeText(mcontext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(mcontext, "" + t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return QuestionList.size();
    }

}
