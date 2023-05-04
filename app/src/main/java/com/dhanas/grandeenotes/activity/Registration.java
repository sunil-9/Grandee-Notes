package com.dhanas.grandeenotes.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dhanas.grandeenotes.Model.LoginRegister.LoginRegiModel;
import com.dhanas.grandeenotes.R;
import com.dhanas.grandeenotes.Utility.PrefManager;
import com.dhanas.grandeenotes.Webservice.AppAPI;
import com.dhanas.grandeenotes.Webservice.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.squareup.picasso.Picasso.Priority.HIGH;

public class Registration extends AppCompatActivity {

    String str_fullname, str_email, str_password, str_phone,course_id;
    ProgressDialog progressDialog;
    private PrefManager prefManager;
    TextView txt_registration, txt_signup;
    EditText et_fullname, et_email, et_password, et_phone;
    ImageView iv_icon;
    Spinner spinner_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registration);
        PrefManager.forceRTLIfSupported(getWindow(), Registration.this);

        Init();

        Picasso.with(Registration.this).load(BaseURL.Image_URL + "" + prefManager.getValue("app_logo"))
                .priority(HIGH).into(iv_icon);

        txt_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        Registration.this, LoginActivity.class));
            }
        });

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_fullname = et_fullname.getText().toString();
                str_email = et_email.getText().toString();
                str_password = et_password.getText().toString();
                str_phone = et_phone.getText().toString();

                if (TextUtils.isEmpty(str_fullname)) {
                    Toast.makeText(Registration.this, "Enter FullName", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(str_password) && str_password.length() < 6) {
                    Toast.makeText(Registration.this, "Enter Password at least 6 digits", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(str_phone)  && str_phone.length() < 8) {
                    Toast.makeText(Registration.this, "Phone Number Not Valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(str_email)) {
                    Toast.makeText(Registration.this, "Email not valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                SignUp();
            }
        });
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public void Init() {
        prefManager = new PrefManager(this);
        progressDialog = new ProgressDialog(Registration.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        txt_registration = (TextView) findViewById(R.id.txt_registration);
        et_fullname = (EditText) findViewById(R.id.et_fullname);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_phone = (EditText) findViewById(R.id.et_phone);
        txt_signup = (TextView) findViewById(R.id.txt_signup);
        spinner_course = findViewById(R.id.spinner_course);

        //for course
        List<String> list_course = new ArrayList<String>();
        list_course.add("BCA");
        list_course.add("BBA");
        list_course.add("BPH");


        ArrayAdapter<String> adapter_course = new ArrayAdapter<String>(Registration.this, android.R.layout.simple_spinner_item, list_course);
        adapter_course.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_course.setAdapter(adapter_course);

        spinner_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Log.e("onItemSelected: ", "onItemSelected: bca selected" );
//                        Toast.makeText(Registration.this,"Bca selected",Toast.LENGTH_SHORT).show();
                        course_id ="1";
                        break;
                    case 1:
                        course_id ="2";
                        break;
                    case 2:
                        course_id ="3";
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        iv_icon=findViewById(R.id.iv_icon);
    }

    public void SignUp() {
        progressDialog.show();
        AppAPI bookNPlayAPI = BaseURL.getVideoAPI();
        Call<LoginRegiModel> call = bookNPlayAPI.Registration(str_fullname, str_email,course_id, str_password, str_phone);
        call.enqueue(new Callback<LoginRegiModel>() {
            @Override
            public void onResponse(Call<LoginRegiModel> call, Response<LoginRegiModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    Toast.makeText(Registration.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus() == 200) {
                        prefManager.setLoginId("" + response.body().getUserid());
                        startActivity(new Intent(Registration.this, MainActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginRegiModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
