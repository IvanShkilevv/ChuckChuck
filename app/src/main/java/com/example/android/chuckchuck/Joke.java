package com.example.android.chuckchuck;

import com.google.gson.annotations.SerializedName;

public class Joke {
    @SerializedName("joke")
    private String jokeText;

    public Joke(String jokeText) {
        this.jokeText = jokeText;
    }

    public String getJokeText() {
        return jokeText;
    }

    public void setJokeText(String jokeText) {
        this.jokeText = jokeText;
    }
}
