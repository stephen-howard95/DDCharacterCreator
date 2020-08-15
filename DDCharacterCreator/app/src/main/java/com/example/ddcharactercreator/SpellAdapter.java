package com.example.ddcharactercreator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.widget.Toast;
import static com.example.ddcharactercreator.DetailActivity.cantripCount;
import static com.example.ddcharactercreator.DetailActivity.spellCount;

public class SpellAdapter extends RecyclerView.Adapter<SpellAdapter.SpellViewHolder> {

    private Context context;
    private List<Spell> spells = new ArrayList<>();
    private Boolean isDatabaseSpell;
    private Character character = DetailActivity.character;
    private ArrayList<Spell> cantripsList = new ArrayList<>();
    private ArrayList<Spell> spellsList = new ArrayList<>();

    public SpellAdapter(@NonNull Context context, List<Spell> spells, Boolean isDatabaseSpell) {
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
        for(int i=0; i<character.getSpellsKnown().size(); i++){
            Spell thisSpell = character.getSpellsKnown().get(i);
            if(thisSpell.getLevel() == 0 && !cantripsList.contains(thisSpell)){
                cantripsList.add(thisSpell);
            } else if(!cantripsList.contains(thisSpell) && !spellsList.contains(thisSpell)){
                spellsList.add(thisSpell);
            }
        }
        Spell spell = spells.get(position);
        holder.spellNameTextView.setText(spell.getSpellName());
        if(spell.getLevel() > 0){
            holder.spellLevelTextView.setText(String.valueOf(spell.getLevel()));
        } else{
            holder.spellLevelTextView.setVisibility(View.GONE);
        }
        switch(spell.getSchool()){
            case "Abjuration":
                holder.spellNameTextView.setTextColor(context.getResources().getColor(R.color.abjuration));
                break;
            case "Conjuration":
                holder.spellNameTextView.setTextColor(context.getResources().getColor(R.color.conjuration));
                break;
            case "Divination":
                holder.spellNameTextView.setTextColor(context.getResources().getColor(R.color.divination));
                break;
            case "Enchantment":
                holder.spellNameTextView.setTextColor(context.getResources().getColor(R.color.enchantment));
                break;
            case "Evocation":
                holder.spellNameTextView.setTextColor(context.getResources().getColor(R.color.evocation));
                break;
            case "Illusion":
                holder.spellNameTextView.setTextColor(context.getResources().getColor(R.color.illusion));
                break;
            case "Necromancy":
                holder.spellNameTextView.setTextColor(context.getResources().getColor(R.color.necromancy));
                break;
            case "Transmutation":
                holder.spellNameTextView.setTextColor(context.getResources().getColor(R.color.transmutation));
                break;
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
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public boolean onLongClick(View v) {
                    int i=0;
                    Boolean alreadyKnowsSpell = false;
                    do{
                        if(character.getSpellsKnown().size() == 0){
                            break;
                        }
                        Spell knownSpell = character.getSpellsKnown().get(i);
                        if(spell.getSpellName().equals(knownSpell.getSpellName())){
                            alreadyKnowsSpell = true;
                            deleteSpell(spell);
                            break;
                        }
                        i++;
                    } while(i<character.getSpellsKnown().size());
                    if(!alreadyKnowsSpell){
                        if(spell.getLevel() == 0){
                            if(cantripsList.size() >= cantripCount){
                                Toast.makeText(context, "You are at your max cantrip count. You cannot add any more cantrips", Toast.LENGTH_SHORT).show();
                            }else{
                                addSpell(spell);
                            }
                        } else if(spell.getLevel() > 0){
                            if(spellsList.size() >= spellCount){
                                Toast.makeText(context, "You are at your max spell count. You cannot add any more spells", Toast.LENGTH_SHORT).show();
                            }else{
                                addSpell(spell);
                            }
                        }
                    }
                    return true;
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

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
        notifyDataSetChanged();
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

    private void addSpell(Spell spell){
        AlertDialog.Builder adb=new AlertDialog.Builder(context);
        adb.setTitle("Add " + spell.getSpellName());
        adb.setMessage(String.format("Are you sure you want to add %s to your prepared spells?", spell.getSpellName()));
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                character.getSpellsKnown().add(spell);
                if(spell.getLevel() == 0){
                    cantripsList.add(spell);
                }else{
                    spellsList.add(spell);
                }
                notifyDataSetChanged();
                Toast.makeText(context, String.format("%s added to spell list", spell.getSpellName()), Toast.LENGTH_SHORT).show();
            }});
        adb.show();
    }

    private void deleteSpell(Spell spell){
        AlertDialog.Builder adb=new AlertDialog.Builder(context);
        adb.setTitle("Delete?");
        adb.setMessage(String.format("You already know %s. Do you want to delete it?", spell.getSpellName()));
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                character.getSpellsKnown().remove(spell);
                if(spell.getLevel() == 0){
                    cantripsList.remove(spell);
                }else{
                    spellsList.remove(spell);
                }
                notifyDataSetChanged();
                Toast.makeText(context, String.format("%s removed from spell list", spell.getSpellName()), Toast.LENGTH_SHORT).show();
            }});
        adb.show();
    }
}
