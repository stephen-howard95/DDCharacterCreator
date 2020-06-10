package com.example.ddcharactercreator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

public class DetailActivity extends AppCompatActivity{

    public static final String CHARACTER = "character";
    public static Character character;
    public static int proficiencyBonus = 2;

    public static Boolean canLongRest;

    private FirebaseAnalytics mFirebaseAnalytics;

    private Boolean isOpen = false;

    private static CharacterDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        character = (Character) getIntent().getExtras().getSerializable(CHARACTER);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CharacterWidgetProvider.class));
        CharacterWidgetProvider.updateCharacterWidget(this, appWidgetManager, appWidgetIds);

        ViewPager viewPager = findViewById(R.id.viewpager);
        final TabAdapter adapter = new TabAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab_main);
        final FloatingActionButton levelUpFab = findViewById(R.id.fab_level_up);
        final FloatingActionButton saveCharacterFab = findViewById(R.id.fab_save_character);
        final FloatingActionButton deleteCharacterFab = findViewById(R.id.fab_delete_character);
        final TextView longRestTextView = findViewById(R.id.level_up);
        final TextView saveCharacterTextView = findViewById(R.id.save_character);
        final TextView deleteCharacterTextView = findViewById(R.id.delete_character);

        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if(isOpen){
                    levelUpFab.setVisibility(View.INVISIBLE);
                    saveCharacterFab.setVisibility(View.INVISIBLE);
                    deleteCharacterFab.setVisibility(View.INVISIBLE);
                    levelUpFab.setClickable(false);
                    saveCharacterFab.setClickable(false);
                    deleteCharacterFab.setClickable(false);
                    longRestTextView.setVisibility(View.INVISIBLE);
                    saveCharacterTextView.setVisibility(View.INVISIBLE);
                    deleteCharacterTextView.setVisibility(View.INVISIBLE);
                    isOpen = false;
                } else{
                    levelUpFab.setVisibility(View.VISIBLE);
                    saveCharacterFab.setVisibility(View.VISIBLE);
                    deleteCharacterFab.setVisibility(View.VISIBLE);
                    levelUpFab.setClickable(true);
                    saveCharacterFab.setClickable(true);
                    deleteCharacterFab.setClickable(true);
                    longRestTextView.setVisibility(View.VISIBLE);
                    saveCharacterTextView.setVisibility(View.VISIBLE);
                    deleteCharacterTextView.setVisibility(View.VISIBLE);
                    isOpen = true;
                }
            }
        });

        saveCharacterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("character_saved", character.getName());
                mFirebaseAnalytics.logEvent("character_saved", bundle);
                onSaveButtonClicked();
            }
        });
        deleteCharacterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("character_deleted", character.getName());
                mFirebaseAnalytics.logEvent("character_deleted", bundle);
                onDeleteButtonClicked();
            }
        });
        levelUpFab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if(character.getLevel() < 20){
                    Bundle bundle = new Bundle();
                    bundle.putString("level_up", character.getName());
                    mFirebaseAnalytics.logEvent("level_up", bundle);
                    onLevelUpButtonClicked();
                } else{
                    Toast.makeText(getApplicationContext(), "You are at Max Level", Toast.LENGTH_SHORT).show();
                    levelUpFab.setVisibility(View.INVISIBLE);
                    saveCharacterFab.setVisibility(View.INVISIBLE);
                    deleteCharacterFab.setVisibility(View.INVISIBLE);
                    levelUpFab.setClickable(false);
                    saveCharacterFab.setClickable(false);
                    deleteCharacterFab.setClickable(false);
                    longRestTextView.setVisibility(View.INVISIBLE);
                    saveCharacterTextView.setVisibility(View.INVISIBLE);
                    deleteCharacterTextView.setVisibility(View.INVISIBLE);
                    isOpen = false;
                }
            }
        });

        mDb = CharacterDatabase.getInstance(getApplicationContext());
    }
    public static int calculateModifier(int statValue){
        if(statValue == 1){
            return -5;
        }else if(statValue <= 3){
            return -4;
        }else if(statValue <= 5){
            return -3;
        }else if(statValue <= 7){
            return -2;
        }else if(statValue <= 9){
            return -1;
        }else if(statValue <= 11){
            return +0;
        }else if(statValue <= 13){
            return +1;
        }else if (statValue <= 15){
            return +2;
        }else if(statValue <= 17){
            return +3;
        }else if(statValue <= 19){
            return +4;
        }else{
            return +5;
        }
    }

    private void onSaveButtonClicked(){
        Character thisCharacter = DetailActivity.character;
        mDb.characterDao().insertCharacter(thisCharacter);
        Toast.makeText(getApplicationContext(), "Character saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void onDeleteButtonClicked(){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Delete?");
        adb.setMessage("Are you sure you want to delete " + character.getName() + "? This will be permanent.");
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Character thisCharacter = DetailActivity.character;
                mDb.characterDao().deleteCharacter(thisCharacter);
                Toast.makeText(getApplicationContext(), "Character deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        adb.show();
    }

    private void onLevelUpButtonClicked(){
        Intent intent = new Intent(this, LevelUpActivity.class);
        startActivity(intent);
    }

    /*private void onLongRestButtonClicked(){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Long Rest");
        adb.setMessage("Taking a Long Rest will set your health back to maximum and reset your spell slots");
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainStatsFragment mainStatsFragment = (MainStatsFragment) getSupportFragmentManager().getFragments().get(0);
                mainStatsFragment.resetHealth();
                if(!character.getCharacterClass().equals("Barbarian") && !character.getCharacterClass().equals("Monk") && !character.getCharacterClass().equals("Fighter") && !character.getCharacterClass().equals("Rogue")){
                    SpellcastingFragment spellcastingFragment = (SpellcastingFragment) getSupportFragmentManager().getFragments().get(1);
                    spellcastingFragment.resetSpellSlots();
                }
            }
        });
        adb.show();
    }*/
}
