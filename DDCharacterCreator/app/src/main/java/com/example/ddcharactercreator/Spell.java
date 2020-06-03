package com.example.ddcharactercreator;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "spellsList")
public class Spell implements Serializable {

    @PrimaryKey
    @NonNull
    private String mSpellName;
    private String mDescription;
    private String mRange;
    private String mDuration;
    private String mConcentration;
    private String mCastingTime;
    private int mLevel;

    @Ignore
    public Spell(){
    }

    @Ignore
    public Spell(String name, int level){
        mSpellName = name;
        mLevel = level;
    }

    public Spell(String spellName, String description, String range, String duration, String concentration, String castingTime, int level){
        mSpellName = spellName;
        mDescription = description;
        mRange = range;
        mDuration = duration;
        mConcentration = concentration;
        mCastingTime = castingTime;
        mLevel = level;
    }

    public String getSpellName(){
        return mSpellName;
    }
    public String getDescription(){
        return mDescription;
    }
    public String getRange(){
        return mRange;
    }
    public String getDuration(){
        return mDuration;
    }
    public String getConcentration(){
        return mConcentration;
    }
    public String getCastingTime(){
        return mCastingTime;
    }
    public int getLevel(){
        return mLevel;
    }
}
