package com.example.ddcharactercreator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ddcharactercreator.DetailActivity.calculateModifier;
import static com.example.ddcharactercreator.DetailActivity.character;
import static com.example.ddcharactercreator.DetailActivity.proficiencyBonus;

public class SpellcastingFragment extends Fragment {

    private SpellAdapter spellsAdapter;
    private SpellAdapter cantripsAdapter;
    private final ArrayList<Spell> fullSpellsList = DetailActivity.character.getSpellsKnown();
    private ArrayList<Spell> spellsList = new ArrayList<Spell>();
    private ArrayList<Spell> cantripsList = new ArrayList<Spell>();
    private int spellCount;
    private int cantripCount;
    private ArrayList<String> spellSlotsClicked = DetailActivity.character.getSpellSlotsClicked();

    @BindView(R.id.spellcasting_ability) TextView spellcastingAbility;
    @BindView(R.id.spell_save_dc) TextView spellSaveDC;
    @BindView(R.id.spell_attack_bonus) TextView spellAttackBonus;

    @BindView(R.id.spell_slots) ScrollView spellSlots;
    @BindView(R.id.add_spells_to_list) TextView addSpellsTextView;
    @BindView(R.id.spells_known) ListView spellsKnown;
    @BindView(R.id.spells_known_label) TextView spellsKnownLabel;
    @BindView(R.id.cantrips_known) ListView cantripsKnown;
    @BindView(R.id.cantrips_known_label) TextView cantripsKnownLabel;

    @BindView(R.id.spell_slot_1) CheckBox spellSlot1;
    @BindView(R.id.spell_slot_2) CheckBox spellSlot2;
    @BindView(R.id.spell_slot_3) CheckBox spellSlot3;
    @BindView(R.id.spell_slot_4) CheckBox spellSlot4;
    @BindView(R.id.spell_slot_5) CheckBox spellSlot5;
    @BindView(R.id.spell_slot_6) CheckBox spellSlot6;
    @BindView(R.id.spell_slot_7) CheckBox spellSlot7;
    @BindView(R.id.spell_slot_8) CheckBox spellSlot8;
    @BindView(R.id.spell_slot_9) CheckBox spellSlot9;
    @BindView(R.id.spell_slot_10) CheckBox spellSlot10;
    @BindView(R.id.spell_slot_11) CheckBox spellSlot11;
    @BindView(R.id.spell_slot_12) CheckBox spellSlot12;
    @BindView(R.id.spell_slot_13) CheckBox spellSlot13;
    @BindView(R.id.spell_slot_14) CheckBox spellSlot14;
    @BindView(R.id.spell_slot_15) CheckBox spellSlot15;
    @BindView(R.id.spell_slot_16) CheckBox spellSlot16;
    @BindView(R.id.spell_slot_17) CheckBox spellSlot17;
    @BindView(R.id.spell_slot_18) CheckBox spellSlot18;
    @BindView(R.id.spell_slot_19) CheckBox spellSlot19;
    @BindView(R.id.spell_slot_20) CheckBox spellSlot20;
    @BindView(R.id.spell_slot_21) CheckBox spellSlot21;
    @BindView(R.id.spell_slot_22) CheckBox spellSlot22;

    public SpellcastingFragment(){
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_spellcasting, container, false);

        final Character character = DetailActivity.character;

        ButterKnife.bind(this, rootView);

        //Separates Spells and Cantrips
        for(int i=0; i<fullSpellsList.size(); i++){
            Spell spell = fullSpellsList.get(i);
            if(spell.getLevel() == 0 && !cantripsList.contains(spell)){
                cantripsList.add(spell);
            } else if(!cantripsList.contains(spell) && !spellsList.contains(spell)){
                spellsList.add(spell);
            }
        }

