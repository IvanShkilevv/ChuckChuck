package com.example.android.chuckchuck;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JokesFragment extends Fragment {
    private static final String LOG_TAG = JokesFragment.class.getSimpleName();
    private Context context;
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JokesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static JokesFragment newInstance(String param1, String param2) {
        JokesFragment fragment = new JokesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_jokes);

//        ArrayList <String> tempJokesList = new ArrayList<>();
//        tempJokesList.add("First joke");
        int userInputJokesQuantity = 5;
        loadDataFromAPI(userInputJokesQuantity);


        return view;
    }

    private void loadDataFromAPI(int userInputJokesQuantity) {
        ChuckJokesAPI chuckJokesAPI = RetrofitClient.getRetrofitInstance().create(ChuckJokesAPI.class);
        Call<List<Joke>> call = chuckJokesAPI.loadJokes(userInputJokesQuantity);
        call.enqueue(new Callback<List<Joke>>() {
            @Override
            public void onResponse(Call<List<Joke>> call, Response<List<Joke>> response) {
                List <Joke> jokesList = response.body();
                //updating UI
                updateRecyclerView(jokesList);
            }

            @Override
            public void onFailure(Call<List<Joke>> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(context, "network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "conversion issue", Toast.LENGTH_SHORT).show();
                    String stackTrace = Log.getStackTraceString(t);
                    Log.e(LOG_TAG, stackTrace);
                }
//                Toast.makeText(context, R.string.server_respond_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateRecyclerView (List <Joke> jokesList) {
        ListAdapter adapter = new ListAdapter(jokesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

}
