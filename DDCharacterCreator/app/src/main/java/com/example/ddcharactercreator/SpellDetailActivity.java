package com.example.ddcharactercreator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SpellDetailActivity extends AppCompatActivity{

    public static final String SPELL = "spell";
    private static Spell spell;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_detail);

        spell = (Spell) getIntent().getExtras().getSerializable(SPELL);

        String spellLevel = String.valueOf(spell.getLevel());
        if(spellLevel.equals("0")){
            spellLevel = "Cantrip";
        }

        TextView nameTextView = findViewById(R.id.spell_name);
        TextView levelTextView = findViewById(R.id.spell_level);
        TextView durationTextView = findViewById(R.id.spell_duration);
        TextView castingTimeTextView = findViewById(R.id.spell_casting_time);
        TextView rangeTextView = findViewById(R.id.spell_range);
        TextView descriptionTextView = findViewById(R.id.spell_description);
        TextView schoolTextView = findViewById(R.id.spell_school);

        nameTextView.setText(spell.getSpellName());
        levelTextView.setText(String.format("Level: %s", spellLevel));
        if(spell.getConcentration().equals("yes")){
            durationTextView.setText(String.format("Duration: Concentration, %s", spell.getDuration()));
        } else{
            durationTextView.setText(String.format("Duration: %s", spell.getDuration()));
        }
        castingTimeTextView.setText(String.format("Casting Time: %s", spell.getCastingTime()));
        rangeTextView.setText(String.format("Range: %s", spell.getRange()));
        schoolTextView.setText(String.format("School: %s", spell.getSchool()));
        switch(spell.getSchool()){
            case "Abjuration":
                schoolTextView.setTextColor(getResources().getColor(R.color.abjuration));
                break;
            case "Conjuration":
                schoolTextView.setTextColor(getResources().getColor(R.color.conjuration));
                break;
            case "Divination":
                schoolTextView.setTextColor(getResources().getColor(R.color.divination));
                break;
            case "Enchantment":
                schoolTextView.setTextColor(getResources().getColor(R.color.enchantment));
                break;
            case "Evocation":
                schoolTextView.setTextColor(getResources().getColor(R.color.evocation));
                break;
            case "Illusion":
                schoolTextView.setTextColor(getResources().getColor(R.color.illusion));
                break;
            case "Necromancy":
                schoolTextView.setTextColor(getResources().getColor(R.color.necromancy));
                break;
            case "Transmutation":
                schoolTextView.setTextColor(getResources().getColor(R.color.transmutation));
                break;
        }
        descriptionTextView.setText(spell.getDescription() + "\n" + spell.getHigherLevel());
    }
}
