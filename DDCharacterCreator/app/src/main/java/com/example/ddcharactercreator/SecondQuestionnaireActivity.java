package com.example.ddcharactercreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ddcharactercreator.DetailActivity.calculateModifier;

public class SecondQuestionnaireActivity extends AppCompatActivity {

    public static final String CHARACTER = "character";
    private static Character character;

    private final ArrayList<String> startingEquipmentChoices = new ArrayList<String>();

    @BindView(R.id.starting_equipment_spinner_1) Spinner startingEquipmentSpinner1;
    @BindView(R.id.starting_equipment_spinner_2) Spinner startingEquipmentSpinner2;
    @BindView(R.id.starting_equipment_spinner_3) Spinner startingEquipmentSpinner3;

    public SecondQuestionnaireActivity(){
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_questionnaire_layout);

        ButterKnife.bind(this);

        character = (Character) getIntent().getExtras().getSerializable(CHARACTER);

        final Spinner skillProficiencySpinner1 = findViewById(R.id.skill_proficiency_choices_spinner_1);
        final Spinner skillProficiencySpinner2 = findViewById(R.id.skill_proficiency_choices_spinner_2);
        final Spinner skillProficiencySpinner3 = findViewById(R.id.skill_proficiency_choices_spinner_3);
        final Spinner skillProficiencySpinner4 = findViewById(R.id.skill_proficiency_choices_spinner_4);

