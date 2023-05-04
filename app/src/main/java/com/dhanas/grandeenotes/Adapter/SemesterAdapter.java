package com.dhanas.grandeenotes.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Activity.SemesterBookList;
import com.dhanas.grandeenotes.Model.SemesterModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import static com.squareup.picasso.Picasso.Priority.HIGH;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.MyViewHolder>{

    private final List<Result> SemesterList;
    Context mcontext;
    PrefManager prefManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_bookname ;
        RoundedImageView iv_thumb;

        public MyViewHolder(View view) {
            super(view);
            txt_bookname = (TextView) view.findViewById(R.id.txt_bookname);
            iv_thumb = (RoundedImageView) view.findViewById(R.id.iv_thumb);
        }
    }


    public SemesterAdapter(Context context, List<Result> SemesterList) {
        this.SemesterList = SemesterList;
        this.mcontext = context;
        prefManager = new PrefManager(mcontext);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.semester_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.e("onBind(semester): ",SemesterList.get(position).getSname());
        holder.txt_bookname.setText("" + SemesterList.get(position).getSname());
//        Toast.makeText(mcontext, "semester is :"+SemesterList.get(position).getSname(), Toast.LENGTH_SHORT).show();
//        holder.txt_tag.setText("" + SemesterList.get(position).getSname().charAt(0));

//todo: change colors of each image view

        if (position == 0)
            holder.iv_thumb.setBackgroundColor(mcontext.getResources().getColor(R.color.cat_1));
         else if (position == 1)
            holder.iv_thumb.setBackgroundColor(mcontext.getResources().getColor(R.color.cat_2));
         else if (position == 2)
            holder.iv_thumb.setBackgroundColor(mcontext.getResources().getColor(R.color.cat_3));
         else if (position == 3)
            holder.iv_thumb.setBackgroundColor(mcontext.getResources().getColor(R.color.cat_4));
         else if (position == 4)
            holder.iv_thumb.setBackgroundColor(mcontext.getResources().getColor(R.color.cat_5));
        else if (position == 5)
            holder.iv_thumb.setBackgroundColor(mcontext.getResources().getColor(R.color.cat_5));
        else if (position == 6)
            holder.iv_thumb.setBackgroundColor(mcontext.getResources().getColor(R.color.cat_5));
        else if (position == 7)
            holder.iv_thumb.setBackgroundColor(mcontext.getResources().getColor(R.color.cat_5));
        else
            holder.iv_thumb.setBackgroundColor(getRandomColor());
//        }
//        Picasso.with(mcontext).load(SemesterList.get(position).getAImage()).priority(HIGH).into(holder.iv_thumb);

        holder.iv_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("click", "call");
//                PrefManager prefManager = new PrefManager(mcontext);
//                prefManager.WallpaperList = new ArrayList<>();
//                prefManager.WallpaperList = LatestList;
//
//                Toast.makeText(mcontext, "clicked semester" +SemesterList.get(position).getSname(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext, SemesterBookList.class);
                intent.putExtra("s_id", SemesterList.get(position).getSid());
                intent.putExtra("s_name", SemesterList.get(position).getSname());

                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SemesterList.size();
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }}
