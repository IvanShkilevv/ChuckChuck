package com.example.android.chuckchuck;

import com.example.android.chuckchuck.retrofit_pojo.ApiRespondSchema;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RequestService {

    @GET("jokes/random/{jokesQuantity}")
    Call<ApiRespondSchema> loadJokes(@Path("jokesQuantity") int jokesQuantity);
}
