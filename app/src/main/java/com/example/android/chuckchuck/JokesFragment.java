package com.example.android.chuckchuck;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.chuckchuck.retrofit_pojo.ApiRespondSchema;
import com.example.android.chuckchuck.retrofit_pojo.Value;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class JokesFragment extends Fragment {
    private final int INPUT_JOKES_LIMIT = 1000000;
    private static final String LOG_TAG = JokesFragment.class.getSimpleName();
    private Context context;
    private RecyclerView recyclerView;
    private EditText countEditText;
    private Button reloadButton;
    private List <Joke> jokesList = new ArrayList<>();
    private List<Value> valuesList;

    public JokesFragment() {
        // Required empty public constructor
    }

    public static JokesFragment newInstance(String param1, String param2) {
        JokesFragment fragment = new JokesFragment();
        Bundle args = new Bundle();
//      input Bundle args here if needed
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);
        countEditText = view.findViewById(R.id.count_edit_text_view);
        reloadButton = view.findViewById(R.id.reload_button);
        recyclerView = view.findViewById(R.id.recycler_view_jokes);

        reloadButton.setOnClickListener(v -> {
            if (!checkNetwork()) {return;}

            String userInput = countEditText.getText().toString();
            if (!checkEmptyInput(userInput)) {return;}

            int inputJokesQuantity =  Integer.parseInt(userInput);
            if (!checkJokesLimit(inputJokesQuantity)) {return;}

            loadDataFromAPI(inputJokesQuantity);
        });

        return view;
    }

    private void loadDataFromAPI(int userInputJokesQuantity) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        RequestService requestService = retrofit.create(RequestService.class);
        Call<ApiRespondSchema> call = requestService.loadJokes(userInputJokesQuantity);

        call.enqueue(new Callback<ApiRespondSchema>() {
            @Override
            public void onResponse(@NonNull Call<ApiRespondSchema> call, @NonNull Response<ApiRespondSchema> response) {
                try {
                    if (response.body() != null) {
                        valuesList = response.body().getValue();
                    }
                    jokesList.clear();

                    for (Value value : valuesList) {
                        String rawJokeText = value.getJoke();
                        String jokeText = StringEscapeUtils.unescapeHtml4(rawJokeText);
                        jokesList.add(new Joke(jokeText));
                    }
                } catch (NullPointerException exception) {
                    String stackTrace = Log.getStackTraceString(exception);
                    Log.e(LOG_TAG, stackTrace);
                }
                updateRecyclerView(jokesList);
            }

            @Override
            public void onFailure(@NonNull Call<ApiRespondSchema> call,@NonNull Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(context, R.string.server_respond_error, Toast.LENGTH_SHORT).show();
                }
                else {
                    String stackTrace = Log.getStackTraceString(t);
                    Log.e(LOG_TAG + R.string.conversion_issue, stackTrace);
                }
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

    private boolean checkNetwork() {
        NetworkConnection network = new NetworkConnection(context);
        if (! network.checkNetworkConnection()) {
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkEmptyInput(String userInput) {
        if (TextUtils.isEmpty(userInput)) {
            Toast.makeText(context, R.string.empty_user_input, Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkJokesLimit(int inputJokesQuantity) {
        if (inputJokesQuantity > INPUT_JOKES_LIMIT) {
            Toast.makeText(context, R.string.above_jokes_limit, Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

}