        ArrayAdapter<CharSequence> skillArrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.skill_proficiencies_array, android.R.layout.simple_spinner_item);
        skillArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        skillProficiencySpinner1.setAdapter(skillArrayAdapter);
        skillProficiencySpinner2.setAdapter(skillArrayAdapter);
        skillProficiencySpinner3.setAdapter(skillArrayAdapter);
        skillProficiencySpinner4.setAdapter(skillArrayAdapter);

        final ArrayList<String> proficiencyChoices = new ArrayList<String>();

        TextView otherProficienciesTextView = findViewById(R.id.other_proficiencies);
        StringBuilder otherProficiencies = new StringBuilder();
        otherProficiencies.append(getString(R.string.other_proficiency_beginning));

        TextView startingHPAndHitDieTextView = findViewById(R.id.starting_hp);

        final EditText startingGoldEditText = findViewById(R.id.starting_gold_edit_text);
        StringBuilder startingGoldHint = new StringBuilder();
        startingGoldHint.append(getString(R.string.starting_gp_roll));

        List<String> equipmentSpinnerArray1 = new ArrayList<String>();
        List<String> equipmentSpinnerArray2 = new ArrayList<String>();
        List<String> equipmentSpinnerArray3 = new ArrayList<String>();

        switch (character.getCharacterClass()){
            //TODO: Change the skill spinner's arrays to reflect their learnable skills
            case "Barbarian":
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner3.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Greataxe");
                equipmentSpinnerArray1.add("Battleaxe");
                equipmentSpinnerArray2.add("Handaxe x2");
                equipmentSpinnerArray2.add("Greatclub");
                startingHPAndHitDieTextView.setText(R.string.barbarian_hit_die);
                otherProficiencies.append(getString(R.string.barbarian_other_proficiencies));
                startingGoldHint.append("2d4 x10");
                break;
            case "Bard":
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner3.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Rapier");
                equipmentSpinnerArray1.add("Longsword");
                equipmentSpinnerArray1.add("Light Crossbow with bolts x20");
                equipmentSpinnerArray2.add("Diplomat's Pack");
                equipmentSpinnerArray2.add("Entertainer's Pack");
                startingHPAndHitDieTextView.setText(R.string.bard_hit_die);
                otherProficiencies.append(getString(R.string.bard_other_proficiencies));
                startingGoldHint.append("5d4 x10");
                break;
            case "Druid":
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner3.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Wooden Shield");
                equipmentSpinnerArray1.add("Shortbow with Arrows x20");
                equipmentSpinnerArray2.add("Scimitar");
                equipmentSpinnerArray2.add("Quarterstaff");
                startingHPAndHitDieTextView.setText(R.string.druid_hit_die);
                otherProficiencies.append(getString(R.string.druid_other_proficiencies));
                startingGoldHint.append("2d4 x10");
                break;
            case "Monk":
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner3.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Shortsword");
                equipmentSpinnerArray1.add("Quarterstaff");
                equipmentSpinnerArray2.add("Dungeoneer's Pack");
                equipmentSpinnerArray2.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.monk_hit_die);
                otherProficiencies.append(getString(R.string.monk_other_proficiencies));
                startingGoldHint.append("5d4");
                break;
            case "Paladin":
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Longsword and Shield");
                equipmentSpinnerArray1.add("Shortsword x2");
                equipmentSpinnerArray2.add("Javelin x5");
                equipmentSpinnerArray2.add("Mace");
                equipmentSpinnerArray3.add("Priest's Pack");
                equipmentSpinnerArray3.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.paladin_hit_die);
                otherProficiencies.append(getString(R.string.paladin_other_proficiencies));
                startingGoldHint.append("5d4 x10");
                break;
            case "Ranger":
                skillProficiencySpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Scale Mail");
                equipmentSpinnerArray1.add("Leather Armor");
                equipmentSpinnerArray2.add("Shortsword x2");
                equipmentSpinnerArray2.add("Handaxe x2");
                equipmentSpinnerArray3.add("Dungeoneer's Pack");
                equipmentSpinnerArray3.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.ranger_hit_die);
                otherProficiencies.append(getString(R.string.ranger_other_proficiencies));
                startingGoldHint.append("5d4 x10");
                break;
            case "Rogue":
                equipmentSpinnerArray1.add("Rapier");
                equipmentSpinnerArray1.add("Shortsword");
                equipmentSpinnerArray2.add("Shortbow with arrows x20");
                equipmentSpinnerArray2.add("Shortsword");
                equipmentSpinnerArray3.add("Burglar's Pack");
                equipmentSpinnerArray3.add("Dungeoneer's Pack");
                equipmentSpinnerArray3.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.rogue_hit_die);
                otherProficiencies.append(getString(R.string.rogue_other_proficiencies));
                startingGoldHint.append("4d4 x10");
                break;
            case "Sorcerer":
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Light Crossbow with bolts x20");
                equipmentSpinnerArray1.add("Quarterstaff");
                equipmentSpinnerArray2.add("Component Pouch");
                equipmentSpinnerArray2.add("Arcane Focus");
                equipmentSpinnerArray3.add("Dungeoneer's Pack");
                equipmentSpinnerArray3.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.sorcerer_hit_die);
                otherProficiencies.append(getString(R.string.sorcerer_other_proficiencies));
                startingGoldHint.append("3d4 x10");
                break;
            case "Warlock":
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Light Crossbow with bolts x20");
                equipmentSpinnerArray1.add("Mace");
                equipmentSpinnerArray2.add("Component Pouch");
                equipmentSpinnerArray2.add("Arcane Focus");
                equipmentSpinnerArray3.add("Scholar's Pack");
                equipmentSpinnerArray3.add("Dungeoneer's Pack");
                startingHPAndHitDieTextView.setText(R.string.warlock_hit_die);
                otherProficiencies.append(getString(R.string.warlock_other_proficiencies));
                startingGoldHint.append("4d4 x10");
                break;
            case "Wizard":
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner3.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Quarterstaff");
                equipmentSpinnerArray1.add("Dagger");
                equipmentSpinnerArray2.add("Scholar's Pack");
                equipmentSpinnerArray2.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.wizard_hit_die);
                otherProficiencies.append(getString(R.string.wizard_other_proficiencies));
                startingGoldHint.append("4d4 x10");
                break;
        }
        otherProficienciesTextView.setText(otherProficiencies.toString());
        startingGoldEditText.setHint(startingGoldHint);

        ArrayAdapter<String> equipmentArrayAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray1);
        ArrayAdapter<String> equipmentArrayAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray2);
        ArrayAdapter<String> equipmentArrayAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray3);

        startingEquipmentSpinner1.setAdapter(equipmentArrayAdapter1);
        startingEquipmentSpinner2.setAdapter(equipmentArrayAdapter2);
        startingEquipmentSpinner3.setAdapter(equipmentArrayAdapter3);

        Button finishButton = findViewById(R.id.button_finished_character_creation);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startingGoldEditText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "roll for your starting gold", Toast.LENGTH_SHORT).show();
                }else{
                    proficiencyChoices.add(skillProficiencySpinner1.getSelectedItem().toString());
                    proficiencyChoices.add(skillProficiencySpinner2.getSelectedItem().toString());
                    if(skillProficiencySpinner3.getVisibility() == View.VISIBLE){
                        proficiencyChoices.add(skillProficiencySpinner3.getSelectedItem().toString());
                    }
                    if(skillProficiencySpinner4.getVisibility() == View.VISIBLE){
                        proficiencyChoices.add(skillProficiencySpinner4.getSelectedItem().toString());
                    }
                    String race = character.getRace();
                    String characterClass = character.getCharacterClass();
                    String alignment = character.getAlignment();
                    String name = character.getName();
                    ArrayList<Integer> statValuesList = getStatBonuses(character, character.getStatValues());
                    ArrayList<Integer> currency = new ArrayList<>();
                    int startingGold = Integer.parseInt(startingGoldEditText.getText().toString());
                    //Initial Current HP
                    currency.add(8 + calculateModifier(character.getStatValues().get(2)));
                    //5 kinds of currency
                    currency.add(0);
                    currency.add(0);
                    currency.add(0);
                    currency.add(startingGold);
                    currency.add(0);
                    //Initial Armor Class
                    currency.add(10 + calculateModifier(character.getStatValues().get(1)));
                    //Any class-specific changes to HP and/or AC
                    switch (character.getCharacterClass()){
                        case "Barbarian":
                            currency.set(0, 12 + calculateModifier(character.getStatValues().get(2)));
                            currency.set(6, 10 + calculateModifier(character.getStatValues().get(1))
                                    + calculateModifier(character.getStatValues().get(2)));
                            break;
                        case "Monk":
                            currency.set(6, 10 + calculateModifier(character.getStatValues().get(1))
                                    + calculateModifier(character.getStatValues().get(4)));
                            break;
                        case "Paladin":
                        case "Ranger":
                            currency.set(0, 10 + calculateModifier(character.getStatValues().get(2)));
                            break;
                        case "Sorcerer":
                        case "Wizard":
                            currency.set(0, 6 + calculateModifier(character.getStatValues().get(2)));
                            break;
                    }
                    ArrayList<String> spellSlotsClicked = new ArrayList<String>();
                    for(int i=0; i<22; i++){
                        spellSlotsClicked.add("no");
                    }
                    launchDetailActivity(new Character(1, race, characterClass, alignment, name,
                            statValuesList, proficiencyChoices, instantiateInventory(startingEquipmentChoices),
                            currency, new ArrayList<String>(), new ArrayList<Spell>(), spellSlotsClicked));
                }
            }
        });
    }
    private void launchDetailActivity(Character character){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.CHARACTER, character);
        startActivity(intent);
    }

    private ArrayList<Integer> getStatBonuses(Character character, ArrayList<Integer> statArray){
        int strength = statArray.get(0);
        int dexterity = statArray.get(1);
        int constitution = statArray.get(2);
        int intelligence = statArray.get(3);
        int wisdom = statArray.get(4);
        int charisma = statArray.get(5);
        switch(character.getRace()){
            case "Dwarf":
                constitution += 2;
                //Mountain Dwarf is chosen for this
                strength += 2;
                break;
            case "Elf":
                dexterity += 2;
                //Wood Elf is chosen for this
                wisdom += 1;
                break;
            case "Halfling":
                dexterity += 2;
                //Stout Halfling is chosen for this
                constitution += 1;
                break;
            case "Human":
                strength += 1;
                dexterity += 1;
                constitution += 1;
                intelligence += 1;
                wisdom += 1;
                charisma += 1;
                break;
            case "Gnome":
                intelligence += 2;
                //Forest Gnome is chosen for this
                dexterity += 1;
                break;
            case "Half-Elf":
                charisma += 2;
                dexterity += 1;
                wisdom += 1;
                break;
            case "Half-Orc":
                strength += 2;
                constitution += 1;
                break;
            case "Tiefling":
                intelligence += 1;
                charisma += 2;
                break;
        }
        statArray.set(0, strength);
        statArray.set(1, dexterity);
        statArray.set(2, constitution);
        statArray.set(3, intelligence);
        statArray.set(4, wisdom);
        statArray.set(5, charisma);
        return statArray;
    }

    private ArrayList<String> instantiateInventory(ArrayList<String> inventory){
        inventory.add(startingEquipmentSpinner1.getSelectedItem().toString());
        inventory.add(startingEquipmentSpinner2.getSelectedItem().toString());
        if(startingEquipmentSpinner3.getVisibility() == View.VISIBLE){
            inventory.add(startingEquipmentSpinner3.getSelectedItem().toString());
        }
        switch (character.getCharacterClass()){
            case "Barbarian":
                inventory.add("Explorer's Pack");
                inventory.add("Javelin x4");
                break;
            case "Bard":
                inventory.add("Leather Armor");
                inventory.add("Dagger");
                inventory.add("Lute");
                break;
            case "Druid":
                inventory.add("Leather Armor");
                inventory.add("Explorer's Pack");
                inventory.add("Druidic Focus");
                break;
            case "Monk":
                inventory.add("Dart x10");
                break;
            case "Paladin":
                inventory.add("Chain Mail");
                inventory.add("Holy Symbol");
                break;
            case "Ranger":
                inventory.add("Longbow");
                inventory.add("Quiver w/ Arrows x20");
                break;
            case "Rogue":
                inventory.add("Leather Armor");
                inventory.add("Dagger x2");
                inventory.add("Thieve's Tools");
                break;
            case "Sorcerer":
                inventory.add("Dagger x2");
                break;
            case "Warlock":
                inventory.add("Leather Armor");
                inventory.add("Dagger x2");
                inventory.add("Handaxe");
                break;
            case "Wizard":
                inventory.add("Spellbook");
                inventory.add("Arcane Focus");
                break;
        }
        return inventory;
    }
}
