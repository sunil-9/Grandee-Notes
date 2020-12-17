package com.dhanas.grandeenotes.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Model.AnswerModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder>{
    private List<Result>  answerList;
    Context mcontext;
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
        if(answerList.get(position).getVote()!=null)
            holder.thumb_count.setText("" + answerList.get(position).getVote());
        else
            holder.thumb_count.setText("0");
    }
    @Override
    public int getItemCount() {
        return answerList.size();
    }


}
