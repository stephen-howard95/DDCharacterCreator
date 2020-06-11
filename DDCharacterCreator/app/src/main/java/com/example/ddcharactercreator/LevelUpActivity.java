package com.example.ddcharactercreator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.ddcharactercreator.DetailActivity.calculateModifier;

public class LevelUpActivity extends AppCompatActivity {

    Character character = DetailActivity.character;

    @BindView(R.id.new_level) TextView newLevel;
    @BindView(R.id.new_spell_level) TextView newSpellLevel;
    //@BindView(R.id.more_spells) TextView moreSpells;
    //@BindView(R.id.more_cantrips) TextView moreCantrips;
    @BindView(R.id.proficiency_bonus_improvement) TextView proficiencyBonusImprovement;
    @BindView(R.id.ability_score_improvement_header) TextView abilityScoreImprovementHeader;
    @BindView(R.id.ability_score_improvement_1) Spinner abilityScoreImprovement1;
    @BindView(R.id.ability_score_improvement_2) Spinner abilityScoreImprovement2;
    @BindView(R.id.more_hp) EditText moreHP;

    @BindView(R.id.bonus_stats_addition_1) TextView bonusStats1;
    @BindView(R.id.bonus_stats_addition_2) TextView bonusStats2;
    @BindView(R.id.bonus_stats_addition_3) TextView bonusStats3;
    @BindView(R.id.bonus_stats_addition_4) TextView bonusStats4;
    @BindView(R.id.bonus_stats_addition_5) TextView bonusStats5;

    @BindView(R.id.character_choice_1_header) TextView choiceHeader1;
    @BindView(R.id.character_choice_2_header) TextView choiceHeader2;
    @BindView(R.id.character_choice_1) Spinner choice1;
    @BindView(R.id.character_choice_2) Spinner choice2;
    @BindView(R.id.finish_button) Button finishButton;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_up);

        ButterKnife.bind(this);

        int level = character.getLevel();
        level += 1;
        newLevel.setText(getString(R.string.new_level) + " " + level);

        // Ability Score/proficiency Bonus improvements
        switch (level){
            case 4:
            case 8:
            case 12:
            case 16:
            case 19:
                abilityScoreImprovementHeader.setVisibility(View.VISIBLE);
                abilityScoreImprovement1.setVisibility(View.VISIBLE);
                abilityScoreImprovement2.setVisibility(View.VISIBLE);
                //Set ability scores to be the spinner options
                break;
            case 5:
            case 9:
            case 13:
            case 17:
                proficiencyBonusImprovement.setVisibility(View.VISIBLE);
                proficiencyBonusImprovement.setText(getString(R.string.proficiency_bonus_improvement) + " +" + DetailActivity.proficiencyBonus);
                break;
        }
        if(character.getCharacterClass().equals("Fighter") && (character.getLevel() == 6 || character.getLevel() == 14)){
            abilityScoreImprovementHeader.setVisibility(View.VISIBLE);
            abilityScoreImprovement1.setVisibility(View.VISIBLE);
            abilityScoreImprovement2.setVisibility(View.VISIBLE);
            //Set ability scores to be the spinner options
        }

       /* //Spellcasting bonuses
        if(character.getCharacterClass().equals("Ranger") || character.getCharacterClass().equals("Paladin")){
            switch (level){
                case 2:
                    newSpellLevel.setVisibility(View.VISIBLE);
                    newSpellLevel.setText(getString(R.string.new_spell_level) + "1");
                    break;
                case 5:
                    newSpellLevel.setVisibility(View.VISIBLE);
                    newSpellLevel.setText(getString(R.string.new_spell_level) + "2");
                    break;
                case 9:
                    newSpellLevel.setVisibility(View.VISIBLE);
                    newSpellLevel.setText(getString(R.string.new_spell_level) + "3");
                    break;
                case 13:
                    newSpellLevel.setVisibility(View.VISIBLE);
                    newSpellLevel.setText(getString(R.string.new_spell_level) + "4");
                    break;
                case 17:
                    newSpellLevel.setVisibility(View.VISIBLE);
                    newSpellLevel.setText(getString(R.string.new_spell_level) + "5");
                    break;
            }
        } else{
            switch(character.getCharacterClass()){
                case "Bard":
                case "Druid":
                case "Cleric":
                case "Sorcerer":
                case "Wizard":
                    switch(level){
                        case 3:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + "2");
                            break;
                        case 5:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + "3");
                            break;
                        case 7:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + "4");
                            break;
                        case 9:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + "5");
                            break;
                        case 11:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + "6");
                            break;
                        case 13:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + "7");
                            break;
                        case 15:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + "8");
                            break;
                        case 17:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + "9");
                            break;
                    }
                    break;
                case "Warlock":
                    switch (level){
                        case 3:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.warlock_spell_slot_improvement) + "2");
                            break;
                        case 5:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.warlock_spell_slot_improvement) + "3");
                            break;
                        case 7:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.warlock_spell_slot_improvement) + "4");
                            break;
                        case 9:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.warlock_spell_slot_improvement) + "5");
                            break;
                    }
                    break;
            }
        }*/

        int finalLevel = level;
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //possibly save the character??
                if(moreHP.getText().toString() == null || moreHP.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Make sure you roll for more health", Toast.LENGTH_SHORT).show();
                } else{
                    ArrayList<Integer> newStatValues = character.getStatValues();
                    if(abilityScoreImprovementHeader.getVisibility() == View.VISIBLE){
                        improveAbilityScore(abilityScoreImprovement1.getSelectedItem().toString(), newStatValues);
                        improveAbilityScore(abilityScoreImprovement2.getSelectedItem().toString(), newStatValues);
                    }
                    Integer addToHP = Integer.parseInt(moreHP.getText().toString());
                    character.getCurrency().set(0, addToHP + (Integer) character.getCurrency().get(0)
                            + calculateModifier(character.getStatValues().get(2)));
                    returnToDetailActivity(new Character(finalLevel, character.getRace(), character.getCharacterClass(),
                            character.getAlignment(), character.getName(), newStatValues,
                            character.getProficiencyChoices(), character.getInventoryList(), character.getCurrency(),
                            character.getSubclass(), character.getSpellsKnown(), character.getSpellSlotsClicked()));
                }
            }
        });
        //Class-specific bonuses
        //TODO: Before you do this, make sure the rest of the info is saving properly, including spellsKnown, inventory list, currency, etc.
        /*switch(character.getCharacterClass()){
            case "Barbarian":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Bard":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Cleric":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Druid":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Fighter":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Monk":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Paladin":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Ranger":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Rogue":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Sorcerer":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Warlock":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
            case "Wizard":
                switch (level){
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                }
                break;
        }*/
    }

    private void returnToDetailActivity(Character character){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.CHARACTER, character);
        startActivity(intent);
    }

    private void improveAbilityScore(String abilityScore, ArrayList<Integer> abilityScoreList){
        switch(abilityScore){
            case "Strength":
                abilityScoreList.set(0, abilityScoreList.get(0) +1);
                break;
            case "Dexterity":
                abilityScoreList.set(1, abilityScoreList.get(1) +1);
                break;
            case "Constitution":
                abilityScoreList.set(2, abilityScoreList.get(2) +1);
                break;
            case "Intelligence":
                abilityScoreList.set(3, abilityScoreList.get(3) +1);
                break;
            case "Wisdom":
                abilityScoreList.set(4, abilityScoreList.get(4) +1);
                break;
            case "Charisma":
                abilityScoreList.set(5, abilityScoreList.get(5) +1);
                break;
        }
    }
}
