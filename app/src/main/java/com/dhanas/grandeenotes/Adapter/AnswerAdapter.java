package com.dhanas.grandeenotes.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.dhanas.grandeenotes.Model.AnswerModel.Result;
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

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder>{
    private List<Result>  answerList;
    Context mcontext;
    String total_vote_count;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView answer,thumb_count;
        ImageView iv_thumb;

        public MyViewHolder(View view) {
            super(view);
            answer= (TextView) view.findViewById(R.id.answer);
            thumb_count= (TextView) view.findViewById(R.id.tv_thumb_count);
            iv_thumb= (ImageView) view.findViewById(R.id.iv_thumb);
        }
    }

    public AnswerAdapter(Context context, List<Result> answerList) {
        this.answerList = answerList;
        this.mcontext = context;
        prefManager = new PrefManager(mcontext);
    }
    @Override
    public AnswerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_answer, parent, false);

        return new AnswerAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final AnswerAdapter.MyViewHolder holder, final int position) {
        Log.e("onBind(QuestionList): ", answerList.get(position).getQ_id());

        holder.answer.setText("" + answerList.get(position).getAnswer());
        String answer_id =answerList.get(position).getA_id();

//        getVoteCount(answer_id);
//        Log.e("onBind(QuestionList): ","total vote for each answer"+total_vote_count);
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<SuccessModel> call = bookNPlayAPI.total_vote( answer_id);
        Log.e("TAG", "updateQuestion: " +answer_id);
        call.enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {

                Log.e("TAG", "onResponse: "+response.body().getMessage());
                holder.thumb_count.setText(response.body().getMessage());
            }
            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Log.e("TAG", "onFailure: vote update model "+t.toString() );
                Toast.makeText(mcontext, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.thumb_count.setText(total_vote_count);

//        holder.thumb_count.setText(String.valueOf(total_vote));

        if(answerList.get(position).getUser_id().equals(prefManager.getLoginId())){
            holder.iv_thumb.setImageResource(R.drawable.ic_edit);

            holder.iv_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        AlertDialog.Builder myDialog1 = new AlertDialog.Builder(mcontext);
                        LayoutInflater inflater1 = LayoutInflater.from(mcontext);
                        View myView1 = inflater1.inflate(R.layout.update_answer, null);
                        myDialog1.setView(myView1);
                        final AlertDialog dialog1 = myDialog1.create();
                        final EditText asked_question = myView1.findViewById(R.id.et_question);
                        asked_question.setText(answerList.get(position).getAnswer());
                        Button et_question_update = myView1.findViewById(R.id.update_question);
                        Button et_question_delete = myView1.findViewById(R.id.delete_question);
                        dialog1.show();

                        et_question_update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String answer =asked_question.getText().toString().trim();
                                if(!answer.isEmpty()){
                                    dialog1.dismiss();
                                    updateQuestion( answer, answer_id);

                                }
                                else {
                                    DeleteQuestion( answer_id);
                                    dialog1.dismiss();
                                }
                                }
                        });
                        et_question_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                                DeleteQuestion( answerList.get(position).getA_id());
                            }
                        });


                    } catch (Exception e) {
                        Log.e("TAG", "onClick: what is the problem " + e.toString());
                    }

                }
            });
        }
        else {
            holder.iv_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //            todo update vote
                    progressDialog = new ProgressDialog(mcontext);
                    progressDialog.setTitle(please_wait);
                    progressDialog.show();
                    AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
                    Call<SuccessModel> call = bookNPlayAPI.update_vote(answer_id,prefManager.getLoginId());
                    Log.e("TAG", "update vote: for " +answer_id+""+prefManager.getLoginId());
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
            });

        }
    }

    private void updateQuestion(String answer, String a_id) {
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setTitle(please_wait);
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<SuccessModel> call = bookNPlayAPI.update_answer( answer, a_id);
        Log.e("TAG", "updateQuestion: " +a_id);
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

    private void DeleteQuestion(String a_id) {
//        todo delete answer
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setTitle(please_wait);
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<SuccessModel> call = bookNPlayAPI.delete_answer(a_id);
        Log.e("TAG", "DeleteQuestion: deleting"+a_id );
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
        return answerList.size();
    }
}
