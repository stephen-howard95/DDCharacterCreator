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
    @BindView(R.id.starting_equipment_spinner_4) Spinner startingEquipmentSpinner4;
    @BindView(R.id.extra_starting_equipment) TextView extraStartingEquipmentTextView;
    @BindView(R.id.race_specific_bonus) TextView raceSpecificBonusTextView;
    @BindView(R.id.race_specific_bonus_spinner) Spinner raceSpecificBonusSpinner;

    @BindView(R.id.level_one_choice_text_1) TextView levelOneChoiceHeader1;
    @BindView(R.id.level_one_choice_text_2) TextView levelOneChoiceHeader2;
    @BindView(R.id.subclass_choice_label) TextView subclassHeader;
    @BindView(R.id.level_one_choice_spinner_1) Spinner levelOneChoiceSpinner1;
    @BindView(R.id.level_one_choice_spinner_2) Spinner levelOneChoiceSpinner2;
    @BindView(R.id.subclass_choice_spinner) Spinner subclassSpinner;

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

        ArrayAdapter<CharSequence> languageArrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);
        skillArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        skillProficiencySpinner1.setAdapter(skillArrayAdapter);
        skillProficiencySpinner2.setAdapter(skillArrayAdapter);
        skillProficiencySpinner3.setAdapter(skillArrayAdapter);
        skillProficiencySpinner4.setAdapter(skillArrayAdapter);

        final ArrayList<String> proficiencyChoices = new ArrayList<String>();

        TextView otherProficienciesTextView = findViewById(R.id.other_proficiencies);

        switch(character.getRace()){
            case "Human":
                raceSpecificBonusTextView.setVisibility(View.VISIBLE);
                raceSpecificBonusSpinner.setVisibility(View.VISIBLE);
                raceSpecificBonusTextView.setText("As a Human, you are able to speak one extra language of your choice");
                raceSpecificBonusSpinner.setAdapter(languageArrayAdapter);
                break;
            case "Half-Elf":
                raceSpecificBonusTextView.setVisibility(View.VISIBLE);
                raceSpecificBonusSpinner.setVisibility(View.VISIBLE);
                raceSpecificBonusTextView.setText("As a Half-Elf, you are able to speak one extra language of your choice");
                raceSpecificBonusSpinner.setAdapter(languageArrayAdapter);
                break;
            case "Warforged":
                raceSpecificBonusTextView.setVisibility(View.VISIBLE);
                raceSpecificBonusSpinner.setVisibility(View.VISIBLE);
                raceSpecificBonusTextView.setText("As a Warforged, you gain an extra skill proficiency of your choice");
                raceSpecificBonusSpinner.setAdapter(skillArrayAdapter);
                break;
        }

        TextView startingHPAndHitDieTextView = findViewById(R.id.starting_hp);

        final EditText startingGoldEditText = findViewById(R.id.starting_gold_edit_text);

        List<String> equipmentSpinnerArray1 = new ArrayList<String>();
        List<String> equipmentSpinnerArray2 = new ArrayList<String>();
        List<String> equipmentSpinnerArray3 = new ArrayList<String>();
        List<String> equipmentSpinnerArray4 = new ArrayList<String>();

        List<String> levelOneChoices1 = new ArrayList<String>();
        List<String> levelOneChoices2 = new ArrayList<String>();
        List<String> subclassChoices = new ArrayList<String>();

        switch (character.getCharacterClass()){
            case "Barbarian":
                subclassHeader.setText("Choose a Primal Path");
                subclassChoices.add("Path of the Berserker");
                subclassChoices.add("Path of the Totem Warrior");
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner3.setVisibility(View.GONE);
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Greataxe");
                equipmentSpinnerArray1.add("Battleaxe");
                equipmentSpinnerArray2.add("Handaxe x2");
                equipmentSpinnerArray2.add("Greatclub");
                startingHPAndHitDieTextView.setText(R.string.barbarian_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.barbarian_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s an explorer's pack and 4 javelins", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 2d4 x10", getString(R.string.starting_gp_roll)));
                break;
            case "Bard":
                subclassHeader.setText("Choose a Bard College");
                subclassChoices.add("College of Lore");
                subclassChoices.add("College of Valor");
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner3.setVisibility(View.GONE);
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Rapier");
                equipmentSpinnerArray1.add("Longsword");
                equipmentSpinnerArray1.add("Light Crossbow with bolts x20");
                equipmentSpinnerArray2.add("Diplomat's Pack");
                equipmentSpinnerArray2.add("Entertainer's Pack");
                startingHPAndHitDieTextView.setText(R.string.bard_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.bard_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s leather armor and a dagger", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 5d4 x10", getString(R.string.starting_gp_roll)));
                break;
            case "Cleric":
                subclassHeader.setText("Choose a Divine Domain");
                subclassChoices.add("Knowledge");
                subclassChoices.add("Life");
                subclassChoices.add("Light");
                subclassChoices.add("Nature");
                subclassChoices.add("Tempest");
                subclassChoices.add("Trickery");
                subclassChoices.add("War");
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Mace");
                equipmentSpinnerArray1.add("Warhammer");
                equipmentSpinnerArray2.add("Scale Mail");
                equipmentSpinnerArray2.add("Leather Armor");
                equipmentSpinnerArray2.add("Chain Mail");
                equipmentSpinnerArray3.add("Light Crossbow with bolts x20");
                equipmentSpinnerArray3.add("Greatclub");
                startingHPAndHitDieTextView.setText(R.string.cleric_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.cleric_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s a shield and a holy symbol", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 5d4 x10", getString(R.string.starting_gp_roll)));
                break;
            case "Druid":
                subclassHeader.setText("Choose a Druid Circle");
                subclassChoices.add("Circle of the Land");
                subclassChoices.add("Circle of the Moon");
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner3.setVisibility(View.GONE);
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Wooden Shield");
                equipmentSpinnerArray1.add("Shortbow with Arrows x20");
                equipmentSpinnerArray2.add("Scimitar");
                equipmentSpinnerArray2.add("Quarterstaff");
                startingHPAndHitDieTextView.setText(R.string.druid_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.druid_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s leather armor, an explorer's pack, and a druidic focus", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 2d4 x10", getString(R.string.starting_gp_roll)));
                break;
            case "Fighter":
                subclassHeader.setText("Choose a Martial Archetype");
                subclassChoices.add("Battle Master");
                subclassChoices.add("Champion");
                subclassChoices.add("Eldritch Knight");
                levelOneChoiceHeader1.setVisibility(View.VISIBLE);
                levelOneChoiceSpinner1.setVisibility(View.VISIBLE);
                levelOneChoiceHeader1.setText("Choose a Fighting Style");
                levelOneChoices1.add("Archery");
                levelOneChoices1.add("Defense");
                levelOneChoices1.add("Dueling");
                levelOneChoices1.add("Great Weapon Fighting");
                levelOneChoices1.add("Protection");
                levelOneChoices1.add("Two-Weapon Fighting");
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Chain Mail");
                equipmentSpinnerArray1.add("Leather Armor & Longbow with arrows x20");
                equipmentSpinnerArray2.add("Battleaxe and a shield");
                equipmentSpinnerArray2.add("Shortsword x2");
                equipmentSpinnerArray3.add("Light Crossbow with bolts x20");
                equipmentSpinnerArray3.add("Handaxe x2");
                equipmentSpinnerArray4.add("Dungeoneer's Pack");
                equipmentSpinnerArray4.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.fighter_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.fighter_other_proficiencies)));
                extraStartingEquipmentTextView.setVisibility(View.GONE);
                startingGoldEditText.setHint(String.format("%s 5d4 x10", getString(R.string.starting_gp_roll)));
                break;
            case "Monk":
                subclassHeader.setText("Choose a Monastic Tradition");
                subclassChoices.add("Way of the Open Hand");
                subclassChoices.add("Way of Shadow");
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner3.setVisibility(View.GONE);
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Shortsword");
                equipmentSpinnerArray1.add("Quarterstaff");
                equipmentSpinnerArray2.add("Dungeoneer's Pack");
                equipmentSpinnerArray2.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.monk_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.monk_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s 10 darts", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 5d4", getString(R.string.starting_gp_roll)));
                break;
            case "Paladin":
                subclassHeader.setText("Choose a Sacred Oath");
                subclassChoices.add("Oath of Devotion");
                subclassChoices.add("Oath of the Ancients");
                subclassChoices.add("Oath of Vengeance");
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Longsword and Shield");
                equipmentSpinnerArray1.add("Shortsword x2");
                equipmentSpinnerArray2.add("Javelin x5");
                equipmentSpinnerArray2.add("Mace");
                equipmentSpinnerArray3.add("Priest's Pack");
                equipmentSpinnerArray3.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.paladin_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.paladin_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s chain mail and a holy symbol", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 5d4 x10", getString(R.string.starting_gp_roll)));
                break;
            case "Ranger":
                subclassHeader.setText("Choose a Ranger Archetype");
                subclassChoices.add("Hunter");
                subclassChoices.add("Beast Master");
                levelOneChoiceHeader1.setVisibility(View.VISIBLE);
                levelOneChoiceSpinner1.setVisibility(View.VISIBLE);
                levelOneChoiceHeader1.setText("Choose a Favored Enemy");
                levelOneChoiceHeader2.setVisibility(View.VISIBLE);
                levelOneChoiceSpinner2.setVisibility(View.VISIBLE);
                levelOneChoiceHeader2.setText("Choose a Favored Terrain");
                levelOneChoices1.add("Aberrations");
                levelOneChoices1.add("Beasts");
                levelOneChoices1.add("Celestials");
                levelOneChoices1.add("Constructs");
                levelOneChoices1.add("Dragons");
                levelOneChoices1.add("Elementals");
                levelOneChoices1.add("Fey");
                levelOneChoices1.add("Fiends");
                levelOneChoices1.add("Giants");
                levelOneChoices1.add("Monstrosities");
                levelOneChoices1.add("Oozes");
                levelOneChoices1.add("Plants");
                levelOneChoices1.add("Undead");
                levelOneChoices2.add("Arctic");
                levelOneChoices2.add("Coast");
                levelOneChoices2.add("Desert");
                levelOneChoices2.add("Forest");
                levelOneChoices2.add("Grassland");
                levelOneChoices2.add("Mountain");
                levelOneChoices2.add("Swamp");
                levelOneChoices2.add("Underdark");
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Scale Mail");
                equipmentSpinnerArray1.add("Leather Armor");
                equipmentSpinnerArray2.add("Shortsword x2");
                equipmentSpinnerArray2.add("Handaxe x2");
                equipmentSpinnerArray3.add("Dungeoneer's Pack");
                equipmentSpinnerArray3.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.ranger_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.ranger_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s a longbow and a quiver of 20 arrows", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 5d4 x10", getString(R.string.starting_gp_roll)));
                break;
            case "Rogue":
                subclassHeader.setText("Choose a Roguish Archetype");
                subclassChoices.add("Thief");
                subclassChoices.add("Assassin");
                subclassChoices.add("Arcane Trickster");
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Rapier");
                equipmentSpinnerArray1.add("Shortsword");
                equipmentSpinnerArray2.add("Shortbow with arrows x20");
                equipmentSpinnerArray2.add("Shortsword");
                equipmentSpinnerArray3.add("Burglar's Pack");
                equipmentSpinnerArray3.add("Dungeoneer's Pack");
                equipmentSpinnerArray3.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.rogue_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.rogue_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s leather armor, 2 daggers, and thieves' tools", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 4d4 x10", getString(R.string.starting_gp_roll)));
                break;
            case "Sorcerer":
                subclassHeader.setText("Choose a Sorcerous Origin");
                subclassChoices.add("Draconic Bloodline");
                subclassChoices.add("Wild Magic");
                subclassChoices.add("Phoenix Sorcery");
                subclassChoices.add("Stone Sorcery");
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Light Crossbow with bolts x20");
                equipmentSpinnerArray1.add("Quarterstaff");
                equipmentSpinnerArray2.add("Component Pouch");
                equipmentSpinnerArray2.add("Arcane Focus");
                equipmentSpinnerArray3.add("Dungeoneer's Pack");
                equipmentSpinnerArray3.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.sorcerer_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.sorcerer_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s 2 daggers", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 3d4 x10", getString(R.string.starting_gp_roll)));
                break;
            case "Warlock":
                subclassHeader.setText("Choose an Otherworldly Patron");
                subclassChoices.add("The Archfey");
                subclassChoices.add("The Fiend");
                subclassChoices.add("The Great Old One");
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Light Crossbow with bolts x20");
                equipmentSpinnerArray1.add("Mace");
                equipmentSpinnerArray2.add("Component Pouch");
                equipmentSpinnerArray2.add("Arcane Focus");
                equipmentSpinnerArray3.add("Scholar's Pack");
                equipmentSpinnerArray3.add("Dungeoneer's Pack");
                startingHPAndHitDieTextView.setText(R.string.warlock_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.warlock_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s leather armor, a handaxe, and 2 daggers", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 4d4 x10", getString(R.string.starting_gp_roll)));
                break;
            case "Wizard":
                subclassHeader.setText("Choose an Arcane Tradition");
                subclassChoices.add("School of Abjuration");
                subclassChoices.add("School of Conjuration");
                subclassChoices.add("School of Divination");
                subclassChoices.add("School of Enchantment");
                subclassChoices.add("School of Evocation");
                subclassChoices.add("School of Illusion");
                subclassChoices.add("School of Necromancy");
                subclassChoices.add("School of Transmutation");
                skillProficiencySpinner3.setVisibility(View.GONE);
                skillProficiencySpinner4.setVisibility(View.GONE);
                startingEquipmentSpinner3.setVisibility(View.GONE);
                startingEquipmentSpinner4.setVisibility(View.GONE);
                equipmentSpinnerArray1.add("Quarterstaff");
                equipmentSpinnerArray1.add("Dagger");
                equipmentSpinnerArray2.add("Scholar's Pack");
                equipmentSpinnerArray2.add("Explorer's Pack");
                startingHPAndHitDieTextView.setText(R.string.wizard_hit_die);
                otherProficienciesTextView.setText(String.format("You also have proficiency with %s", getString(R.string.wizard_other_proficiencies)));
                extraStartingEquipmentTextView.setText(String.format("%s a spellbook", getString(R.string.starting_equipment_info)));
                startingGoldEditText.setHint(String.format("%s 4d4 x10", getString(R.string.starting_gp_roll)));
                break;
        }
        ArrayAdapter<String> equipmentArrayAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray1);
        ArrayAdapter<String> equipmentArrayAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray2);
        ArrayAdapter<String> equipmentArrayAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray3);
        ArrayAdapter<String> equipmentArrayAdapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray4);

        startingEquipmentSpinner1.setAdapter(equipmentArrayAdapter1);
        startingEquipmentSpinner2.setAdapter(equipmentArrayAdapter2);
        startingEquipmentSpinner3.setAdapter(equipmentArrayAdapter3);
        startingEquipmentSpinner4.setAdapter(equipmentArrayAdapter4);

        ArrayAdapter<String> levelOneChoicesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, levelOneChoices1);
        levelOneChoiceSpinner1.setAdapter(levelOneChoicesAdapter);
        ArrayAdapter<String> levelOneChoicesAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, levelOneChoices2);
        levelOneChoiceSpinner2.setAdapter(levelOneChoicesAdapter2);
        ArrayAdapter<String> subclassAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, subclassChoices);
        subclassSpinner.setAdapter(subclassAdapter);

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
                    String characterSubclass = subclassSpinner.getSelectedItem().toString();
                    ArrayList<Integer> currency = new ArrayList<>();
                    int startingGold = Integer.parseInt(startingGoldEditText.getText().toString());
                    //Initial Armor Class
                    currency.add(10 + calculateModifier(character.getStatValues().get(1)));
                    //5 kinds of currency
                    currency.add(0);
                    currency.add(0);
                    currency.add(0);
                    currency.add(startingGold);
                    currency.add(0);
                    //Initial Current HP
                    currency.add(8 + calculateModifier(character.getStatValues().get(2)));
                    //Temporary HP
                    currency.add(0);
                    //Any class-specific changes to HP and/or AC, as well as raceAndClassBonusStats
                    ArrayList<String> bonusStats = new ArrayList<String>();
                    ArrayList<String> bonusStats2 = new ArrayList<String>();
                    StringBuilder languages = new StringBuilder();
                    languages.append(getString(R.string.languages_known));
                    switch (character.getRace()){
                        case "Dwarf":
                            //Mountain Dwarf is chosen for this
                            languages.append(" Common, Dwarvish, ");
                            bonusStats.add(languages.toString());
                            bonusStats.add("Darkvision: 60 ft");
                            bonusStats.add("Dwarven Resilience: Advantage on saving throws against poison and resistance to poison damage");
                            bonusStats.add("Stonecunning: Add double your proficiency bonus to INT History checks relating to stonework");
                            break;
                        case "Elf":
                            //Wood Elf is chosen for this
                            languages.append(" Common, Elvish, ");
                            bonusStats.add(languages.toString());
                            bonusStats.add("Darkvision: 60 ft");
                            bonusStats.add("Fey Ancestry: Advantage on saving throws against being charmed");
                            bonusStats.add("Fey Ancestry: Magic effects cannot put you to sleep");
                            bonusStats.add("Trance: You can get a full Long Rest by only meditating for 4 hours");
                            break;
                        case "Goliath":
                            languages.append(" Common, Giant, ");
                            bonusStats.add(languages.toString());
                            bonusStats.add("Stone's Endurance: Once per short/long rest, you can use your reaction after taking damage to reduce the damage by 1d12 + your CON modifier");
                            bonusStats.add("Powerful Build: You count for 1 size larger when determining your carrying capacity and the weight you can carry/push/lift/etc.");
                            break;
                        case "Halfling":
                            //Stout Halfling is chosen for this
                            languages.append(" Common, Halfling, ");
                            bonusStats.add(languages.toString());
                            bonusStats.add("Lucky: When you roll a 1 on a D20, you can choose to re-roll and use the new roll");
                            bonusStats.add("Brave: Advantage on saving throws against being frightened");
                            bonusStats.add("Stout Resilience: Advantage on saving throws against poison and resistance to poison damage");
                            break;
                        case "Human":
                            languages.append(" Common, ");
                            languages.append(raceSpecificBonusSpinner.getSelectedItem().toString());
                            bonusStats.add(languages.toString());
                            break;
                        case "Dragonborn":
                            //Red Dragonborn is chosen for this
                            languages.append(" Common, Draconic, ");
                            bonusStats.add(languages.toString());
                            bonusStats.add("Breath Weapon: Once per short or long rest, you can use your breath attack as an action. 15ft. cone of fire" /*+ depends on which color is chosen*/);
                            bonusStats.add("Draconic Resistance: You have resistance to fire damage" /*damage type depends on color chosen*/);
                            break;
                        case "Gnome":
                            //Forest Gnome is chosen for this
                            languages.append(" Common, Gnomish, ");
                            bonusStats.add(languages.toString());
                            bonusStats.add("Darkvision: 60 ft");
                            bonusStats.add("Gnome Cunning: Advantage on INT, WIS, and CHA saving throws against magic");
                            bonusStats.add("Speak With Small Beasts: Through sounds and gestures, you can communicate simple ideas with small animals");
                            break;
                        case "Half-Elf":
                            languages.append(" Common, Elvish, ");
                            languages.append(raceSpecificBonusSpinner.getSelectedItem().toString());
                            bonusStats.add(languages.toString());
                            bonusStats.add("Darkvision: 60 ft");
                            bonusStats.add("Fey Ancestry: Advantage on saving throws against being charmed");
                            bonusStats.add("Fey Ancestry: Magic effects cannot put you to sleep");
                            break;
                        case "Half-Orc":
                            languages.append(" Common, Orcish, ");
                            bonusStats.add(languages.toString());
                            bonusStats.add("Darkvision: 60 ft");
                            bonusStats.add("Relentless Endurance: When you are reduced to 0hp, you can drop to 1 instead. You can do this once per Long Rest");
                            bonusStats.add("Savage Attacks: When you score a critical hit, you can roll an extra of the weapon's damage die");
                            break;
                        case "Tabaxi":
                            languages.append(" Common, ");
                            bonusStats.add(languages.toString());
                            bonusStats.add("Darkvision: 60 ft");
                            bonusStats.add("Cat's Claws: You have a climbing speed of 20ft. Also, your unarmed strikes deal 1d4 + your STR modifier slashing damage");
                            bonusStats.add("Feline Agility: During combat, you can double your speed during your turn. Once you use this ability, you cannot use it again until you spend 1 turn not moving.");
                            break;
                        case "Tiefling":
                            languages.append(" Common, Infernal, ");
                            bonusStats.add(languages.toString());
                            bonusStats.add("Darkvision: 60 ft");
                            bonusStats.add("Hellish Resistance: You are resistant to Fire damage");
                            break;
                        case "Warforged":
                            languages.append(" Common, ");
                            bonusStats.add(languages.toString());
                            bonusStats.add("Constructed Resilience: You have resistance to poison damage and advantage on saving throws against being poisoned");
                            bonusStats.add("You are immune to disease, and you don't need to eat, drink, breathe, or sleep, and magic cannot put you to sleep.");
                            bonusStats.add("Sentry's Rest: When you take a long rest, you must spend at least 6 hours in an inactive state rather than sleeping. You are not unconscious and you can see/hear as normal");
                            bonusStats.add("Integrated Protection: To don/doff armor, you must spend 1 hour incorporating/unincorporating it with your armor.");
                            currency.set(0, currency.get(0) + 1);
                            proficiencyChoices.add(raceSpecificBonusSpinner.getSelectedItem().toString());
                            break;
                    }
                    switch (character.getCharacterClass()){
                        case "Barbarian":
                            bonusStats.add(getString(R.string.rage_description));
                            currency.set(6, 12 + calculateModifier(character.getStatValues().get(2)));
                            currency.set(0, 10 + calculateModifier(character.getStatValues().get(1))
                                    + calculateModifier(character.getStatValues().get(2)));
                            break;
                        case "Bard":
                            bonusStats.add(getString(R.string.bardic_inspiration_description));
                            break;
                        case "Cleric":
                            switch(characterSubclass){
                                case "Knowledge":
                                    //learn 2 languages and gain double proficiency in 2 of the following: Arcana, History, Religion or Nature.
                                    break;
                                case "Life":
                                    //Proficiency in Heavy Armor
                                    bonusStats.add(getString(R.string.disciple_of_life));
                                    break;
                                case "Light":
                                    bonusStats.add(getString(R.string.warding_flare));
                                    break;
                                case "Nature":
                                    // Choose a druid cantrip and proficiency in either nature, animal handling, or survival.
                                    break;
                                case "Trickery":
                                    bonusStats.add(getString(R.string.blessing_of_the_trickster));
                                    break;
                                case "War":
                                    bonusStats.add(getString(R.string.war_priest));
                                    break;
                            }
                            break;
                        case "Druid":
                            languages.append("Druidic, ");
                            break;
                        case "Fighter":
                            switch(levelOneChoiceSpinner1.getSelectedItem().toString()){
                                case "Archery":
                                    bonusStats.add("Fighting Style: " + getString(R.string.archery));
                                    break;
                                case "Defense":
                                    bonusStats.add("Fighting Style: " + getString(R.string.defense));
                                    break;
                                case "Dueling":
                                    bonusStats.add("Fighting Style: " + getString(R.string.dueling));
                                    break;
                                case "Great Weapon Fighting":
                                    bonusStats.add("Fighting Style: " + getString(R.string.great_weapon_fighting));
                                    break;
                                case "Protection":
                                    bonusStats.add("Fighting Style: " + getString(R.string.protection));
                                    break;
                                case "Two-Weapon Fighting":
                                    bonusStats.add("Fighting Style: " + getString(R.string.two_weapon_fighting));
                                    break;
                            }
                            bonusStats.add(getString(R.string.second_wind));
                            currency.set(6, 10 + calculateModifier(character.getStatValues().get(2)));
                            break;
                        case "Monk":
                            bonusStats.add(getString(R.string.martial_arts));
                            currency.set(0, 10 + calculateModifier(character.getStatValues().get(1))
                                    + calculateModifier(character.getStatValues().get(4)));
                            break;
                        case "Paladin":
                            bonusStats.add(getString(R.string.divine_sense));
                            bonusStats.add(getString(R.string.lay_on_hands));
                            currency.set(6, 10 + calculateModifier(character.getStatValues().get(2)));
                            break;
                        case "Ranger":
                            bonusStats.add(getString(R.string.favored_terrain_description));
                            bonusStats.add(getString(R.string.favored_enemy_description));
                            bonusStats2.add(levelOneChoiceSpinner2.getSelectedItem().toString());
                            bonusStats2.add(levelOneChoiceSpinner1.getSelectedItem().toString());
                            currency.set(6, 10 + calculateModifier(character.getStatValues().get(2)));
                            break;
                        case "Rogue":
                            languages.append("Thieve's Cant, ");
                            bonusStats.add(getString(R.string.sneak_attack_description));
                            break;
                        case "Sorcerer":
                            currency.set(6, 6 + calculateModifier(character.getStatValues().get(2)));
                            switch(characterSubclass){
                                case "Draconic Bloodline":
                                    currency.set(6, (currency.get(6) +1));
                                    currency.set(0, (13 + calculateModifier(character.getStatValues().get(1))));
                                    if(!character.getRace().equals("Dragonborn")){
                                        languages.append("Draconic");
                                    }
                                    break;
                                case "Wild Magic":
                                    bonusStats.add(getString(R.string.wild_magic_surge));
                                    bonusStats.add(getString(R.string.tides_of_chaos));
                                    break;
                                case "Phoenix Sorcery":
                                    bonusStats.add(getString(R.string.ignite));
                                    bonusStats.add(getString(R.string.mantle_of_flame));
                                    break;
                                case "Stone Sorcery":
                                    currency.set(6, (currency.get(6) +1));
                                    bonusStats.add(getString(R.string.stones_durability));
                                    break;
                            }
                            break;
                        case "Warlock":
                            switch(characterSubclass){
                                case "The Archfey":
                                    bonusStats.add(getString(R.string.fey_presence));
                                    break;
                                case "The Fiend":
                                    bonusStats.add(getString(R.string.dark_ones_blessing));
                                    break;
                                case "The Great Old One":
                                    bonusStats.add(getString(R.string.awakened_mind));
                                    break;
                            }
                            break;
                        case "Wizard":
                            bonusStats.add(getString(R.string.arcane_recovery));
                            currency.set(6, 6 + calculateModifier(character.getStatValues().get(2)));
                            break;
                    }
                    ArrayList<String> spellSlotsClicked = new ArrayList<String>();
                    for(int i=0; i<22; i++){
                        spellSlotsClicked.add("no");
                    }
                    launchDetailActivity(new Character(1, race, characterClass, alignment, name,
                            statValuesList, proficiencyChoices, instantiateInventory(startingEquipmentChoices),
                            currency, characterSubclass, new ArrayList<Spell>(), spellSlotsClicked, bonusStats, bonusStats2));
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
            case "Goliath":
                strength += 2;
                constitution += 1;
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
            case "Dragonborn":
                //Red Dragonborn is chosen for this
                strength += 2;
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
            case "Tabaxi":
                dexterity += 2;
                charisma += 1;
                break;
            case "Tiefling":
                intelligence += 1;
                charisma += 2;
                break;
            case "Warforged":
                constitution += 2;
                strength += 1;
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
        if(startingEquipmentSpinner4.getVisibility() == View.VISIBLE){
            inventory.add(startingEquipmentSpinner4.getSelectedItem().toString());
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
            case "Cleric":
                inventory.add("Shield");
                inventory.add("Holy Symbol");
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
