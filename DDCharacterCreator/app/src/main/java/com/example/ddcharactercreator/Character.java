package com.example.ddcharactercreator;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "characterList")
public class Character implements Serializable {

    private int mLevel;
    private String mRace;
    private String mCharacterClass;
    private String mAlignment;
    @PrimaryKey
    @NonNull
    private String mName;
    private ArrayList<Integer> mStatValues;
    private ArrayList<String> mProficiencyChoices;
    private ArrayList<String> mInventoryList;
    private ArrayList<Integer> mCurrency;
    //add subclass to MainActivity character list?? (Level 4 school of Evocation Elf Wizard?)
    private String mSubclass;
    private ArrayList<Spell> mSpellsKnown;
    private ArrayList<String> mSpellSlotsClicked;

    @Ignore
    public Character(){
    }

    @Ignore
    public Character(int level, String race, String characterClass, String alignment, String name, ArrayList<Integer> statValues){
        mLevel = level;
        mRace = race;
        mCharacterClass = characterClass;
        mAlignment = alignment;
        mName = name;
        mStatValues = statValues;
    }

    @Ignore
    public Character(int level, String race, String characterClass, String alignment, String name, ArrayList<Integer> statValues, ArrayList<String> proficiencyChoices, ArrayList<String> inventoryList, ArrayList<Integer> currency){
        mLevel = level;
        mRace = race;
        mCharacterClass = characterClass;
        mAlignment = alignment;
        mName = name;
        mStatValues = statValues;
        mProficiencyChoices = proficiencyChoices;
        mInventoryList = inventoryList;
        mCurrency = currency;
    }

    public Character(int level, String race, String characterClass, String alignment, String name, ArrayList<Integer> statValues,
                     ArrayList<String> proficiencyChoices, ArrayList<String> inventoryList, ArrayList<Integer> currency,
                     String subclass, ArrayList<Spell> spellsKnown, ArrayList<String> spellSlotsClicked){
        mLevel = level;
        mRace = race;
        mCharacterClass = characterClass;
        mAlignment = alignment;
        mName = name;
        mStatValues = statValues;
        mProficiencyChoices = proficiencyChoices;
        mInventoryList = inventoryList;
        mCurrency = currency;
        mSubclass = subclass;
        mSpellsKnown = spellsKnown;
        mSpellSlotsClicked = spellSlotsClicked;
    }

    public int getLevel(){
        return mLevel;
    }
    public String getRace(){
        return mRace;
    }
    public String getCharacterClass(){
        return mCharacterClass;
    }
    public String getAlignment(){
        return mAlignment;
    }
    public String getName(){
        return mName;
    }
    public ArrayList<Integer> getStatValues(){
        return mStatValues;
    }
    public ArrayList<String> getProficiencyChoices(){
        return mProficiencyChoices;
    }
    public ArrayList<String> getInventoryList(){
        return mInventoryList;
    }
    public ArrayList getCurrency(){
        return mCurrency;
    }
    public String getSubclass(){
        return mSubclass;
    }
    public ArrayList<Spell> getSpellsKnown(){
        return mSpellsKnown;
    }
    public ArrayList<String> getSpellSlotsClicked(){
        return mSpellSlotsClicked;
    }
}
