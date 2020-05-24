package com.example.ddcharactercreator;

import androidx.appcompat.app.AppCompatActivity;

public class AddCharacter extends AppCompatActivity {

    // Extra for the character ID to be received in the intent
    public static final String EXTRA_CHARACTER_ID = "extraCharacterId";
    // Extra for the character ID to be received after rotation
    public static final String INSTANCE_CHARACTER_ID = "instanceCharacterId";
    // Constant for default character id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;

    private int mCharacterId = -1;

    private CharacterDatabase mDatabase;
}
