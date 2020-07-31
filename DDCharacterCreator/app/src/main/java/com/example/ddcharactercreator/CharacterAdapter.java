package com.example.ddcharactercreator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private static CharacterDatabase mDb;
    private Context context;
    private List<Character> characters = new ArrayList<>();

    public CharacterAdapter(@NonNull Context context, List<Character> characters) {
        this.context = context;
        this.characters = characters;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.character_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        final Character character = characters.get(position);
        holder.nameTextView.setText(character.getName());
        holder.levelTextView.setText(String.valueOf(character.getLevel()));
        holder.raceTextView.setText(character.getRace());
        holder.classTextView.setText(character.getCharacterClass());
        holder.deleteCharacterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDb = CharacterDatabase.getInstance(context);
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + character.getName() + "? This will be permanent.");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Character thisCharacter = characters.get(position);
                        mDb.characterDao().deleteCharacter(thisCharacter);
                    }
                });
                adb.show();
            }
        });
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.CHARACTER, character);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(characters != null){
            return characters.size();
        }else{
            return 0;
        }
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
        notifyDataSetChanged();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTextView;
        private TextView levelTextView;
        private TextView raceTextView;
        private TextView classTextView;
        private ImageView deleteCharacterImageView;
        private View parentView;

        public CharacterViewHolder(@NonNull View view){
            super(view);
            this.parentView = view;
            this.nameTextView = view.findViewById(R.id.character_name);
            this.levelTextView = view.findViewById(R.id.character_level);
            this.raceTextView = view.findViewById(R.id.character_race);
            this.classTextView = view.findViewById(R.id.character_class);
            this.deleteCharacterImageView = view.findViewById(R.id.delete_character_button);
        }
    }
}
