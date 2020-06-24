package com.example.ddcharactercreator;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Comparator;

@Entity(tableName = "spellsList")
public class Spell implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("name")
    private String mSpellName;
    @SerializedName("desc")
    private String mDescription;
    @SerializedName("higher_level")
    private String mHigherLevel;
    @SerializedName("range")
    private String mRange;
    @SerializedName("duration")
    private String mDuration;
    @SerializedName("concentration")
    private String mConcentration;
    @SerializedName("casting_time")
    private String mCastingTime;
    @SerializedName("level_int")
    private int mLevel;
    @SerializedName("dnd_class")
    private String mClassList;

    @Ignore
    public Spell(){
    }
    @Ignore
    public Spell(String name, int level){
        mSpellName = name;
        mLevel = level;
    }

    public Spell(String spellName, String description, String higherLevel, String range, String duration, String concentration, String castingTime, int level, String classList){
        mSpellName = spellName;
        mDescription = description;
        mHigherLevel = higherLevel;
        mRange = range;
        mDuration = duration;
        mConcentration = concentration;
        mCastingTime = castingTime;
        mLevel = level;
        mClassList = classList;
    }

    public String getSpellName(){
        return mSpellName;
    }
    public String getDescription(){
        return mDescription;
    }
    public String getHigherLevel(){
        return mHigherLevel;
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
    public String getClassList(){
        return mClassList;
    }

    public static Comparator<Spell> spellNameComparator = new Comparator<Spell>() {
        public int compare(Spell spell1, Spell spell2){
            String spellName1 = spell1.getSpellName().toUpperCase();
            String spellName2 = spell2.getSpellName().toUpperCase();
            return spellName1.compareTo(spellName2);
        }
    };
}
