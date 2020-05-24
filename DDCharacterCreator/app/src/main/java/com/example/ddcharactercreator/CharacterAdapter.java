package com.example.ddcharactercreator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends ArrayAdapter<Character> {

    private static CharacterDatabase mDb;

    public CharacterAdapter(@NonNull Context context, ArrayList<Character> characters) {
        super(context, 0, characters);
    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.character_list_item, parent, false);
        }
        final Character character = getItem(position);
        TextView nameTextView = view.findViewById(R.id.character_name);
        nameTextView.setText(character.getName());
        TextView levelTextView = view.findViewById(R.id.character_level);
        levelTextView.setText(String.valueOf(character.getLevel()));
        TextView raceTextView = view.findViewById(R.id.character_race);
        raceTextView.setText(character.getRace());
        TextView classTextView = view.findViewById(R.id.character_class);
        classTextView.setText(character.getCharacterClass());
        ImageView deleteCharacterImageView = view.findViewById(R.id.delete_character_button);
        deleteCharacterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDb = CharacterDatabase.getInstance(getContext());
                AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + character.getName() + "? This will be permanent.");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Character thisCharacter = getItem(position);
                        mDb.characterDao().deleteCharacter(thisCharacter);
                    }
                });
                adb.show();
            }
        });
        return view;
    }

    public void setCharacters(List<Character> characters) {
        notifyDataSetChanged();
    }
}
