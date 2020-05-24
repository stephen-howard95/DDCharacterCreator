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

    private ArrayList<String> characterDescription;

    @BindView(R.id.character_name) TextView characterName;
    @BindView(R.id.character_level) TextView characterLevel;
    @BindView(R.id.character_class) TextView characterClass;
    @BindView(R.id.character_race) TextView characterRace;
    @BindView(R.id.character_alignment) TextView characterAlignment;

    @BindView(R.id.height) EditText height;
    @BindView(R.id.weight) EditText weight;
    @BindView(R.id.age) EditText age;
    @BindView(R.id.skin_color) EditText skinColor;
    @BindView(R.id.eye_color) EditText eyeColor;
    @BindView(R.id.hair) EditText hair;
    @BindView(R.id.personality_traits) EditText personalityTraits;
    @BindView(R.id.ideals) EditText ideals;
    @BindView(R.id.bonds) EditText bonds;
    @BindView(R.id.flaws) EditText flaws;
    @BindView(R.id.backstory) EditText backstory;
    @BindView(R.id.languages) EditText languagesKnown;

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

        //Setting the character description.
        characterDescription = character.getCharacterDescription();

        if(characterDescription.isEmpty()){
            characterDescription.add(height.getText().toString());
            characterDescription.add(weight.getText().toString());
            characterDescription.add(age.getText().toString());
            characterDescription.add(skinColor.getText().toString());
            characterDescription.add(eyeColor.getText().toString());
            characterDescription.add(hair.getText().toString());
            characterDescription.add(personalityTraits.getText().toString());
            characterDescription.add(ideals.getText().toString());
            characterDescription.add(bonds.getText().toString());
            characterDescription.add(flaws.getText().toString());
            characterDescription.add(backstory.getText().toString());
        }

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
        languagesKnown.setText(languages.toString());

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        DetailActivity.canLongRest = false;

        height.setText(characterDescription.get(0));
        weight.setText(characterDescription.get(1));
        age.setText(characterDescription.get(2));
        skinColor.setText(characterDescription.get(3));
        eyeColor.setText(characterDescription.get(4));
        hair.setText(characterDescription.get(5));
        personalityTraits.setText(characterDescription.get(6));
        ideals.setText(characterDescription.get(7));
        bonds.setText(characterDescription.get(8));
        flaws.setText(characterDescription.get(9));
        backstory.setText(characterDescription.get(10));
    }

    @Override
    public void onPause() {
        super.onPause();
        characterDescription.set(0, height.getText().toString());
        characterDescription.set(1, weight.getText().toString());
        characterDescription.set(2, age.getText().toString());
        characterDescription.set(3, skinColor.getText().toString());
        characterDescription.set(4, eyeColor.getText().toString());
        characterDescription.set(5, hair.getText().toString());
        characterDescription.set(6, personalityTraits.getText().toString());
        characterDescription.set(7, ideals.getText().toString());
        characterDescription.set(8, bonds.getText().toString());
        characterDescription.set(9, flaws.getText().toString());
        characterDescription.set(10, backstory.getText().toString());
    }
}
