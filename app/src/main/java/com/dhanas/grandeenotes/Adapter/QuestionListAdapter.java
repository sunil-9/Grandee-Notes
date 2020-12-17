package com.dhanas.grandeenotes.Adapter;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.dhanas.grandeenotes.Fragment.ForumFragment;
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

public class QuestionListAdapter  extends RecyclerView.Adapter<QuestionListAdapter.MyViewHolder>{

    private List<Result> QuestionList;
    Context mcontext;
    PrefManager prefManager;
    ProgressDialog progressDialog;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Question;
        ImageView status_pic;
        public MyViewHolder(View view) {
            super(view);
            Question= (TextView) view.findViewById(R.id.Question);
            status_pic=  view.findViewById(R.id.status_pic);

        }
    }

    public QuestionListAdapter(Context context, List<Result> QuestionList) {
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
        Log.e("onBind(QuestionList): ",QuestionList.get(position).getQ_id());
        String question_asked_each_position = QuestionList.get(position).getQuestion();

        holder.Question.setText("" + question_asked_each_position);
       String uid =prefManager.getLoginId();
       String fu_id =QuestionList.get(position).getUser_id();
       if (uid.equals(fu_id)){
           holder.status_pic.setImageResource(R.drawable.ic_user1);
           holder.status_pic.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(mcontext, "able to edit", Toast.LENGTH_SHORT).show();
                   AlertDialog.Builder myDialog = new AlertDialog.Builder(mcontext);
                   LayoutInflater inflater = LayoutInflater.from(mcontext);
                   View myView = inflater.inflate(R.layout.ask_question, null);
                   myDialog.setView(myView);
                   final AlertDialog dialog = myDialog.create();
                   final EditText asked_question = myView.findViewById(R.id.et_question);
                   asked_question.setText(question_asked_each_position);
                   Button save_question = myView.findViewById(R.id.save_question);
                   dialog.show();
                   Toast.makeText(mcontext, "edit this" + QuestionList.get(position).getQuestion(), Toast.LENGTH_SHORT).show();
                   prefManager =new PrefManager(mcontext);
                   String  user_id = prefManager.getLoginId();
                   updateQuestion(user_id,question_asked_each_position,QuestionList.get(position).getQ_id());
                   dialog.dismiss();
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
    public void updateQuestion(String user_id,String Questions,String q_id) {
        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setTitle(please_wait);
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<SuccessModel> call = bookNPlayAPI.update_question(user_id,Questions,q_id);
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
