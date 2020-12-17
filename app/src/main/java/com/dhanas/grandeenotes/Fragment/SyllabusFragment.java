package com.dhanas.grandeenotes.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Adapter.AuthorBookAdapter;
import com.dhanas.grandeenotes.Model.BookModel.BookModel;
import com.dhanas.grandeenotes.Model.BookModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.dhanas.grandeenotes.Webservice.AppAPI;
import com.dhanas.grandeenotes.Webservice.BaseURL;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyllabusFragment extends Fragment {
    String semester_id;
    RecyclerView rv_booklist;

    PrefManager prefManager;
    ProgressDialog progressDialog;

    List<Result> BookList;
    AuthorBookAdapter bookAdapter;


    public SyllabusFragment(String semester_id) {
        this.semester_id = semester_id;
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            //switch_theme.setChecked(true);
            getActivity().setTheme(R.style.darktheme);
        } else {
            getActivity().setTheme(R.style.AppTheme);
        }
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_syllabus, container, false);
        prefManager = new PrefManager(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        rv_booklist = (RecyclerView) view.findViewById(R.id.rv_booklist);


        books_by_syllabus();


        return view;
    }

    private void books_by_syllabus() {
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<BookModel> call = bookNPlayAPI.books_by_syllabus(semester_id, prefManager.getLoginId());
        call.enqueue(new Callback<BookModel>() {
            @Override
            public void onResponse(Call<BookModel> call, Response<BookModel> response) {
                if (response.code() == 200) {
                    BookList = new ArrayList<>();
                    BookList = response.body().getResult();

                    bookAdapter = new AuthorBookAdapter(getActivity(), BookList);
                    rv_booklist.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
//                    rv_booklist.setLayoutManager(mLayoutManager3);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
                    rv_booklist.setLayoutManager(gridLayoutManager);
                    rv_booklist.setItemAnimator(new DefaultItemAnimator());
                    rv_booklist.setAdapter(bookAdapter);
                    bookAdapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BookModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}


