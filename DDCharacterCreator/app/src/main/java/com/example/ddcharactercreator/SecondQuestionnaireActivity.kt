package com.example.ddcharactercreator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

class SecondQuestionnaireActivity : AppCompatActivity() {

    private val startingEquipmentChoices = ArrayList<String>()

    @BindView(R.id.starting_equipment_spinner_1)
    var startingEquipmentSpinner1: Spinner? = null

    @BindView(R.id.starting_equipment_spinner_2)
    var startingEquipmentSpinner2: Spinner? = null

    @BindView(R.id.starting_equipment_spinner_3)
    var startingEquipmentSpinner3: Spinner? = null

    @BindView(R.id.starting_equipment_spinner_4)
    var startingEquipmentSpinner4: Spinner? = null

    @BindView(R.id.extra_starting_equipment)
    var extraStartingEquipmentTextView: TextView? = null

    @BindView(R.id.race_specific_bonus)
    var raceSpecificBonusTextView: TextView? = null

    @BindView(R.id.race_specific_bonus_spinner)
    var raceSpecificBonusSpinner: Spinner? = null

    @BindView(R.id.level_one_choice_text_1)
    var levelOneChoiceHeader1: TextView? = null

    @BindView(R.id.level_one_choice_text_2)
    var levelOneChoiceHeader2: TextView? = null

    @BindView(R.id.subclass_choice_label)
    var subclassHeader: TextView? = null

    @BindView(R.id.level_one_choice_spinner_1)
    var levelOneChoiceSpinner1: Spinner? = null

    @BindView(R.id.level_one_choice_spinner_2)
    var levelOneChoiceSpinner2: Spinner? = null

