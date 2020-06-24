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
    //@BindView(R.id.more_spells) TextView moreSpells;
    //@BindView(R.id.more_cantrips) TextView moreCantrips;
    @BindView(R.id.proficiency_bonus_improvement) TextView proficiencyBonusImprovement;
    @BindView(R.id.ability_score_improvement_header) TextView abilityScoreImprovementHeader;
    @BindView(R.id.ability_score_improvement_1) Spinner abilityScoreImprovement1;
    @BindView(R.id.ability_score_improvement_2) Spinner abilityScoreImprovement2;
    @BindView(R.id.more_hp_header) TextView moreHPHeader;
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

        // Health restrictions
        switch (character.getCharacterClass()){
            case "Barbarian":
                moreHPHeader.setText(getString(R.string.barbarian_hit_die));
                hitPointMax = 12;
                break;
            case "Bard":
                moreHPHeader.setText(getString(R.string.bard_hit_die));
                hitPointMax = 8;
                break;
            case "Cleric":
                moreHPHeader.setText(getString(R.string.cleric_hit_die));
                hitPointMax = 8;
                break;
            case "Druid":
                moreHPHeader.setText(getString(R.string.druid_hit_die));
                hitPointMax = 8;
                break;
            case "Fighter":
                moreHPHeader.setText(getString(R.string.fighter_hit_die));
                hitPointMax = 10;
                break;
            case "Monk":
                moreHPHeader.setText(getString(R.string.monk_hit_die));
                hitPointMax = 8;
                break;
            case "Paladin":
                moreHPHeader.setText(getString(R.string.paladin_hit_die));
                hitPointMax = 10;
                break;
            case "Ranger":
                moreHPHeader.setText(getString(R.string.ranger_hit_die));
                hitPointMax = 10;
                break;
            case "Rogue":
                moreHPHeader.setText(getString(R.string.rogue_hit_die));
                hitPointMax = 8;
                break;
            case "Sorcerer":
                moreHPHeader.setText(getString(R.string.sorcerer_hit_die));
                hitPointMax = 6;
                break;
            case "Warlock":
                moreHPHeader.setText(getString(R.string.warlock_hit_die));
                hitPointMax = 8;
                break;
            case "Wizard":
                moreHPHeader.setText(getString(R.string.wizard_hit_die));
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
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //possibly save the character??
                if(moreHP.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Make sure you roll for more health", Toast.LENGTH_SHORT).show();
                } else{
                    ArrayList<Integer> newStatValues = character.getStatValues();
                    if(abilityScoreImprovementHeader.getVisibility() == View.VISIBLE){
                        improveAbilityScore(abilityScoreImprovement1.getSelectedItem().toString(), newStatValues);
                        improveAbilityScore(abilityScoreImprovement2.getSelectedItem().toString(), newStatValues);
                    }
                    if((character.getCharacterClass().equals("Paladin") || character.getCharacterClass().equals("Ranger")) && finalLevel == 2){
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
                    Integer addToHP = Integer.parseInt(moreHP.getText().toString());
                    character.getCurrency().set(0, addToHP + (Integer) character.getCurrency().get(0)
                            + calculateModifier(character.getStatValues().get(2)));
                    returnToDetailActivity(new Character(finalLevel, character.getRace(), character.getCharacterClass(),
                            character.getAlignment(), character.getName(), newStatValues,
                            character.getProficiencyChoices(), character.getInventoryList(), character.getCurrency(),
                            character.getSubclass(), character.getSpellsKnown(), character.getSpellSlotsClicked(), character.getRaceAndClassBonusStats()));
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
                        //Choose a primal path. That choice will change a TextView, as you earn a bonus from your choice at level 3
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You have an extra usage of Rage");
                        break;
                    case 4:
                        break;
                    case 5:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(R.string.extra_attack);
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack));
                        break;
                    case 6:
                        //primal path upgrade
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You have an extra usage of Rage");
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
                        //primal path upgrade
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
                        //primal path upgrade
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
                        //choose a bard college, gives you bonuses at levels 3, 6, and 14.
                        //expertise choices
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
                        //magical secrets
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Bardic Inspiration die is now a d10");
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
                        //magical secrets
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
                        //magical secrets
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
                        break;
                    case 7:
                        break;
                    case 8:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Destroy undead now destroys undead of CR 1 or lower");
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
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Destroy undead now destroys undead of CR 4 or lower");
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
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.action_surge));
                        character.getRaceAndClassBonusStats().add(getString(R.string.action_surge));
                        break;
                    case 3:
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
                        break;
                    case 8:
                        break;
                    case 9:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.indomitable));
                        character.getRaceAndClassBonusStats().add(getString(R.string.indomitable));
                        break;
                    case 10:
                        break;
                    case 11:
                        //TODO: how to change extra attack amount in the list.
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Extra Attack feature now allows you to attack 3 times in one action");
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
                        break;
                    case 19:
                        break;
                    case 20:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your Extra Attack feature now allows you to attack 4 times in one action");
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
                        break;
                }
                break;
            case "Ranger":
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
                        break;
                    case 8:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.lands_stride));
                        character.getRaceAndClassBonusStats().add(getString(R.string.lands_stride));
                        break;
                    case 9:
                        break;
                    case 10:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.hide_in_plain_sight));
                        character.getRaceAndClassBonusStats().add(getString(R.string.hide_in_plain_sight));
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.vanish));
                        character.getRaceAndClassBonusStats().add(getString(R.string.vanish));
                        break;
                    case 15:
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
}
