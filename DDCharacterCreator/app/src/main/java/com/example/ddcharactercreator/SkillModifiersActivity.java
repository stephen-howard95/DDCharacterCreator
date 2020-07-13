package com.example.ddcharactercreator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.example.ddcharactercreator.DetailActivity.calculateModifier;
import static com.example.ddcharactercreator.DetailActivity.character;
import static com.example.ddcharactercreator.DetailActivity.proficiencyBonus;

public class SkillModifiersActivity extends AppCompatActivity {
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_modifiers_layout);

        Character character = DetailActivity.character;

        if (character.getLevel() > 16){
            proficiencyBonus = 6;
        }else if(character.getLevel() > 12){
            proficiencyBonus = 5;
        } else if(character.getLevel() > 8){
            proficiencyBonus = 4;
        } else if(character.getLevel() > 4){
            proficiencyBonus = 3;
        }

        TextView toolProficiencyLabel = findViewById(R.id.tool_proficiencies_label);
        TextView toolProficienciesTextView = findViewById(R.id.tool_proficiencies);
        TextView toolProficienciesModifier = findViewById(R.id.tool_proficiency_modifier);
        ListView weaponArmorProficienciesListView = findViewById(R.id.weapon_armor_proficiencies);
        ArrayList<String> weaponArmorProficiencies = new ArrayList<>();

        //Skill Proficiencies
        TextView acrobaticsModifier = findViewById(R.id.acrobatics_modifier);
        TextView animalHandlingModifier = findViewById(R.id.animal_handling_modifier);
        TextView arcanaModifier = findViewById(R.id.arcana_modifier);
        TextView athleticsModifier = findViewById(R.id.athletics_modifier);
        TextView deceptionModifier = findViewById(R.id.deception_modifier);
        TextView historyModifier = findViewById(R.id.history_modifier);
        TextView insightModifier = findViewById(R.id.insight_modifier);
        TextView intimidationModifier = findViewById(R.id.intimidation_modifier);
        TextView investigationModifier = findViewById(R.id.investigation_modifier);
        TextView medicineModifier = findViewById(R.id.medicine_modifier);
        TextView natureModifier = findViewById(R.id.nature_modifier);
        TextView perceptionModifier = findViewById(R.id.perception_modifier);
        TextView performanceModifier = findViewById(R.id.performance_modifier);
        TextView persuasionModifier = findViewById(R.id.persuasion_modifier);
        TextView religionModifier = findViewById(R.id.religion_modifier);
        TextView sleightOfHandModifier = findViewById(R.id.sleight_of_hand_modifier);
        TextView stealthModifier = findViewById(R.id.stealth_modifier);
        TextView survivalModifier = findViewById(R.id.survival_modifier);

        TextView acrobaticsLabel = findViewById(R.id.acrobatics_label);
        acrobaticsLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(acrobaticsLabel, acrobaticsModifier, "Acrobatics", 1);
            }
        });
        TextView animalHandlingLabel = findViewById(R.id.animal_handling_label);
        animalHandlingLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(animalHandlingLabel, animalHandlingModifier, "Animal Handling", 4);
            }
        });
        TextView arcanaLabel = findViewById(R.id.arcana_label);
        arcanaLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(arcanaLabel, arcanaModifier, "Arcana", 3);
            }
        });
        TextView athleticsLabel = findViewById(R.id.athletics_label);
        athleticsLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(athleticsLabel, athleticsModifier, "Athletics", 0);
            }
        });
        TextView deceptionLabel = findViewById(R.id.deception_label);
        deceptionLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(deceptionLabel, deceptionModifier, "Deception", 5);
            }
        });
        TextView historyLabel = findViewById(R.id.history_label);
        historyLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(historyLabel, historyModifier, "History", 3);
            }
        });
        TextView insightLabel = findViewById(R.id.insight_label);
        insightLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(insightLabel, insightModifier, "Insight", 4);
            }
        });
        TextView intimidationLabel = findViewById(R.id.intimidation_label);
        intimidationLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(intimidationLabel, intimidationModifier, "Intimidation", 5);
            }
        });
        TextView investigationLabel = findViewById(R.id.investigation_label);
        investigationLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(investigationLabel, investigationModifier, "Investigation", 3);
            }
        });
        TextView medicineLabel = findViewById(R.id.medicine_label);
        medicineLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(medicineLabel, medicineModifier, "Medicine", 4);
            }
        });
        TextView natureLabel = findViewById(R.id.nature_label);
        natureLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(natureLabel, natureModifier, "Nature", 3);
            }
        });
        TextView perceptionLabel = findViewById(R.id.perception_label);
        perceptionLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(perceptionLabel, perceptionModifier, "Perception", 4);
            }
        });
        TextView performanceLabel = findViewById(R.id.performance_label);
        performanceLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(performanceLabel, performanceModifier, "Performance", 5);
            }
        });
        TextView persuasionLabel = findViewById(R.id.persuasion_label);
        persuasionLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(persuasionLabel, persuasionModifier, "Persuasion", 5);
            }
        });
        TextView religionLabel = findViewById(R.id.religion_label);
        religionLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(religionLabel, religionModifier, "Religion", 3);
            }
        });
        TextView sleightOfHandLabel = findViewById(R.id.sleight_of_hand_label);
        sleightOfHandLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(sleightOfHandLabel, sleightOfHandModifier, "Sleight of Hand", 1);
            }
        });
        TextView stealthLabel = findViewById(R.id.stealth_label);
        stealthLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(stealthLabel, stealthModifier, "Stealth", 1);
            }
        });
        TextView survivalLabel = findViewById(R.id.survival_label);
        survivalLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProficiencyStatus(survivalLabel, survivalModifier, "Survival", 4);
            }
        });

        acrobaticsModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(1))));
        animalHandlingModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4))));
        arcanaModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3))));
        athleticsModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(0))));
        deceptionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5))));
        historyModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3))));
        insightModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4))));
        intimidationModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5))));
        investigationModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3))));
        medicineModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4))));
        natureModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3))));
        perceptionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4))));
        performanceModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5))));
        persuasionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5))));
        religionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3))));
        sleightOfHandModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(1))));
        stealthModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(1))));
        survivalModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4))));

        switch(character.getRace()){
            case "Elf":
                if(!character.getProficiencyChoices().contains("Perception")){
                    character.getProficiencyChoices().add("Perception");
                }
                break;
            case "Half-Elf":
                if(!character.getProficiencyChoices().contains("Perception")){
                    character.getProficiencyChoices().add("Perception");
                }
                if(!character.getProficiencyChoices().contains("Insight")){
                    character.getProficiencyChoices().add("Insight");
                }
                break;
            case "Half-Orc":
                if(!character.getProficiencyChoices().contains("Intimidation")){
                    character.getProficiencyChoices().add("Intimidation");
                }
                break;
            case "Tabaxi":
                if(!character.getProficiencyChoices().contains("Perception")){
                    character.getProficiencyChoices().add("Perception");
                }
                if(!character.getProficiencyChoices().contains("Stealth")){
                    character.getProficiencyChoices().add("Stealth");
                }
                break;
        }
        for(int i=0; i<character.getProficiencyChoices().size(); i++){
            switch(character.getProficiencyChoices().get(i)){
                case "Acrobatics":
                    acrobaticsModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(1)) + proficiencyBonus));
                    acrobaticsLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    acrobaticsModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Animal Handling":
                    animalHandlingModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4)) + proficiencyBonus));
                    animalHandlingLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    animalHandlingModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Arcana":
                    arcanaModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3)) + proficiencyBonus));
                    arcanaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    arcanaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Athletics":
                    athleticsModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(0)) + proficiencyBonus));
                    athleticsLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    athleticsModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Deception":
                    deceptionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5)) + proficiencyBonus));
                    deceptionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    deceptionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "History":
                    historyModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3)) + proficiencyBonus));
                    historyLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    historyModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Insight":
                    insightModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4)) + proficiencyBonus));
                    insightLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    insightModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Intimidation":
                    intimidationModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5)) + proficiencyBonus));
                    intimidationLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    intimidationModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Investigation":
                    investigationModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3)) + proficiencyBonus));
                    investigationLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    investigationModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Medicine":
                    medicineModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4)) + proficiencyBonus));
                    medicineLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    medicineModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Nature":
                    natureModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3)) + proficiencyBonus));
                    natureLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    natureModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Perception":
                    perceptionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4)) + proficiencyBonus));
                    perceptionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    perceptionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Performance":
                    performanceModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5)) + proficiencyBonus));
                    performanceLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    performanceModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Persuasion":
                    persuasionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5)) + proficiencyBonus));
                    persuasionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    persuasionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Religion":
                    religionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3)) + proficiencyBonus));
                    religionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    religionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Sleight of Hand":
                    sleightOfHandModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(1)) + proficiencyBonus));
                    sleightOfHandLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    sleightOfHandModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Stealth":
                    stealthModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(1)) + proficiencyBonus));
                    stealthLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    stealthModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
                case "Survival":
                    survivalModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4)) + proficiencyBonus));
                    survivalLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    survivalModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    break;
            }
        }
        //Bard's Jack of All Trades
        if(character.getCharacterClass().equals("Bard") && character.getLevel() >= 2){
            List<String> skillsList = Arrays.asList(getResources().getStringArray(R.array.skill_proficiencies_array));
            for(int i=0; i<skillsList.size(); i++){
                if(!character.getProficiencyChoices().contains(skillsList.get(i))){
                    switch(skillsList.get(i)){
                        case "Acrobatics":
                            acrobaticsModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(1)) + (proficiencyBonus/2))));
                            break;
                        case "Animal Handling":
                            animalHandlingModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(4)) + (proficiencyBonus/2))));
                            break;
                        case "Arcana":
                            arcanaModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(3)) + (proficiencyBonus/2))));
                            break;
                        case "Athletics":
                            athleticsModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(0)) + (proficiencyBonus/2))));
                            break;
                        case "Deception":
                            deceptionModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(5)) + (proficiencyBonus/2))));
                            break;
                        case "History":
                            historyModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(3)) + (proficiencyBonus/2))));
                            break;
                        case "Insight":
                            insightModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(4)) + (proficiencyBonus/2))));
                            break;
                        case "Intimidation":
                            intimidationModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(5)) + (proficiencyBonus/2))));
                            break;
                        case "Investigation":
                            investigationModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(3)) + (proficiencyBonus/2))));
                            break;
                        case "Medicine":
                            medicineModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(4)) + (proficiencyBonus/2))));
                            break;
                        case "Nature":
                            natureModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(3)) + (proficiencyBonus/2))));
                            break;
                        case "Perception":
                            perceptionModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(4)) + (proficiencyBonus/2))));
                            break;
                        case "Performance":
                            performanceModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(5)) + (proficiencyBonus/2))));
                            break;
                        case "Persuasion":
                            persuasionModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(5)) + (proficiencyBonus/2))));
                            break;
                        case "Religion":
                            religionModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(3)) + (proficiencyBonus/2))));
                            break;
                        case "Sleight of Hand":
                            sleightOfHandModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(1)) + (proficiencyBonus/2))));
                            break;
                        case "Stealth":
                            stealthModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(1)) + (proficiencyBonus/2))));
                            break;
                        case "Survival":
                            survivalModifier.setText(String.valueOf(Math.floor(calculateModifier(character.getStatValues().get(4)) + (proficiencyBonus/2))));
                            break;
                    }
                }
            }
        }
        ListAdapter adapter = new ListAdapter(getApplicationContext(), weaponArmorProficiencies);

        //Weapon/armor/tool proficiencies
        switch(character.getCharacterClass()){
            case "Barbarian":
                toolProficienciesModifier.setVisibility(View.GONE);
                toolProficienciesTextView.setVisibility(View.GONE);
                toolProficiencyLabel.setVisibility(View.GONE);
                weaponArmorProficiencies.add("Light Armor");
                weaponArmorProficiencies.add("Medium Armor");
                weaponArmorProficiencies.add("Shields");
                weaponArmorProficiencies.add("Simple Weapons");
                weaponArmorProficiencies.add("Martial Weapons");
                break;
            case "Bard":
                toolProficienciesTextView.setText("Musical Instruments");
                toolProficienciesModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5)) + proficiencyBonus));
                weaponArmorProficiencies.add("Light Armor");
                weaponArmorProficiencies.add("Simple Weapons");
                weaponArmorProficiencies.add("Hand Crossbows");
                weaponArmorProficiencies.add("Longswords");
                weaponArmorProficiencies.add("Rapiers");
                weaponArmorProficiencies.add("Shortswords");
                break;
            case "Cleric":
                toolProficienciesModifier.setVisibility(View.GONE);
                toolProficienciesTextView.setVisibility(View.GONE);
                toolProficiencyLabel.setVisibility(View.GONE);
                weaponArmorProficiencies.add("Light Armor");
                weaponArmorProficiencies.add("Medium Armor");
                weaponArmorProficiencies.add("Shields");
                weaponArmorProficiencies.add("Simple Weapons");
                break;
            case "Druid":
                toolProficienciesTextView.setText("Herbalism Kit");
                toolProficienciesModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4)) + proficiencyBonus));
                weaponArmorProficiencies.add("Light Armor");
                weaponArmorProficiencies.add("Medium Armor");
                weaponArmorProficiencies.add("Shields");
                weaponArmorProficiencies.add("Clubs");
                weaponArmorProficiencies.add("Daggers");
                weaponArmorProficiencies.add("Darts");
                weaponArmorProficiencies.add("Javelins");
                weaponArmorProficiencies.add("Maces");
                weaponArmorProficiencies.add("Quarterstaffs");
                weaponArmorProficiencies.add("Scimitars");
                weaponArmorProficiencies.add("Sickles");
                weaponArmorProficiencies.add("Slings");
                weaponArmorProficiencies.add("Spears");
                break;
            case "Fighter":
                toolProficienciesModifier.setVisibility(View.GONE);
                toolProficienciesTextView.setVisibility(View.GONE);
                toolProficiencyLabel.setVisibility(View.GONE);
                weaponArmorProficiencies.add("All Armor");
                weaponArmorProficiencies.add("Shields");
                weaponArmorProficiencies.add("Simple Weapons");
                weaponArmorProficiencies.add("Martial Weapons");
                break;
            case "Monk":
                //TODO: This involves a choice. It's actually one type of musical instrument or artisan's tools
                toolProficienciesModifier.setVisibility(View.GONE);
                toolProficienciesTextView.setVisibility(View.GONE);
                toolProficiencyLabel.setVisibility(View.GONE);
                weaponArmorProficiencies.add("Simple Weapons");
                weaponArmorProficiencies.add("Shortswords");
                break;
            case "Paladin":
                toolProficienciesModifier.setVisibility(View.GONE);
                toolProficienciesTextView.setVisibility(View.GONE);
                toolProficiencyLabel.setVisibility(View.GONE);
                weaponArmorProficiencies.add("All Armor");
                weaponArmorProficiencies.add("Shields");
                weaponArmorProficiencies.add("Simple Weapons");
                weaponArmorProficiencies.add("Martial Weapons");
                break;
            case "Ranger":
                toolProficienciesModifier.setVisibility(View.GONE);
                toolProficienciesTextView.setVisibility(View.GONE);
                toolProficiencyLabel.setVisibility(View.GONE);
                weaponArmorProficiencies.add("Light Armor");
                weaponArmorProficiencies.add("Medium Armor");
                weaponArmorProficiencies.add("Shields");
                weaponArmorProficiencies.add("Simple Weapons");
                weaponArmorProficiencies.add("Martial Weapons");
                break;
            case "Rogue":
                toolProficienciesTextView.setText("Thieves' Tools");
                toolProficienciesModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(1)) + proficiencyBonus));
                weaponArmorProficiencies.add("Light Armor");
                weaponArmorProficiencies.add("Simple Weapons");
                weaponArmorProficiencies.add("Hand Crossbows");
                weaponArmorProficiencies.add("Longswords");
                weaponArmorProficiencies.add("Rapiers");
                weaponArmorProficiencies.add("Shortswords");
                break;
            case "Sorcerer":
                toolProficienciesModifier.setVisibility(View.GONE);
                toolProficienciesTextView.setVisibility(View.GONE);
                toolProficiencyLabel.setVisibility(View.GONE);
                weaponArmorProficiencies.add("Daggers");
                weaponArmorProficiencies.add("Darts");
                weaponArmorProficiencies.add("Slings");
                weaponArmorProficiencies.add("Quarterstaffs");
                weaponArmorProficiencies.add("Light Crossbows");
                break;
            case "Warlock":
                toolProficienciesModifier.setVisibility(View.GONE);
                toolProficienciesTextView.setVisibility(View.GONE);
                toolProficiencyLabel.setVisibility(View.GONE);
                weaponArmorProficiencies.add("Light Armor");
                weaponArmorProficiencies.add("Simple Weapons");
                break;
            case "Wizard":
                toolProficienciesModifier.setVisibility(View.GONE);
                toolProficienciesTextView.setVisibility(View.GONE);
                toolProficiencyLabel.setVisibility(View.GONE);
                weaponArmorProficiencies.add("Daggers");
                weaponArmorProficiencies.add("Darts");
                weaponArmorProficiencies.add("Slings");
                weaponArmorProficiencies.add("Quarterstaffs");
                weaponArmorProficiencies.add("Light Crossbows");
                break;
        }
        weaponArmorProficienciesListView.setAdapter(adapter);
    }

    //Allows for skill proficiencies to change.
    public void changeProficiencyStatus(TextView label, TextView modifier, String skill, int statValue) {
        if(!character.getProficiencyChoices().contains(skill)){
            label.setTextColor(getResources().getColor(R.color.proficiency_blue));
            modifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
            modifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(statValue)) + proficiencyBonus));
            character.getProficiencyChoices().add(skill);
        }else{
            label.setTextColor(getResources().getColor(R.color.defaultTextColor));
            modifier.setTextColor(getResources().getColor(R.color.defaultTextColor));
            if(character.getCharacterClass().equals("Bard") && character.getLevel() >= 2){
                modifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(statValue)) + proficiencyBonus/2));
            }else{
                modifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(statValue))));
            }
            character.getProficiencyChoices().remove(skill);
        }
    }
}
