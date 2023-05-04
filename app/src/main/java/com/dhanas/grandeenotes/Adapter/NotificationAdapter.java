package com.dhanas.grandeenotes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Model.NotificationsModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.squareup.picasso.Picasso.Priority.HIGH;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>  {

    private final List<Result> notificationList;
    Context mcontext;
    PrefManager prefManager;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_noti_desc,txt_noti_title;
        public ImageView iv_noti_image;

        public MyViewHolder(View view) {
            super(view);
            txt_noti_desc = (TextView) view.findViewById(R.id.txt_noti_desc);
            txt_noti_title = (TextView) view.findViewById(R.id.txt_noti_title);
            iv_noti_image =  view.findViewById(R.id.iv_noti_image);
        }
    }


    public NotificationAdapter(Context context, List<Result> notificationList) {
        this.notificationList = notificationList;
        this.mcontext = context;
        prefManager = new PrefManager(mcontext);
    }

    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);

        return new NotificationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.MyViewHolder holder, final int position) {
        holder.txt_noti_title.setText("" + notificationList.get(position).getTitle());
        holder.txt_noti_desc.setText("" + notificationList.get(position).getDescription());
        if(notificationList.get(position).getImage_url()!= null){
            Picasso.with(mcontext).load(notificationList.get(position).getImage_url()).priority(HIGH).into(holder.iv_noti_image);

        }
    }
    @Override
    public int getItemCount() {
        return notificationList.size();
    }

}
