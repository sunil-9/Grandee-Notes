package com.dhanas.grandeenotes.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhanas.grandeenotes.Adapter.AllBooksAdapter;
import com.dhanas.grandeenotes.Model.BookModel.BookModel;
import com.dhanas.grandeenotes.Model.BookModel.Result;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.dhanas.grandeenotes.Webservice.AppAPI;
import com.dhanas.grandeenotes.Webservice.BaseURL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends Fragment {
    PrefManager prefManager;
    String user_id;
    ProgressDialog progressDialog;

    private EditText et_search;
    private ImageButton bt_clear;
    ScrollView scrollbar;
    List<Result> all_books;
    List<Result> searched_books;
    List<Result> recent_books;
    TextView clear_history;

    LinearLayout ly_all_books, ly_search_list, ly_recent_search;
    RecyclerView  rv_all_books, rv_search_list, rv_recent_search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.searchfragment, container, false);

        prefManager = new PrefManager(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        et_search = (EditText) root.findViewById(R.id.et_search);
        bt_clear = (ImageButton) root.findViewById(R.id.bt_clear);

        ly_search_list = root.findViewById(R.id.ly_search_list);
        ly_recent_search = root.findViewById(R.id.ly_recent_search);
        ly_all_books = root.findViewById(R.id.ly_all_books);

        rv_search_list = root.findViewById(R.id.rv_search_list);
        rv_recent_search = root.findViewById(R.id.rv_recent_search);
        rv_all_books = root.findViewById(R.id.rv_all_books);

        scrollbar = (ScrollView) root.findViewById(R.id.scrollbar);
        user_id = prefManager.getLoginId();

        clear_history =root.findViewById(R.id.clear_history);
        clear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
            }
        });

        searchOnOff(false);

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
                scrollbar.setVisibility(View.GONE);
            }
        });
        show_all_book_list();
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    saveSearchedData();
                    searchAction();
                    return true;
                }
                return false;
            }
        });



        return root;
    }
    private void displaySearchHistory() {
//        todo list all history data

    }

    private void clearHistory() {
//        todo delete all data form sqllite database using sugar orm
    }

    private void saveSearchedData() {
        //todo use sugar orm to store data in sqllite database.
    }

    private void searchAction() {
            scrollbar.setVisibility(View.GONE);

            final String query = et_search.getText().toString().trim();
            if (!query.equals("")) {
                searchOnOff(true);
                progressDialog.show();
                AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
                Call<BookModel> call = bookNPlayAPI.bookSearch(query);
                call.enqueue(new Callback<BookModel>() {
                    @Override
                    public void onResponse(Call<BookModel> call, Response<BookModel> response) {
                        if (response.code() == 200) {
                            all_books = new ArrayList<>();
                            all_books = response.body().getResult();
                            Log.e("TAG", "onResponse: all books:11 " + response.body().getResult());
                            AllBooksAdapter allBooksAdapter = new AllBooksAdapter( all_books,getActivity(), prefManager);
                            rv_search_list.setHasFixedSize(true);
                            RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity(),
                                    LinearLayoutManager.VERTICAL, false);
                            rv_search_list.setLayoutManager(mLayoutManager3);
                            rv_search_list.setItemAnimator(new DefaultItemAnimator());
                            rv_search_list.setAdapter(allBooksAdapter);
                            allBooksAdapter.notifyDataSetChanged();
                        }
                        else {
                        }
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<BookModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("TAG", "onResponse: all books: 2 failed to connect due to "+ t.toString());
                    }
                });
            }

            }

    private void show_all_book_list() {
        displaySearchHistory();
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<BookModel> call = bookNPlayAPI.booklist();
        call.enqueue(new Callback<BookModel>() {
            @Override
            public void onResponse(Call<BookModel> call, Response<BookModel> response) {
                if (response.code() == 200) {
                    all_books = new ArrayList<>();
                    all_books = response.body().getResult();
                    Log.e("TAG", "onResponse: all books:1 " + response.code());
                    AllBooksAdapter allBooksAdapter = new AllBooksAdapter( all_books,getActivity(), prefManager);
                    rv_all_books.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.VERTICAL, false);
                    rv_all_books.setLayoutManager(mLayoutManager3);
                    rv_all_books.setItemAnimator(new DefaultItemAnimator());
                    rv_all_books.setAdapter(allBooksAdapter);
                    allBooksAdapter.notifyDataSetChanged();
                }
                else {
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BookModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "onResponse: all books: 2 failed to connect due to "+ t.toString());
            }
        });
    }



    private void searchOnOff(boolean searching) {
        if (searching) {
            scrollbar.setVisibility(View.VISIBLE);
            ly_search_list.setVisibility(View.VISIBLE);
            ly_recent_search.setVisibility(View.GONE);
            ly_all_books.setVisibility(View.GONE);
        } else {
            scrollbar.setVisibility(View.VISIBLE);
            ly_search_list.setVisibility(View.GONE);
            ly_recent_search.setVisibility(View.VISIBLE);
            ly_all_books.setVisibility(View.VISIBLE);
        }

    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

