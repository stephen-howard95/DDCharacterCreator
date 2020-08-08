package com.example.ddcharactercreator;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.widget.Toast;

public class SpellAdapter extends RecyclerView.Adapter<SpellAdapter.SpellViewHolder> {

    private Context context;
    private ArrayList<Spell> spells;
    private Boolean isDatabaseSpell;

    public SpellAdapter(@NonNull Context context, ArrayList<Spell> spells, Boolean isDatabaseSpell) {
        this.context = context;
        this.spells = spells;
        this.isDatabaseSpell = isDatabaseSpell;
    }

    @NonNull
    @Override
    public SpellAdapter.SpellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpellViewHolder(LayoutInflater.from(context).inflate(R.layout.spell_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SpellViewHolder holder, int position) {
        Spell spell = spells.get(position);
        holder.spellNameTextView.setText(spell.getSpellName());
        if(spell.getLevel() > 0){
            holder.spellLevelTextView.setText(String.valueOf(spell.getLevel()));
        } else{
            holder.spellLevelTextView.setVisibility(View.GONE);
        }
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpellDetailActivity.class);
                intent.putExtra(SpellDetailActivity.SPELL, spell);
                context.startActivity(intent);
            }
        });
        if(isDatabaseSpell){
            holder.parentView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Character character = DetailActivity.character;
                    //prevents duplicate spells from being added to the list and warns the user
                    int i=0;
                    boolean spellIsUnique = true;
                    do{
                        if(character.getSpellsKnown().size() == 0){
                            break;
                        }
                        Spell knownSpell = character.getSpellsKnown().get(i);
                        if(spell.getSpellName().equals(knownSpell.getSpellName())){
                            Toast.makeText(context, "You already know this spell", Toast.LENGTH_SHORT).show();
                            spellIsUnique = false;
                            break;
                        }
                        i++;
                    } while(i<character.getSpellsKnown().size());
                    if(spellIsUnique){
                        character.getSpellsKnown().add(spell);
                        ((Activity) context).finish();
                        return true;
                    }
                    return false;
                }
            });
        }else{
            holder.parentView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder adb=new AlertDialog.Builder(context);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete " + spell.getSpellName());
                    final int positionToRemove = position;
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            character.getSpellsKnown().remove(spell);
                            spells.remove(positionToRemove);
                            notifyDataSetChanged();
                        }});
                    adb.show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return spells.size();
    }

    public class SpellViewHolder extends RecyclerView.ViewHolder{
        private TextView spellNameTextView;
        private TextView spellLevelTextView;
        private View parentView;

        public SpellViewHolder(@NonNull View view){
            super(view);
            this.parentView = view;
            this.spellNameTextView = view.findViewById(R.id.spell_name);
            this.spellLevelTextView = view.findViewById(R.id.spell_level);
        }
    }
}
