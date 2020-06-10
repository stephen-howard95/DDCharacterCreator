package com.example.ddcharactercreator;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SpellContainer {

    @SerializedName("results")
    private ArrayList<Spell> results;

    public ArrayList<Spell> getResults(){
        return results;
    }
}
