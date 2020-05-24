package com.example.ddcharactercreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FirstQuestionnaireActivity extends AppCompatActivity {



    public FirstQuestionnaireActivity(){
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_questionnaire_layout);

        final Spinner raceSpinner = findViewById(R.id.race_spinner);
        final Spinner classSpinner = findViewById(R.id.class_spinner);
        final Spinner alignmentSpinner = findViewById(R.id.alignment_spinner);
        final EditText characterNameEditText = findViewById(R.id.character_name_edit_text);

        final StatNumberPicker strengthStatNumberPicker = findViewById(R.id.strength_level);
        final StatNumberPicker dexterityStatNumberPicker = findViewById(R.id.dexterity_level);
        final StatNumberPicker constitutionStatNumberPicker = findViewById(R.id.constitution_level);
        final StatNumberPicker intelligenceStatNumberPicker = findViewById(R.id.intelligence_level);
        final StatNumberPicker wisdomStatNumberPicker = findViewById(R.id.wisdom_level);
        final StatNumberPicker charismaStatNumberPicker = findViewById(R.id.charisma_level);

        Button nextQuestionnaireButton = findViewById(R.id.next_questionnaire_button);
        nextQuestionnaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(characterNameEditText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Choose your character's name", Toast.LENGTH_SHORT).show();
                }else {
                    String race = raceSpinner.getSelectedItem().toString();
                    String characterClass = classSpinner.getSelectedItem().toString();
                    String alignment = alignmentSpinner.getSelectedItem().toString();
                    String name = characterNameEditText.getText().toString();

                    ArrayList<Integer> statsValuesList = new ArrayList<Integer>();
                    statsValuesList.add(strengthStatNumberPicker.getValue());
                    statsValuesList.add(dexterityStatNumberPicker.getValue());
                    statsValuesList.add(constitutionStatNumberPicker.getValue());
                    statsValuesList.add(intelligenceStatNumberPicker.getValue());
                    statsValuesList.add(wisdomStatNumberPicker.getValue());
                    statsValuesList.add(charismaStatNumberPicker.getValue());

                    nextQuestionnaire(new Character(1, race, characterClass, alignment, name, statsValuesList));
                }
            }
        });
    }
    private void nextQuestionnaire(Character character){
        Intent intent = new Intent(this, SecondQuestionnaireActivity.class);
        intent.putExtra(SecondQuestionnaireActivity.CHARACTER, character);
        startActivity(intent);
    }
}
