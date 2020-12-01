package com.example.ddcharactercreator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class FirstQuestionnaireActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_questionnaire_layout)
        val raceSpinner = findViewById<Spinner>(R.id.race_spinner)
        val classSpinner = findViewById<Spinner>(R.id.class_spinner)
        val alignmentSpinner = findViewById<Spinner>(R.id.alignment_spinner)
        val characterNameEditText = findViewById<EditText>(R.id.character_name_edit_text)
        val strengthStatNumberPicker = findViewById<StatNumberPicker>(R.id.strength_level)
        val dexterityStatNumberPicker = findViewById<StatNumberPicker>(R.id.dexterity_level)
        val constitutionStatNumberPicker = findViewById<StatNumberPicker>(R.id.constitution_level)
        val intelligenceStatNumberPicker = findViewById<StatNumberPicker>(R.id.intelligence_level)
        val wisdomStatNumberPicker = findViewById<StatNumberPicker>(R.id.wisdom_level)
        val charismaStatNumberPicker = findViewById<StatNumberPicker>(R.id.charisma_level)
        val nextQuestionnaireButton = findViewById<Button>(R.id.next_questionnaire_button)
        nextQuestionnaireButton.setOnClickListener {
            if (characterNameEditText.text.toString() == "") {
                Toast.makeText(applicationContext, "Choose your character's name", Toast.LENGTH_SHORT).show()
            } else {
                val race = raceSpinner.selectedItem.toString()
                val characterClass = classSpinner.selectedItem.toString()
                val alignment = alignmentSpinner.selectedItem.toString()
                val name = characterNameEditText.text.toString()
                val statsValuesList = ArrayList<Int>()
                statsValuesList.add(strengthStatNumberPicker.value)
                statsValuesList.add(dexterityStatNumberPicker.value)
                statsValuesList.add(constitutionStatNumberPicker.value)
                statsValuesList.add(intelligenceStatNumberPicker.value)
                statsValuesList.add(wisdomStatNumberPicker.value)
                statsValuesList.add(charismaStatNumberPicker.value)
                nextQuestionnaire(Character(1, race, characterClass, alignment, name, statsValuesList))
            }
        }
    }

    private fun nextQuestionnaire(character: Character) {
        val intent = Intent(this, SecondQuestionnaireActivity::class.java)
        intent.putExtra(SecondQuestionnaireActivity.CHARACTER, character)
        startActivity(intent)
    }
}