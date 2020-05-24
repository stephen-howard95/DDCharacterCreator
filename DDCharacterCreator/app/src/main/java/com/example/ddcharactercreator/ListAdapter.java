package com.example.ddcharactercreator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String> {
    public ListAdapter(@NonNull Context context, ArrayList<String> strings) {
        super(context, 0, strings);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.string_list_item_layout, parent, false);
        }
        String item = getItem(position);
        TextView listItemTextView = view.findViewById(R.id.string_list_item);
        listItemTextView.setText(item);
        return view;
    }
}
