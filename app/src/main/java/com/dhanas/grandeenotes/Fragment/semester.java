package com.dhanas.grandeenotes.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Adapter.AuthorAdapter;
import com.dhanas.grandeenotes.Model.AuthorModel.AuthorModel;
import com.dhanas.grandeenotes.Model.AuthorModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.dhanas.grandeenotes.Webservice.AppAPI;
import com.dhanas.grandeenotes.Webservice.BaseURL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class semester extends Fragment {

    PrefManager prefManager;
    ProgressDialog progressDialog;
    List<Result> AuthorList;
    RecyclerView rv_author;
    AuthorAdapter authorAdapter;
    LinearLayout ly_no_data;
    TextView txt_no_record;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.authorfragment, container, false);

        rv_author = (RecyclerView) root.findViewById(R.id.rv_author);

        prefManager = new PrefManager(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        ly_no_data = (LinearLayout) root.findViewById(R.id.ly_no_data);
        txt_no_record = (TextView) root.findViewById(R.id.txt_no_record);

        SemesterList();

        return root;
    }

    private void SemesterList() {
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<AuthorModel> call = bookNPlayAPI.autherlist(prefManager.getLoginId());
        call.enqueue(new Callback<AuthorModel>() {
            @Override
            public void onResponse(Call<AuthorModel> call, Response<AuthorModel> response) {
                if (response.code() == 200) {

                    AuthorList = new ArrayList<>();
                    AuthorList = response.body().getResult();
                    Log.e("AuthorList", "" + AuthorList.size());

                    if (AuthorList.size() > 0) {
                        authorAdapter = new AuthorAdapter(getActivity(), AuthorList);
                        rv_author.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity(),
                                LinearLayoutManager.HORIZONTAL, false);
//                    rv_author.setLayoutManager(mLayoutManager3);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                                3, LinearLayoutManager.VERTICAL, false);
                        rv_author.setLayoutManager(gridLayoutManager);
                        rv_author.setItemAnimator(new DefaultItemAnimator());
                        rv_author.setAdapter(authorAdapter);
                        authorAdapter.notifyDataSetChanged();
                    } else {
                        txt_no_record.setText("" + Html.fromHtml(response.body().getMessage()));
                        ly_no_data.setVisibility(View.VISIBLE);
                        rv_author.setVisibility(View.GONE);
                    }
                } else {
                    txt_no_record.setText("" + Html.fromHtml(response.body().getMessage()));
                    ly_no_data.setVisibility(View.VISIBLE);
                    rv_author.setVisibility(View.GONE);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AuthorModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
