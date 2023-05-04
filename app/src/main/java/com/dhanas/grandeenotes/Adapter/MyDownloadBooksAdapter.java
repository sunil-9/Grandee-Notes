package com.dhanas.grandeenotes.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Activity.BookDetails;
import com.dhanas.grandeenotes.Activity.PDFShow;
import com.dhanas.grandeenotes.Model.downloads.DownloadModel;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.dhanas.grandeenotes.Utility.Constants.PDF_DIRECTORY;
import static com.squareup.picasso.Picasso.Priority.HIGH;

public class MyDownloadBooksAdapter extends RecyclerView.Adapter<MyDownloadBooksAdapter.MyViewHolder> {

    private final List<DownloadModel> downloadedData;
    Context mcontext;
    PrefManager prefManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_bookname;
        ImageView iv_thumb;

        public MyViewHolder(View view) {
            super(view);
            txt_bookname = (TextView) view.findViewById(R.id.txt_bookname);
            iv_thumb = (ImageView) view.findViewById(R.id.iv_thumb);
        }
    }


    public MyDownloadBooksAdapter(Context context, List<DownloadModel> downloadedData, SQLiteDatabase mDatabase) {
        this.downloadedData = downloadedData;
        this.mcontext = context;
        prefManager = new PrefManager(mcontext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mydownloadbook_item2, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(NewArrivalList!=null){
            String book_link = NewArrivalList.get(position).getBook_name();
            String fileName=getFileName(book_link);
            String image_link ="file://"+NewArrivalList.get(position).getImage();
            holder.txt_bookname.setText(fileName);

            Log.e("click ", "book  image location "+image_link);

            Picasso.with(mcontext).load(image_link)
                    .priority(HIGH).into(holder.iv_thumb);

            holder.iv_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("click", "book location "+book_link);

                    mcontext.startActivity(new Intent(mcontext, PDFShow.class)
                            .putExtra("link",  book_link)
                            .putExtra("toolbarTitle",  fileName)
                            .putExtra("type", "file"));
                }
            });
        }
        else {
            Toast.makeText(mcontext, "no download history", Toast.LENGTH_SHORT).show();
        }
    }
    private String getFileName(String url) {
        return url.substring(url.lastIndexOf('/'));
    }

    @Override
    public int getItemCount() {
        return downloadedData.size();
    }

}
