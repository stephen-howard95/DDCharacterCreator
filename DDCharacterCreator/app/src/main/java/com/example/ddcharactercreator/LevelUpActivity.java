package com.example.ddcharactercreator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.ddcharactercreator.DetailActivity.calculateModifier;

public class LevelUpActivity extends AppCompatActivity {

    Character character = DetailActivity.character;

    @BindView(R.id.new_level) TextView newLevel;
    @BindView(R.id.new_spell_level) TextView newSpellLevel;
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

    private int hitPointMax;

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
        ArrayList<String> abilityScoreList1 = new ArrayList<>();
        ArrayList<String> abilityScoreList2 = new ArrayList<>();
        abilityScoreList1.add("Strength");
        abilityScoreList1.add("Dexterity");
        abilityScoreList1.add("Constitution");
        abilityScoreList1.add("Intelligence");
        abilityScoreList1.add("Wisdom");
        abilityScoreList1.add("Charisma");
        abilityScoreList2.add("Strength");
        abilityScoreList2.add("Dexterity");
        abilityScoreList2.add("Constitution");
        abilityScoreList2.add("Intelligence");
        abilityScoreList2.add("Wisdom");
        abilityScoreList2.add("Charisma");
        ArrayAdapter<String> abilityScoreAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, abilityScoreList1);
        ArrayAdapter<String> abilityScoreAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, abilityScoreList2);
        switch (level){
            case 4:
            case 8:
            case 12:
            case 16:
            case 19:
                abilityScoreImprovementHeader.setVisibility(View.VISIBLE);
                abilityScoreImprovement1.setVisibility(View.VISIBLE);
                abilityScoreImprovement2.setVisibility(View.VISIBLE);
                break;
            case 5:
            case 9:
            case 13:
            case 17:
                proficiencyBonusImprovement.setVisibility(View.VISIBLE);
                proficiencyBonusImprovement.setText(getString(R.string.proficiency_bonus_improvement) + " +" + String.valueOf(DetailActivity.proficiencyBonus + 1));
                break;
        }
        if(character.getCharacterClass().equals("Fighter") && (level == 6 || level == 14)){
            abilityScoreImprovementHeader.setVisibility(View.VISIBLE);
            abilityScoreImprovement1.setVisibility(View.VISIBLE);
            abilityScoreImprovement2.setVisibility(View.VISIBLE);
        }
        //Prevents a stat from going above 20
        if(character.getStatValues().get(0) == 20){
            abilityScoreAdapter1.remove("Strength");
            abilityScoreAdapter2.remove("Strength");
        } else if(character.getStatValues().get(0) == 19){
            abilityScoreAdapter2.remove("Strength");
        }
        if(character.getStatValues().get(1) == 20){
            abilityScoreAdapter1.remove("Dexterity");
            abilityScoreAdapter2.remove("Dexterity");
        }else if(character.getStatValues().get(1) == 19){
            abilityScoreAdapter2.remove("Dexterity");
        }
        if(character.getStatValues().get(2) == 20){
            abilityScoreAdapter1.remove("Constitution");
            abilityScoreAdapter2.remove("Constitution");
        }else if(character.getStatValues().get(2) == 19){
            abilityScoreAdapter2.remove("Constitution");
        }
        if(character.getStatValues().get(3) == 20){
            abilityScoreAdapter1.remove("Intelligence");
            abilityScoreAdapter2.remove("Intelligence");
        }else if(character.getStatValues().get(3) == 19){
            abilityScoreAdapter2.remove("Intelligence");
        }
        if(character.getStatValues().get(4) == 20){
            abilityScoreAdapter1.remove("Wisdom");
            abilityScoreAdapter2.remove("Wisdom");
        }else if(character.getStatValues().get(4) == 19){
            abilityScoreAdapter2.remove("Wisdom");
        }
        if(character.getStatValues().get(5) == 20){
            abilityScoreAdapter1.remove("Charisma");
            abilityScoreAdapter2.remove("Charisma");
        }else if(character.getStatValues().get(5) == 19){
            abilityScoreAdapter2.remove("Charisma");
        }
        abilityScoreImprovement1.setAdapter(abilityScoreAdapter1);
        abilityScoreImprovement2.setAdapter(abilityScoreAdapter2);

        // Health restrictions
        switch (character.getCharacterClass()){
            case "Barbarian":
                moreHP.setHint(getString(R.string.barbarian_hit_die));
                hitPointMax = 12;
                break;
            case "Bard":
                moreHP.setHint(getString(R.string.bard_hit_die));
                hitPointMax = 8;
                break;
            case "Cleric":
                moreHP.setHint(getString(R.string.cleric_hit_die));
                hitPointMax = 8;
                break;
            case "Druid":
                moreHP.setHint(getString(R.string.druid_hit_die));
                hitPointMax = 8;
                break;
            case "Fighter":
                moreHP.setHint(getString(R.string.fighter_hit_die));
                hitPointMax = 10;
                break;
            case "Monk":
                moreHP.setHint(getString(R.string.monk_hit_die));
                hitPointMax = 8;
                break;
            case "Paladin":
                moreHP.setHint(getString(R.string.paladin_hit_die));
                hitPointMax = 10;
                break;
            case "Ranger":
                moreHP.setHint(getString(R.string.ranger_hit_die));
                hitPointMax = 10;
                break;
            case "Rogue":
                moreHP.setHint(getString(R.string.rogue_hit_die));
                hitPointMax = 8;
                break;
            case "Sorcerer":
                moreHP.setHint(getString(R.string.sorcerer_hit_die));
                hitPointMax = 6;
                break;
            case "Warlock":
                moreHP.setHint(getString(R.string.warlock_hit_die));
                hitPointMax = 8;
                break;
            case "Wizard":
                moreHP.setHint(getString(R.string.wizard_hit_die));
                hitPointMax = 6;
                break;
        }
        moreHP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!moreHP.getText().toString().equals("") && Integer.parseInt(moreHP.getText().toString()) > hitPointMax){
                    moreHP.setText(String.valueOf(hitPointMax));
                }
            }
        });

        //Spellcasting bonuses
        if(character.getCharacterClass().equals("Ranger") || character.getCharacterClass().equals("Paladin")){
            switch (level){
                case 2:
                    newSpellLevel.setVisibility(View.VISIBLE);
                    newSpellLevel.setText(getString(R.string.new_spell_level) + " 1");
                    break;
                case 5:
                    newSpellLevel.setVisibility(View.VISIBLE);
                    newSpellLevel.setText(getString(R.string.new_spell_level) + " 2");
                    break;
                case 9:
                    newSpellLevel.setVisibility(View.VISIBLE);
                    newSpellLevel.setText(getString(R.string.new_spell_level) + " 3");
                    break;
                case 13:
                    newSpellLevel.setVisibility(View.VISIBLE);
                    newSpellLevel.setText(getString(R.string.new_spell_level) + " 4");
                    break;
                case 17:
                    newSpellLevel.setVisibility(View.VISIBLE);
                    newSpellLevel.setText(getString(R.string.new_spell_level) + " 5");
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
                            newSpellLevel.setText(getString(R.string.new_spell_level) + " 2");
                            break;
                        case 5:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + " 3");
                            break;
                        case 7:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + " 4");
                            break;
                        case 9:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + " 5");
                            break;
                        case 11:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + " 6");
                            break;
                        case 13:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + " 7");
                            break;
                        case 15:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + " 8");
                            break;
                        case 17:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.new_spell_level) + " 9");
                            break;
                    }
                    break;
                case "Warlock":
                    switch (level){
                        case 3:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.warlock_spell_slot_improvement) + " 2");
                            break;
                        case 5:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.warlock_spell_slot_improvement) + " 3");
                            break;
                        case 7:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.warlock_spell_slot_improvement) + " 4");
                            break;
                        case 9:
                            newSpellLevel.setVisibility(View.VISIBLE);
                            newSpellLevel.setText(getString(R.string.warlock_spell_slot_improvement) + " 5");
                            break;
                    }
                    break;
            }
        }

        int finalLevel = level;
        int previousConModifier = calculateModifier(character.getStatValues().get(2));
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //possibly save the character??
                if(moreHP.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Make sure you roll for more health", Toast.LENGTH_SHORT).show();
                }else if(choice1.getVisibility() == View.VISIBLE && choice2.getVisibility() == View.VISIBLE && choice1.getSelectedItem().toString().equals(choice2.getSelectedItem().toString())){
                    Toast.makeText(getApplicationContext(), "Make sure you choose 2 different options", Toast.LENGTH_SHORT).show();
                }else {
                    ArrayList<Integer> newStatValues = character.getStatValues();
                    if(abilityScoreImprovementHeader.getVisibility() == View.VISIBLE){
                        improveAbilityScore(abilityScoreImprovement1.getSelectedItem().toString(), newStatValues);
                        improveAbilityScore(abilityScoreImprovement2.getSelectedItem().toString(), newStatValues);
                    }
                    Integer addToHP = Integer.parseInt(moreHP.getText().toString());
                    if(character.getSubclass().equals("Draconic Bloodline") || character.getSubclass().equals("Stone Sorcery")){
                        addToHP += 1;
                    }
                    if(calculateModifier(character.getStatValues().get(2)) > previousConModifier){
                        addToHP += finalLevel;
                    }
                    character.getCurrency().set(6, addToHP + character.getCurrency().get(6)
                            + calculateModifier(character.getStatValues().get(2)));
                    String subclass = character.getSubclass();
                    switch(character.getCharacterClass()){
                        case "Barbarian":
                            if((finalLevel == 3 || finalLevel == 6 || finalLevel == 14) && character.getSubclass().equals("Path of the Totem Warrior")){
                                switch(choice1.getSelectedItem().toString()){
                                    case "Bear Totem Spirit":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_bear));
                                        break;
                                    case "Eagle Totem Spirit":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_eagle));
                                        break;
                                    case "Wolf Totem Spirit":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_wolf));
                                        break;
                                    case "Tiger Totem Spirit":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_tiger));
                                        break;
                                    case "Aspect of the Bear":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.aspect_of_the_bear));
                                        break;
                                    case "Aspect of the Eagle":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.aspect_of_the_eagle));
                                        break;
                                    case "Aspect of the Wolf":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.aspect_of_the_wolf));
                                        break;
                                    case "Aspect of the Tiger":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.aspect_of_the_tiger));
                                        break;
                                    case "Totemic Attunement: Bear":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totemic_attunement_bear));
                                        break;
                                    case "Totemic Attunement: Eagle":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totemic_attunement_eagle));
                                        break;
                                    case "Totemic Attunement: Wolf":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totemic_attunement_wolf));
                                        break;
                                    case "Totemic Attunement: Tiger":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totemic_attunement_tiger));
                                        break;
                                }
                            }
                            break;
                        case "Bard":
                            if(finalLevel == 3 || finalLevel == 10){
                                character.getProficiencyChoices().remove(choice1.getSelectedItem().toString());
                                character.getProficiencyChoices().remove(choice2.getSelectedItem().toString());
                                character.getClassBasedBonusStats2().add(choice1.getSelectedItem().toString());
                                character.getClassBasedBonusStats2().add(choice2.getSelectedItem().toString());
                            }
                            break;
                        case "Druid":
                            if(finalLevel == 3 && character.getSubclass().equals("Circle of the Land")){
                                subclass = subclass + ", " + choice1.getSelectedItem().toString();
                            }
                            break;
                        case "Ranger":
                            if(finalLevel == 6){
                                character.getClassBasedBonusStats2().add(choice1.getSelectedItem().toString());
                                character.getClassBasedBonusStats2().add(choice2.getSelectedItem().toString());
                            }
                            if(finalLevel == 10){
                                character.getClassBasedBonusStats2().add(choice1.getSelectedItem().toString());
                            }
                            if(finalLevel == 14){
                                character.getClassBasedBonusStats2().add(choice2.getSelectedItem().toString());
                            }
                        case "Fighter":
                            if(character.getSubclass().equals("Battle Master") && (finalLevel == 3 || finalLevel == 7 || finalLevel == 10 || finalLevel == 15)){
                                switch(choice1.getSelectedItem().toString()){
                                    case "Commander's Strike":
                                        character.getClassBasedBonusStats2().add(getString(R.string.commanders_strike));
                                        break;
                                    case "Disarming Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.disarming_attack));
                                        break;
                                    case "Distracting Strike":
                                        character.getClassBasedBonusStats2().add(getString(R.string.distracting_strike));
                                        break;
                                    case "Evasive Footwork":
                                        character.getClassBasedBonusStats2().add(getString(R.string.evasive_footwork));
                                        break;
                                    case "Feinting Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.feinting_attack));
                                        break;
                                    case "Goading Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.goading_attack));
                                        break;
                                    case "Lunging Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.lunging_attack));
                                        break;
                                    case "Maneuvering Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.maneuvering_attack));
                                        break;
                                    case "Menacing Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.menacing_attack));
                                        break;
                                    case "Parry":
                                        character.getClassBasedBonusStats2().add(getString(R.string.parry));
                                        break;
                                    case "Precision Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.precision_attack));
                                        break;
                                    case "Pushing Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.pushing_attack));
                                        break;
                                    case "Rally":
                                        character.getClassBasedBonusStats2().add(getString(R.string.rally));
                                        break;
                                    case "Riposte":
                                        character.getClassBasedBonusStats2().add(getString(R.string.riposte));
                                        break;
                                    case "Sweeping Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.sweeping_attack));
                                        break;
                                    case "Trip Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.trip_attack));
                                        break;
                                }
                                switch(choice2.getSelectedItem().toString()){
                                    case "Commander's Strike":
                                        character.getClassBasedBonusStats2().add(getString(R.string.commanders_strike));
                                        break;
                                    case "Disarming Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.disarming_attack));
                                        break;
                                    case "Distracting Strike":
                                        character.getClassBasedBonusStats2().add(getString(R.string.distracting_strike));
                                        break;
                                    case "Evasive Footwork":
                                        character.getClassBasedBonusStats2().add(getString(R.string.evasive_footwork));
                                        break;
                                    case "Feinting Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.feinting_attack));
                                        break;
                                    case "Goading Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.goading_attack));
                                        break;
                                    case "Lunging Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.lunging_attack));
                                        break;
                                    case "Maneuvering Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.maneuvering_attack));
                                        break;
                                    case "Menacing Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.menacing_attack));
                                        break;
                                    case "Parry":
                                        character.getClassBasedBonusStats2().add(getString(R.string.parry));
                                        break;
                                    case "Precision Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.precision_attack));
                                        break;
                                    case "Pushing Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.pushing_attack));
                                        break;
                                    case "Rally":
                                        character.getClassBasedBonusStats2().add(getString(R.string.rally));
                                        break;
                                    case "Riposte":
                                        character.getClassBasedBonusStats2().add(getString(R.string.riposte));
                                        break;
                                    case "Sweeping Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.sweeping_attack));
                                        break;
                                    case "Trip Attack":
                                        character.getClassBasedBonusStats2().add(getString(R.string.trip_attack));
                                        break;
                                }
                            }
                        case "Paladin":
                            if(((character.getCharacterClass().equals("Paladin") || character.getCharacterClass().equals("Ranger")) && finalLevel == 2)
                                    || (character.getSubclass().equals("Champion") && finalLevel == 10)){
                                switch(choice1.getSelectedItem().toString()){
                                    case "Archery":
                                        character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.archery));
                                        break;
                                    case "Defense":
                                        character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.defense));
                                        break;
                                    case "Dueling":
                                        character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.dueling));
                                        break;
                                    case "Great Weapon Fighting":
                                        character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.great_weapon_fighting));
                                        break;
                                    case "Protection":
                                        character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.protection));
                                        break;
                                    case "Two-Weapon Fighting":
                                        character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.two_weapon_fighting));
                                        break;
                                }
                            }
                            if(character.getSubclass().equals("Hunter") && (finalLevel == 3 || finalLevel == 7 || finalLevel == 11 || finalLevel == 15)){
                                switch(choice1.getSelectedItem().toString()){
                                    case "Colossus Slayer":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.colossus_slayer));
                                        break;
                                    case "Giant Killer":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.giant_killer));
                                        break;
                                    case "Horde Breaker":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.horde_breaker));
                                        break;
                                    case "Escape the Horde":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.escape_the_horde));
                                        break;
                                    case "Multiattack Defense":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.multiattack_defense));
                                        break;
                                    case "Steel Will":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.steel_will));
                                        break;
                                    case "Volley":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.volley));
                                        break;
                                    case "Whirlwind Attack":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.whirlwind_attack));
                                        break;
                                    case "Evasion":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.evasion));
                                        break;
                                    case "Stand Against the Tide":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.stand_against_the_tide));
                                        break;
                                    case "Uncanny Dodge":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.uncanny_dodge));
                                        break;
                                }
                            }
                            break;
                        case "Rogue":
                            if(finalLevel == 2 || finalLevel == 6){
                                character.getProficiencyChoices().remove(choice1.getSelectedItem().toString());
                                character.getProficiencyChoices().remove(choice2.getSelectedItem().toString());
                                character.getClassBasedBonusStats2().add(choice1.getSelectedItem().toString());
                                character.getClassBasedBonusStats2().add(choice2.getSelectedItem().toString());
                            }
                            break;
                        case "Sorcerer":
                            if(finalLevel == 3){
                                switch(choice1.getSelectedItem().toString()){
                                    case "Careful Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.careful_spell));
                                        break;
                                    case "Distant Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.distant_spell));
                                        break;
                                    case "Empowered Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.empowered_spell));
                                        break;
                                    case "Extended Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.extended_spell));
                                        break;
                                    case "Heightened Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.heightened_spell));
                                        break;
                                    case "Quickened Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.quickened_spell));
                                        break;
                                    case "Subtle Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.subtle_spell));
                                        break;
                                    case "Twinned Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.twinned_spell));
                                        break;
                                }
                                switch(choice2.getSelectedItem().toString()){
                                    case "Careful Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.careful_spell));
                                        break;
                                    case "Distant Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.distant_spell));
                                        break;
                                    case "Empowered Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.empowered_spell));
                                        break;
                                    case "Extended Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.extended_spell));
                                        break;
                                    case "Heightened Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.heightened_spell));
                                        break;
                                    case "Quickened Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.quickened_spell));
                                        break;
                                    case "Subtle Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.subtle_spell));
                                        break;
                                    case "Twinned Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.twinned_spell));
                                        break;
                                }
                            }
                            if(finalLevel == 10 || finalLevel == 17){
                                switch(choice1.getSelectedItem().toString()){
                                    case "Careful Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.careful_spell));
                                        break;
                                    case "Distant Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.distant_spell));
                                        break;
                                    case "Empowered Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.empowered_spell));
                                        break;
                                    case "Extended Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.extended_spell));
                                        break;
                                    case "Heightened Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.heightened_spell));
                                        break;
                                    case "Quickened Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.quickened_spell));
                                        break;
                                    case "Subtle Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.subtle_spell));
                                        break;
                                    case "Twinned Spell":
                                        character.getClassBasedBonusStats2().add(getString(R.string.twinned_spell));
                                        break;
                                }
                            }
                            break;
                        case "Warlock":
                            if(finalLevel == 2){
                                switch(choice1.getSelectedItem().toString()){
                                    case "Agonizing Blast":
                                        character.getClassBasedBonusStats2().add(getString(R.string.agonizing_blast));
                                        break;
                                    case "Beguiling Influence":
                                        character.getClassBasedBonusStats2().add(getString(R.string.beguiling_influence));
                                        break;
                                    case "Devil's Sight":
                                        character.getClassBasedBonusStats2().add(getString(R.string.devils_sight));
                                        break;
                                    case "Eldritch Spear":
                                        character.getClassBasedBonusStats2().add(getString(R.string.eldritch_spear));
                                        break;
                                    case "Eyes of the Rune Keeper":
                                        character.getClassBasedBonusStats2().add(getString(R.string.eyes_of_the_rune_keeper));
                                        break;
                                    case "Gaze of Two Minds":
                                        character.getClassBasedBonusStats2().add(getString(R.string.gaze_of_two_minds));
                                        break;
                                    case "Repelling Blast":
                                        character.getClassBasedBonusStats2().add(getString(R.string.repelling_blast));
                                        break;
                                }
                                switch(choice2.getSelectedItem().toString()){
                                    case "Agonizing Blast":
                                        character.getClassBasedBonusStats2().add(getString(R.string.agonizing_blast));
                                        break;
                                    case "Beguiling Influence":
                                        character.getClassBasedBonusStats2().add(getString(R.string.beguiling_influence));
                                        break;
                                    case "Devil's Sight":
                                        character.getClassBasedBonusStats2().add(getString(R.string.devils_sight));
                                        break;
                                    case "Eldritch Spear":
                                        character.getClassBasedBonusStats2().add(getString(R.string.eldritch_spear));
                                        break;
                                    case "Eyes of the Rune Keeper":
                                        character.getClassBasedBonusStats2().add(getString(R.string.eyes_of_the_rune_keeper));
                                        break;
                                    case "Gaze of Two Minds":
                                        character.getClassBasedBonusStats2().add(getString(R.string.gaze_of_two_minds));
                                        break;
                                    case "Repelling Blast":
                                        character.getClassBasedBonusStats2().add(getString(R.string.repelling_blast));
                                        break;
                                }
                            }
                            if(finalLevel == 3){
                                switch(choice1.getSelectedItem().toString()){
                                    case "Pact of the Blade":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.pact_of_the_blade));
                                        break;
                                    case "Pact of the Chain":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.pact_of_the_chain));
                                        break;
                                    case "Pact of the Talisman":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.pact_of_the_talisman));
                                        break;
                                    case "Pact of the Tome":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.pact_of_the_tome));
                                        break;
                                }
                            }
                            if(finalLevel == 5 || finalLevel == 7 || finalLevel == 9 || finalLevel == 12 || finalLevel == 15 || finalLevel == 18){
                                switch(choice1.getSelectedItem().toString()){
                                    case "Agonizing Blast":
                                        character.getClassBasedBonusStats2().add(getString(R.string.agonizing_blast));
                                        break;
                                    case "Beguiling Influence":
                                        character.getClassBasedBonusStats2().add(getString(R.string.beguiling_influence));
                                        break;
                                    case "Book of Ancient Secrets":
                                        character.getClassBasedBonusStats2().add(getString(R.string.book_of_ancient_secrets));
                                        break;
                                    case "Devil's Sight":
                                        character.getClassBasedBonusStats2().add(getString(R.string.devils_sight));
                                        break;
                                    case "Eldritch Spear":
                                        character.getClassBasedBonusStats2().add(getString(R.string.eldritch_spear));
                                        break;
                                    case "Eye of Yog-Sothoth":
                                        character.getClassBasedBonusStats2().add(getString(R.string.eye_of_yog_sothoth));
                                        break;
                                    case "Eyes of the Rune Keeper":
                                        character.getClassBasedBonusStats2().add(getString(R.string.eyes_of_the_rune_keeper));
                                        break;
                                    case "Gaze of Two Minds":
                                        character.getClassBasedBonusStats2().add(getString(R.string.gaze_of_two_minds));
                                        break;
                                    case "Lifedrinker":
                                        character.getClassBasedBonusStats2().add(getString(R.string.lifedrinker));
                                        break;
                                    case "One With Shadows":
                                        character.getClassBasedBonusStats2().add(getString(R.string.one_with_shadows));
                                        break;
                                    case "Reality Tear":
                                        character.getClassBasedBonusStats2().add(getString(R.string.reality_tear));
                                        break;
                                    case "Repelling Blast":
                                        character.getClassBasedBonusStats2().add(getString(R.string.repelling_blast));
                                        break;
                                    case "Thirsting Blade":
                                        character.getClassBasedBonusStats2().add(getString(R.string.thirsting_blade));
                                        break;
                                    case "Trickster's Step":
                                        character.getClassBasedBonusStats2().add(getString(R.string.tricksters_step));
                                        break;
                                    case "Unbreakable Tether":
                                        character.getClassBasedBonusStats2().add(getString(R.string.unbreakable_tether));
                                        break;
                                    case "Voice of the Chain Master":
                                        character.getClassBasedBonusStats2().add(getString(R.string.voice_of_the_chain_master));
                                        break;
                                    case "Witch Sight":
                                        character.getClassBasedBonusStats2().add(getString(R.string.witch_sight));
                                        break;
                                }
                            }
                            if(finalLevel == 11 || finalLevel == 13 || finalLevel == 17){
                                SpellDatabase mDb = SpellDatabase.getInstance(getApplicationContext());
                                List<Spell> spellsList = mDb.spellDao().loadAllSpells();
                                Spell spell = new Spell();
                                for(int i=0; i<spellsList.size(); i++){
                                    if(spellsList.get(i).getSpellName().equals(choice1.getSelectedItem().toString())){
                                        spell = spellsList.get(i);
                                        break;
                                    }
                                }
                                character.getSpellsKnown().add(spell);
                            } else if(finalLevel == 15){
                                SpellDatabase mDb = SpellDatabase.getInstance(getApplicationContext());
                                List<Spell> spellsList = mDb.spellDao().loadAllSpells();
                                Spell spell = new Spell();
                                for(int i=0; i<spellsList.size(); i++){
                                    if(spellsList.get(i).getSpellName().equals(choice2.getSelectedItem().toString())){
                                        spell = spellsList.get(i);
                                        break;
                                    }
                                }
                                character.getSpellsKnown().add(spell);
                            }
                            break;
                        case "Wizard":
                            if(finalLevel == 20){
                                getSubclassSpells(choice1.getSelectedItem().toString());
                                getSubclassSpells(choice2.getSelectedItem().toString());
                            }
                            break;
                    }
                   /* if(character.getCharacterClass().equals("Wizard") && choice1.getVisibility() == View.VISIBLE && choice2.getVisibility() == View.VISIBLE){
                        SpellDatabase mDb = SpellDatabase.getInstance(getApplicationContext());
                        List<Spell> spellsList = mDb.spellDao().loadAllSpells();
                        Spell spell1 = spellsList.get(The spell who's name is the one chosen in choice1);
                        Spell spell2 = spellsList.get(The spell who's name is the one chosen in choice2);

                        //List both spells in Character Fragment the chosen spells. At level 18, they can be cast
                        // as cantrips, at level 20, they can be cast as cantrips once per long rest.
                    }*/
                    returnToDetailActivity(new Character(finalLevel, character.getRace(), character.getCharacterClass(),
                            character.getAlignment(), character.getName(), newStatValues,
                            character.getProficiencyChoices(), character.getInventoryList(), character.getCurrency(),
                            subclass, character.getSpellsKnown(), character.getSpellSlotsClicked(),
                            character.getRaceAndClassBonusStats(), character.getClassBasedBonusStats2()));
                }
            }
        });
        //Class-specific bonuses
        switch(character.getCharacterClass()){
            case "Barbarian":
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.reckless_attack);
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(R.string.danger_sense);
                        character.getRaceAndClassBonusStats().add(getString(R.string.reckless_attack));
                        character.getRaceAndClassBonusStats().add(getString(R.string.danger_sense));
                        break;
                    case 3:
                        ArrayList<String> totemChoices = new ArrayList<>();
                        ArrayAdapter<String> totemAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, totemChoices);
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You have an extra usage of Rage");
                        switch(character.getSubclass()){
                            case "Path of the Berserker":
                                bonusStats2.setVisibility(View.VISIBLE);
                                bonusStats2.setText(getString(R.string.frenzy));
                                character.getRaceAndClassBonusStats().add(getString(R.string.frenzy));
                                break;
                            case "Path of the Totem Warrior":
                                bonusStats2.setVisibility(View.VISIBLE);
                                bonusStats2.setText("You can now cast Beast Sense and Speak with Animals as a ritual");
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText("Choose a Totem Spirit");
                                choice1.setVisibility(View.VISIBLE);
                                totemChoices.add("Bear Totem Spirit");
                                totemChoices.add("Eagle Totem Spirit");
                                totemChoices.add("Wolf Totem Spirit");
                                totemChoices.add("Tiger Totem Spirit");
                                choice1.setAdapter(totemAdapter);
                                break;
                        }
                        break;
                    case 4:
                        break;
                    case 5:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.extra_attack);
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack));
                        break;
                    case 6:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You have an extra usage of Rage");
                        switch(character.getSubclass()){
                            case "Path of the Berserker":
                                bonusStats2.setVisibility(View.VISIBLE);
                                bonusStats2.setText(getString(R.string.mindless_rage));
                                character.getRaceAndClassBonusStats().add(getString(R.string.mindless_rage));
                                break;
                            case "Path of the Totem Warrior":
                                ArrayList<String> totemPathChoices = new ArrayList<>();
                                ArrayAdapter<String> totemPathAdapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_spinner_dropdown_item, totemPathChoices);
                                totemPathChoices.add("Aspect of the Bear");
                                totemPathChoices.add("Aspect of the Eagle");
                                totemPathChoices.add("Aspect of the Wolf");
                                totemPathChoices.add("Aspect of the Tiger");
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText(getString(R.string.totem_spirit));
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(totemPathAdapter);
                                break;
                        }
                        break;
                    case 7:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.feral_instinct);
                        character.getRaceAndClassBonusStats().add(getString(R.string.feral_instinct));
                        break;
                    case 8:
                        break;
                    case 9:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.brutal_critical);
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText("Your rage now deals extra damage");
                        character.getRaceAndClassBonusStats().add(getString(R.string.brutal_critical));
                        break;
                    case 10:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Path of the Berserker":
                                bonusStats1.setText(getString(R.string.intimidating_presence));
                                character.getRaceAndClassBonusStats().add(getString(R.string.intimidating_presence));
                                break;
                            case "Path of the Totem Warrior":
                                bonusStats1.setText(getString(R.string.spirit_walker));
                                character.getRaceAndClassBonusStats().add(getString(R.string.spirit_walker));
                                break;
                        }
                        break;
                    case 11:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.relentless_rage);
                        character.getRaceAndClassBonusStats().add(getString(R.string.relentless_rage));
                        break;
                    case 12:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You have an extra usage of Rage");
                        break;
                    case 13:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Brutal critical is now 2 extra damage dice");
                        break;
                    case 14:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Path of the Berserker":
                                bonusStats1.setText(getString(R.string.retaliation));
                                character.getRaceAndClassBonusStats().add(getString(R.string.retaliation));
                                break;
                            case "Path of the Totem Warrior":
                                ArrayList<String> totemPathChoices = new ArrayList<>();
                                ArrayAdapter<String> totemPathAdapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_spinner_dropdown_item, totemPathChoices);
                                totemPathChoices.add("Totemic Attunement: Bear");
                                totemPathChoices.add("Totemic Attunement: Eagle");
                                totemPathChoices.add("Totemic Attunement: Wolf");
                                totemPathChoices.add("Totemic Attunement: Tiger");
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText(getString(R.string.totem_spirit));
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(totemPathAdapter);
                                break;
                        }
                        break;
                    case 15:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.persistent_rage);
                        character.getRaceAndClassBonusStats().add(getString(R.string.persistent_rage));
                        break;
                    case 16:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your rage now deals extra damage");
                        break;
                    case 17:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You have an extra usage of Rage");
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText("Brutal critical is now 3 extra damage dice");
                        break;
                    case 18:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.indomitable_might);
                        character.getRaceAndClassBonusStats().add(getString(R.string.indomitable_might));
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You can now use rage an unlimited amount of times.");
                        character.getStatValues().set(0, character.getStatValues().get(0) + 4);
                        character.getStatValues().set(2, character.getStatValues().get(2) + 4);
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(R.string.primal_champion);
                        character.getRaceAndClassBonusStats().add(getString(R.string.primal_champion));
                        break;
                }
                break;
            case "Bard":
                ArrayList<String> bardExpertiseChoices = new ArrayList<>();
                ArrayAdapter<String> bardExpertiseAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, bardExpertiseChoices);
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.song_of_rest);
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(R.string.jack_of_all_trades);
                        character.getRaceAndClassBonusStats().add(getString(R.string.song_of_rest));
                        character.getRaceAndClassBonusStats().add(getString(R.string.jack_of_all_trades));
                        break;
                    case 3:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "College of Lore":
                                bonusStats1.setText(getString(R.string.cutting_words));
                                character.getRaceAndClassBonusStats().add(getString(R.string.cutting_words));
                                bonusStats2.setText("You now have proficiency in 3 more skills of your choice");
                                break;
                            case "College of Valor":
                                bonusStats1.setText(getString(R.string.combat_inspiration));
                                character.getRaceAndClassBonusStats().add(getString(R.string.combat_inspiration));
                                bonusStats2.setText("You now have proficiency with Medium Armor, Shields, and Martial Weapons");
                                break;
                        }
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose two of your proficiencies. Your proficiency bonus will be doubled for any ability checks you make with that skill");
                        choice1.setVisibility(View.VISIBLE);
                        choice2.setVisibility(View.VISIBLE);
                        bardExpertiseChoices.addAll(character.getProficiencyChoices());
                        choice1.setAdapter(bardExpertiseAdapter);
                        choice2.setAdapter(bardExpertiseAdapter);
                        break;
                    case 4:
                        break;
                    case 5:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.font_of_inspiration);
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText("Your Bardic Inspiration die is now a d8");
                        character.getRaceAndClassBonusStats().add(getString(R.string.font_of_inspiration));
                        break;
                    case 6:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.countercharm);
                        character.getRaceAndClassBonusStats().add(getString(R.string.countercharm));
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "College of Lore":
                                bonusStats2.setText(getString(R.string.magical_secrets));
                                break;
                            case "College of Valor":
                                bonusStats2.setText(getString(R.string.extra_attack));
                                character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack));
                                break;
                        }
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Song of Rest die is now a d8");
                        break;
                    case 10:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Bardic Inspiration die is now a d10");
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.magical_secrets));
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose two of your proficiencies. Your proficiency bonus will be doubled for any ability checks you make with that skill");
                        choice1.setVisibility(View.VISIBLE);
                        choice2.setVisibility(View.VISIBLE);
                        bardExpertiseChoices.addAll(character.getProficiencyChoices());
                        choice1.setAdapter(bardExpertiseAdapter);
                        choice2.setAdapter(bardExpertiseAdapter);
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Song of Rest die is now a d10");
                        break;
                    case 14:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.magical_secrets));
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "College of Lore":
                                bonusStats2.setText(getString(R.string.peerless_skill));
                                character.getRaceAndClassBonusStats().add(getString(R.string.peerless_skill));
                                break;
                            case "College of Valor":
                                bonusStats2.setText(getString(R.string.battle_magic));
                                character.getRaceAndClassBonusStats().add(getString(R.string.battle_magic));
                                break;
                        }
                        break;
                    case 15:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Bardic Inspiration die is now a d12");
                        break;
                    case 16:
                        break;
                    case 17:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Song of Rest die is now a d12");
                        break;
                    case 18:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.magical_secrets));
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.superior_inspiration);
                        character.getRaceAndClassBonusStats().add(getString(R.string.superior_inspiration));
                        break;
                }
                break;
            case "Cleric":
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.channel_divinity));
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.channel_divinity_turn_undead));
                        character.getRaceAndClassBonusStats().add(getString(R.string.channel_divinity));
                        bonusStats3.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Knowledge":
                                bonusStats3.setText(getString(R.string.channel_divinity_knowledge_of_the_ages));
                                break;
                            case "Life":
                                bonusStats3.setText(getString(R.string.channel_divinity_preserve_life));
                                break;
                            case "Light":
                                bonusStats3.setText(getString(R.string.channel_divinity_radiance_of_the_dawn));
                                break;
                            case "Nature":
                                bonusStats3.setText(getString(R.string.channel_divinity_charm_animals_and_plants));
                                break;
                            case "Tempest":
                                bonusStats3.setText(getString(R.string.channel_divinity_destructive_wrath));
                                break;
                            case "Trickery":
                                bonusStats3.setText(getString(R.string.channel_divinity_invoke_duplicity));
                                break;
                            case "War":
                                bonusStats3.setText(getString(R.string.channel_divinity_guided_strike));
                                break;
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.destroy_undead));
                        character.getRaceAndClassBonusStats().add(getString(R.string.destroy_undead));
                        break;
                    case 6:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You can now use Channel Divinity twice per short/long rest");
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Knowledge":
                                bonusStats2.setText(getString(R.string.channel_divinity_read_thoughts));
                                break;
                            case "Life":
                                bonusStats2.setText(getString(R.string.blessed_healer));
                                character.getRaceAndClassBonusStats().add(getString(R.string.blessed_healer));
                                break;
                            case "Light":
                                bonusStats2.setText(getString(R.string.improved_flare));
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_flare));
                                break;
                            case "Nature":
                                bonusStats2.setText(getString(R.string.dampen_elements));
                                character.getRaceAndClassBonusStats().add(getString(R.string.dampen_elements));
                                break;
                            case "Tempest":
                                bonusStats2.setText(getString(R.string.thunderbolt_strike));
                                character.getRaceAndClassBonusStats().add(getString(R.string.thunderbolt_strike));
                                break;
                            case "Trickery":
                                bonusStats2.setText(getString(R.string.channel_divinity_cloak_of_shadows));
                                break;
                            case "War":
                                bonusStats2.setText(getString(R.string.channel_divinity_war_gods_blessing));
                                break;
                        }
                        break;
                    case 7:
                        break;
                    case 8:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Destroy undead now destroys undead of CR 1 or lower");
                        bonusStats2.setVisibility(View.VISIBLE);
                        StringBuilder divineStrike = new StringBuilder();
                        divineStrike.append(getString(R.string.divine_strike));
                        switch(character.getSubclass()){
                            case "Knowledge":
                            case "Light":
                                bonusStats2.setText(getString(R.string.potent_spellcasting));
                                character.getRaceAndClassBonusStats().add(getString(R.string.potent_spellcasting));
                                break;
                            case "Life":
                                divineStrike.append("Radiant");
                                bonusStats2.setText(divineStrike.toString());
                                character.getRaceAndClassBonusStats().add(divineStrike.toString());
                                break;
                            case "Nature":
                                divineStrike.append("either Cold, Fire, or Lightning");
                                bonusStats2.setText(divineStrike.toString());
                                character.getRaceAndClassBonusStats().add(divineStrike.toString());
                                break;
                            case "Tempest":
                                divineStrike.append("Thunder");
                                bonusStats2.setText(divineStrike.toString());
                                character.getRaceAndClassBonusStats().add(divineStrike.toString());
                                break;
                            case "Trickery":
                                divineStrike.append("Poison");
                                bonusStats2.setText(divineStrike.toString());
                                character.getRaceAndClassBonusStats().add(divineStrike.toString());
                                break;
                            case "War":
                                divineStrike.append("the same type of damage dealt by the weapon");
                                bonusStats2.setText(divineStrike.toString());
                                character.getRaceAndClassBonusStats().add(divineStrike.toString());
                                break;
                        }
                        break;
                    case 9:
                        break;
                    case 10:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.divine_intervention));
                        character.getRaceAndClassBonusStats().add(getString(R.string.divine_intervention));
                        break;
                    case 11:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Destroy undead now destroys undead of CR 2 or lower");
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Destroy undead now destroys undead of CR 3 or lower");
                        switch(character.getSubclass()){
                            case "Life":
                            case "Nature":
                            case "Tempest":
                            case "Trickery":
                            case "War":
                                bonusStats2.setVisibility(View.VISIBLE);
                                bonusStats2.setText(getString(R.string.improved_divine_strike));
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_divine_strike));
                        }
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.improved_divine_strike));
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Destroy undead now destroys undead of CR 4 or lower");
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Knowledge":
                                bonusStats2.setText(getString(R.string.visions_of_the_past));
                                character.getRaceAndClassBonusStats().add(getString(R.string.visions_of_the_past));
                                break;
                            case "Life":
                                bonusStats2.setText(R.string.supreme_healing);
                                character.getRaceAndClassBonusStats().add(getString(R.string.supreme_healing));
                                break;
                            case "Light":
                                bonusStats2.setText(getString(R.string.corona_of_light));
                                character.getRaceAndClassBonusStats().add(getString(R.string.corona_of_light));
                                break;
                            case "Nature":
                                bonusStats2.setText(getString(R.string.master_of_nature));
                                character.getRaceAndClassBonusStats().add(getString(R.string.master_of_nature));
                                break;
                            case "Tempest":
                                bonusStats2.setText(getString(R.string.stormborn));
                                character.getRaceAndClassBonusStats().add(getString(R.string.stormborn));
                                break;
                            case "Trickery":
                                bonusStats2.setText(getString(R.string.improved_duplicity));
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_duplicity));
                                break;
                            case "War":
                                bonusStats2.setText(getString(R.string.avatar_of_battle));
                                character.getRaceAndClassBonusStats().add(getString(R.string.avatar_of_battle));
                                break;
                        }
                        break;
                    case 18:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You can now use Channel Divinity three times per short/long rest");
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Divine Intervention ability now automatically succeeds");
                        break;
                }
                break;
            case "Druid":
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.wild_shape));
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.wild_shape_limits) + "Max CR = 1/4, No Flying/Swimming Speed");
                        character.getRaceAndClassBonusStats().add(getString(R.string.wild_shape));
                        bonusStats3.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Circle of the Land":
                                bonusStats3.setText(getString(R.string.natural_recovery));
                                character.getRaceAndClassBonusStats().add(getString(R.string.natural_recovery));
                                break;
                            case "Circle of the Moon":
                                bonusStats3.setText(getString(R.string.combat_wild_shape));
                                character.getRaceAndClassBonusStats().add(getString(R.string.combat_wild_shape));
                                break;
                        }
                        break;
                    case 3:
                        if(character.getSubclass().equals("Circle of the Land")){
                            ArrayList<String> landCircleChoices = new ArrayList<>();
                            ArrayAdapter<String> landCircleAdapter = new ArrayAdapter<String>(this,
                                    android.R.layout.simple_spinner_dropdown_item, landCircleChoices);
                            choiceHeader1.setVisibility(View.VISIBLE);
                            choiceHeader1.setText("Choose a type of land. This will provide you with more spells");
                            choice1.setVisibility(View.VISIBLE);
                            landCircleChoices.add("Arctic");
                            landCircleChoices.add("Coast");
                            landCircleChoices.add("Desert");
                            landCircleChoices.add("Forest");
                            landCircleChoices.add("Grassland");
                            landCircleChoices.add("Mountain");
                            landCircleChoices.add("Swamp");
                            landCircleChoices.add("Underdark");
                            choice1.setAdapter(landCircleAdapter);
                        }
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        bonusStats1.setVisibility(View.VISIBLE);
                        if(character.getSubclass().contains("Circle of the Land")){
                            bonusStats1.setText(getString(R.string.lands_stride));
                            character.getRaceAndClassBonusStats().add(getString(R.string.lands_stride));
                        } else if(character.getSubclass().equals("Circle of the Moon")){
                            bonusStats2.setVisibility(View.VISIBLE);
                            bonusStats1.setText(getString(R.string.circle_forms));
                            character.getRaceAndClassBonusStats().add(getString(R.string.circle_forms));
                            bonusStats2.setText(getString(R.string.primal_strike));
                            character.getRaceAndClassBonusStats().add(getString(R.string.primal_strike));
                        }
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        bonusStats1.setVisibility(View.VISIBLE);
                        if(character.getSubclass().contains("Circle of the Land")){
                            bonusStats1.setText(getString(R.string.natures_ward));
                            character.getRaceAndClassBonusStats().add(getString(R.string.natures_ward));
                        } else if(character.getSubclass().equals("Circle of the Moon")){
                            bonusStats1.setText(getString(R.string.elemental_wild_shape));
                            character.getRaceAndClassBonusStats().add(getString(R.string.elemental_wild_shape));
                        }
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        bonusStats1.setVisibility(View.VISIBLE);
                        if(character.getSubclass().contains("Circle of the Land")){
                            bonusStats1.setText(getString(R.string.natures_sanctuary));
                            character.getRaceAndClassBonusStats().add(getString(R.string.natures_sanctuary));
                        } else if(character.getSubclass().equals("Circle of the Moon")){
                            bonusStats1.setText(getString(R.string.thousand_forms));
                            character.getRaceAndClassBonusStats().add(getString(R.string.thousand_forms));
                        }
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.timeless_body_druid));
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.beast_spells));
                        character.getRaceAndClassBonusStats().add(getString(R.string.timeless_body_druid));
                        character.getRaceAndClassBonusStats().add(getString(R.string.beast_spells));
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.archdruid));
                        break;
                }
                break;
            case "Fighter":
                List<String> maneuvers = new ArrayList<String>();
                ArrayAdapter<String> maneuverAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, maneuvers);
                maneuvers.add("Commander's Strike");
                maneuvers.add("Disarming Attack");
                maneuvers.add("Distracting Strike");
                maneuvers.add("Evasive Footwork");
                maneuvers.add("Feinting Attack");
                maneuvers.add("Goading Attack");
                maneuvers.add("Lunging Attack");
                maneuvers.add("Maneuvering Attack");
                maneuvers.add("Menacing Attack");
                maneuvers.add("Parry");
                maneuvers.add("Precision Attack");
                maneuvers.add("Pushing Attack");
                maneuvers.add("Rally");
                maneuvers.add("Riposte");
                maneuvers.add("Sweeping Attack");
                maneuvers.add("Trip Attack");
                for(int i=0; i<character.getClassBasedBonusStats2().size(); i++){
                    for(int j=0; j<maneuvers.size(); j++){
                        if(character.getClassBasedBonusStats2().get(i).contains(maneuvers.get(j))){
                            maneuvers.remove(maneuvers.get(j));
                        }
                    }
                }
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.action_surge));
                        character.getRaceAndClassBonusStats().add(getString(R.string.action_surge));
                        break;
                    case 3:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Battle Master":
                                bonusStats1.setText(getString(R.string.battle_master_description));
                                character.getRaceAndClassBonusStats().add(getString(R.string.battle_master_description));
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText("Choose 2 starting Maneuvers");
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(maneuverAdapter);
                                choice2.setVisibility(View.VISIBLE);
                                choice2.setAdapter(maneuverAdapter);
                                break;
                            case "Champion":
                                bonusStats1.setText(getString(R.string.improved_critical));
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_critical));
                                break;
                            case "Eldritch Knight":
                                bonusStats1.setText("You now have the ability to cast spells. Go to the Spellcasting tab for more information");
                                bonusStats2.setVisibility(View.VISIBLE);
                                bonusStats2.setText(getString(R.string.weapon_bond));
                                character.getRaceAndClassBonusStats().add(getString(R.string.weapon_bond));
                                break;
                        }
                        break;
                    case 4:
                        break;
                    case 5:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.extra_attack));
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack));
                        break;
                    case 6:
                        break;
                    case 7:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Battle Master":
                                bonusStats1.setText(getString(R.string.know_your_enemy));
                                character.getRaceAndClassBonusStats().add(getString(R.string.know_your_enemy));
                                bonusStats2.setVisibility(View.VISIBLE);
                                bonusStats2.setText("You have another Superiority Die");
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText("Choose 2 more Maneuvers");
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(maneuverAdapter);
                                choice2.setVisibility(View.VISIBLE);
                                choice2.setAdapter(maneuverAdapter);
                                break;
                            case "Champion":
                                bonusStats1.setText(getString(R.string.remarkable_athlete));
                                character.getRaceAndClassBonusStats().add(getString(R.string.remarkable_athlete));
                                break;
                            case "Eldritch Knight":
                                bonusStats1.setText(getString(R.string.war_magic));
                                character.getRaceAndClassBonusStats().add(getString(R.string.war_magic));
                                break;
                        }
                        break;
                    case 8:
                        break;
                    case 9:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.indomitable));
                        character.getRaceAndClassBonusStats().add(getString(R.string.indomitable));
                        break;
                    case 10:
                        switch(character.getSubclass()){
                            case "Battle Master":
                                bonusStats1.setVisibility(View.VISIBLE);
                                bonusStats1.setText("Your Superiority dice are now d10s");
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText("Choose 2 more Maneuvers");
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(maneuverAdapter);
                                choice2.setVisibility(View.VISIBLE);
                                choice2.setAdapter(maneuverAdapter);
                                break;
                            case "Champion":
                                List<String> fightingStyles = new ArrayList<String>();
                                ArrayAdapter<String> fightingStyleAdapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_spinner_dropdown_item, fightingStyles);
                                fightingStyles.add("Archery");
                                fightingStyles.add("Defense");
                                fightingStyles.add("Dueling");
                                fightingStyles.add("Great Weapon Fighting");
                                fightingStyles.add("Protection");
                                fightingStyles.add("Two-Weapon Fighting");
                                for(int i=0; i<fightingStyles.size(); i++){
                                    for(int j=0; j<character.getRaceAndClassBonusStats().size(); j++){
                                        if(character.getRaceAndClassBonusStats().get(j).contains(fightingStyles.get(i))){
                                            fightingStyles.remove(fightingStyles.get(i));
                                        }
                                    }
                                }
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText("Choose a Fighting Style");
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(fightingStyleAdapter);
                                break;
                            case "Eldritch Knight":
                                bonusStats1.setVisibility(View.VISIBLE);
                                bonusStats1.setText(getString(R.string.eldritch_strike));
                                character.getRaceAndClassBonusStats().add(getString(R.string.eldritch_strike));
                        }
                        break;
                    case 11:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.extra_attack_3));
                        character.getRaceAndClassBonusStats().remove(getString(R.string.extra_attack));
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack_3));
                        break;
                    case 12:
                        break;
                    case 13:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You have gained another use of Indomitable per long rest");
                        break;
                    case 14:
                        break;
                    case 15:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Battle Master":
                                bonusStats1.setText(getString(R.string.relentless));
                                character.getRaceAndClassBonusStats().add(getString(R.string.relentless));
                                bonusStats2.setVisibility(View.VISIBLE);
                                bonusStats2.setText("You have another Superiority Die");
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText("Choose 2 more Maneuvers");
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(maneuverAdapter);
                                choice2.setVisibility(View.VISIBLE);
                                choice2.setAdapter(maneuverAdapter);
                                break;
                            case "Champion":
                                bonusStats1.setText(getString(R.string.superior_critical));
                                character.getRaceAndClassBonusStats().remove(getString(R.string.improved_critical));
                                character.getRaceAndClassBonusStats().add(getString(R.string.superior_critical));
                                break;
                            case "Eldritch Knight":
                                bonusStats1.setText(getString(R.string.arcane_charge));
                                character.getRaceAndClassBonusStats().add(getString(R.string.arcane_charge));
                                break;
                        }
                        break;
                    case 16:
                        break;
                    case 17:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You now have a second use of Action Surge per long rest");
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText("You have gained another use of Indomitable per long rest");
                        break;
                    case 18:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Battle Master":
                                bonusStats1.setVisibility(View.VISIBLE);
                                bonusStats1.setText("Your Superiority dice are now d12s");
                                break;
                            case "Champion":
                                bonusStats1.setText(getString(R.string.survivor));
                                character.getRaceAndClassBonusStats().add(getString(R.string.survivor));
                                break;
                            case "Eldritch Knight":
                                bonusStats1.setText(getString(R.string.improved_war_magic));
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_war_magic));
                                break;
                        }
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.extra_attack_4));
                        character.getRaceAndClassBonusStats().remove(getString(R.string.extra_attack_3));
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack_4));
                        break;
                }
                break;
            case "Monk":
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.ki_points_description));
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.unarmored_movement_description));
                        character.getRaceAndClassBonusStats().add(getString(R.string.unarmored_movement_description));
                        break;
                    case 3:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.deflect_missiles));
                        character.getRaceAndClassBonusStats().add(getString(R.string.deflect_missiles));
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Way of the Open Hand":
                                bonusStats2.setText(getString(R.string.open_hand_technique));
                                character.getRaceAndClassBonusStats().add(getString(R.string.open_hand_technique));
                                break;
                            case "Way of Shadow":
                                bonusStats2.setText(getString(R.string.shadow_arts));
                                character.getRaceAndClassBonusStats().add(getString(R.string.shadow_arts));
                                break;
                        }
                        break;
                    case 4:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.slow_fall));
                        character.getRaceAndClassBonusStats().add(getString(R.string.slow_fall));
                        break;
                    case 5:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your unarmed strikes now deal 1d6 damage");
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.extra_attack));
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack));
                        break;
                    case 6:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Unarmored movement is now +15ft");
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.ki_empowered_strikes));
                        character.getRaceAndClassBonusStats().add(getString(R.string.ki_empowered_strikes));
                        bonusStats3.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Way of the Open Hand":
                                bonusStats3.setText(getString(R.string.wholeness_of_body));
                                character.getRaceAndClassBonusStats().add(getString(R.string.wholeness_of_body));
                                break;
                            case "Way of Shadow":
                                bonusStats3.setText(getString(R.string.shadow_step));
                                character.getRaceAndClassBonusStats().add(getString(R.string.shadow_step));
                                break;
                        }
                        break;
                    case 7:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.evasion));
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.stillness_of_mind));
                        character.getRaceAndClassBonusStats().add(getString(R.string.evasion));
                        character.getRaceAndClassBonusStats().add(getString(R.string.stillness_of_mind));
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Unarmored movement is now +20ft");
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.purity_of_body));
                        character.getRaceAndClassBonusStats().add(getString(R.string.purity_of_body));
                        break;
                    case 11:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your unarmed strikes now deal 1d8 damage");
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Way of the Open Hand":
                                bonusStats2.setText(getString(R.string.tranquility));
                                character.getRaceAndClassBonusStats().add(getString(R.string.tranquility));
                                break;
                            case "Way of Shadow":
                                bonusStats2.setText(getString(R.string.cloak_of_shadows));
                                character.getRaceAndClassBonusStats().add(getString(R.string.cloak_of_shadows));
                                break;
                        }
                        break;
                    case 12:
                        break;
                    case 13:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.tongue_of_the_sun_and_moon));
                        character.getRaceAndClassBonusStats().add(getString(R.string.tongue_of_the_sun_and_moon));
                        break;
                    case 14:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Unarmored movement is now +25ft");
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.diamond_soul));
                        character.getRaceAndClassBonusStats().add(getString(R.string.diamond_soul));
                        break;
                    case 15:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.timeless_body_monk));
                        character.getRaceAndClassBonusStats().add(getString(R.string.timeless_body_monk));
                        break;
                    case 16:
                        break;
                    case 17:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your unarmed strikes now deal 1d10 damage");
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Way of the Open Hand":
                                bonusStats2.setText(getString(R.string.quivering_palm));
                                character.getRaceAndClassBonusStats().add(getString(R.string.quivering_palm));
                                break;
                            case "Way of Shadow":
                                bonusStats2.setText(getString(R.string.opportunist));
                                character.getRaceAndClassBonusStats().add(getString(R.string.opportunist));
                                break;
                        }
                        break;
                    case 18:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Unarmored movement is now +30ft");
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.perfect_self));
                        character.getRaceAndClassBonusStats().add(getString(R.string.perfect_self));
                        break;
                }
                break;
            case "Paladin":
                switch (level){
                    case 2:
                        List<String> fightingStyles = new ArrayList<String>();
                        ArrayAdapter<String> fightingStyleAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, fightingStyles);
                        fightingStyles.add("Defense");
                        fightingStyles.add("Dueling");
                        fightingStyles.add("Great Weapon Fighting");
                        fightingStyles.add("Protection");
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You now have the ability to cast spells");
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.divine_smite));
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a Fighting Style");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(fightingStyleAdapter);
                        character.getRaceAndClassBonusStats().add(getString(R.string.divine_smite));
                        break;
                    case 3:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.divine_health));
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText(getString(R.string.channel_divinity_paladin));
                        bonusStats3.setVisibility(View.VISIBLE);
                        bonusStats4.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Oath of Devotion":
                                bonusStats3.setText(getString(R.string.channel_divinity_sacred_weapon));
                                bonusStats4.setText(getString(R.string.channel_divinity_turn_the_unholy));
                                break;
                            case "Oath of the Ancients":
                                bonusStats3.setText(getString(R.string.channel_divinity_natures_wrath));
                                bonusStats4.setText(getString(R.string.channel_divinity_turn_the_faithless));
                                break;
                            case "Oath of Vengeance":
                                bonusStats3.setText(getString(R.string.channel_divinity_abjure_enemy));
                                bonusStats4.setText(getString(R.string.channel_divinity_vow_of_enmity));
                                break;
                        }
                        character.getRaceAndClassBonusStats().add(getString(R.string.channel_divinity_paladin));
                        character.getRaceAndClassBonusStats().add(getString(R.string.divine_health));
                        break;
                    case 4:
                        break;
                    case 5:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.extra_attack));
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack));
                        break;
                    case 6:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.aura_of_protection));
                        character.getRaceAndClassBonusStats().add(getString(R.string.aura_of_protection));
                        break;
                    case 7:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Oath of Devotion":
                                bonusStats1.setText(getString(R.string.aura_of_devotion));
                                character.getRaceAndClassBonusStats().add(getString(R.string.aura_of_devotion));
                                break;
                            case "Oath of the Ancients":
                                bonusStats1.setText(getString(R.string.aura_of_warding));
                                character.getRaceAndClassBonusStats().add(getString(R.string.aura_of_warding));
                                break;
                            case "Oath of Vengeance":
                                bonusStats1.setText(getString(R.string.relentless_avenger));
                                character.getRaceAndClassBonusStats().add(getString(R.string.relentless_avenger));
                                break;
                        }
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.aura_of_courage));
                        character.getRaceAndClassBonusStats().add(getString(R.string.aura_of_courage));
                        break;
                    case 11:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.improved_divine_smite));
                        character.getRaceAndClassBonusStats().add(getString(R.string.improved_divine_smite));
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.cleansing_touch));
                        character.getRaceAndClassBonusStats().add(getString(R.string.cleansing_touch));
                        break;
                    case 15:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Oath of Devotion":
                                bonusStats1.setText(getString(R.string.purity_of_spirit));
                                character.getRaceAndClassBonusStats().add(getString(R.string.purity_of_spirit));
                                break;
                            case "Oath of the Ancients":
                                bonusStats3.setText(getString(R.string.undying_sentinel));
                                character.getRaceAndClassBonusStats().add(getString(R.string.undying_sentinel));
                                break;
                            case "Oath of Vengeance":
                                bonusStats3.setText(getString(R.string.soul_of_vengeance));
                                character.getRaceAndClassBonusStats().add(getString(R.string.soul_of_vengeance));
                                break;
                        }
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Auras now have a radius of 30 ft.");
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Oath of Devotion":
                                bonusStats1.setText(getString(R.string.holy_nimbus));
                                character.getRaceAndClassBonusStats().add(getString(R.string.holy_nimbus));
                                break;
                            case "Oath of the Ancients":
                                bonusStats3.setText(getString(R.string.elder_champion));
                                character.getRaceAndClassBonusStats().add(getString(R.string.elder_champion));
                                break;
                            case "Oath of Vengeance":
                                bonusStats3.setText(getString(R.string.avenging_angel));
                                character.getRaceAndClassBonusStats().add(getString(R.string.avenging_angel));
                                break;
                        }
                        break;
                }
                break;
            case "Ranger":
                List<String> hunterSubclassChoice = new ArrayList<String>();
                ArrayAdapter<String> favoredTerrainAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.favored_terrain_array));
                ArrayAdapter<String> favoredEnemyAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.favored_enemies_array));
                switch (level){
                    case 2:
                        List<String> fightingStyles = new ArrayList<String>();
                        ArrayAdapter<String> fightingStyleAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, fightingStyles);
                        fightingStyles.add("Archery");
                        fightingStyles.add("Defense");
                        fightingStyles.add("Dueling");
                        fightingStyles.add("Two-Weapon Fighting");
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a Fighting Style");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(fightingStyleAdapter);
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You now have the ability to cast spells");
                        break;
                    case 3:
                        switch(character.getSubclass()){
                            case "Hunter":
                                ArrayAdapter<String> hunterSubclassAdapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_spinner_dropdown_item, hunterSubclassChoice);
                                hunterSubclassChoice.add("Colossus Slayer");
                                hunterSubclassChoice.add("Giant Killer");
                                hunterSubclassChoice.add("Horde Breaker");
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText("Choose a subclass option");
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(hunterSubclassAdapter);
                                break;
                            case "Beast Master":
                                bonusStats1.setVisibility(View.VISIBLE);
                                bonusStats1.setText(getString(R.string.rangers_companion));
                                character.getRaceAndClassBonusStats().add(getString(R.string.rangers_companion));
                                break;
                        }
                        break;
                    case 4:
                        break;
                    case 5:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.extra_attack));
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack));
                        break;
                    case 6:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose another Favored Terrain");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(favoredTerrainAdapter);
                        choiceHeader2.setVisibility(View.VISIBLE);
                        choiceHeader2.setText("Choose another Favored Enemy");
                        choice2.setVisibility(View.VISIBLE);
                        choice2.setAdapter(favoredEnemyAdapter);
                        break;
                    case 7:
                        switch(character.getSubclass()){
                            case "Hunter":
                                ArrayAdapter<String> hunterSubclassAdapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_spinner_dropdown_item, hunterSubclassChoice);
                                hunterSubclassChoice.add("Escape the Horde");
                                hunterSubclassChoice.add("Multiattack Defense");
                                hunterSubclassChoice.add("Steel Will");
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText("Choose a subclass option");
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(hunterSubclassAdapter);
                                break;
                            case "Beast Master":
                                bonusStats1.setVisibility(View.VISIBLE);
                                bonusStats1.setText(getString(R.string.exceptional_training));
                                character.getRaceAndClassBonusStats().add(getString(R.string.exceptional_training));
                                break;
                        }
                        break;
                    case 8:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.lands_stride));
                        character.getRaceAndClassBonusStats().add(getString(R.string.lands_stride));
                        break;
                    case 9:
                        break;
                    case 10:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose another Favored Terrain");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(favoredTerrainAdapter);
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.hide_in_plain_sight));
                        character.getRaceAndClassBonusStats().add(getString(R.string.hide_in_plain_sight));
                        break;
                    case 11:
                        switch(character.getSubclass()){
                            case "Hunter":
                                ArrayAdapter<String> hunterSubclassAdapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_spinner_dropdown_item, hunterSubclassChoice);
                                hunterSubclassChoice.add("Volley");
                                hunterSubclassChoice.add("Whirlwind Attack");
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText("Choose a subclass option");
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(hunterSubclassAdapter);
                                break;
                            case "Beast Master":
                                bonusStats1.setVisibility(View.VISIBLE);
                                bonusStats1.setText(getString(R.string.bestial_fury));
                                character.getRaceAndClassBonusStats().add(getString(R.string.bestial_fury));
                                break;
                        }
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        choiceHeader2.setVisibility(View.VISIBLE);
                        choiceHeader2.setText("Choose another Favored Enemy");
                        choice2.setVisibility(View.VISIBLE);
                        choice2.setAdapter(favoredEnemyAdapter);
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.vanish));
                        character.getRaceAndClassBonusStats().add(getString(R.string.vanish));
                        break;
                    case 15:
                        switch(character.getSubclass()){
                            case "Hunter":
                                ArrayAdapter<String> hunterSubclassAdapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_spinner_dropdown_item, hunterSubclassChoice);
                                hunterSubclassChoice.add("Evasion");
                                hunterSubclassChoice.add("Stand Against the Tide");
                                hunterSubclassChoice.add("Uncanny Dodge");
                                choiceHeader1.setVisibility(View.VISIBLE);
                                choiceHeader1.setText("Choose a subclass option");
                                choice1.setVisibility(View.VISIBLE);
                                choice1.setAdapter(hunterSubclassAdapter);
                                break;
                            case "Beast Master":
                                bonusStats1.setVisibility(View.VISIBLE);
                                bonusStats1.setText(getString(R.string.share_spells));
                                character.getRaceAndClassBonusStats().add(getString(R.string.share_spells));
                                break;
                        }
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.feral_senses));
                        character.getRaceAndClassBonusStats().add(getString(R.string.feral_senses));
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.foe_slayer));
                        character.getRaceAndClassBonusStats().add(getString(R.string.foe_slayer));
                        break;
                }
                break;
            case "Rogue":
                ArrayList<String> rogueExpertiseChoices = new ArrayList<>();
                ArrayAdapter<String> rogueExpertiseAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, rogueExpertiseChoices);
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.cunning_action));
                        character.getRaceAndClassBonusStats().add(getString(R.string.cunning_action));
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose two of your proficiencies. Your proficiency bonus will be doubled for any ability checks you make with that skill");
                        choice1.setVisibility(View.VISIBLE);
                        choice2.setVisibility(View.VISIBLE);
                        rogueExpertiseChoices.addAll(character.getProficiencyChoices());
                        rogueExpertiseChoices.add("Thieve's Tools");
                        choice1.setAdapter(rogueExpertiseAdapter);
                        choice2.setAdapter(rogueExpertiseAdapter);
                        break;
                    case 3:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your sneak attack now does 2d6 damage");
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Thief":
                                bonusStats2.setText(getString(R.string.fast_hands));
                                bonusStats3.setVisibility(View.VISIBLE);
                                bonusStats3.setText(getString(R.string.second_story_work));
                                character.getRaceAndClassBonusStats().add(getString(R.string.fast_hands));
                                character.getRaceAndClassBonusStats().add(getString(R.string.second_story_work));
                                break;
                            case "Assassin":
                                bonusStats2.setText(getString(R.string.assassinate));
                                character.getRaceAndClassBonusStats().add(getString(R.string.assassinate));
                                break;
                            case "Arcane Trickster":
                                bonusStats2.setText("You now have the ability to cast spells. Go to the Spellcasting tab for more information");
                                bonusStats3.setVisibility(View.VISIBLE);
                                bonusStats3.setText(getString(R.string.mage_hand_legerdemain));
                                character.getRaceAndClassBonusStats().add(getString(R.string.mage_hand_legerdemain));
                                break;
                        }
                        break;
                    case 4:
                        break;
                    case 5:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.uncanny_dodge));
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText("Your sneak attack now does 3d6 damage");
                        character.getRaceAndClassBonusStats().add(getString(R.string.uncanny_dodge));
                        break;
                    case 6:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose two of your proficiencies. Your proficiency bonus will be doubled for any ability checks you make with that skill");
                        choice1.setVisibility(View.VISIBLE);
                        choice2.setVisibility(View.VISIBLE);
                        rogueExpertiseChoices.addAll(character.getProficiencyChoices());
                        if(!character.getClassBasedBonusStats2().contains("Thieve's Tools")){
                            rogueExpertiseChoices.add("Thieve's Tools");
                        }
                        choice1.setAdapter(rogueExpertiseAdapter);
                        choice2.setAdapter(rogueExpertiseAdapter);
                        break;
                    case 7:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.evasion));
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText("Your sneak attack now does 4d6 damage");
                        character.getRaceAndClassBonusStats().add(getString(R.string.evasion));
                        break;
                    case 8:
                        break;
                    case 9:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your sneak attack now does 5d6 damage");
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Thief":
                                bonusStats2.setText(getString(R.string.supreme_sneak));
                                character.getRaceAndClassBonusStats().add(getString(R.string.supreme_sneak));
                                break;
                            case "Assassin":
                                bonusStats2.setText(getString(R.string.infiltration_expertise));
                                character.getRaceAndClassBonusStats().add(getString(R.string.infiltration_expertise));
                                break;
                            case "Arcane Trickster":
                                bonusStats2.setText(getString(R.string.magical_ambush));
                                character.getRaceAndClassBonusStats().add(getString(R.string.magical_ambush));
                                break;
                        }
                        break;
                    case 10:
                        break;
                    case 11:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.reliable_talent));
                        bonusStats2.setVisibility(View.VISIBLE);
                        bonusStats2.setText("Your sneak attack now does 6d6 damage");
                        character.getRaceAndClassBonusStats().add(getString(R.string.reliable_talent));
                        break;
                    case 12:
                        break;
                    case 13:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your sneak attack now does 7d6 damage");
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Thief":
                                bonusStats2.setText(getString(R.string.use_magic_device));
                                character.getRaceAndClassBonusStats().add(getString(R.string.use_magic_device));
                                break;
                            case "Assassin":
                                bonusStats2.setText(getString(R.string.imposter));
                                character.getRaceAndClassBonusStats().add(getString(R.string.imposter));
                                break;
                            case "Arcane Trickster":
                                bonusStats2.setText(getString(R.string.versatile_trickster));
                                character.getRaceAndClassBonusStats().add(getString(R.string.versatile_trickster));
                                break;
                        }
                        break;
                    case 14:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.blindsense));
                        character.getRaceAndClassBonusStats().add(getString(R.string.blindsense));
                        break;
                    case 15:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your sneak attack now does 8d6 damage");
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You now have proficiency in WIS saving throws");
                        break;
                    case 16:
                        break;
                    case 17:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your sneak attack now does 9d6 damage");
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Thief":
                                bonusStats2.setText(getString(R.string.thiefs_reflexes));
                                character.getRaceAndClassBonusStats().add(getString(R.string.thiefs_reflexes));
                                break;
                            case "Assassin":
                                bonusStats2.setText(getString(R.string.death_strike));
                                character.getRaceAndClassBonusStats().add(getString(R.string.death_strike));
                                break;
                            case "Arcane Trickster":
                                bonusStats2.setText(getString(R.string.spell_thief));
                                character.getRaceAndClassBonusStats().add(getString(R.string.spell_thief));
                                break;
                        }
                        break;
                    case 18:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.elusive));
                        character.getRaceAndClassBonusStats().add(getString(R.string.elusive));
                        break;
                    case 19:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your sneak attack now does 10d6 damage");
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.stroke_of_luck));
                        character.getRaceAndClassBonusStats().add(getString(R.string.stroke_of_luck));
                        break;
                }
                break;
            case "Sorcerer":
                List<String> metamagicOptions = new ArrayList<String>();
                ArrayAdapter<String> metamagicAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, metamagicOptions);
                metamagicOptions.add("Careful Spell");
                metamagicOptions.add("Distant Spell");
                metamagicOptions.add("Empowered Spell");
                metamagicOptions.add("Extended Spell");
                metamagicOptions.add("Heightened Spell");
                metamagicOptions.add("Quickened Spell");
                metamagicOptions.add("Subtle Spell");
                metamagicOptions.add("Twinned Spell");
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.font_of_magic));
                        character.getRaceAndClassBonusStats().add(getString(R.string.font_of_magic));
                        break;
                    case 3:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose 2 Metamagic Options");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(metamagicAdapter);
                        choice2.setVisibility(View.VISIBLE);
                        choice2.setAdapter(metamagicAdapter);
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Wild Magic":
                                bonusStats1.setText(getString(R.string.bend_luck));
                                character.getRaceAndClassBonusStats().add(getString(R.string.bend_luck));
                                break;
                            case "Draconic Bloodline":
                                bonusStats1.setText(getString(R.string.elemental_affinity));
                                character.getRaceAndClassBonusStats().add(getString(R.string.elemental_affinity));
                                break;
                            case "Phoenix Sorcery":
                                bonusStats1.setText(getString(R.string.phoenix_spark));
                                character.getRaceAndClassBonusStats().add(getString(R.string.phoenix_spark));
                                break;
                            case "Stone Sorcery":
                                bonusStats1.setText(getString(R.string.stone_aegis));
                                character.getRaceAndClassBonusStats().add(getString(R.string.stone_aegis));
                                break;
                        }
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a new Metamagic Option");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(metamagicAdapter);
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Wild Magic":
                                bonusStats1.setText(getString(R.string.controlled_chaos));
                                character.getRaceAndClassBonusStats().add(getString(R.string.controlled_chaos));
                                break;
                            case "Draconic Bloodline":
                                bonusStats1.setText(getString(R.string.dragon_wings));
                                character.getRaceAndClassBonusStats().add(getString(R.string.dragon_wings));
                                break;
                            case "Phoenix Sorcery":
                                bonusStats1.setText(getString(R.string.nourishing_fire));
                                character.getRaceAndClassBonusStats().add(getString(R.string.nourishing_fire));
                                break;
                            case "Stone Sorcery":
                                bonusStats1.setText(getString(R.string.stones_edge));
                                character.getRaceAndClassBonusStats().add(getString(R.string.stones_edge));
                                break;
                        }
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a new Metamagic Option");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(metamagicAdapter);
                        break;
                    case 18:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "Wild Magic":
                                bonusStats1.setText(getString(R.string.spell_bombardment));
                                character.getRaceAndClassBonusStats().add(getString(R.string.spell_bombardment));
                                break;
                            case "Draconic Bloodline":
                                bonusStats1.setText(getString(R.string.draconic_presence));
                                character.getRaceAndClassBonusStats().add(getString(R.string.draconic_presence));
                                break;
                            case "Phoenix Sorcery":
                                bonusStats1.setText(getString(R.string.form_of_the_phoenix));
                                character.getRaceAndClassBonusStats().add(getString(R.string.form_of_the_phoenix));
                                break;
                            case "Stone Sorcery":
                                bonusStats1.setText(getString(R.string.earth_masters_aegis));
                                character.getRaceAndClassBonusStats().add(getString(R.string.earth_masters_aegis));
                                break;
                        }
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.sorcerous_restoration));
                        character.getRaceAndClassBonusStats().add(getString(R.string.sorcerous_restoration));
                        break;
                }
                break;
            case "Warlock":
                ArrayList<String> mysticArcanumChoices;
                ArrayAdapter<String> mysticArcanumAdapter;
                ArrayList<String> eldritchInvocations = new ArrayList<>();
                eldritchInvocations.add("Beguiling Influence");
                eldritchInvocations.add("Devil's Sight");
                eldritchInvocations.add("Eyes of the Rune Keeper");
                eldritchInvocations.add("Gaze of Two Minds");
                for(int i=0; i<character.getSpellsKnown().size(); i++){
                    if(character.getSpellsKnown().get(i).getSpellName().equalsIgnoreCase("Eldritch Blast")){
                        eldritchInvocations.add("Agonizing Blast");
                        eldritchInvocations.add("Eldritch Spear");
                        eldritchInvocations.add("Repelling Blast");
                    }
                }
                if(character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_tome))){
                    eldritchInvocations.add("Book of Ancient Secrets");
                }else if(character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_chain))){
                    eldritchInvocations.add("Voice of the Chain Master");
                } else if(character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_talisman))){
                    eldritchInvocations.add("Reality Tear");
                }
                if(finalLevel >= 5){
                    eldritchInvocations.add("One With Shadows");
                    if(character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_blade))){
                        eldritchInvocations.add("Thirsting Blade");
                    } else if(character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_talisman))){
                        eldritchInvocations.add("Trickster's Step");
                        eldritchInvocations.add("Eye of Yog-Sothoth");
                    }
                }
                if(finalLevel >= 12){
                    if(character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_blade))){
                        eldritchInvocations.add("Lifedrinker");
                    }
                }
                if(finalLevel >= 15){
                    eldritchInvocations.add("Witch Sight");
                    if(character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_chain))){
                        eldritchInvocations.add("Unbreakable Tether");
                    }
                }
                for(int i=0; i<eldritchInvocations.size(); i++){
                    for(int j=0; j<character.getClassBasedBonusStats2().size(); j++){
                        if(character.getClassBasedBonusStats2().get(j).contains(eldritchInvocations.get(i))){
                            eldritchInvocations.remove(i);
                            i--;
                            break;
                        }
                    }
                }
                ArrayAdapter<String> eldritchInvocationsAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item, eldritchInvocations);
                switch (level){
                    case 2:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose two Eldritch Invocations");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(eldritchInvocationsAdapter);
                        choice2.setVisibility(View.VISIBLE);
                        choice2.setAdapter(eldritchInvocationsAdapter);
                        break;
                    case 3:
                        ArrayList<String> pactBoonChoices = new ArrayList<>();
                        ArrayAdapter<String> pactBoonAdapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_dropdown_item, pactBoonChoices);
                        pactBoonChoices.add("Pact of the Blade");
                        pactBoonChoices.add("Pact of the Chain");
                        pactBoonChoices.add("Pact of the Talisman");
                        pactBoonChoices.add("Pact of the Tome");
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Your Patron bestows a gift upon you for your loyal service. Choose your Pact Boon");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(pactBoonAdapter);
                        break;
                    case 4:
                        break;
                    case 5:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose another Eldritch Invocation");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(eldritchInvocationsAdapter);
                        break;
                    case 6:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "The Archfey":
                                bonusStats1.setText(getString(R.string.misty_escape));
                                character.getRaceAndClassBonusStats().add(getString(R.string.misty_escape));
                                break;
                            case "The Fiend":
                                bonusStats1.setText(getString(R.string.dark_ones_own_luck));
                                character.getRaceAndClassBonusStats().add(getString(R.string.dark_ones_own_luck));
                                break;
                            case "The Great Old One":
                                bonusStats1.setText(getString(R.string.entropic_ward));
                                character.getRaceAndClassBonusStats().add(getString(R.string.entropic_ward));
                                break;
                        }
                        break;
                    case 7:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose another Eldritch Invocation");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(eldritchInvocationsAdapter);
                        break;
                    case 8:
                        break;
                    case 9:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose another Eldritch Invocation");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(eldritchInvocationsAdapter);
                        break;
                    case 10:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "The Archfey":
                                bonusStats1.setText(getString(R.string.beguiling_defenses));
                                character.getRaceAndClassBonusStats().add(getString(R.string.beguiling_defenses));
                                break;
                            case "The Fiend":
                                bonusStats1.setText(getString(R.string.fiendish_resilience));
                                character.getRaceAndClassBonusStats().add(getString(R.string.fiendish_resilience));
                                break;
                            case "The Great Old One":
                                bonusStats1.setText(getString(R.string.thought_shield));
                                character.getRaceAndClassBonusStats().add(getString(R.string.thought_shield));
                                break;
                        }
                        break;
                    case 11:
                        mysticArcanumChoices = getSpellsPerLevel(6);
                        mysticArcanumAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, mysticArcanumChoices);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choice1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText(getString(R.string.mystic_arcanum_choice));
                        choice1.setAdapter(mysticArcanumAdapter);
                        break;
                    case 12:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose another Eldritch Invocation");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(eldritchInvocationsAdapter);
                        break;
                    case 13:
                        mysticArcanumChoices = getSpellsPerLevel(7);
                        mysticArcanumAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, mysticArcanumChoices);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choice1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText(getString(R.string.mystic_arcanum_choice));
                        choice1.setAdapter(mysticArcanumAdapter);
                        break;
                    case 14:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "The Archfey":
                                bonusStats1.setText(getString(R.string.dark_delirium));
                                character.getRaceAndClassBonusStats().add(getString(R.string.dark_delirium));
                                break;
                            case "The Fiend":
                                bonusStats1.setText(getString(R.string.hurl_through_hell));
                                character.getRaceAndClassBonusStats().add(getString(R.string.hurl_through_hell));
                                break;
                            case "The Great Old One":
                                bonusStats1.setText(getString(R.string.create_thrall));
                                character.getRaceAndClassBonusStats().add(getString(R.string.create_thrall));
                                break;
                        }
                        break;
                    case 15:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose another Eldritch Invocation");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(eldritchInvocationsAdapter);
                        mysticArcanumChoices = getSpellsPerLevel(8);
                        mysticArcanumAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, mysticArcanumChoices);
                        choiceHeader2.setVisibility(View.VISIBLE);
                        choice2.setVisibility(View.VISIBLE);
                        choiceHeader2.setText(getString(R.string.mystic_arcanum_choice));
                        choice2.setAdapter(mysticArcanumAdapter);
                        break;
                    case 16:
                        break;
                    case 17:
                        mysticArcanumChoices = getSpellsPerLevel(9);
                        mysticArcanumAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, mysticArcanumChoices);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choice1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText(getString(R.string.mystic_arcanum_choice));
                        choice1.setAdapter(mysticArcanumAdapter);
                        break;
                    case 18:
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose another Eldritch Invocation");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(eldritchInvocationsAdapter);
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.eldritch_master));
                        character.getRaceAndClassBonusStats().add(getString(R.string.eldritch_master));
                        break;
                }
                break;
            case "Wizard":
                ArrayList<String> signatureSpells1;
                ArrayList<String> signatureSpells2;
                ArrayAdapter<String> signatureSpellAdapter1;
                ArrayAdapter<String> signatureSpellAdapter2;
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats2.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "School of Abjuration":
                                bonusStats1.setText(getString(R.string.abjuration_savant));
                                bonusStats2.setText(getString(R.string.arcane_ward));
                                character.getRaceAndClassBonusStats().add(getString(R.string.abjuration_savant));
                                character.getRaceAndClassBonusStats().add(getString(R.string.arcane_ward));
                                break;
                            case "School of Conjuration":
                                bonusStats1.setText(getString(R.string.conjuration_savant));
                                bonusStats2.setText(getString(R.string.minor_conjuration));
                                character.getRaceAndClassBonusStats().add(getString(R.string.conjuration_savant));
                                character.getRaceAndClassBonusStats().add(getString(R.string.minor_conjuration));
                                break;
                            case "School of Divination":
                                bonusStats1.setText(getString(R.string.divination_savant));
                                bonusStats2.setText(getString(R.string.portent));
                                character.getRaceAndClassBonusStats().add(getString(R.string.divination_savant));
                                character.getRaceAndClassBonusStats().add(getString(R.string.portent));
                                break;
                            case "School of Enchantment":
                                bonusStats1.setText(getString(R.string.enchantment_savant));
                                bonusStats2.setText(getString(R.string.hypnotic_gaze));
                                character.getRaceAndClassBonusStats().add(getString(R.string.enchantment_savant));
                                character.getRaceAndClassBonusStats().add(getString(R.string.hypnotic_gaze));
                                break;
                            case "School of Evocation":
                                bonusStats1.setText(getString(R.string.evocation_savant));
                                bonusStats2.setText(getString(R.string.sculpt_spells));
                                character.getRaceAndClassBonusStats().add(getString(R.string.evocation_savant));
                                character.getRaceAndClassBonusStats().add(getString(R.string.sculpt_spells));
                                break;
                            case "School of Illusion":
                                bonusStats1.setText(getString(R.string.illusion_savant));
                                bonusStats2.setText(getString(R.string.improved_minor_illusion));
                                character.getRaceAndClassBonusStats().add(getString(R.string.illusion_savant));
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_minor_illusion));
                                getSubclassSpells("minor illusion");
                                break;
                            case "School of Necromancy":
                                bonusStats1.setText(getString(R.string.necromancy_savant));
                                bonusStats2.setText(getString(R.string.grim_harvest));
                                character.getRaceAndClassBonusStats().add(getString(R.string.necromancy_savant));
                                character.getRaceAndClassBonusStats().add(getString(R.string.grim_harvest));
                                break;
                            case "School of Transmutation":
                                bonusStats1.setText(getString(R.string.transmutation_savant));
                                bonusStats2.setText(getString(R.string.minor_alchemy));
                                character.getRaceAndClassBonusStats().add(getString(R.string.transmutation_savant));
                                character.getRaceAndClassBonusStats().add(getString(R.string.minor_alchemy));
                                break;
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "School of Abjuration":
                                bonusStats1.setText(getString(R.string.projected_ward));
                                character.getRaceAndClassBonusStats().add(getString(R.string.projected_ward));
                                break;
                            case "School of Conjuration":
                                bonusStats1.setText(getString(R.string.benign_transposition));
                                character.getRaceAndClassBonusStats().add(getString(R.string.benign_transposition));
                                break;
                            case "School of Divination":
                                bonusStats1.setText(getString(R.string.expert_divination));
                                character.getRaceAndClassBonusStats().add(getString(R.string.expert_divination));
                                break;
                            case "School of Enchantment":
                                bonusStats1.setText(getString(R.string.instinctive_charm));
                                character.getRaceAndClassBonusStats().add(getString(R.string.instinctive_charm));
                                break;
                            case "School of Evocation":
                                bonusStats1.setText(getString(R.string.potent_cantrip));
                                character.getRaceAndClassBonusStats().add(getString(R.string.potent_cantrip));
                                break;
                            case "School of Illusion":
                                bonusStats1.setText(getString(R.string.malleable_illusions));
                                character.getRaceAndClassBonusStats().add(getString(R.string.malleable_illusions));
                                break;
                            case "School of Necromancy":
                                bonusStats1.setText(getString(R.string.undead_thralls));
                                character.getRaceAndClassBonusStats().add(getString(R.string.undead_thralls));
                                getSubclassSpells("animate dead");
                                break;
                            case "School of Transmutation":
                                bonusStats1.setText(getString(R.string.transmuters_stone));
                                character.getRaceAndClassBonusStats().add(getString(R.string.transmuters_stone));
                                break;
                        }
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "School of Abjuration":
                                bonusStats1.setText(getString(R.string.improved_abjuration));
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_abjuration));
                                break;
                            case "School of Conjuration":
                                bonusStats1.setText(getString(R.string.focused_conjuration));
                                character.getRaceAndClassBonusStats().add(getString(R.string.focused_conjuration));
                                break;
                            case "School of Divination":
                                bonusStats1.setText(getString(R.string.the_third_eye));
                                character.getRaceAndClassBonusStats().add(getString(R.string.the_third_eye));
                                break;
                            case "School of Enchantment":
                                bonusStats1.setText(getString(R.string.split_enchantment));
                                character.getRaceAndClassBonusStats().add(getString(R.string.split_enchantment));
                                break;
                            case "School of Evocation":
                                bonusStats1.setText(getString(R.string.empowered_evocation));
                                character.getRaceAndClassBonusStats().add(getString(R.string.empowered_evocation));
                                break;
                            case "School of Illusion":
                                bonusStats1.setText(getString(R.string.illusory_self));
                                character.getRaceAndClassBonusStats().add(getString(R.string.illusory_self));
                                break;
                            case "School of Necromancy":
                                bonusStats1.setText(getString(R.string.inured_to_undeath));
                                character.getRaceAndClassBonusStats().add(getString(R.string.inured_to_undeath));
                                break;
                            case "School of Transmutation":
                                bonusStats1.setText(getString(R.string.shapechanger));
                                character.getRaceAndClassBonusStats().add(getString(R.string.shapechanger));
                                getSubclassSpells("polymorph");
                                break;
                        }
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:bonusStats1.setVisibility(View.VISIBLE);
                        switch(character.getSubclass()){
                            case "School of Abjuration":
                                bonusStats1.setText(getString(R.string.spell_resistance));
                                character.getRaceAndClassBonusStats().add(getString(R.string.spell_resistance));
                                break;
                            case "School of Conjuration":
                                bonusStats1.setText(getString(R.string.durable_summons));
                                character.getRaceAndClassBonusStats().add(getString(R.string.durable_summons));
                                break;
                            case "School of Divination":
                                bonusStats1.setText(getString(R.string.greater_portent));
                                character.getRaceAndClassBonusStats().add(getString(R.string.greater_portent));
                                break;
                            case "School of Enchantment":
                                bonusStats1.setText(getString(R.string.alter_memories));
                                character.getRaceAndClassBonusStats().add(getString(R.string.alter_memories));
                                break;
                            case "School of Evocation":
                                bonusStats1.setText(getString(R.string.overchannel));
                                character.getRaceAndClassBonusStats().add(getString(R.string.overchannel));
                                break;
                            case "School of Illusion":
                                bonusStats1.setText(getString(R.string.illusory_reality));
                                character.getRaceAndClassBonusStats().add(getString(R.string.illusory_reality));
                                break;
                            case "School of Necromancy":
                                bonusStats1.setText(getString(R.string.command_undead));
                                character.getRaceAndClassBonusStats().add(getString(R.string.command_undead));
                                break;
                            case "School of Transmutation":
                                bonusStats1.setText(getString(R.string.master_transmuter));
                                character.getRaceAndClassBonusStats().add(getString(R.string.master_transmuter));
                                break;
                        }
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        signatureSpells1 = getSpellsPerLevel(1);
                        signatureSpellAdapter1 = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, signatureSpells1);
                        signatureSpells2 = getSpellsPerLevel(2);
                        signatureSpellAdapter2 = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, signatureSpells2);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choice1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText(getString(R.string.spell_mastery));
                        choice1.setAdapter(signatureSpellAdapter1);
                        choice2.setVisibility(View.VISIBLE);
                        choice2.setAdapter(signatureSpellAdapter2);
                        break;
                    case 19:
                        break;
                    case 20:
                        signatureSpells1 = getSpellsPerLevel(3);
                        signatureSpellAdapter1 = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, signatureSpells1);
                        signatureSpells2 = getSpellsPerLevel(3);
                        signatureSpellAdapter2 = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, signatureSpells2);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choice1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText(getString(R.string.signature_spells));
                        choice1.setAdapter(signatureSpellAdapter1);
                        choice2.setVisibility(View.VISIBLE);
                        choice2.setAdapter(signatureSpellAdapter2);
                        break;
                }
                break;
        }
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

    public ArrayList<String> getSpellsPerLevel(int spellLevel){
        ArrayList<String> spellChoices = new ArrayList<>();
        SpellDatabase mDb = SpellDatabase.getInstance(getApplicationContext());
        List<Spell> spellsList = mDb.spellDao().loadAllSpells();

        for(int i=0; i<spellsList.size(); i++){
            if(!spellsList.get(i).getClassList().contains(character.getCharacterClass()) || spellsList.get(i).getLevel() != spellLevel){
                spellsList.remove(i);
                i--;
            } else{
                spellChoices.add(spellsList.get(i).getSpellName());
            }
        }
        return spellChoices;
    }

    private void getSubclassSpells(String spellName){
        SpellDatabase mDb = SpellDatabase.getInstance(getApplicationContext());
        List<Spell> spellsList = mDb.spellDao().loadAllSpells();
        for(int i=0; i<spellsList.size(); i++){
            if(spellsList.get(i).getSpellName().equalsIgnoreCase(spellName)){
                Spell subclassSpell = spellsList.get(i);
                character.getSpellsKnown().add(subclassSpell);
                for(int j=0; j<character.getSpellsKnown().size()-1; j++){
                    if(character.getSpellsKnown().get(j).getSpellName().equals(subclassSpell.getSpellName())){
                        character.getSpellsKnown().remove(subclassSpell);
                    }
                }
            }
        }
    }
}
