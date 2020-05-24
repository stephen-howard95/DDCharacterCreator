package com.example.ddcharactercreator;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ArrayListSpellConverter {

    @TypeConverter
    public static ArrayList<Spell> fromSpell(String value) {
        Type listType = new TypeToken<ArrayList<Spell>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromArrayList(ArrayList<Spell> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
