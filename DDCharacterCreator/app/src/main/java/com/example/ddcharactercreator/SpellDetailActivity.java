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

        TextView nameTextView = findViewById(R.id.spell_name);
        TextView levelTextView = findViewById(R.id.spell_level);
        TextView durationTextView = findViewById(R.id.spell_duration);
        TextView concentrationTextView = findViewById(R.id.spell_concentration);
        TextView castingTimeTextView = findViewById(R.id.spell_casting_time);
        TextView rangeTextView = findViewById(R.id.spell_range);
        TextView descriptionTextView = findViewById(R.id.spell_description);

        nameTextView.setText(spell.getSpellName());
        levelTextView.setText(String.valueOf(spell.getLevel()));
        durationTextView.setText(spell.getDuration());
        castingTimeTextView.setText(spell.getCastingTime());
        rangeTextView.setText(spell.getRange());
        descriptionTextView.setText(spell.getDescription() + "\n" + spell.getHigherLevel());
        if(spell.getConcentration().equals("yes")){
            concentrationTextView.setText(R.string.concentration);
        }
    }
}
