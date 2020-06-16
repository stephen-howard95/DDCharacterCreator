package com.example.ddcharactercreator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.List;
import static com.example.ddcharactercreator.DetailActivity.calculateModifier;
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
        TextView animalHandlingLabel = findViewById(R.id.animal_handling_label);
        TextView arcanaLabel = findViewById(R.id.arcana_label);
        TextView athleticsLabel = findViewById(R.id.athletics_label);
        TextView deceptionLabel = findViewById(R.id.deception_label);
        TextView historyLabel = findViewById(R.id.history_label);
        TextView insightLabel = findViewById(R.id.insight_label);
        TextView intimidationLabel = findViewById(R.id.intimidation_label);
        TextView investigationLabel = findViewById(R.id.investigation_label);
        TextView medicineLabel = findViewById(R.id.medicine_label);
        TextView natureLabel = findViewById(R.id.nature_label);
        TextView perceptionLabel = findViewById(R.id.perception_label);
        TextView performanceLabel = findViewById(R.id.performance_label);
        TextView persuasionLabel = findViewById(R.id.persuasion_label);
        TextView religionLabel = findViewById(R.id.religion_label);
        TextView sleightOfHandLabel = findViewById(R.id.sleight_of_hand_label);
        TextView stealthLabel = findViewById(R.id.stealth_label);
        TextView survivalLabel = findViewById(R.id.survival_label);

        acrobaticsModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(1))));
        animalHandlingModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4))));
        arcanaModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3))));
        athleticsModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(0))));
        deceptionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5))));
        historyModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3))));
        insightModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5))));
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
                perceptionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4)) + proficiencyBonus));
                perceptionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                perceptionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Half-Elf":
                perceptionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4)) + proficiencyBonus));
                perceptionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                perceptionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                insightModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5)) + proficiencyBonus));
                insightLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                insightModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Half-Orc":
                intimidationModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5)) + proficiencyBonus));
                intimidationLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intimidationModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
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
                    insightModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5)) + proficiencyBonus));
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
    }
}