    @BindView(R.id.subclass_choice_spinner)
    var subclassSpinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_questionnaire_layout)
        ButterKnife.bind(this)
        character = intent.extras.getSerializable(CHARACTER) as Character
        val skillProficiencySpinner1 = findViewById<Spinner>(R.id.skill_proficiency_choices_spinner_1)
        val skillProficiencySpinner2 = findViewById<Spinner>(R.id.skill_proficiency_choices_spinner_2)
        val skillProficiencySpinner3 = findViewById<Spinner>(R.id.skill_proficiency_choices_spinner_3)
        val skillProficiencySpinner4 = findViewById<Spinner>(R.id.skill_proficiency_choices_spinner_4)

        val skillArrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.skill_proficiencies_array, android.R.layout.simple_spinner_item)
        skillArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val languageArrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item)
        skillArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        skillProficiencySpinner1.adapter = skillArrayAdapter
        skillProficiencySpinner2.adapter = skillArrayAdapter
        skillProficiencySpinner3.adapter = skillArrayAdapter
        skillProficiencySpinner4.adapter = skillArrayAdapter
        val proficiencyChoices = ArrayList<String>()
        val otherProficienciesTextView = findViewById<TextView>(R.id.other_proficiencies)
        when (character!!.getRace()) {
            "Human" -> {
                raceSpecificBonusTextView!!.visibility = View.VISIBLE
                raceSpecificBonusSpinner!!.visibility = View.VISIBLE
                raceSpecificBonusTextView!!.text = "As a Human, you are able to speak one extra language of your choice"
                raceSpecificBonusSpinner!!.adapter = languageArrayAdapter
            }
            "Half-Elf" -> {
                raceSpecificBonusTextView!!.visibility = View.VISIBLE
                raceSpecificBonusSpinner!!.visibility = View.VISIBLE
                raceSpecificBonusTextView!!.text = "As a Half-Elf, you are able to speak one extra language of your choice"
                raceSpecificBonusSpinner!!.adapter = languageArrayAdapter
            }
            "Warforged" -> {
                raceSpecificBonusTextView!!.visibility = View.VISIBLE
                raceSpecificBonusSpinner!!.visibility = View.VISIBLE
                raceSpecificBonusTextView!!.text = "As a Warforged, you gain an extra skill proficiency of your choice"
                raceSpecificBonusSpinner!!.adapter = skillArrayAdapter
            }
        }
        val startingHPAndHitDieTextView = findViewById<TextView>(R.id.starting_hp)
        val startingGoldEditText = findViewById<EditText>(R.id.starting_gold_edit_text)
        val equipmentSpinnerArray1: MutableList<String> = ArrayList()
        val equipmentSpinnerArray2: MutableList<String> = ArrayList()
        val equipmentSpinnerArray3: MutableList<String> = ArrayList()
        val equipmentSpinnerArray4: MutableList<String> = ArrayList()
        val levelOneChoices1: MutableList<String> = ArrayList()
        val levelOneChoices2: MutableList<String> = ArrayList()
        val subclassChoices: MutableList<String> = ArrayList()
        when (character!!.getCharacterClass()) {
            "Barbarian" -> {
                subclassHeader!!.text = "Choose a Primal Path"
                subclassChoices.add("Path of the Berserker")
                subclassChoices.add("Path of the Totem Warrior")
                skillProficiencySpinner3.visibility = View.GONE
                skillProficiencySpinner4.visibility = View.GONE
                startingEquipmentSpinner3!!.visibility = View.GONE
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Greataxe")
                equipmentSpinnerArray1.add("Battleaxe")
                equipmentSpinnerArray2.add("Handaxe x2")
                equipmentSpinnerArray2.add("Greatclub")
                startingHPAndHitDieTextView.setText(R.string.barbarian_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.barbarian_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s an explorer's pack and 4 javelins", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 2d4 x10", getString(R.string.starting_gp_roll))
            }
            "Bard" -> {
                subclassHeader!!.text = "Choose a Bard College"
                subclassChoices.add("College of Lore")
                subclassChoices.add("College of Valor")
                skillProficiencySpinner4.visibility = View.GONE
                startingEquipmentSpinner3!!.visibility = View.GONE
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Rapier")
                equipmentSpinnerArray1.add("Longsword")
                equipmentSpinnerArray1.add("Light Crossbow with bolts x20")
                equipmentSpinnerArray2.add("Diplomat's Pack")
                equipmentSpinnerArray2.add("Entertainer's Pack")
                startingHPAndHitDieTextView.setText(R.string.bard_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.bard_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s leather armor and a dagger", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 5d4 x10", getString(R.string.starting_gp_roll))
            }
            "Cleric" -> {
                subclassHeader!!.text = "Choose a Divine Domain"
                subclassChoices.add("Knowledge")
                subclassChoices.add("Life")
                subclassChoices.add("Light")
                subclassChoices.add("Nature")
                subclassChoices.add("Tempest")
                subclassChoices.add("Trickery")
                subclassChoices.add("War")
                skillProficiencySpinner3.visibility = View.GONE
                skillProficiencySpinner4.visibility = View.GONE
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Mace")
                equipmentSpinnerArray1.add("Warhammer")
                equipmentSpinnerArray2.add("Scale Mail")
                equipmentSpinnerArray2.add("Leather Armor")
                equipmentSpinnerArray2.add("Chain Mail")
                equipmentSpinnerArray3.add("Light Crossbow with bolts x20")
                equipmentSpinnerArray3.add("Greatclub")
                startingHPAndHitDieTextView.setText(R.string.cleric_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.cleric_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s a shield and a holy symbol", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 5d4 x10", getString(R.string.starting_gp_roll))
            }
            "Druid" -> {
                subclassHeader!!.text = "Choose a Druid Circle"
                subclassChoices.add("Circle of the Land")
                subclassChoices.add("Circle of the Moon")
                skillProficiencySpinner3.visibility = View.GONE
                skillProficiencySpinner4.visibility = View.GONE
                startingEquipmentSpinner3!!.visibility = View.GONE
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Wooden Shield")
                equipmentSpinnerArray1.add("Shortbow with Arrows x20")
                equipmentSpinnerArray2.add("Scimitar")
                equipmentSpinnerArray2.add("Quarterstaff")
                startingHPAndHitDieTextView.setText(R.string.druid_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.druid_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s leather armor, an explorer's pack, and a druidic focus", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 2d4 x10", getString(R.string.starting_gp_roll))
            }
            "Fighter" -> {
                subclassHeader!!.text = "Choose a Martial Archetype"
                subclassChoices.add("Battle Master")
                subclassChoices.add("Champion")
                subclassChoices.add("Eldritch Knight")
                levelOneChoiceHeader1!!.visibility = View.VISIBLE
                levelOneChoiceSpinner1!!.visibility = View.VISIBLE
                levelOneChoiceHeader1!!.text = "Choose a Fighting Style"
                levelOneChoices1.add("Archery")
                levelOneChoices1.add("Defense")
                levelOneChoices1.add("Dueling")
                levelOneChoices1.add("Great Weapon Fighting")
                levelOneChoices1.add("Protection")
                levelOneChoices1.add("Two-Weapon Fighting")
                skillProficiencySpinner3.visibility = View.GONE
                skillProficiencySpinner4.visibility = View.GONE
                equipmentSpinnerArray1.add("Chain Mail")
                equipmentSpinnerArray1.add("Leather Armor & Longbow with arrows x20")
                equipmentSpinnerArray2.add("Battleaxe and a shield")
                equipmentSpinnerArray2.add("Shortsword x2")
                equipmentSpinnerArray3.add("Light Crossbow with bolts x20")
                equipmentSpinnerArray3.add("Handaxe x2")
                equipmentSpinnerArray4.add("Dungeoneer's Pack")
                equipmentSpinnerArray4.add("Explorer's Pack")
                startingHPAndHitDieTextView.setText(R.string.fighter_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.fighter_other_proficiencies))
                extraStartingEquipmentTextView!!.visibility = View.GONE
                startingGoldEditText.hint = String.format("%s 5d4 x10", getString(R.string.starting_gp_roll))
            }
            "Monk" -> {
                subclassHeader!!.text = "Choose a Monastic Tradition"
                subclassChoices.add("Way of the Open Hand")
                subclassChoices.add("Way of Shadow")
                skillProficiencySpinner3.visibility = View.GONE
                skillProficiencySpinner4.visibility = View.GONE
                startingEquipmentSpinner3!!.visibility = View.GONE
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Shortsword")
                equipmentSpinnerArray1.add("Quarterstaff")
                equipmentSpinnerArray2.add("Dungeoneer's Pack")
                equipmentSpinnerArray2.add("Explorer's Pack")
                startingHPAndHitDieTextView.setText(R.string.monk_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.monk_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s 10 darts", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 5d4", getString(R.string.starting_gp_roll))
            }
            "Paladin" -> {
                subclassHeader!!.text = "Choose a Sacred Oath"
                subclassChoices.add("Oath of Devotion")
                subclassChoices.add("Oath of the Ancients")
                subclassChoices.add("Oath of Vengeance")
                skillProficiencySpinner3.visibility = View.GONE
                skillProficiencySpinner4.visibility = View.GONE
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Longsword and Shield")
                equipmentSpinnerArray1.add("Shortsword x2")
                equipmentSpinnerArray2.add("Javelin x5")
                equipmentSpinnerArray2.add("Mace")
                equipmentSpinnerArray3.add("Priest's Pack")
                equipmentSpinnerArray3.add("Explorer's Pack")
                startingHPAndHitDieTextView.setText(R.string.paladin_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.paladin_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s chain mail and a holy symbol", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 5d4 x10", getString(R.string.starting_gp_roll))
            }
            "Ranger" -> {
                subclassHeader!!.text = "Choose a Ranger Archetype"
                subclassChoices.add("Hunter")
                subclassChoices.add("Beast Master")
                //TODO: I now have a string-array of favored terrain/enemies. Fix it here.
                levelOneChoiceHeader1!!.visibility = View.VISIBLE
                levelOneChoiceSpinner1!!.visibility = View.VISIBLE
                levelOneChoiceHeader1!!.text = "Choose a Favored Enemy"
                levelOneChoiceHeader2!!.visibility = View.VISIBLE
                levelOneChoiceSpinner2!!.visibility = View.VISIBLE
                levelOneChoiceHeader2!!.text = "Choose a Favored Terrain"
                levelOneChoices1.add("Aberrations")
                levelOneChoices1.add("Beasts")
                levelOneChoices1.add("Celestials")
                levelOneChoices1.add("Constructs")
                levelOneChoices1.add("Dragons")
                levelOneChoices1.add("Elementals")
                levelOneChoices1.add("Fey")
                levelOneChoices1.add("Fiends")
                levelOneChoices1.add("Giants")
                levelOneChoices1.add("Monstrosities")
                levelOneChoices1.add("Oozes")
                levelOneChoices1.add("Plants")
                levelOneChoices1.add("Undead")
                levelOneChoices2.add("Arctic")
                levelOneChoices2.add("Coast")
                levelOneChoices2.add("Desert")
                levelOneChoices2.add("Forest")
                levelOneChoices2.add("Grassland")
                levelOneChoices2.add("Mountain")
                levelOneChoices2.add("Swamp")
                levelOneChoices2.add("Underdark")
                skillProficiencySpinner4.visibility = View.GONE
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Scale Mail")
                equipmentSpinnerArray1.add("Leather Armor")
                equipmentSpinnerArray2.add("Shortsword x2")
                equipmentSpinnerArray2.add("Handaxe x2")
                equipmentSpinnerArray3.add("Dungeoneer's Pack")
                equipmentSpinnerArray3.add("Explorer's Pack")
                startingHPAndHitDieTextView.setText(R.string.ranger_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.ranger_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s a longbow and a quiver of 20 arrows", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 5d4 x10", getString(R.string.starting_gp_roll))
            }
            "Rogue" -> {
                subclassHeader!!.text = "Choose a Roguish Archetype"
                subclassChoices.add("Thief")
                subclassChoices.add("Assassin")
                subclassChoices.add("Arcane Trickster")
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Rapier")
                equipmentSpinnerArray1.add("Shortsword")
                equipmentSpinnerArray2.add("Shortbow with arrows x20")
                equipmentSpinnerArray2.add("Shortsword")
                equipmentSpinnerArray3.add("Burglar's Pack")
                equipmentSpinnerArray3.add("Dungeoneer's Pack")
                equipmentSpinnerArray3.add("Explorer's Pack")
                startingHPAndHitDieTextView.setText(R.string.rogue_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.rogue_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s leather armor, 2 daggers, and thieves' tools", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 4d4 x10", getString(R.string.starting_gp_roll))
            }
            "Sorcerer" -> {
                subclassHeader!!.text = "Choose a Sorcerous Origin"
                subclassChoices.add("Draconic Bloodline")
                subclassChoices.add("Wild Magic")
                subclassChoices.add("Phoenix Sorcery")
                subclassChoices.add("Stone Sorcery")
                skillProficiencySpinner3.visibility = View.GONE
                skillProficiencySpinner4.visibility = View.GONE
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Light Crossbow with bolts x20")
                equipmentSpinnerArray1.add("Quarterstaff")
                equipmentSpinnerArray2.add("Component Pouch")
                equipmentSpinnerArray2.add("Arcane Focus")
                equipmentSpinnerArray3.add("Dungeoneer's Pack")
                equipmentSpinnerArray3.add("Explorer's Pack")
                startingHPAndHitDieTextView.setText(R.string.sorcerer_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.sorcerer_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s 2 daggers", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 3d4 x10", getString(R.string.starting_gp_roll))
            }
            "Warlock" -> {
                subclassHeader!!.text = "Choose an Otherworldly Patron"
                subclassChoices.add("The Archfey")
                subclassChoices.add("The Fiend")
                subclassChoices.add("The Great Old One")
                skillProficiencySpinner3.visibility = View.GONE
                skillProficiencySpinner4.visibility = View.GONE
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Light Crossbow with bolts x20")
                equipmentSpinnerArray1.add("Mace")
                equipmentSpinnerArray2.add("Component Pouch")
                equipmentSpinnerArray2.add("Arcane Focus")
                equipmentSpinnerArray3.add("Scholar's Pack")
                equipmentSpinnerArray3.add("Dungeoneer's Pack")
                startingHPAndHitDieTextView.setText(R.string.warlock_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.warlock_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s leather armor, a handaxe, and 2 daggers", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 4d4 x10", getString(R.string.starting_gp_roll))
            }
            "Wizard" -> {
                subclassHeader!!.text = "Choose an Arcane Tradition"
                subclassChoices.add("School of Abjuration")
                subclassChoices.add("School of Conjuration")
                subclassChoices.add("School of Divination")
                subclassChoices.add("School of Enchantment")
                subclassChoices.add("School of Evocation")
                subclassChoices.add("School of Illusion")
                subclassChoices.add("School of Necromancy")
                subclassChoices.add("School of Transmutation")
                skillProficiencySpinner3.visibility = View.GONE
                skillProficiencySpinner4.visibility = View.GONE
                startingEquipmentSpinner3!!.visibility = View.GONE
                startingEquipmentSpinner4!!.visibility = View.GONE
                equipmentSpinnerArray1.add("Quarterstaff")
                equipmentSpinnerArray1.add("Dagger")
                equipmentSpinnerArray2.add("Scholar's Pack")
                equipmentSpinnerArray2.add("Explorer's Pack")
                startingHPAndHitDieTextView.setText(R.string.wizard_hit_die)
                otherProficienciesTextView.text = String.format("You also have proficiency with %s", getString(R.string.wizard_other_proficiencies))
                extraStartingEquipmentTextView!!.text = String.format("%s a spellbook", getString(R.string.starting_equipment_info))
                startingGoldEditText.hint = String.format("%s 4d4 x10", getString(R.string.starting_gp_roll))
            }
        }
        val equipmentArrayAdapter1 = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray1)
        val equipmentArrayAdapter2 = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray2)
        val equipmentArrayAdapter3 = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray3)
        val equipmentArrayAdapter4 = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, equipmentSpinnerArray4)
        startingEquipmentSpinner1!!.adapter = equipmentArrayAdapter1
        startingEquipmentSpinner2!!.adapter = equipmentArrayAdapter2
        startingEquipmentSpinner3!!.adapter = equipmentArrayAdapter3
        startingEquipmentSpinner4!!.adapter = equipmentArrayAdapter4
        val levelOneChoicesAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, levelOneChoices1)
        levelOneChoiceSpinner1!!.adapter = levelOneChoicesAdapter
        val levelOneChoicesAdapter2 = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, levelOneChoices2)
        levelOneChoiceSpinner2!!.adapter = levelOneChoicesAdapter2
        val subclassAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, subclassChoices)
        subclassSpinner!!.adapter = subclassAdapter
        val finishButton = findViewById<Button>(R.id.button_finished_character_creation)
        finishButton.setOnClickListener {
            if (startingGoldEditText.text.toString() == "") {
                Toast.makeText(applicationContext, "roll for your starting gold", Toast.LENGTH_SHORT).show()
            } else {
                proficiencyChoices.add(skillProficiencySpinner1.selectedItem.toString())
                proficiencyChoices.add(skillProficiencySpinner2.selectedItem.toString())
                if (skillProficiencySpinner3.visibility == View.VISIBLE) {
                    proficiencyChoices.add(skillProficiencySpinner3.selectedItem.toString())
                }
                if (skillProficiencySpinner4.visibility == View.VISIBLE) {
                    proficiencyChoices.add(skillProficiencySpinner4.selectedItem.toString())
                }
                val race = character!!.getRace()
                val characterClass = character!!.getCharacterClass()
                val alignment = character!!.getAlignment()
                val name = character!!.getName()
                val statValuesList = getStatBonuses(character, character!!.getStatValues())
                val characterSubclass = subclassSpinner!!.selectedItem.toString()
                val currency = ArrayList<Int>()
                val startingGold = startingGoldEditText.text.toString().toInt()
                //Initial Armor Class
                currency.add(10 + (this as DetailActivity?)!!.calculateModifier(character.getStatValues()[1]))
                //5 kinds of currency
                currency.add(0)
                currency.add(0)
                currency.add(0)
                currency.add(startingGold)
                currency.add(0)
                //Initial Current HP
                currency.add(8 + calculateModifier(character.getStatValues()[2]))
                //Temporary HP
                currency.add(0)
                //Maximum HP
                currency.add(8 + calculateModifier(character.getStatValues()[2]))
                //Any class-specific changes to HP and/or AC, as well as raceAndClassBonusStats
                val bonusStats = ArrayList<String>()
                val bonusStats2 = ArrayList<String>()
                val languages = StringBuilder()
                languages.append(getString(R.string.languages_known))
                when (character.getRace()) {
                    "Dwarf" -> {
                        //Mountain Dwarf is chosen for this
                        languages.append(" Common, Dwarvish, ")
                        bonusStats.add(languages.toString())
                        bonusStats.add("Darkvision: 60 ft")
                        bonusStats.add("Dwarven Resilience: Advantage on saving throws against poison and resistance to poison damage")
                        bonusStats.add("Stonecunning: Add double your proficiency bonus to INT History checks relating to stonework")
                    }
                    "Elf" -> {
                        //Wood Elf is chosen for this
                        languages.append(" Common, Elvish, ")
                        bonusStats.add(languages.toString())
                        bonusStats.add("Darkvision: 60 ft")
                        bonusStats.add("Fey Ancestry: Advantage on saving throws against being charmed")
                        bonusStats.add("Fey Ancestry: Magic effects cannot put you to sleep")
                        bonusStats.add("Trance: You can get a full Long Rest by only meditating for 4 hours")
                    }
                    "Goliath" -> {
                        languages.append(" Common, Giant, ")
                        bonusStats.add(languages.toString())
                        bonusStats.add("Stone's Endurance: Once per short/long rest, you can use your reaction after taking damage to reduce the damage by 1d12 + your CON modifier")
                        bonusStats.add("Powerful Build: You count for 1 size larger when determining your carrying capacity and the weight you can carry/push/lift/etc.")
                    }
                    "Halfling" -> {
                        //Stout Halfling is chosen for this
                        languages.append(" Common, Halfling, ")
                        bonusStats.add(languages.toString())
                        bonusStats.add("Lucky: When you roll a 1 on a D20, you can choose to re-roll and use the new roll")
                        bonusStats.add("Brave: Advantage on saving throws against being frightened")
                        bonusStats.add("Stout Resilience: Advantage on saving throws against poison and resistance to poison damage")
                    }
                    "Human" -> {
                        languages.append(" Common, ")
                        languages.append(raceSpecificBonusSpinner!!.selectedItem.toString())
                        bonusStats.add(languages.toString())
                    }
                    "Dragonborn" -> {
                        //Red Dragonborn is chosen for this
                        languages.append(" Common, Draconic, ")
                        bonusStats.add(languages.toString())
                        bonusStats.add("Breath Weapon: Once per short or long rest, you can use your breath attack as an action. 15ft. cone of fire" /*+ depends on which color is chosen*/)
                        bonusStats.add("Draconic Resistance: You have resistance to fire damage" /*damage type depends on color chosen*/)
                    }
                    "Gnome" -> {
                        //Forest Gnome is chosen for this
                        languages.append(" Common, Gnomish, ")
                        bonusStats.add(languages.toString())
                        bonusStats.add("Darkvision: 60 ft")
                        bonusStats.add("Gnome Cunning: Advantage on INT, WIS, and CHA saving throws against magic")
                        bonusStats.add("Speak With Small Beasts: Through sounds and gestures, you can communicate simple ideas with small animals")
                    }
                    "Half-Elf" -> {
                        languages.append(" Common, Elvish, ")
                        languages.append(raceSpecificBonusSpinner!!.selectedItem.toString())
                        bonusStats.add(languages.toString())
                        bonusStats.add("Darkvision: 60 ft")
                        bonusStats.add("Fey Ancestry: Advantage on saving throws against being charmed")
                        bonusStats.add("Fey Ancestry: Magic effects cannot put you to sleep")
                    }
                    "Half-Orc" -> {
                        languages.append(" Common, Orcish, ")
                        bonusStats.add(languages.toString())
                        bonusStats.add("Darkvision: 60 ft")
                        bonusStats.add("Relentless Endurance: When you are reduced to 0hp, you can drop to 1 instead. You can do this once per Long Rest")
                        bonusStats.add("Savage Attacks: When you score a critical hit, you can roll an extra of the weapon's damage die")
                    }
                    "Tabaxi" -> {
                        languages.append(" Common, ")
                        bonusStats.add(languages.toString())
                        bonusStats.add("Darkvision: 60 ft")
                        bonusStats.add("Cat's Claws: You have a climbing speed of 20ft. Also, your unarmed strikes deal 1d4 + your STR modifier slashing damage")
                        bonusStats.add("Feline Agility: During combat, you can double your speed during your turn. Once you use this ability, you cannot use it again until you spend 1 turn not moving.")
                    }
                    "Tiefling" -> {
                        languages.append(" Common, Infernal, ")
                        bonusStats.add(languages.toString())
                        bonusStats.add("Darkvision: 60 ft")
                        bonusStats.add("Hellish Resistance: You are resistant to Fire damage")
                    }
                    "Warforged" -> {
                        languages.append(" Common, ")
                        bonusStats.add(languages.toString())
                        bonusStats.add("Constructed Resilience: You have resistance to poison damage and advantage on saving throws against being poisoned")
                        bonusStats.add("You are immune to disease, and you don't need to eat, drink, breathe, or sleep, and magic cannot put you to sleep.")
                        bonusStats.add("Sentry's Rest: When you take a long rest, you must spend at least 6 hours in an inactive state rather than sleeping. You are not unconscious and you can see/hear as normal")
                        bonusStats.add("Integrated Protection: To don/doff armor, you must spend 1 hour incorporating/unincorporating it with your armor.")
                        currency[0] = currency[0] + 1
                        proficiencyChoices.add(raceSpecificBonusSpinner!!.selectedItem.toString())
                    }
                }
                when (character.getCharacterClass()) {
                    "Barbarian" -> {
                        bonusStats.add(getString(R.string.rage_description))
                        currency[6] = 12 + calculateModifier(character.getStatValues()[2])
                        currency[8] = 12 + calculateModifier(character.getStatValues()[2])
                        currency[0] = (10 + calculateModifier(character.getStatValues()[1])
                                + calculateModifier(character.getStatValues()[2]))
                    }
                    "Bard" -> bonusStats.add(getString(R.string.bardic_inspiration_description))
                    "Cleric" -> when (characterSubclass) {
                        "Knowledge" -> {
                        }
                        "Life" ->                                     //Proficiency in Heavy Armor
                            bonusStats.add(getString(R.string.disciple_of_life))
                        "Light" -> bonusStats.add(getString(R.string.warding_flare))
                        "Nature" -> {
                        }
                        "Trickery" -> bonusStats.add(getString(R.string.blessing_of_the_trickster))
                        "War" -> bonusStats.add(getString(R.string.war_priest))
                    }
                    "Druid" -> languages.append("Druidic, ")
                    "Fighter" -> {
                        when (levelOneChoiceSpinner1!!.selectedItem.toString()) {
                            "Archery" -> bonusStats.add("Fighting Style: " + getString(R.string.archery))
                            "Defense" -> bonusStats.add("Fighting Style: " + getString(R.string.defense))
                            "Dueling" -> bonusStats.add("Fighting Style: " + getString(R.string.dueling))
                            "Great Weapon Fighting" -> bonusStats.add("Fighting Style: " + getString(R.string.great_weapon_fighting))
                            "Protection" -> bonusStats.add("Fighting Style: " + getString(R.string.protection))
                            "Two-Weapon Fighting" -> bonusStats.add("Fighting Style: " + getString(R.string.two_weapon_fighting))
                        }
                        bonusStats.add(getString(R.string.second_wind))
                        currency[6] = 10 + calculateModifier(character.getStatValues()[2])
                        currency[8] = 10 + calculateModifier(character.getStatValues()[2])
                    }
                    "Monk" -> {
                        bonusStats.add(getString(R.string.martial_arts))
                        currency[0] = (10 + calculateModifier(character.getStatValues()[1])
                                + calculateModifier(character.getStatValues()[4]))
                    }
                    "Paladin" -> {
                        bonusStats.add(getString(R.string.divine_sense))
                        bonusStats.add(getString(R.string.lay_on_hands))
                        currency[6] = 10 + calculateModifier(character.getStatValues()[2])
                        currency[8] = 10 + calculateModifier(character.getStatValues()[2])
                    }
                    "Ranger" -> {
                        bonusStats.add(getString(R.string.favored_terrain_description))
                        bonusStats.add(getString(R.string.favored_enemy_description))
                        bonusStats2.add(levelOneChoiceSpinner2!!.selectedItem.toString())
                        bonusStats2.add(levelOneChoiceSpinner1!!.selectedItem.toString())
                        currency[6] = 10 + calculateModifier(character.getStatValues()[2])
                        currency[8] = 10 + calculateModifier(character.getStatValues()[2])
                    }
                    "Rogue" -> {
                        languages.append("Thieve's Cant, ")
                        bonusStats.add(getString(R.string.sneak_attack_description))
                    }
                    "Sorcerer" -> {
                        currency[6] = 6 + calculateModifier(character.getStatValues()[2])
                        currency[8] = 6 + calculateModifier(character.getStatValues()[2])
                        when (characterSubclass) {
                            "Draconic Bloodline" -> {
                                currency[6] = currency[6] + 1
                                currency[8] = currency[8] + 1
                                currency[0] = 13 + calculateModifier(character.getStatValues()[1])
                                if (character.getRace() != "Dragonborn") {
                                    languages.append("Draconic")
                                }
                            }
                            "Wild Magic" -> {
                                bonusStats.add(getString(R.string.wild_magic_surge))
                                bonusStats.add(getString(R.string.tides_of_chaos))
                            }
                            "Phoenix Sorcery" -> {
                                bonusStats.add(getString(R.string.ignite))
                                bonusStats.add(getString(R.string.mantle_of_flame))
                            }
                            "Stone Sorcery" -> {
                                currency[6] = currency[6] + 1
                                currency[8] = currency[8] + 1
                                bonusStats.add(getString(R.string.stones_durability))
                            }
                        }
                    }
                    "Warlock" -> when (characterSubclass) {
                        "The Archfey" -> bonusStats.add(getString(R.string.fey_presence))
                        "The Fiend" -> bonusStats.add(getString(R.string.dark_ones_blessing))
                        "The Great Old One" -> bonusStats.add(getString(R.string.awakened_mind))
                    }
                    "Wizard" -> {
                        bonusStats.add(getString(R.string.arcane_recovery))
                        currency[6] = 6 + calculateModifier(character.getStatValues()[2])
                        currency[8] = 6 + calculateModifier(character.getStatValues()[2])
                    }
                }
                val spellSlotsClicked = ArrayList<String>()
                for (i in 0..21) {
                    spellSlotsClicked.add("no")
                }
                launchDetailActivity(Character(1, race, characterClass, alignment, name,
                        statValuesList, proficiencyChoices, instantiateInventory(startingEquipmentChoices),
                        currency, characterSubclass, ArrayList(), spellSlotsClicked, bonusStats, bonusStats2))
            }
        }
    }

    private fun launchDetailActivity(character: Character) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra((this as DetailActivity?)!!.CHARACTER, character)
        startActivity(intent)
    }

    private fun getStatBonuses(character: Character?, statArray: ArrayList<Int>): ArrayList<Int> {
        var strength = statArray[0]
        var dexterity = statArray[1]
        var constitution = statArray[2]
        var intelligence = statArray[3]
        var wisdom = statArray[4]
        var charisma = statArray[5]
        when (character!!.getRace()) {
            "Dwarf" -> {
                constitution += 2
                //Mountain Dwarf is chosen for this
                strength += 2
            }
            "Elf" -> {
                dexterity += 2
                //Wood Elf is chosen for this
                wisdom += 1
            }
            "Goliath", "Half-Orc" -> {
                strength += 2
                constitution += 1
            }
            "Halfling" -> {
                dexterity += 2
                //Stout Halfling is chosen for this
                constitution += 1
            }
            "Human" -> {
                strength += 1
                dexterity += 1
                constitution += 1
                intelligence += 1
                wisdom += 1
                charisma += 1
            }
            "Dragonborn" -> {
                //Red Dragonborn is chosen for this
                strength += 2
                charisma += 1
            }
            "Gnome" -> {
                intelligence += 2
                //Forest Gnome is chosen for this
                dexterity += 1
            }
            "Half-Elf" -> {
                charisma += 2
                dexterity += 1
                wisdom += 1
            }
            "Tabaxi" -> {
                dexterity += 2
                charisma += 1
            }
            "Tiefling" -> {
                intelligence += 1
                charisma += 2
            }
            "Warforged" -> {
                constitution += 2
                strength += 1
            }
        }
        statArray[0] = strength
        statArray[1] = dexterity
        statArray[2] = constitution
        statArray[3] = intelligence
        statArray[4] = wisdom
        statArray[5] = charisma
        return statArray
    }

    private fun instantiateInventory(inventory: ArrayList<String>): ArrayList<String> {
        inventory.add(startingEquipmentSpinner1!!.selectedItem.toString())
        inventory.add(startingEquipmentSpinner2!!.selectedItem.toString())
        if (startingEquipmentSpinner3!!.visibility == View.VISIBLE) {
            inventory.add(startingEquipmentSpinner3!!.selectedItem.toString())
        }
        if (startingEquipmentSpinner4!!.visibility == View.VISIBLE) {
            inventory.add(startingEquipmentSpinner4!!.selectedItem.toString())
        }
        when (character!!.getCharacterClass()) {
            "Barbarian" -> {
                inventory.add("Explorer's Pack")
                inventory.add("Javelin x4")
            }
            "Bard" -> {
                inventory.add("Leather Armor")
                inventory.add("Dagger")
                inventory.add("Lute")
            }
            "Cleric" -> {
                inventory.add("Shield")
                inventory.add("Holy Symbol")
            }
            "Druid" -> {
                inventory.add("Leather Armor")
                inventory.add("Explorer's Pack")
                inventory.add("Druidic Focus")
            }
            "Monk" -> inventory.add("Dart x10")
            "Paladin" -> {
                inventory.add("Chain Mail")
                inventory.add("Holy Symbol")
            }
            "Ranger" -> {
                inventory.add("Longbow")
                inventory.add("Quiver w/ Arrows x20")
            }
            "Rogue" -> {
                inventory.add("Leather Armor")
                inventory.add("Dagger x2")
                inventory.add("Thieve's Tools")
            }
            "Sorcerer" -> inventory.add("Dagger x2")
            "Warlock" -> {
                inventory.add("Leather Armor")
                inventory.add("Dagger x2")
                inventory.add("Handaxe")
            }
            "Wizard" -> {
                inventory.add("Spellbook")
                inventory.add("Arcane Focus")
            }
        }
        return inventory
    }

    companion object {
        const val CHARACTER = "character"
        private var character: Character? = null
    }
}