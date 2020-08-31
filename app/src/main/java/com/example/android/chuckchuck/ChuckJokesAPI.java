package com.example.android.chuckchuck;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ChuckJokesAPI {

    @GET("jokes/random/{jokesQuantity}")
    Call<List<Joke>> loadJokes(@Path("jokesQuantity") int jokesQuantity);
}
