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

public class SpellAdapter extends ArrayAdapter<Spell> {
    public SpellAdapter(@NonNull Context context, ArrayList<Spell> spells) {
        super(context, 0, spells);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View spellView = convertView;
        if (spellView == null){
            spellView = LayoutInflater.from(getContext()).inflate(R.layout.spell_list_item, parent, false);
        }
        Spell spell = getItem(position);
        TextView spellNameTextView = spellView.findViewById(R.id.spell_name);
        spellNameTextView.setText(spell.getSpellName());

        TextView spellLevelTextView = spellView.findViewById(R.id.spell_level);
        spellLevelTextView.setText(String.valueOf(spell.getLevel()));

        return spellView;
    }
}