        if(character.getCharacterClass().equals("Barbarian") || character.getCharacterClass().equals("Fighter") || character.getCharacterClass().equals("Monk") || character.getCharacterClass().equals("Rogue")){
            spellcastingAbility.setVisibility(View.GONE);
            spellSaveDC.setVisibility(View.GONE);
            spellAttackBonus.setVisibility(View.GONE);
            spellSlots.setVisibility(View.GONE);
            spellsKnown.setVisibility(View.GONE);
            spellsKnownLabel.setVisibility(View.GONE);
            cantripsKnown.setVisibility(View.GONE);
            cantripsKnownLabel.setVisibility(View.GONE);
            addSpellsTextView.setText("This character class does not have access to Spellcasting features");
            addSpellsTextView.setTextSize(48);
        }else {
            switch (character.getCharacterClass()) {
                case "Bard":
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.charisma_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellCount = 4;
                    cantripCount = 2;
                    break;
                case "Cleric":
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.wisdom_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellCount = character.getLevel() + calculateModifier(character.getStatValues().get(4));
                    cantripCount = 3;
                    break;
                case "Druid":
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.wisdom_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellCount = character.getLevel() + calculateModifier(character.getStatValues().get(4));
                    cantripCount = 2;
                    break;
                case "Paladin":
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.charisma_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellCount = character.getLevel()/2 + calculateModifier(character.getStatValues().get(5));
                    cantripCount = -2;
                    break;
                case "Ranger":
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.wisdom_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellCount = character.getLevel() + calculateModifier(character.getStatValues().get(4));
                    cantripCount = -2;
                    break;
                case "Sorcerer":
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.charisma_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellCount = 2;
                    cantripCount = 4;
                    break;
                case "Warlock":
                    spellSlot2.setVisibility(View.GONE);
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.charisma_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellCount = 2;
                    cantripCount = 2;
                case "Wizard":
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.intelligence_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(3))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(3))));
                    spellCount = character.getLevel() + calculateModifier(character.getStatValues().get(3));
                    cantripCount = 3;
                    break;
            }
            if(spellCount<1){
                spellCount = 1;
            }

            spellsAdapter = new SpellAdapter(getContext(), spellsList);
            cantripsAdapter = new SpellAdapter(getContext(), cantripsList);
            addSpellsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(character.getCharacterClass().equals("Paladin") && character.getLevel() < 2){
                        Toast.makeText(getContext(), "You do not have access to the Spellcasting feature yet", Toast.LENGTH_SHORT).show();
                    } else if(character.getCharacterClass().equals("Ranger") && character.getLevel() < 2){
                        Toast.makeText(getContext(), "You do not have access to the Spellcasting feature yet", Toast.LENGTH_SHORT).show();
                    } else if (spellsList.size() >= spellCount && cantripsList.size() >= cantripCount) {
                        Toast.makeText(getContext(), "You cannot learn any more spells or cantrips", Toast.LENGTH_SHORT).show();
                    } else {
                        addSpellToList();
                    }
                }
            });

            spellsKnown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    launchSpellDetailActivity((Spell) parent.getItemAtPosition(position));
                }
            });
            spellsKnown.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder adb=new AlertDialog.Builder(getContext());
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete " + spellsList.get(position).getSpellName());
                    final int positionToRemove = position;
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            fullSpellsList.remove(spellsList.get(positionToRemove));
                            spellsList.remove(positionToRemove);
                            spellsAdapter.notifyDataSetChanged();
                        }});
                    adb.show();
                    return true;
                }
            });
            cantripsKnown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    launchSpellDetailActivity((Spell) parent.getItemAtPosition(position));
                }
            });
            cantripsKnown.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder adb=new AlertDialog.Builder(getContext());
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete " + cantripsList.get(position).getSpellName());
                    final int positionToRemove = position;
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            fullSpellsList.remove(cantripsList.get(positionToRemove));
                            cantripsList.remove(positionToRemove);
                            cantripsAdapter.notifyDataSetChanged();
                        }});
                    adb.show();
                    return true;
                }
            });
            spellsKnown.setAdapter(spellsAdapter);
            cantripsKnown.setAdapter(cantripsAdapter);

            //Spell Slots
            spellSlot1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(0, "yes");
                    } else{
                        spellSlotsClicked.set(0, "no");
                    }
                }
            });
            spellSlot2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(1, "yes");
                    } else{
                        spellSlotsClicked.set(1, "no");
                    }
                }
            });
            spellSlot3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(2, "yes");
                    } else{
                        spellSlotsClicked.set(2, "no");
                    }
                }
            });
            spellSlot4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(3, "yes");
                    } else{
                        spellSlotsClicked.set(3, "no");
                    }
                }
            });
            spellSlot5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(4, "yes");
                    } else{
                        spellSlotsClicked.set(4, "no");
                    }
                }
            });
            spellSlot6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(5, "yes");
                    } else{
                        spellSlotsClicked.set(5, "no");
                    }
                }
            });
            spellSlot7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(6, "yes");
                    } else{
                        spellSlotsClicked.set(6, "no");
                    }
                }
            });
            spellSlot8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(7, "yes");
                    } else{
                        spellSlotsClicked.set(7, "no");
                    }
                }
            });
            spellSlot9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(8, "yes");
                    } else{
                        spellSlotsClicked.set(8, "no");
                    }
                }
            });
            spellSlot10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(9, "yes");
                    } else{
                        spellSlotsClicked.set(9, "no");
                    }
                }
            });
            spellSlot11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(10, "yes");
                    } else{
                        spellSlotsClicked.set(10, "no");
                    }
                }
            });
            spellSlot12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(11, "yes");
                    } else{
                        spellSlotsClicked.set(11, "no");
                    }
                }
            });
            spellSlot13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(12, "yes");
                    } else{
                        spellSlotsClicked.set(12, "no");
                    }
                }
            });
            spellSlot14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(13, "yes");
                    } else{
                        spellSlotsClicked.set(13, "no");
                    }
                }
            });
            spellSlot15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(14, "yes");
                    } else{
                        spellSlotsClicked.set(14, "no");
                    }
                }
            });
            spellSlot16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(15, "yes");
                    } else{
                        spellSlotsClicked.set(15, "no");
                    }
                }
            });
            spellSlot17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(16, "yes");
                    } else{
                        spellSlotsClicked.set(16, "no");
                    }
                }
            });
            spellSlot18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(17, "yes");
                    } else{
                        spellSlotsClicked.set(17, "no");
                    }
                }
            });
            spellSlot19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(18, "yes");
                    } else{
                        spellSlotsClicked.set(18, "no");
                    }
                }
            });
            spellSlot20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(19, "yes");
                    } else{
                        spellSlotsClicked.set(19, "no");
                    }
                }
            });
            spellSlot21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(20, "yes");
                    } else{
                        spellSlotsClicked.set(20, "no");
                    }
                }
            });
            spellSlot22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        spellSlotsClicked.set(21, "yes");
                    } else{
                        spellSlotsClicked.set(21, "no");
                    }
                }
            });
        }
        return rootView;
    }
    private void addSpellToList(){
        Intent intent = new Intent(getActivity(), SpellChooserActivity.class);
        startActivity(intent);
    }

    private void launchSpellDetailActivity(Spell spell){
        Intent intent = new Intent(getActivity(), SpellDetailActivity.class);
        intent.putExtra(SpellDetailActivity.SPELL, spell);
        startActivity(intent);
    }

    public void resetSpellSlots(){
        spellSlot1.setChecked(false);
        spellSlot2.setChecked(false);
        spellSlot3.setChecked(false);
        spellSlot4.setChecked(false);
        spellSlot5.setChecked(false);
        spellSlot6.setChecked(false);
        spellSlot7.setChecked(false);
        spellSlot8.setChecked(false);
        spellSlot9.setChecked(false);
        spellSlot10.setChecked(false);
        spellSlot11.setChecked(false);
        spellSlot12.setChecked(false);
        spellSlot13.setChecked(false);
        spellSlot14.setChecked(false);
        spellSlot15.setChecked(false);
        spellSlot16.setChecked(false);
        spellSlot17.setChecked(false);
        spellSlot18.setChecked(false);
        spellSlot19.setChecked(false);
        spellSlot20.setChecked(false);
        spellSlot21.setChecked(false);
        spellSlot22.setChecked(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        DetailActivity.canLongRest = true;

        if(fullSpellsList.size() > 0){
            for(int i=0; i<fullSpellsList.size(); i++){
                Spell spell = fullSpellsList.get(i);
                if(spell.getLevel() == 0 && !cantripsList.contains(spell)){
                    cantripsList.add(spell);
                } else if(!cantripsList.contains(spell) && !spellsList.contains(spell)){
                    spellsList.add(spell);
                }
            }
            spellsAdapter.notifyDataSetChanged();
            cantripsAdapter.notifyDataSetChanged();
        }
        if(character.getSpellSlotsClicked().get(0).equals("yes")){
            spellSlot1.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(1).equals("yes")){
            spellSlot2.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(2).equals("yes")){
            spellSlot3.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(3).equals("yes")){
            spellSlot4.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(4).equals("yes")){
            spellSlot5.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(5).equals("yes")){
            spellSlot6.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(6).equals("yes")){
            spellSlot7.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(7).equals("yes")){
            spellSlot8.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(8).equals("yes")){
            spellSlot9.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(9).equals("yes")){
            spellSlot10.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(10).equals("yes")){
            spellSlot11.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(11).equals("yes")){
            spellSlot12.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(12).equals("yes")){
            spellSlot13.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(13).equals("yes")){
            spellSlot14.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(14).equals("yes")){
            spellSlot15.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(15).equals("yes")){
            spellSlot16.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(16).equals("yes")){
            spellSlot17.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(17).equals("yes")){
            spellSlot18.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(18).equals("yes")){
            spellSlot19.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(19).equals("yes")){
            spellSlot20.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(20).equals("yes")){
            spellSlot21.setChecked(true);
        }
        if(character.getSpellSlotsClicked().get(21).equals("yes")){
            spellSlot22.setChecked(true);
        }
    }
}
