package com.example.ddcharactercreator;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.StringViewHolder> {

    private Context context;
    private ArrayList<String> strings;
    private Boolean canDelete;

    public ListAdapter(@NonNull Context context, ArrayList<String> strings, Boolean canDelete) {
        this.context = context;
        this.strings = strings;
        this.canDelete = canDelete;
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
        if(canDelete){
            holder.parentView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete " + item);
                    final int positionToRemove = position;
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            strings.remove(positionToRemove);
                            notifyDataSetChanged();
                        }
                    });
                    adb.show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.strings.size();
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
