package com.dhanas.grandeenotes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Activity.BookDetails;
import com.dhanas.grandeenotes.Model.BookModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.squareup.picasso.Picasso;
import java.util.List;
import static com.squareup.picasso.Picasso.Priority.HIGH;

public class AllBooksAdapter extends RecyclerView.Adapter<AllBooksAdapter.MyViewHolder>{

    private List<Result> NewArrivalList;
    Context mcontext;
    PrefManager prefManager;
    SQLiteDatabase mDatabase;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_bookname, txt_view;
        ImageView iv_thumb;

        public MyViewHolder(View view) {
            super(view);
            txt_bookname = (TextView) view.findViewById(R.id.txt_bookname);
            iv_thumb = (ImageView) view.findViewById(R.id.iv_thumb);
            txt_view = (TextView) view.findViewById(R.id.txt_view);
        }
    }

    public AllBooksAdapter(List<Result> newArrivalList, Context mcontext, PrefManager prefManager) {
        NewArrivalList = newArrivalList;
        this.mcontext = mcontext;
        this.prefManager = prefManager;
    }


    @Override
    public AllBooksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vertical_book_item, parent, false);

        return new AllBooksAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AllBooksAdapter.MyViewHolder holder, final int position) {

//        holder.txt_view.setText("" + NewArrivalList.get(position).getReadcnt());
        holder.txt_bookname.setText("" + NewArrivalList.get(position).getBTitle());
        Picasso.with(mcontext).load(NewArrivalList.get(position).getBImage()).priority(HIGH).into(holder.iv_thumb);

        holder.iv_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("click", "call");
                Intent intent = new Intent(mcontext, BookDetails.class);
                intent.putExtra("ID", NewArrivalList.get(position).getBId());
                mcontext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return NewArrivalList.size();
    }

}
