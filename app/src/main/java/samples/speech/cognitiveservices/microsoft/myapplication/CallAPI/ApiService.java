package samples.speech.cognitiveservices.microsoft.myapplication.CallAPI;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import retrofit2.http.Query;

public interface ApiService {
    //link api : https://thanhhust.x10.mx/thanh/getdata2.php

    HttpLoggingInterceptor httpinterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(httpinterceptor);
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://thanhhust.x10.mx/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okBuilder.build())
            .build()
            .create(ApiService.class);
    @GET("thanh/getdata2.php")
    Observable<List<Value>> getListValue(@Query("table") String table);

    @GET("thanh/login2.php")
    Observable<ResponseBody> login(@Query("account") String account, @Query("password") String password);

    @GET("thanh/info.php")
    Observable<Information> getInfo(@Query("account") String account);

    @GET("thanh/signup2.php")
    Observable<ResponseBody> signup(@Query("account") String account, @Query("password") String password);

    @GET("thanh/detail_info.php")
    Observable<Detail_info> getDetailInfo(@Query("account") String account);
    @FormUrlEncoded
    @POST("thanh/answer_true.php")
    Observable<Void> postTrue(@Field("account") String account, @Field("answer") String answer);
    @FormUrlEncoded
    @POST("thanh/answer_false.php")
    Observable<Void> postFalse(@Field("account") String account, @Field("answer") String answer);
    @FormUrlEncoded
    @POST("/thanh/study.php")
    Observable<Void> postStudy(@Field("account") String account,@Field("answer") String answer);
    @GET("thanh/study_topic.php")
    Observable<List<Vocabulary>> getStudy_topic(@Query("account") String account,@Query("topic") String topic);
    @GET("thanh/topic.php")
    Observable<List<Topic>> getTopic(@Query("account") String account);
    @GET("thanh/definitions.php")
    Observable<Definition> getDefinition(@Query("word") String word);
    @GET("thanh/examples.php")
    Observable<Example> getExample(@Query("word") String word);
}
