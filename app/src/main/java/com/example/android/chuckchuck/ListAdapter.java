package com.example.android.chuckchuck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private List <Joke> jokesList;

    public ListAdapter(List <Joke> jokesList) {
        this.jokesList = jokesList;
    }

    // inflating the item layout and creating the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View singleItemLayout  = inflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(singleItemLayout);
    }

    // Setting the view attributes based on the data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Displaying list item to it's correct position
        String joke = jokesList.get(position).getJokeText();
        TextView textView = holder.jokeText;
        textView.setText(joke);
    }

    @Override
    public int getItemCount() {
        return jokesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        // Defining item's views
        TextView jokeText;

        ViewHolder(View itemView) {
            super(itemView);
            jokeText =  itemView.findViewById(R.id.text_view_list_item);
        }
    }

}
