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
import java.util.Collections;
import java.util.List;
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
    private SpellDatabase mDb;
    private List<Spell> completeSpellsList;

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

        if((character.getCharacterClass().equals("Barbarian") && !character.getSubclass().equals("Path of the Totem Warrior")) || (character.getCharacterClass().equals("Fighter") && !character.getSubclass().equals("Eldritch Knight")) || character.getCharacterClass().equals("Monk") || (character.getCharacterClass().equals("Rogue") && !character.getSubclass().equals("Arcane Trickster"))){
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
            mDb = SpellDatabase.getInstance(getContext());
            completeSpellsList = mDb.spellDao().loadAllSpells();
            Collections.sort(completeSpellsList, Spell.spellNameComparator);

            switch (character.getCharacterClass()) {
                case "Barbarian":
                    spellSlot1.setVisibility(View.GONE);
                    spellSlot2.setVisibility(View.GONE);
                    getSubclassSpells("beast sense");
                    getSubclassSpells("speak with animals");
                    if(character.getLevel() >= 10){
                        getSubclassSpells("commune with nature");
                    }
                    break;
                case "Bard":
                    primarySpellcasterSlotsPerLevel();
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.charisma_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    if(character.getLevel() <= 9){
                        spellCount = character.getLevel() + 3;
                    } else if(character.getLevel() <= 11){
                        spellCount = character.getLevel() + 4;
                    } else if(character.getLevel() <= 13){
                        spellCount = character.getLevel() + 3;
                    } else if(character.getLevel() <= 15){
                        spellCount = character.getLevel() + 4;
                    } else if(character.getLevel() <= 17){
                        spellCount = character.getLevel() + 3;
                    } else if(character.getLevel() <= 20){
                        spellCount = 22;
                    }
                    cantripCount = 2;
                    break;
                case "Cleric":
                    primarySpellcasterSlotsPerLevel();
                    switch(character.getSubclass()){
                        case "Knowledge":
                            switch (character.getLevel()){
                                case 1:
                                    getSubclassSpells("command");
                                    getSubclassSpells("identify");
                                    break;
                                case 3:
                                    getSubclassSpells("augury");
                                    getSubclassSpells("suggestion");
                                    break;
                                case 5:
                                    getSubclassSpells("nondetection");
                                    getSubclassSpells("speak with dead");
                                    break;
                                case 7:
                                    getSubclassSpells("arcane eye");
                                    getSubclassSpells("confusion");
                                    break;
                                case 9:
                                    getSubclassSpells("legend lore");
                                    getSubclassSpells("scrying");
                                    break;
                            }
                            break;
                        case "Life":
                            switch (character.getLevel()){
                                case 1:
                                    getSubclassSpells("bless");
                                    getSubclassSpells("cure wounds");
                                    break;
                                case 3:
                                    getSubclassSpells("lesser restoration");
                                    getSubclassSpells("spiritual weapon");
                                    break;
                                case 5:
                                    getSubclassSpells("beacon of hope");
                                    getSubclassSpells("revivify");
                                    break;
                                case 7:
                                    getSubclassSpells("death ward");
                                    getSubclassSpells("guardian of faith");
                                    break;
                                case 9:
                                    getSubclassSpells("mass cure wounds");
                                    getSubclassSpells("raise dead");
                                    break;
                            }
                            break;
                        case "Light":
                            switch (character.getLevel()){
                                case 1:
                                    getSubclassSpells("light");
                                    getSubclassSpells("burning hands");
                                    getSubclassSpells("faerie fire");
                                    break;
                                case 3:
                                    getSubclassSpells("flaming sphere");
                                    getSubclassSpells("scorching ray");
                                    break;
                                case 5:
                                    getSubclassSpells("daylight");
                                    getSubclassSpells("fireball");
                                    break;
                                case 7:
                                    getSubclassSpells("guardian of faith");
                                    getSubclassSpells("wall of fire");
                                    break;
                                case 9:
                                    getSubclassSpells("flame strike");
                                    getSubclassSpells("scrying");
                            }
                            break;
                        case "Nature":
                            switch(character.getLevel()){
                                case 1:
                                    getSubclassSpells("druidcraft");
                                    getSubclassSpells("animal friendship");
                                    getSubclassSpells("speak with animals");
                                    break;
                                case 3:
                                    getSubclassSpells("barkskin");
                                    getSubclassSpells("spike growth");
                                    break;
                                case 5:
                                    getSubclassSpells("plant growth");
                                    getSubclassSpells("wind wall");
                                    break;
                                case 7:
                                    getSubclassSpells("dominate beast");
                                    getSubclassSpells("grasping vine");
                                    break;
                                case 9:
                                    getSubclassSpells("insect plague");
                                    getSubclassSpells("tree stride");
                                    break;
                            }
                            break;
                        case "Tempest":
                            switch(character.getLevel()){
                                case 1:
                                    getSubclassSpells("fog cloud");
                                    getSubclassSpells("thunderwave");
                                    break;
                                case 3:
                                    getSubclassSpells("gust of wind");
                                    getSubclassSpells("shatter");
                                    break;
                                case 5:
                                    getSubclassSpells("call lightning");
                                    getSubclassSpells("sleet storm");
                                    break;
                                case 7:
                                    getSubclassSpells("control water");
                                    getSubclassSpells("ice storm");
                                    break;
                                case 9:
                                    getSubclassSpells("destructive wave");
                                    getSubclassSpells("insect plague");
                                    break;
                            }
                            break;
                        case "Trickery":
                            switch(character.getLevel()){
                                case 1:
                                    getSubclassSpells("charm person");
                                    getSubclassSpells("disguise self");
                                    break;
                                case 3:
                                    getSubclassSpells("mirror image");
                                    getSubclassSpells("pass without trace");
                                    break;
                                case 5:
                                    getSubclassSpells("blink");
                                    getSubclassSpells("dispel magic");
                                    break;
                                case 7:
                                    getSubclassSpells("dimension door");
                                    getSubclassSpells("polymorph");
                                    break;
                                case 9:
                                    getSubclassSpells("dominate person");
                                    getSubclassSpells("modify memory");
                            }
                            break;
                        case "War":
                            switch(character.getLevel()){
                                case 1:
                                    getSubclassSpells("divine favor");
                                    getSubclassSpells("shield of faith");
                                    break;
                                case 3:
                                    getSubclassSpells("magic weapon");
                                    getSubclassSpells("spiritual weapon");
                                    break;
                                case 5:
                                    getSubclassSpells("crusader's mantle");
                                    getSubclassSpells("spirit guardians");
                                    break;
                                case 7:
                                    getSubclassSpells("freedom of movement");
                                    getSubclassSpells("stoneskin");
                                    break;
                                case 9:
                                    getSubclassSpells("flame strike");
                                    getSubclassSpells("hold monster");
                                    break;
                            }
                            break;
                    }
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.wisdom_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellCount = character.getLevel() + calculateModifier(character.getStatValues().get(4));
                    cantripCount = 3;
                    break;
                case "Druid":
                    primarySpellcasterSlotsPerLevel();
                    switch(character.getSubclass()){
                        case "Circle of the Land, Arctic":
                            switch(character.getLevel()){
                                case 3:
                                    getSubclassSpells("hold person");
                                    getSubclassSpells("spike growth");
                                    break;
                                case 5:
                                    getSubclassSpells("sleet storm");
                                    getSubclassSpells("slow");
                                    break;
                                case 7:
                                    getSubclassSpells("freedom of movement");
                                    getSubclassSpells("ice storm");
                                    break;
                                case 9:
                                    getSubclassSpells("commune with nature");
                                    getSubclassSpells("cone of cold");
                                    break;
                            }
                            break;
                        case "Circle of the Land, Coast":
                            switch(character.getLevel()){
                                case 3:
                                    getSubclassSpells("mirror image");
                                    getSubclassSpells("misty step");
                                    break;
                                case 5:
                                    getSubclassSpells("water breathing");
                                    getSubclassSpells("water walk");
                                    break;
                                case 7:
                                    getSubclassSpells("control water");
                                    getSubclassSpells("freedom of movement");
                                    break;
                                case 9:
                                    getSubclassSpells("conjure elemental");
                                    getSubclassSpells("scrying");
                                    break;
                            }
                            break;
                        case "Circle of the Land, Desert":
                            switch(character.getLevel()){
                                case 3:
                                    getSubclassSpells("blur");
                                    getSubclassSpells("silence");
                                    break;
                                case 5:
                                    getSubclassSpells("create food and water");
                                    getSubclassSpells("protection from energy");
                                    break;
                                case 7:
                                    getSubclassSpells("blight");
                                    getSubclassSpells("hallucinatory terrain");
                                    break;
                                case 9:
                                    getSubclassSpells("insect plague");
                                    getSubclassSpells("wall of stone");
                                    break;
                            }
                            break;
                        case "Circle of the Land, Forest":
                            switch(character.getLevel()){
                                case 3:
                                    getSubclassSpells("barkskin");
                                    getSubclassSpells("spider climb");
                                    break;
                                case 5:
                                    getSubclassSpells("call lightning");
                                    getSubclassSpells("plant growth");
                                    break;
                                case 7:
                                    getSubclassSpells("divination");
                                    getSubclassSpells("freedom of movement");
                                    break;
                                case 9:
                                    getSubclassSpells("commune with nature");
                                    getSubclassSpells("tree stride");
                                    break;
                            }
                            break;
                        case "Circle of the Land, Grassland":
                            switch(character.getLevel()){
                                case 3:
                                    getSubclassSpells("invisibility");
                                    getSubclassSpells("pass without trace");
                                    break;
                                case 5:
                                    getSubclassSpells("daylight");
                                    getSubclassSpells("haste");
                                    break;
                                case 7:
                                    getSubclassSpells("divination");
                                    getSubclassSpells("freedom of movement");
                                    break;
                                case 9:
                                    getSubclassSpells("dream");
                                    getSubclassSpells("insect plague");
                                    break;
                            }
                            break;
                        case "Circle of the Land, Mountain":
                            switch(character.getLevel()){
                                case 3:
                                    getSubclassSpells("spider climb");
                                    getSubclassSpells("spike growth");
                                    break;
                                case 5:
                                    getSubclassSpells("lightning bolt");
                                    getSubclassSpells("meld into stone");
                                    break;
                                case 7:
                                    getSubclassSpells("stone shape");
                                    getSubclassSpells("stoneskin");
                                    break;
                                case 9:
                                    getSubclassSpells("passwall");
                                    getSubclassSpells("wall of stone");
                                    break;
                            }
                            break;
                        case "Circle of the Land, Swamp":
                            switch(character.getLevel()){
                                case 3:
                                    getSubclassSpells("darkness");
                                    getSubclassSpells("acid arrow");
                                    break;
                                case 5:
                                    getSubclassSpells("water walk");
                                    getSubclassSpells("stinking cloud");
                                    break;
                                case 7:
                                    getSubclassSpells("freedom of movement");
                                    getSubclassSpells("locate creature");
                                    break;
                                case 9:
                                    getSubclassSpells("insect plague");
                                    getSubclassSpells("scrying");
                                    break;
                            }
                            break;
                        case "Circle of the Land, Underdark":
                            switch(character.getLevel()){
                                case 3:
                                    getSubclassSpells("spider climb");
                                    getSubclassSpells("web");
                                    break;
                                case 5:
                                    getSubclassSpells("gaseous form");
                                    getSubclassSpells("stinking cloud");
                                    break;
                                case 7:
                                    getSubclassSpells("greater invisibility");
                                    getSubclassSpells("stone shape");
                                    break;
                                case 9:
                                    getSubclassSpells("cloudkill");
                                    getSubclassSpells("insect plague");
                                    break;
                            }
                            break;
                    }
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.wisdom_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellCount = character.getLevel() + calculateModifier(character.getStatValues().get(4));
                    cantripCount = 2;
                    if(character.getSubclass().contains("Circle of the Land")){
                        cantripCount += 1;
                    }
                    break;
                case "Fighter":
                    tertiarySpellcasterSlotsPerLevel();
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.intelligence_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(3))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(3))));
                    if(character.getLevel() <= 4){
                        spellCount = character.getLevel();
                    } else if(character.getLevel() <= 6){
                        spellCount = 4;
                    } else if(character.getLevel() <= 8){
                        spellCount = character.getLevel()-2;
                    } else if(character.getLevel() <= 11){
                        spellCount = character.getLevel()-3;
                    }else if(character.getLevel() <= 14){
                        spellCount = character.getLevel()-4;
                    }else if(character.getLevel() == 15){
                        spellCount = 10;
                    } else if(character.getLevel() <= 18){
                        spellCount = 11;
                    } else if(character.getLevel() <= 20){
                        spellCount = character.getLevel()-7;
                    }
                    cantripCount = 2;
                    break;
                case "Paladin":
                    secondarySpellcasterSlotsPerLevel();
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.charisma_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellCount = character.getLevel()/2 + calculateModifier(character.getStatValues().get(5));
                    cantripCount = -2;
                    cantripsKnown.setVisibility(View.INVISIBLE);
                    break;
                case "Ranger":
                    secondarySpellcasterSlotsPerLevel();
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.wisdom_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(4))));
                    spellCount = (character.getLevel()/2) + 1;
                    cantripsKnown.setVisibility(View.INVISIBLE);
                    cantripCount = -2;
                    break;
                case "Rogue":
                    tertiarySpellcasterSlotsPerLevel();
                    getSubclassSpells("Mage Hand");
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.intelligence_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(3))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(3))));
                    if(character.getLevel() <= 4){
                        spellCount = character.getLevel();
                    } else if(character.getLevel() <= 6){
                        spellCount = 4;
                    } else if(character.getLevel() <= 8){
                        spellCount = character.getLevel()-2;
                    } else if(character.getLevel() <= 11){
                        spellCount = character.getLevel()-3;
                    }else if(character.getLevel() <= 14){
                        spellCount = character.getLevel()-4;
                    }else if(character.getLevel() == 15){
                        spellCount = 10;
                    } else if(character.getLevel() <= 18){
                        spellCount = 11;
                    } else if(character.getLevel() <= 20){
                        spellCount = character.getLevel()-7;
                    }
                    cantripCount = 3;
                    break;
                case "Sorcerer":
                    primarySpellcasterSlotsPerLevel();
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.charisma_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    if(character.getLevel() <= 11){
                        spellCount = character.getLevel() + 1;
                    } else if(character.getLevel() <= 13){
                        spellCount = character.getLevel();
                    } else if(character.getLevel() <= 15){
                        spellCount = character.getLevel() - 1;
                    } else if(character.getLevel() <= 17){
                        spellCount = character.getLevel() - 2;
                    } else if(character.getLevel() <= 20){
                        spellCount = 15;
                    }
                    cantripCount = 4;
                    break;
                case "Warlock":
                    spellSlot2.setVisibility(View.GONE);
                    warlockSpellSlotsPerLevel();
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.charisma_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(5))));
                    if(character.getLevel() <= 9){
                        spellCount = character.getLevel() + 1;
                    } else if(character.getLevel() <= 11){
                        spellCount = character.getLevel();
                    } else if(character.getLevel() <= 13){
                        spellCount = character.getLevel() - 1;
                    } else if(character.getLevel() <= 15){
                        spellCount = character.getLevel() - 2;
                    } else if(character.getLevel() <= 17){
                        spellCount = character.getLevel() - 3;
                    } else if(character.getLevel() == 18){
                        spellCount = 14;
                    } else if(character.getLevel() <= 20){
                        spellCount = 15;
                    }
                    cantripCount = 2;
                    break;
                case "Wizard":
                    primarySpellcasterSlotsPerLevel();
                    spellcastingAbility.setText(getString(R.string.spellcasting_ability_label) + getString(R.string.intelligence_label));
                    spellSaveDC.setText(getString(R.string.spell_save_dc_label) + (8 + proficiencyBonus + calculateModifier(character.getStatValues().get(3))));
                    spellAttackBonus.setText(getString(R.string.spell_attack_bonus_label) + (proficiencyBonus + calculateModifier(character.getStatValues().get(3))));
                    spellCount = character.getLevel() + calculateModifier(character.getStatValues().get(3));
                    cantripCount = 3;
                    if(character.getSubclass().equals("School of Illusion")){
                        cantripCount += 1;
                    }
                    break;
            }
            if(spellCount<1){
                spellCount = 1;
            }
            if(character.getLevel() >= 10 && (character.getSubclass().equals("Eldritch Knight") || character.getSubclass().equals("Arcane Trickster"))){
                cantripCount += 1;
            } else if(character.getLevel() >= 10){
                cantripCount += 2;
            } else if(character.getLevel() >= 4){
                cantripCount += 1;
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
                    } else if(character.getSubclass().equals("Path of the Totem Warrior")){
                        Toast.makeText(getContext(), "Due to your class, you cannot learn any spells.", Toast.LENGTH_SHORT).show();
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
    private void primarySpellcasterSlotsPerLevel(){
        switch(character.getLevel()){
            case 20:
                spellSlot20.setVisibility(View.VISIBLE);
            case 19:
                spellSlot18.setVisibility(View.VISIBLE);
            case 18:
                spellSlot16.setVisibility(View.VISIBLE);
            case 17:
                spellSlot22.setVisibility(View.VISIBLE);
            case 16:
            case 15:
                spellSlot21.setVisibility(View.VISIBLE);
            case 14:
            case 13:
                spellSlot19.setVisibility(View.VISIBLE);
            case 12:
            case 11:
                spellSlot17.setVisibility(View.VISIBLE);
            case 10:
                spellSlot15.setVisibility(View.VISIBLE);
            case 9:
                spellSlot13.setVisibility(View.VISIBLE);
                spellSlot14.setVisibility(View.VISIBLE);
            case 8:
                spellSlot12.setVisibility(View.VISIBLE);
            case 7:
                spellSlot11.setVisibility(View.VISIBLE);
            case 6:
                spellSlot10.setVisibility(View.VISIBLE);
            case 5:
                spellSlot8.setVisibility(View.VISIBLE);
                spellSlot9.setVisibility(View.VISIBLE);
            case 4:
                spellSlot7.setVisibility(View.VISIBLE);
            case 3:
                spellSlot4.setVisibility(View.VISIBLE);
                spellSlot5.setVisibility(View.VISIBLE);
                spellSlot6.setVisibility(View.VISIBLE);
            case 2:
                spellSlot3.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void secondarySpellcasterSlotsPerLevel(){
        switch(character.getLevel()){
            case 20:
            case 19:
                spellSlot15.setVisibility(View.VISIBLE);
            case 18:
            case 17:
                spellSlot13.setVisibility(View.VISIBLE);
                spellSlot14.setVisibility(View.VISIBLE);
            case 16:
            case 15:
                spellSlot12.setVisibility(View.VISIBLE);
            case 14:
            case 13:
                spellSlot11.setVisibility(View.VISIBLE);
            case 12:
            case 11:
                spellSlot10.setVisibility(View.VISIBLE);
            case 10:
            case 9:
                spellSlot8.setVisibility(View.VISIBLE);
                spellSlot9.setVisibility(View.VISIBLE);
            case 8:
            case 7:
                spellSlot7.setVisibility(View.VISIBLE);
            case 6:
            case 5:
                spellSlot4.setVisibility(View.VISIBLE);
                spellSlot5.setVisibility(View.VISIBLE);
                spellSlot6.setVisibility(View.VISIBLE);
            case 4:
            case 3:
                spellSlot3.setVisibility(View.VISIBLE);
            case 2:
                spellSlot1.setVisibility(View.VISIBLE);
                spellSlot2.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void tertiarySpellcasterSlotsPerLevel(){
        switch(character.getLevel()){
            case 20:
            case 19:
                spellSlot11.setVisibility(View.VISIBLE);
            case 18:
            case 17:
            case 16:
                spellSlot10.setVisibility(View.VISIBLE);
            case 15:
            case 14:
            case 13:
                spellSlot8.setVisibility(View.VISIBLE);
                spellSlot9.setVisibility(View.VISIBLE);
            case 12:
            case 11:
            case 10:
                spellSlot7.setVisibility(View.VISIBLE);
            case 9:
            case 8:
            case 7:
                spellSlot4.setVisibility(View.VISIBLE);
                spellSlot5.setVisibility(View.VISIBLE);
                spellSlot6.setVisibility(View.VISIBLE);
            case 6:
            case 5:
            case 4:
                spellSlot3.setVisibility(View.VISIBLE);
            case 3:
                spellSlot1.setVisibility(View.VISIBLE);
                spellSlot2.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void warlockSpellSlotsPerLevel(){
        if(character.getLevel() >= 2){
            spellSlot2.setVisibility(View.VISIBLE);
        }
        if(character.getLevel() >= 3){
            spellSlot1.setText(getString(R.string.second_level_spell));
            spellSlot2.setText(getString(R.string.second_level_spell));
        }
        if(character.getLevel() >= 5){
            spellSlot1.setText(getString(R.string.third_level_spell));
            spellSlot2.setText(getString(R.string.third_level_spell));
        }
        if(character.getLevel() >= 7){
            spellSlot1.setText(getString(R.string.fourth_level_spell));
            spellSlot2.setText(getString(R.string.fourth_level_spell));
        }
        if(character.getLevel() >= 9){
            spellSlot1.setText(getString(R.string.fifth_level_spell));
            spellSlot2.setText(getString(R.string.fifth_level_spell));
        }
        if(character.getLevel() >= 11){
            spellSlot3.setVisibility(View.VISIBLE);
            spellSlot3.setText(getString(R.string.fifth_level_spell));
            spellSlot17.setVisibility(View.VISIBLE);
            //SetText to 6th level mystic arcanum??
        }
        if(character.getLevel() >= 13){
            spellSlot19.setVisibility(View.VISIBLE);
            //setText to 7th level mystic arcanum??
        }
        if(character.getLevel() >= 15){
            spellSlot21.setVisibility(View.VISIBLE);
            //setText to 8th level mystic arcanum??
        }
        if(character.getLevel() >= 17){
            spellSlot4.setVisibility(View.VISIBLE);
            spellSlot4.setText(getString(R.string.fifth_level_spell));
            spellSlot22.setVisibility(View.VISIBLE);
            //setText to 9th level mystic arcanum??
        }
    }
    private void getSubclassSpells(String spellName){
        for(int i=0; i<completeSpellsList.size(); i++){
            if(completeSpellsList.get(i).getSpellName().equalsIgnoreCase(spellName)){
                Spell subclassSpell = completeSpellsList.get(i);
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
