package com.dhanas.grandeenotes.Webservice;


import com.dhanas.grandeenotes.Model.AnswerModel.AnswerModel;
import com.dhanas.grandeenotes.Model.AuthorModel.AuthorModel;
import com.dhanas.grandeenotes.Model.BookModel.BookModel;
import com.dhanas.grandeenotes.Model.CategoryModel.CategoryModel;
import com.dhanas.grandeenotes.Model.CommentModel.CommentModel;
import com.dhanas.grandeenotes.Model.FreeBookModel.FreeBookModel;
import com.dhanas.grandeenotes.Model.GeneralSettings.GeneralSettings;
import com.dhanas.grandeenotes.Model.LoginRegister.LoginRegiModel;
import com.dhanas.grandeenotes.Model.NotificationsModel.NotificationModel;
import com.dhanas.grandeenotes.Model.ProfileModel.ProfileModel;
import com.dhanas.grandeenotes.Model.Question.QuestionModel;
import com.dhanas.grandeenotes.Model.ReadDowncntModel.ReadDowncntModel;
import com.dhanas.grandeenotes.Model.SemesterModel.SemesterModel;
import com.dhanas.grandeenotes.Model.SuccessModel.SuccessModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppAPI {


    @FormUrlEncoded
    @POST("login")
    Call<LoginRegiModel> login(@Field("email") String email_id,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("login_fb")
    Call<LoginRegiModel> login_fb(@Field("email") String email_id);

    @FormUrlEncoded
    @POST("registration")
    Call<LoginRegiModel> Registration(@Field("fullname") String full_name,
                                      @Field("email") String email_id,
                                      @Field("course_id") String course_id,
                                      @Field("password") String password,
                                      @Field("mobile_number") String phone);

    @FormUrlEncoded
    @POST("add_question")
    Call<SuccessModel> add_question(@Field("user_id") String user_id,
                                    @Field("question") String question);

    @FormUrlEncoded
    @POST("update_question")
    Call<SuccessModel> update_question(@Field("question") String question,
                                       @Field("q_id") String q_id);

    @FormUrlEncoded
    @POST("update_vote")
    Call<SuccessModel> update_vote(@Field("answer_id") String answer_id,
                                   @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("update_answer")
    Call<SuccessModel> update_answer(@Field("answer") String answer,
                                     @Field("a_id") String a_id);

    @FormUrlEncoded
    @POST("delete_question")
    Call<SuccessModel> delete_question(@Field("q_id") String q_id);

    @FormUrlEncoded
    @POST("delete_answer")
    Call<SuccessModel> delete_answer(@Field("a_id") String a_id);

    @FormUrlEncoded
    @POST("registration_fb")
    Call<LoginRegiModel> registration_fb(@Field("fullname") String full_name,
                                         @Field("email") String email_id);


    @FormUrlEncoded
    @POST("all_answer")
    Call<AnswerModel> all_answer(@Field("q_id") String q_id);


    @GET("general_setting")
    Call<GeneralSettings> general_settings();

    @GET("categorylist")
    Call<CategoryModel> categorylist();

    @GET("all_question")
    Call<QuestionModel> all_question();

    @FormUrlEncoded
    @POST("books_by_category")
    Call<BookModel> books_by_category(@Field("cat_id") String cat_id);



    @FormUrlEncoded
    @POST("popularbooklist")
    Call<BookModel> popularbooklist(@Field("u_id") String u_id);

    @FormUrlEncoded
    @POST("autherlist")
    Call<AuthorModel> autherlist(@Field("u_id") String u_id);

    @FormUrlEncoded
    @POST("newarriaval")
    Call<BookModel> newarriaval(@Field("u_id") String u_id);

    @FormUrlEncoded
    @POST("feature_item")
    Call<BookModel> feature_item(@Field("u_id") String u_id);

    @GET("semesterlist")
    Call<SemesterModel> semesterlist();

    @FormUrlEncoded
    @POST("books_by_author")
    Call<BookModel> books_by_author(@Field("a_id") String a_id);

    @FormUrlEncoded
    @POST("bookdetails")
    Call<BookModel> bookdetails(@Field("b_id") String b_id,
                                @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("books_by_course")
    Call<BookModel> books_by_course(@Field("s_id") String s_id,
                                    @Field("user_id") String user_id);


    @GET("booklist")
    Call<BookModel> booklist();


    @FormUrlEncoded
    @POST("books_by_oldquestion")
    Call<BookModel> books_by_oldquestion(@Field("s_id") String s_id,
                                         @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("bookSearch")
    Call<BookModel> bookSearch(@Field("query") String query);


    @FormUrlEncoded
    @POST("books_by_syllabus")
    Call<BookModel> books_by_syllabus(@Field("s_id") String s_id,
                                      @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("add_purchase")
    Call<SuccessModel> add_purchase(@Field("fb_id") String fb_id,
                                    @Field("user_id") String user_id,
                                    @Field("amount") String amount,
                                    @Field("currency_code") String currency_code,
                                    @Field("short_description") String short_description,
                                    @Field("payment_id") String payment_id,
                                    @Field("state") String state,
                                    @Field("create_time") String create_time);

    @FormUrlEncoded
    @POST("purchaselist")
    Call<BookModel> purchaselist(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("alldownload")
    Call<BookModel> alldownload(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("related_item")
    Call<BookModel> related_item(@Field("fcat_id") String fcat_id);

    @FormUrlEncoded
    @POST("add_download")
    Call<SuccessModel> add_download(@Field("user_id") String user_id,
                                    @Field("b_id") String b_id);

    @FormUrlEncoded
    @POST("profile")
    Call<ProfileModel> profile(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("view_add_by_user")
    Call<SuccessModel> view_add_by_user(@Field("id") String id);

    @FormUrlEncoded
    @POST("download_add_by_user")
    Call<SuccessModel> download_add_by_user(@Field("id") String id);

    @FormUrlEncoded
    @POST("add_continue_read")
    Call<SuccessModel> add_continue_read(@Field("user_id") String user_id,
                                         @Field("b_id") String b_id);

    @FormUrlEncoded
    @POST("continue_read")
    Call<BookModel> continue_read(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("add_comment")
    Call<SuccessModel> add_comment(@Field("b_id") String b_id,
                                   @Field("user_id") String user_id,
                                   @Field("comment") String comment);

    @FormUrlEncoded
    @POST("view_comment")
    Call<CommentModel> view_comment(@Field("b_id") String b_id);

    @FormUrlEncoded
    @POST("add_bookmark")
    Call<SuccessModel> add_bookmark(@Field("user_id") String user_id,
                                    @Field("b_id") String b_id);

    @FormUrlEncoded
    @POST("allBookmark")
    Call<BookModel> allBookmark(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("checkbookmark")
    Call<SuccessModel> checkbookmark(@Field("user_id") String user_id,
                                     @Field("b_id") String b_id);

    @FormUrlEncoded
    @POST("give_rating")
    Call<SuccessModel> give_rating(@Field("user_id") String user_id,
                                   @Field("book_id") String book_id,
                                   @Field("rating") String rating);

    @FormUrlEncoded
    @POST("readcnt_by_author")
    Call<ReadDowncntModel> readcnt_by_author(@Field("a_id") String a_id);

    @FormUrlEncoded
    @POST("free_paid_booklist")
    Call<FreeBookModel> free_paid_booklist(@Field("is_paid") String is_paid);

    @FormUrlEncoded
    @POST("update_profile")
    Call<SuccessModel> update_profile(@Field("user_id") String user_id,
                                      @Field("fullname") String fullname,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("mobile_number") String mobile_number,
                                      @Field("course") String course
    );

    @FormUrlEncoded
    @POST("send_answer")
    Call<SuccessModel> send_answer(@Field("answer") String answer,
                                   @Field("q_id") String q_id,
                                   @Field("loginId") String loginId);

    @FormUrlEncoded
    @POST("total_vote")
    Call<SuccessModel> total_vote(@Field("a_id") String a_id);

    @GET("notificationList")
    Call<NotificationModel> notificationList();
}
