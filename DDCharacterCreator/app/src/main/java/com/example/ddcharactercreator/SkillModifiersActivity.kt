package com.example.ddcharactercreator

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class SkillModifiersActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.skill_modifiers_layout)
        val character: Character = (this as DetailActivity?)!!.character
        if (character.getLevel() > 16) {
            proficiencyBonus = 6
        } else if (character.getLevel() > 12) {
            proficiencyBonus = 5
        } else if (character.getLevel() > 8) {
            proficiencyBonus = 4
        } else if (character.getLevel() > 4) {
            proficiencyBonus = 3
        }
        val toolProficiencyLabel = findViewById<TextView>(R.id.tool_proficiencies_label)
        val toolProficienciesTextView = findViewById<TextView>(R.id.tool_proficiencies)
        val toolProficienciesModifier = findViewById<TextView>(R.id.tool_proficiency_modifier)
        val weaponArmorProficienciesListView = findViewById<RecyclerView>(R.id.weapon_armor_proficiencies)
        val weaponArmorProficiencies = ArrayList<String>()

        //Skill Proficiencies
        val acrobaticsModifier = findViewById<TextView>(R.id.acrobatics_modifier)
        val animalHandlingModifier = findViewById<TextView>(R.id.animal_handling_modifier)
        val arcanaModifier = findViewById<TextView>(R.id.arcana_modifier)
        val athleticsModifier = findViewById<TextView>(R.id.athletics_modifier)
        val deceptionModifier = findViewById<TextView>(R.id.deception_modifier)
        val historyModifier = findViewById<TextView>(R.id.history_modifier)
        val insightModifier = findViewById<TextView>(R.id.insight_modifier)
        val intimidationModifier = findViewById<TextView>(R.id.intimidation_modifier)
        val investigationModifier = findViewById<TextView>(R.id.investigation_modifier)
        val medicineModifier = findViewById<TextView>(R.id.medicine_modifier)
        val natureModifier = findViewById<TextView>(R.id.nature_modifier)
        val perceptionModifier = findViewById<TextView>(R.id.perception_modifier)
        val performanceModifier = findViewById<TextView>(R.id.performance_modifier)
        val persuasionModifier = findViewById<TextView>(R.id.persuasion_modifier)
        val religionModifier = findViewById<TextView>(R.id.religion_modifier)
        val sleightOfHandModifier = findViewById<TextView>(R.id.sleight_of_hand_modifier)
        val stealthModifier = findViewById<TextView>(R.id.stealth_modifier)
        val survivalModifier = findViewById<TextView>(R.id.survival_modifier)
        val acrobaticsLabel = findViewById<TextView>(R.id.acrobatics_label)
        acrobaticsLabel.setOnClickListener { changeProficiencyStatus(acrobaticsLabel, acrobaticsModifier, "Acrobatics", 1) }
        val animalHandlingLabel = findViewById<TextView>(R.id.animal_handling_label)
        animalHandlingLabel.setOnClickListener { changeProficiencyStatus(animalHandlingLabel, animalHandlingModifier, "Animal Handling", 4) }
        val arcanaLabel = findViewById<TextView>(R.id.arcana_label)
        arcanaLabel.setOnClickListener { changeProficiencyStatus(arcanaLabel, arcanaModifier, "Arcana", 3) }
        val athleticsLabel = findViewById<TextView>(R.id.athletics_label)
        athleticsLabel.setOnClickListener { changeProficiencyStatus(athleticsLabel, athleticsModifier, "Athletics", 0) }
        val deceptionLabel = findViewById<TextView>(R.id.deception_label)
        deceptionLabel.setOnClickListener { changeProficiencyStatus(deceptionLabel, deceptionModifier, "Deception", 5) }
        val historyLabel = findViewById<TextView>(R.id.history_label)
        historyLabel.setOnClickListener { changeProficiencyStatus(historyLabel, historyModifier, "History", 3) }
        val insightLabel = findViewById<TextView>(R.id.insight_label)
        insightLabel.setOnClickListener { changeProficiencyStatus(insightLabel, insightModifier, "Insight", 4) }
        val intimidationLabel = findViewById<TextView>(R.id.intimidation_label)
        intimidationLabel.setOnClickListener { changeProficiencyStatus(intimidationLabel, intimidationModifier, "Intimidation", 5) }
        val investigationLabel = findViewById<TextView>(R.id.investigation_label)
        investigationLabel.setOnClickListener { changeProficiencyStatus(investigationLabel, investigationModifier, "Investigation", 3) }
        val medicineLabel = findViewById<TextView>(R.id.medicine_label)
        medicineLabel.setOnClickListener { changeProficiencyStatus(medicineLabel, medicineModifier, "Medicine", 4) }
        val natureLabel = findViewById<TextView>(R.id.nature_label)
        natureLabel.setOnClickListener { changeProficiencyStatus(natureLabel, natureModifier, "Nature", 3) }
        val perceptionLabel = findViewById<TextView>(R.id.perception_label)
        perceptionLabel.setOnClickListener { changeProficiencyStatus(perceptionLabel, perceptionModifier, "Perception", 4) }
        val performanceLabel = findViewById<TextView>(R.id.performance_label)
        performanceLabel.setOnClickListener { changeProficiencyStatus(performanceLabel, performanceModifier, "Performance", 5) }
        val persuasionLabel = findViewById<TextView>(R.id.persuasion_label)
        persuasionLabel.setOnClickListener { changeProficiencyStatus(persuasionLabel, persuasionModifier, "Persuasion", 5) }
        val religionLabel = findViewById<TextView>(R.id.religion_label)
        religionLabel.setOnClickListener { changeProficiencyStatus(religionLabel, religionModifier, "Religion", 3) }
        val sleightOfHandLabel = findViewById<TextView>(R.id.sleight_of_hand_label)
        sleightOfHandLabel.setOnClickListener { changeProficiencyStatus(sleightOfHandLabel, sleightOfHandModifier, "Sleight of Hand", 1) }
        val stealthLabel = findViewById<TextView>(R.id.stealth_label)
        stealthLabel.setOnClickListener { changeProficiencyStatus(stealthLabel, stealthModifier, "Stealth", 1) }
        val survivalLabel = findViewById<TextView>(R.id.survival_label)
        survivalLabel.setOnClickListener { changeProficiencyStatus(survivalLabel, survivalModifier, "Survival", 4) }
        acrobaticsModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]))
        animalHandlingModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]))
        arcanaModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]))
        athleticsModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[0]))
        deceptionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]))
        historyModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]))
        insightModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]))
        intimidationModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]))
        investigationModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]))
        medicineModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]))
        natureModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]))
        perceptionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]))
        performanceModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]))
        persuasionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]))
        religionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]))
        sleightOfHandModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]))
        stealthModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]))
        survivalModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]))
        when (character.getRace()) {
            "Elf" -> if (!character.getProficiencyChoices().contains("Perception")) {
                character.getProficiencyChoices().add("Perception")
            }
            "Goliath" -> if (!character.getProficiencyChoices().contains("Athletics")) {
                character.getProficiencyChoices().add("Athletics")
            }
            "Half-Elf" -> {
                if (!character.getProficiencyChoices().contains("Perception")) {
                    character.getProficiencyChoices().add("Perception")
                }
                if (!character.getProficiencyChoices().contains("Insight")) {
                    character.getProficiencyChoices().add("Insight")
                }
            }
            "Half-Orc" -> if (!character.getProficiencyChoices().contains("Intimidation")) {
                character.getProficiencyChoices().add("Intimidation")
            }
            "Tabaxi" -> {
                if (!character.getProficiencyChoices().contains("Perception")) {
                    character.getProficiencyChoices().add("Perception")
                }
                if (!character.getProficiencyChoices().contains("Stealth")) {
                    character.getProficiencyChoices().add("Stealth")
                }
            }
        }
        for (i in character.getProficiencyChoices().indices) {
            when (character.getProficiencyChoices()[i]) {
                "Acrobatics" -> {
                    acrobaticsModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]) + proficiencyBonus)
                    acrobaticsLabel.setTextColor(getColor(R.color.proficiency_blue))
                    acrobaticsModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Animal Handling" -> {
                    animalHandlingModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus)
                    animalHandlingLabel.setTextColor(getColor(R.color.proficiency_blue))
                    animalHandlingModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Arcana" -> {
                    arcanaModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus)
                    arcanaLabel.setTextColor(getColor(R.color.proficiency_blue))
                    arcanaModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Athletics" -> {
                    athleticsModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[0]) + proficiencyBonus)
                    athleticsLabel.setTextColor(getColor(R.color.proficiency_blue))
                    athleticsModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Deception" -> {
                    deceptionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus)
                    deceptionLabel.setTextColor(getColor(R.color.proficiency_blue))
                    deceptionModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "History" -> {
                    historyModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus)
                    historyLabel.setTextColor(getColor(R.color.proficiency_blue))
                    historyModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Insight" -> {
                    insightModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus)
                    insightLabel.setTextColor(getColor(R.color.proficiency_blue))
                    insightModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Intimidation" -> {
                    intimidationModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus)
                    intimidationLabel.setTextColor(getColor(R.color.proficiency_blue))
                    intimidationModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Investigation" -> {
                    investigationModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus)
                    investigationLabel.setTextColor(getColor(R.color.proficiency_blue))
                    investigationModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Medicine" -> {
                    medicineModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus)
                    medicineLabel.setTextColor(getColor(R.color.proficiency_blue))
                    medicineModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Nature" -> {
                    natureModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus)
                    natureLabel.setTextColor(getColor(R.color.proficiency_blue))
                    natureModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Perception" -> {
                    perceptionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus)
                    perceptionLabel.setTextColor(getColor(R.color.proficiency_blue))
                    perceptionModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Performance" -> {
                    performanceModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus)
                    performanceLabel.setTextColor(getColor(R.color.proficiency_blue))
                    performanceModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Persuasion" -> {
                    persuasionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus)
                    persuasionLabel.setTextColor(getColor(R.color.proficiency_blue))
                    persuasionModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Religion" -> {
                    religionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus)
                    religionLabel.setTextColor(getColor(R.color.proficiency_blue))
                    religionModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Sleight of Hand" -> {
                    sleightOfHandModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]) + proficiencyBonus)
                    sleightOfHandLabel.setTextColor(getColor(R.color.proficiency_blue))
                    sleightOfHandModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Stealth" -> {
                    stealthModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]) + proficiencyBonus)
                    stealthLabel.setTextColor(getColor(R.color.proficiency_blue))
                    stealthModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
                "Survival" -> {
                    survivalModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus)
                    survivalLabel.setTextColor(getColor(R.color.proficiency_blue))
                    survivalModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
            }
        }
        //Bard's Jack of All Trades
        if (character.getCharacterClass() == "Bard" && character.getLevel() >= 2) {
            val skillsList = ArrayList<String>()
            skillsList.addAll(getResources().getStringArray(R.array.skill_proficiencies_array))
            for (i in skillsList.indices) {
                if (!character.getProficiencyChoices().contains(skillsList[i]) && !character.getClassBasedBonusStats2().contains(skillsList[i])) {
                    when (skillsList[i]) {
                        "Acrobatics" -> acrobaticsModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]) + proficiencyBonus / 2)
                        "Animal Handling" -> animalHandlingModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus / 2)
                        "Arcana" -> arcanaModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus / 2)
                        "Athletics" -> athleticsModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[0]) + proficiencyBonus / 2)
                        "Deception" -> deceptionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus / 2)
                        "History" -> historyModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus / 2)
                        "Insight" -> insightModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus / 2)
                        "Intimidation" -> intimidationModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus / 2)
                        "Investigation" -> investigationModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus / 2)
                        "Medicine" -> medicineModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus / 2)
                        "Nature" -> natureModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus / 2)
                        "Perception" -> perceptionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus / 2)
                        "Performance" -> performanceModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus / 2)
                        "Persuasion" -> persuasionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus / 2)
                        "Religion" -> religionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus / 2)
                        "Sleight of Hand" -> sleightOfHandModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]) + proficiencyBonus / 2)
                        "Stealth" -> stealthModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]) + proficiencyBonus / 2)
                        "Survival" -> survivalModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus / 2)
                    }
                }
            }
        }
        //Expertise
        if (character.getCharacterClass() == "Bard" || character.getCharacterClass() == "Rogue") {
            val skillsList = ArrayList<String>()
            skillsList.addAll(getResources().getStringArray(R.array.skill_proficiencies_array))
            skillsList.add("Thieve's Tools")
            for (i in skillsList.indices) {
                if (character.getClassBasedBonusStats2().contains(skillsList[i])) {
                    when (skillsList[i]) {
                        "Acrobatics" -> {
                            acrobaticsLabel.setTextColor(getColor(R.color.expertise_blue))
                            acrobaticsModifier.setTextColor(getColor(R.color.expertise_blue))
                            acrobaticsModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]) + proficiencyBonus * 2)
                        }
                        "Animal Handling" -> {
                            animalHandlingLabel.setTextColor(getColor(R.color.expertise_blue))
                            animalHandlingModifier.setTextColor(getColor(R.color.expertise_blue))
                            animalHandlingModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus * 2)
                        }
                        "Arcana" -> {
                            arcanaLabel.setTextColor(getColor(R.color.expertise_blue))
                            arcanaModifier.setTextColor(getColor(R.color.expertise_blue))
                            arcanaModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus * 2)
                        }
                        "Athletics" -> {
                            athleticsLabel.setTextColor(getColor(R.color.expertise_blue))
                            athleticsModifier.setTextColor(getColor(R.color.expertise_blue))
                            athleticsModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[0]) + proficiencyBonus * 2)
                        }
                        "Deception" -> {
                            deceptionLabel.setTextColor(getColor(R.color.expertise_blue))
                            deceptionModifier.setTextColor(getColor(R.color.expertise_blue))
                            deceptionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus * 2)
                        }
                        "History" -> {
                            historyLabel.setTextColor(getColor(R.color.expertise_blue))
                            historyModifier.setTextColor(getColor(R.color.expertise_blue))
                            historyModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus * 2)
                        }
                        "Insight" -> {
                            insightLabel.setTextColor(getColor(R.color.expertise_blue))
                            insightModifier.setTextColor(getColor(R.color.expertise_blue))
                            insightModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus * 2)
                        }
                        "Intimidation" -> {
                            intimidationLabel.setTextColor(getColor(R.color.expertise_blue))
                            intimidationModifier.setTextColor(getColor(R.color.expertise_blue))
                            intimidationModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus * 2)
                        }
                        "Investigation" -> {
                            investigationLabel.setTextColor(getColor(R.color.expertise_blue))
                            investigationModifier.setTextColor(getColor(R.color.expertise_blue))
                            investigationModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus * 2)
                        }
                        "Medicine" -> {
                            medicineLabel.setTextColor(getColor(R.color.expertise_blue))
                            medicineModifier.setTextColor(getColor(R.color.expertise_blue))
                            medicineModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus * 2)
                        }
                        "Nature" -> {
                            natureLabel.setTextColor(getColor(R.color.expertise_blue))
                            natureModifier.setTextColor(getColor(R.color.expertise_blue))
                            natureModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus * 2)
                        }
                        "Perception" -> {
                            perceptionLabel.setTextColor(getColor(R.color.expertise_blue))
                            perceptionModifier.setTextColor(getColor(R.color.expertise_blue))
                            perceptionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus * 2)
                        }
                        "Performance" -> {
                            performanceLabel.setTextColor(getColor(R.color.expertise_blue))
                            performanceModifier.setTextColor(getColor(R.color.expertise_blue))
                            performanceModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus * 2)
                        }
                        "Persuasion" -> {
                            persuasionLabel.setTextColor(getColor(R.color.expertise_blue))
                            persuasionModifier.setTextColor(getColor(R.color.expertise_blue))
                            persuasionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus * 2)
                        }
                        "Religion" -> {
                            religionLabel.setTextColor(getColor(R.color.expertise_blue))
                            religionModifier.setTextColor(getColor(R.color.expertise_blue))
                            religionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[3]) + proficiencyBonus * 2)
                        }
                        "Sleight of Hand" -> {
                            sleightOfHandLabel.setTextColor(getColor(R.color.expertise_blue))
                            sleightOfHandModifier.setTextColor(getColor(R.color.expertise_blue))
                            sleightOfHandModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]) + proficiencyBonus * 2)
                        }
                        "Stealth" -> {
                            stealthLabel.setTextColor(getColor(R.color.expertise_blue))
                            stealthModifier.setTextColor(getColor(R.color.expertise_blue))
                            stealthModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]) + proficiencyBonus * 2)
                        }
                        "Survival" -> {
                            survivalLabel.setTextColor(getColor(R.color.expertise_blue))
                            survivalModifier.setTextColor(getColor(R.color.expertise_blue))
                            survivalModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus * 2)
                        }
                        "Thieve's Tools" -> {
                            toolProficienciesTextView.setTextColor(getColor(R.color.expertise_blue))
                            toolProficienciesModifier.setTextColor(getColor(R.color.expertise_blue))
                            toolProficienciesModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[1]) + proficiencyBonus * 2)
                        }
                    }
                }
            }
        }

        //Weapon/armor/tool proficiencies
        val adapter = ListAdapter(this, weaponArmorProficiencies, false)
        when (character.getCharacterClass()) {
            "Barbarian" -> {
                toolProficienciesModifier.visibility = View.GONE
                toolProficienciesTextView.visibility = View.GONE
                toolProficiencyLabel.visibility = View.GONE
                weaponArmorProficiencies.add("Light Armor")
                weaponArmorProficiencies.add("Medium Armor")
                weaponArmorProficiencies.add("Shields")
                weaponArmorProficiencies.add("Simple Weapons")
                weaponArmorProficiencies.add("Martial Weapons")
            }
            "Bard" -> {
                toolProficienciesTextView.text = "Musical Instruments"
                toolProficienciesModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus)
                weaponArmorProficiencies.add("Light Armor")
                weaponArmorProficiencies.add("Simple Weapons")
                weaponArmorProficiencies.add("Hand Crossbows")
                weaponArmorProficiencies.add("Longswords")
                weaponArmorProficiencies.add("Rapiers")
                weaponArmorProficiencies.add("Shortswords")
                if (character.getSubclass() == "College of Valor") {
                    weaponArmorProficiencies.add("Medium Armor")
                    weaponArmorProficiencies.add("Shields")
                    weaponArmorProficiencies.add("Martial Weapons")
                }
            }
            "Cleric" -> {
                toolProficienciesModifier.visibility = View.GONE
                toolProficienciesTextView.visibility = View.GONE
                toolProficiencyLabel.visibility = View.GONE
                weaponArmorProficiencies.add("Light Armor")
                weaponArmorProficiencies.add("Medium Armor")
                weaponArmorProficiencies.add("Shields")
                weaponArmorProficiencies.add("Simple Weapons")
                when (character.getSubclass()) {
                    "Tempest", "War" -> {
                        weaponArmorProficiencies.add("Martial Weapons")
                        weaponArmorProficiencies.add("Heavy Armor")
                    }
                    "Life", "Nature" -> weaponArmorProficiencies.add("Heavy Armor")
                }
            }
            "Druid" -> {
                toolProficienciesTextView.text = "Herbalism Kit"
                toolProficienciesModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[4]) + proficiencyBonus)
                weaponArmorProficiencies.add("Light Armor")
                weaponArmorProficiencies.add("Medium Armor")
                weaponArmorProficiencies.add("Shields")
                weaponArmorProficiencies.add("Clubs")
                weaponArmorProficiencies.add("Daggers")
                weaponArmorProficiencies.add("Darts")
                weaponArmorProficiencies.add("Javelins")
                weaponArmorProficiencies.add("Maces")
                weaponArmorProficiencies.add("Quarterstaffs")
                weaponArmorProficiencies.add("Scimitars")
                weaponArmorProficiencies.add("Sickles")
                weaponArmorProficiencies.add("Slings")
                weaponArmorProficiencies.add("Spears")
            }
            "Fighter" -> {
                toolProficienciesModifier.visibility = View.GONE
                toolProficienciesTextView.visibility = View.GONE
                toolProficiencyLabel.visibility = View.GONE
                weaponArmorProficiencies.add("All Armor")
                weaponArmorProficiencies.add("Shields")
                weaponArmorProficiencies.add("Simple Weapons")
                weaponArmorProficiencies.add("Martial Weapons")
            }
            "Monk" -> {
                toolProficienciesModifier.visibility = View.GONE
                toolProficienciesTextView.visibility = View.GONE
                toolProficiencyLabel.visibility = View.GONE
                weaponArmorProficiencies.add("Simple Weapons")
                weaponArmorProficiencies.add("Shortswords")
            }
            "Paladin" -> {
                toolProficienciesModifier.visibility = View.GONE
                toolProficienciesTextView.visibility = View.GONE
                toolProficiencyLabel.visibility = View.GONE
                weaponArmorProficiencies.add("All Armor")
                weaponArmorProficiencies.add("Shields")
                weaponArmorProficiencies.add("Simple Weapons")
                weaponArmorProficiencies.add("Martial Weapons")
            }
            "Ranger" -> {
                toolProficienciesModifier.visibility = View.GONE
                toolProficienciesTextView.visibility = View.GONE
                toolProficiencyLabel.visibility = View.GONE
                weaponArmorProficiencies.add("Light Armor")
                weaponArmorProficiencies.add("Medium Armor")
                weaponArmorProficiencies.add("Shields")
                weaponArmorProficiencies.add("Simple Weapons")
                weaponArmorProficiencies.add("Martial Weapons")
            }
            "Rogue" -> {
                toolProficienciesTextView.text = "Thieves' Tools"
                if (character.getSubclass() == "Assassin") {
                    toolProficienciesTextView.text = "Thieve's Tools, Disguise Kit, Poisoner's Kit"
                }
                weaponArmorProficiencies.add("Light Armor")
                weaponArmorProficiencies.add("Simple Weapons")
                weaponArmorProficiencies.add("Hand Crossbows")
                weaponArmorProficiencies.add("Longswords")
                weaponArmorProficiencies.add("Rapiers")
                weaponArmorProficiencies.add("Shortswords")
            }
            "Sorcerer" -> {
                toolProficienciesModifier.visibility = View.GONE
                toolProficienciesTextView.visibility = View.GONE
                toolProficiencyLabel.visibility = View.GONE
                weaponArmorProficiencies.add("Daggers")
                weaponArmorProficiencies.add("Darts")
                weaponArmorProficiencies.add("Slings")
                weaponArmorProficiencies.add("Quarterstaffs")
                weaponArmorProficiencies.add("Light Crossbows")
            }
            "Warlock" -> {
                toolProficienciesModifier.visibility = View.GONE
                toolProficienciesTextView.visibility = View.GONE
                toolProficiencyLabel.visibility = View.GONE
                weaponArmorProficiencies.add("Light Armor")
                weaponArmorProficiencies.add("Simple Weapons")
                if (character.getClassBasedBonusStats2().contains(getString(R.string.beguiling_influence))) {
                    deceptionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus)
                    deceptionLabel.setTextColor(getColor(R.color.proficiency_blue))
                    deceptionModifier.setTextColor(getColor(R.color.proficiency_blue))
                    persuasionModifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[5]) + proficiencyBonus)
                    persuasionLabel.setTextColor(getColor(R.color.proficiency_blue))
                    persuasionModifier.setTextColor(getColor(R.color.proficiency_blue))
                }
            }
            "Wizard" -> {
                toolProficienciesModifier.visibility = View.GONE
                toolProficienciesTextView.visibility = View.GONE
                toolProficiencyLabel.visibility = View.GONE
                weaponArmorProficiencies.add("Daggers")
                weaponArmorProficiencies.add("Darts")
                weaponArmorProficiencies.add("Slings")
                weaponArmorProficiencies.add("Quarterstaffs")
                weaponArmorProficiencies.add("Light Crossbows")
            }
        }
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this,
                layoutManager.orientation)
        weaponArmorProficienciesListView.layoutManager = layoutManager
        weaponArmorProficienciesListView.addItemDecoration(dividerItemDecoration)
        weaponArmorProficienciesListView.adapter = adapter
    }

    //Allows for skill proficiencies to change.
    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeProficiencyStatus(label: TextView, modifier: TextView, skill: String, statValue: Int) {
        if ((this as DetailActivity?)!!.character.getClassBasedBonusStats2().contains(skill)) {
            if (!character.getProficiencyChoices().contains(skill)) {
                label.setTextColor(getColor(R.color.proficiency_blue))
                modifier.setTextColor(getColor(R.color.proficiency_blue))
                modifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[statValue]) + proficiencyBonus)
                character.getProficiencyChoices().add(skill)
            } else {
                label.setTextColor(getColor(R.color.defaultTextColor))
                modifier.setTextColor(getColor(R.color.defaultTextColor))
                if (character.getCharacterClass() == "Bard" && character.getLevel() >= 2) {
                    modifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[statValue]) + proficiencyBonus / 2)
                } else {
                    modifier.text = java.lang.String.valueOf(calculateModifier(character.getStatValues()[statValue]))
                }
                character.getProficiencyChoices().remove(skill)
            }
        }
    }
}