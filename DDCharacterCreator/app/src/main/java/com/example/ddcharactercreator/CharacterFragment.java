package com.example.ddcharactercreator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterFragment extends Fragment {
    
    @BindView(R.id.character_name) TextView characterName;
    @BindView(R.id.character_level) TextView characterLevel;
    @BindView(R.id.character_class) TextView characterClass;
    @BindView(R.id.character_race) TextView characterRace;
    @BindView(R.id.character_alignment) TextView characterAlignment;
    @BindView(R.id.languages) EditText languagesKnown;

    @BindView(R.id.subclass_info_textview_1) TextView subclassInfoTextView1;
    @BindView(R.id.subclass_info_textview_2) TextView subclassInfoTextView2;
    @BindView(R.id.subclass_info_textview_3) TextView subclassInfoTextView3;
    @BindView(R.id.checkboxes_textview_1) TextView checkBoxes1;
    @BindView(R.id.checkboxes_textview_2) TextView checkBoxes2;
    @BindView(R.id.checkboxes_textview_3) TextView checkBoxes3;
    @BindView(R.id.checkboxes_textview_4) TextView checkBoxes4;
    @BindView(R.id.checkboxes_textview_5) TextView checkBoxes5;
    @BindView(R.id.checkbox_1_1) CheckBox checkBox1_1;
    @BindView(R.id.checkbox_1_2) CheckBox checkBox1_2;
    @BindView(R.id.checkbox_1_3) CheckBox checkBox1_3;
    @BindView(R.id.checkbox_1_4) CheckBox checkBox1_4;
    @BindView(R.id.checkbox_1_5) CheckBox checkBox1_5;
    @BindView(R.id.checkbox_1_6) CheckBox checkBox1_6;
    @BindView(R.id.checkbox_2_1) CheckBox checkBox2_1;
    @BindView(R.id.checkbox_2_2) CheckBox checkBox2_2;
    @BindView(R.id.checkbox_2_3) CheckBox checkBox2_3;
    @BindView(R.id.checkbox_2_4) CheckBox checkBox2_4;
    @BindView(R.id.checkbox_2_5) CheckBox checkBox2_5;
    @BindView(R.id.checkbox_2_6) CheckBox checkBox2_6;
    @BindView(R.id.checkbox_3_1) CheckBox checkBox3_1;
    @BindView(R.id.checkbox_3_2) CheckBox checkBox3_2;
    @BindView(R.id.checkbox_3_3) CheckBox checkBox3_3;
    @BindView(R.id.checkbox_4_1) CheckBox checkBox4_1;
    @BindView(R.id.checkbox_5_1) CheckBox checkBox5_1;
    @BindView(R.id.subclass_info_edittext_textview) TextView editTextTextView;
    @BindView(R.id.subclass_info_edittext) EditText subclassInfoEditText;
    @BindView(R.id.subclass_info_listview_header) TextView subclassInfoHeader;
    @BindView(R.id.subclass_info_listview) ListView subclassInfoListView;
    @BindView(R.id.extra_info_listview_header) TextView extraInfoHeader;
    @BindView(R.id.extra_info_listview) ListView bonusStatsList;

    public CharacterFragment(){
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character, container, false);

        Character character = DetailActivity.character;

        ButterKnife.bind(this, rootView);

        //Setting Character name, level, character class, race, and alignment
        characterName.setText(character.getName());
        characterLevel.setText("Level " + character.getLevel());
        characterClass.setText(character.getCharacterClass());
        characterRace.setText(character.getRace());
        characterAlignment.setText(character.getAlignment());

        //Setting languages known
        StringBuilder languages = new StringBuilder();
        languages.append(getString(R.string.languages_known));

        switch(character.getRace()){
            case "Dwarf":
                languages.append(" Common, Dwarvish, ");
                break;
            case "Elf":
            case "Half-Elf":
                languages.append(" Common, Elvish, ");
                break;
            case "Halfling":
                languages.append(" Common, Halfling, ");
                break;
            case "Human":
                languages.append(" Common, ");
                break;
            case "Dragonborn":
                languages.append(" Common, Draconic, ");
                break;
            case "Gnome":
                languages.append(" Common, Gnomish, ");
                break;
            case "Half-Orc":
                languages.append(" Common, Orcish, ");
                break;
            case "Tiefling":
                languages.append(" Common, Infernal, ");
                break;
        }
        if(character.getCharacterClass().equals("Druid")){
            languages.append("Druidic, ");
        }
        if(character.getCharacterClass().equals("Rogue")){
            languages.append("Thieve's Cant, ");
        }
        languagesKnown.setText(languages.toString());

        ArrayList<String> bonusStats = new ArrayList<String>();

        //Adding in Racial Benefits
        switch(character.getRace()){
            case "Dwarf":
                //Mountain Dwarf is chosen for this
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("Advantage on saving throws against poison");
                bonusStats.add("Resistance to poison damage");
                bonusStats.add("Add double your proficiency bonus to INT History checks relating to stonework");
                break;
            case "Elf":
                //Wood Elf is chosen for this
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("Advantage on saving throws against being charmed");
                bonusStats.add("Magic effects cannot put you to sleep");
                bonusStats.add("You can get a full Long Rest by only meditating for 4 hours");
                break;
            case "Halfling":
                //Stout Halfling is chosen for this
                bonusStats.add("When you roll a 1 on a D20, you can choose to re-roll and use the new roll");
                bonusStats.add("Advantage on saving throws against being frightened");
                bonusStats.add("Advantage on saving throws against poison");
                bonusStats.add("Resistance to poison damage");
                break;
            case "Gnome":
                //Forest Gnome is chosen for this
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("Advantage on INT, WIS, and CHA saving throws against magic");
                bonusStats.add("Through sounds and gestures, you can communicate simple ideas with small animals");
                break;
            case "Half-Elf":
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("Advantage on saving throws against being charmed");
                bonusStats.add("Magic effects cannot put you to sleep");
                break;
            case "Half-Orc":
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("When you are reduced to 0hp, you can drop to 1 instead. You can do this once per Long Rest");
                bonusStats.add("When you score a critical hit, you can roll an extra of the weapon's damage die");
                break;
            case "Tiefling":
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("You are resistant to Fire damage");
                break;
        }
        //Adding class info when applicable
        switch(character.getCharacterClass()){
            case "Barbarian":
                subclassInfoTextView1.setText(R.string.rage_damage);
                checkBoxes1.setVisibility(View.VISIBLE);
                checkBoxes1.setText(R.string.rage_uses);
                checkBox1_1.setVisibility(View.VISIBLE);
                checkBox1_2.setVisibility(View.VISIBLE);
                bonusStats.add(getString(R.string.rage_description));
                break;
            case "Bard":
                subclassInfoTextView1.setText(R.string.bardic_inspiration_die);
                bonusStats.add(getString(R.string.bardic_inspiration_description));
                checkBoxes1.setVisibility(View.VISIBLE);
                checkBoxes1.setText(R.string.bardic_inspiration_uses);
                checkBox1_1.setVisibility(View.VISIBLE);
                if(calculateModifier(character.getStatValues().get(5)) >= 2){
                    checkBox1_2.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 3){
                    checkBox1_3.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 4){
                    checkBox1_4.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 5){
                    checkBox1_5.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 6){
                    checkBox1_6.setVisibility(View.VISIBLE);
                }
                break;
            case "Cleric":
                //Only subclass stuff. depending on divine domain, level 1 provides extra languages,
                // skill & weapon/armor proficiencies, spells/cantrips, bonusStats additions, checkBoxes
                break;
            case "Druid":
                //Literally have nothing to add here at level 1.
                break;
            case "Fighter":
                subclassInfoTextView1.setText(R.string.fighting_style);
                checkBoxes1.setVisibility(View.VISIBLE);
                checkBox1_1.setVisibility(View.VISIBLE);
                checkBoxes1.setText(R.string.second_wind_amount);
                bonusStats.add(getString(R.string.second_wind));
                break;
            case "Monk":
                subclassInfoTextView1.setText(R.string.unarmed_strike_damage);
                bonusStats.add(getString(R.string.martial_arts));
                break;
            case "Paladin":
                checkBoxes1.setVisibility(View.VISIBLE);
                checkBoxes1.setText(R.string.divine_sense_uses);
                checkBox1_1.setVisibility(View.VISIBLE);
                if(calculateModifier(character.getStatValues().get(5)) >= 1){
                    checkBox1_2.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 2){
                    checkBox1_3.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 3){
                    checkBox1_4.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 4){
                    checkBox1_5.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 5){
                    checkBox1_6.setVisibility(View.VISIBLE);
                }
                editTextTextView.setVisibility(View.VISIBLE);
                editTextTextView.setText(R.string.lay_on_hands_pool);
                subclassInfoEditText.setText(String.valueOf(character.getLevel()*5));
                bonusStats.add(getString(R.string.divine_sense));
                bonusStats.add(getString(R.string.lay_on_hands));
                break;
            case "Ranger":
                subclassInfoTextView1.setText(R.string.favored_enemy);
                subclassInfoTextView2.setVisibility(View.VISIBLE);
                subclassInfoTextView2.setText(R.string.favored_terrain);
                bonusStats.add(getString(R.string.favored_enemy_description));
                bonusStats.add(getString(R.string.favored_terrain_description));
                break;
            case "Rogue":
                subclassInfoTextView1.setText(R.string.sneak_attack_damage);
                bonusStats.add(getString(R.string.sneak_attack_description));
                break;
            case "Sorcerer":
                //Only subclass stuff at level 1. Can add a TextView(dragon ancestor), affect your
                // HP/AC, or a checkbox(tides of chaos, wild magic)
                break;
            case "Warlock":
                //Only subclass stuff at level 1. Can add a checkbox, add spells to the Warlock spell
                // list, and bonusStats info.
                break;
            case "Wizard":
                subclassInfoTextView1.setText(R.string.arcane_recovery_amount);
                bonusStats.add(getString(R.string.arcane_recovery));
                break;
        }
        ListAdapter adapter = new ListAdapter(getContext(), bonusStats);
        bonusStatsList.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        DetailActivity.canLongRest = false;
    }
}
