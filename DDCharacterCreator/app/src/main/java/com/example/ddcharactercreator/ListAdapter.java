package com.example.ddcharactercreator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.StringViewHolder> {

    private Context context;
    private ArrayList<String> strings;

    public ListAdapter(@NonNull Context context, ArrayList<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StringViewHolder(LayoutInflater.from(context).inflate(R.layout.string_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
        String item = strings.get(position);
        holder.listItemTextView.setText(item);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class StringViewHolder extends RecyclerView.ViewHolder{
        private TextView listItemTextView;
        private View parentView;

        public StringViewHolder(@NonNull View view){
            super(view);
            this.parentView = view;
            this.listItemTextView = view.findViewById(R.id.string_list_item);
        }
    }
}
