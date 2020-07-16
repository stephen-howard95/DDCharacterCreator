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
                if(moreHP.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Make sure you roll for more health", Toast.LENGTH_SHORT).show();
                } else{
                    ArrayList<Integer> newStatValues = character.getStatValues();
                    if(abilityScoreImprovementHeader.getVisibility() == View.VISIBLE){
                        improveAbilityScore(abilityScoreImprovement1.getSelectedItem().toString(), newStatValues);
                        improveAbilityScore(abilityScoreImprovement2.getSelectedItem().toString(), newStatValues);
                    }
                    Integer addToHP = Integer.parseInt(moreHP.getText().toString());
                    if(character.getSubclass().equals("Draconic Bloodline") || character.getSubclass().equals("Stone Sorcery")){
                        addToHP += 1;
                    }
                    String subclass = character.getSubclass();
                    switch(character.getCharacterClass()){
                        case "Wizard":
                            if(finalLevel == 2){
                                subclass = choice1.getSelectedItem().toString();
                                switch(choice1.getSelectedItem().toString()){
                                    case "School of Abjuration":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.abjuration_savant));
                                        character.getRaceAndClassBonusStats().add(getString(R.string.arcane_ward));
                                        break;
                                    case "School of Conjuration":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.conjuration_savant));
                                        character.getRaceAndClassBonusStats().add(getString(R.string.minor_conjuration));
                                        break;
                                    case "School of Divination":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.divination_savant));
                                        character.getRaceAndClassBonusStats().add(getString(R.string.portent));
                                        break;
                                }
                            } else if(finalLevel == 20){
                                getSubclassSpells(choice1.getSelectedItem().toString());
                                getSubclassSpells(choice2.getSelectedItem().toString());
                            }
                            break;
                        case "Barbarian":
                            if(finalLevel == 6 || finalLevel == 14){
                                switch(choice1.getSelectedItem().toString()){
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
                        case "Bard":
                        case "Fighter":
                        case "Rogue":
                            if(finalLevel == 3){
                                subclass = choice1.getSelectedItem().toString();
                                switch(subclass){
                                    case "Path of the Berserker":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.frenzy));
                                        break;
                                    case "Path of the Bear Totem Warrior":
                                        subclass = "Path of the Totem Warrior";
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_bear));
                                        break;
                                    case "Path of the Eagle Totem Warrior":
                                        subclass = "Path of the Totem Warrior";
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_eagle));
                                        break;
                                    case "Path of the Wolf Totem Warrior":
                                        subclass = "Path of the Totem Warrior";
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_wolf));
                                        break;
                                    case "Path of the Tiger Totem Warrior":
                                        subclass = "Path of the Totem Warrior";
                                        character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_tiger));
                                        break;
                                    case "College of Lore":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.cutting_words));
                                        break;
                                    case "College of Valor":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.combat_inspiration));
                                        break;
                                    case "Champion":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.improved_critical));
                                        break;
                                    case "Eldritch Knight":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.weapon_bond));
                                        break;
                                    case "Thief":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.fast_hands));
                                        character.getRaceAndClassBonusStats().add(getString(R.string.second_story_work));
                                        break;
                                    case "Assassin":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.assassinate));
                                        break;
                                    case "Arcane Trickster":
                                        character.getRaceAndClassBonusStats().add(getString(R.string.mage_hand_legerdemain));
                                        break;
                                }
                            }
                            if(character.getSubclass().equals("Champion") && finalLevel == 10){
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
                            break;
                        case "Druid":
                            if(finalLevel == 2){
                                subclass = choice1.getSelectedItem().toString();
                            } else if(finalLevel == 3 && character.getSubclass().equals("Circle of the Land")){
                                subclass = subclass + ", " + choice1.getSelectedItem().toString();
                            }
                            switch(subclass){
                                case "Circle of the Land":
                                    character.getRaceAndClassBonusStats().add(getString(R.string.natural_recovery));
                                    break;
                                case "Circle of the Moon":
                                    character.getRaceAndClassBonusStats().add(getString(R.string.combat_wild_shape));
                                    break;
                            }
                            break;
                        case "Paladin":
                        case "Ranger":
                            if(finalLevel == 2){
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

                    }
                   /* if(character.getCharacterClass().equals("Warlock") && choice1.getVisibility() == View.VISIBLE){
                        SpellDatabase mDb = SpellDatabase.getInstance(getApplicationContext());
                        List<Spell> spellsList = mDb.spellDao().loadAllSpells();
                        Spell spell = spellsList.get(The spell who's name is the one chosen in choice1);

                        character.getSpellsKnown().add(spell);
                    }
                    if(character.getCharacterClass().equals("Wizard") && choice1.getVisibility() == View.VISIBLE && choice2.getVisibility() == View.VISIBLE){
                        SpellDatabase mDb = SpellDatabase.getInstance(getApplicationContext());
                        List<Spell> spellsList = mDb.spellDao().loadAllSpells();
                        Spell spell1 = spellsList.get(The spell who's name is the one chosen in choice1);
                        Spell spell2 = spellsList.get(The spell who's name is the one chosen in choice2);

                        //List both spells in Character Fragment the chosen spells. At level 18, they can be cast
                        // as cantrips, at level 20, they can be cast as cantrips once per long rest.
                    }*/
                    character.getCurrency().set(0, addToHP + (Integer) character.getCurrency().get(0)
                            + calculateModifier(character.getStatValues().get(2)));
                    returnToDetailActivity(new Character(finalLevel, character.getRace(), character.getCharacterClass(),
                            character.getAlignment(), character.getName(), newStatValues,
                            character.getProficiencyChoices(), character.getInventoryList(), character.getCurrency(),
                            subclass, character.getSpellsKnown(), character.getSpellSlotsClicked(), character.getRaceAndClassBonusStats()));
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
                        ArrayList<String> subclassChoices = new ArrayList<>();
                        ArrayAdapter<String> subclassAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, subclassChoices);
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("You have an extra usage of Rage");
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a Primal Path");
                        choice1.setVisibility(View.VISIBLE);
                        subclassChoices.add("Path of the Berserker");
                        subclassChoices.add("Path of the Bear Totem Warrior");
                        subclassChoices.add("Path of the Eagle Totem Warrior");
                        subclassChoices.add("Path of the Wolf Totem Warrior");
                        subclassChoices.add("Path of the Tiger Totem Warrior");
                        choice1.setAdapter(subclassAdapter);
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
                        ArrayList<String> subclassChoices = new ArrayList<>();
                        ArrayAdapter<String> subclassAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, subclassChoices);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a Bard Subclass");
                        choice1.setVisibility(View.VISIBLE);
                        subclassChoices.add("College of Lore");
                        subclassChoices.add("College of Valor");
                        choice1.setAdapter(subclassAdapter);
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
                        ArrayList<String> subclassChoices = new ArrayList<>();
                        ArrayAdapter<String> subclassAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, subclassChoices);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a Druid Circle");
                        choice1.setVisibility(View.VISIBLE);
                        subclassChoices.add("Circle of the Land");
                        subclassChoices.add("Circle of the Moon");
                        choice1.setAdapter(subclassAdapter);
                        break;
                    case 3:
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
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.action_surge));
                        character.getRaceAndClassBonusStats().add(getString(R.string.action_surge));
                        break;
                    case 3:
                        ArrayList<String> subclassChoices = new ArrayList<>();
                        ArrayAdapter<String> subclassAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, subclassChoices);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a Martial Archetype");
                        choice1.setVisibility(View.VISIBLE);
                        subclassChoices.add("Champion");
                        subclassChoices.add("Eldritch Knight");
                        choice1.setAdapter(subclassAdapter);
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
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.cunning_action));
                        character.getRaceAndClassBonusStats().add(getString(R.string.cunning_action));
                        break;
                    case 3:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText("Your sneak attack now does 2d6 damage");
                        ArrayList<String> subclassChoices = new ArrayList<>();
                        ArrayAdapter<String> subclassAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, subclassChoices);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a Roguish Archetype");
                        choice1.setVisibility(View.VISIBLE);
                        subclassChoices.add("Thief");
                        subclassChoices.add("Assassin");
                        subclassChoices.add("Arcane Trickster");
                        choice1.setAdapter(subclassAdapter);
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
                        /*bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.expertise));
                        List<String> expertiseChoices = new ArrayList<String>();
                        ArrayAdapter<String> expertiseAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, expertiseChoices);
                        expertiseChoices.addAll(character.getProficiencyChoices());
                        expertiseChoices.add("Thieve's Tools");
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a skill proficiency.");
                        choice1.setVisibility(View.VISIBLE);
                        choice1.setAdapter(expertiseAdapter);*/
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
                switch (level){
                    case 2:
                        bonusStats1.setVisibility(View.VISIBLE);
                        bonusStats1.setText(getString(R.string.font_of_magic));
                        character.getRaceAndClassBonusStats().add(getString(R.string.font_of_magic));
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
                        //extra metamagic option
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
                        //extra metamagic option
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
                switch (level){
                    case 2:
                        //choose 2 eldritch invocations
                        break;
                    case 3:
                        //choose a pact boon
                        break;
                    case 4:
                        break;
                    case 5:
                        //new eldritch invocation
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
                        //new eldritch invocation
                        break;
                    case 8:
                        break;
                    case 9:
                        //new eldritch invocation
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
                        //new eldritch invocation
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
                        //new eldritch invocation
                        mysticArcanumChoices = getSpellsPerLevel(8);
                        mysticArcanumAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, mysticArcanumChoices);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choice1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText(getString(R.string.mystic_arcanum_choice));
                        choice1.setAdapter(mysticArcanumAdapter);
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
                        //new eldritch invocation
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
                        ArrayList<String> subclassChoices = new ArrayList<>();
                        ArrayAdapter<String> subclassAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_dropdown_item, subclassChoices);
                        choiceHeader1.setVisibility(View.VISIBLE);
                        choiceHeader1.setText("Choose a School of Magic");
                        choice1.setVisibility(View.VISIBLE);
                        subclassChoices.add("School of Abjuration");
                        subclassChoices.add("School of Conjuration");
                        subclassChoices.add("School of Divination");
                        choice1.setAdapter(subclassAdapter);
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

    private ArrayList<String> getSpellsPerLevel(int spellLevel){
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
