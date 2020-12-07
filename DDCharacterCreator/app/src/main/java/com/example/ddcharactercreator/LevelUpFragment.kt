package com.example.ddcharactercreator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "IMPLICIT_BOXING_IN_IDENTITY_EQUALS", "DEPRECATED_IDENTITY_EQUALS")
class LevelUpFragment : Fragment() {
    var character: Character = (activity as DetailActivity?)!!.character

    @BindView(R.id.new_level)
    var newLevel: TextView? = null

    @BindView(R.id.new_spell_level)
    var newSpellLevel: TextView? = null

    @BindView(R.id.proficiency_bonus_improvement)
    var proficiencyBonusImprovement: TextView? = null

    @BindView(R.id.ability_score_improvement_header)
    var abilityScoreImprovementHeader: TextView? = null

    @BindView(R.id.ability_score_improvement_1)
    var abilityScoreImprovement1: Spinner? = null

    @BindView(R.id.ability_score_improvement_2)
    var abilityScoreImprovement2: Spinner? = null

    @BindView(R.id.more_hp)
    var moreHP: EditText? = null

    @BindView(R.id.bonus_stats_addition_1)
    var bonusStats1: TextView? = null

    @BindView(R.id.bonus_stats_addition_2)
    var bonusStats2: TextView? = null

    @BindView(R.id.bonus_stats_addition_3)
    var bonusStats3: TextView? = null

    @BindView(R.id.bonus_stats_addition_4)
    var bonusStats4: TextView? = null

    @BindView(R.id.bonus_stats_addition_5)
    var bonusStats5: TextView? = null

    @BindView(R.id.character_choice_1_header)
    var choiceHeader1: TextView? = null

    @BindView(R.id.character_choice_2_header)
    var choiceHeader2: TextView? = null

    @BindView(R.id.character_choice_1)
    var choice1: Spinner? = null

    @BindView(R.id.character_choice_2)
    var choice2: Spinner? = null

    @BindView(R.id.finish_button)
    var finishButton: Button? = null
    private var hitPointMax = 0

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.activity_level_up, container, false)
        ButterKnife.bind(this, rootView)
        var level = character.getLevel()
        level += 1
        newLevel!!.text = getString(R.string.new_level) + " " + level

        // Ability Score/proficiency Bonus improvements
        val abilityScoreList1 = ArrayList<String>()
        val abilityScoreList2 = ArrayList<String>()
        abilityScoreList1.add("Strength")
        abilityScoreList1.add("Dexterity")
        abilityScoreList1.add("Constitution")
        abilityScoreList1.add("Intelligence")
        abilityScoreList1.add("Wisdom")
        abilityScoreList1.add("Charisma")
        abilityScoreList2.add("Strength")
        abilityScoreList2.add("Dexterity")
        abilityScoreList2.add("Constitution")
        abilityScoreList2.add("Intelligence")
        abilityScoreList2.add("Wisdom")
        abilityScoreList2.add("Charisma")
        val abilityScoreAdapter1 = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, abilityScoreList1)
        val abilityScoreAdapter2 = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, abilityScoreList2)
        when (level) {
            4, 8, 12, 16, 19 -> {
                abilityScoreImprovementHeader!!.visibility = View.VISIBLE
                abilityScoreImprovement1!!.visibility = View.VISIBLE
                abilityScoreImprovement2!!.visibility = View.VISIBLE
            }
            5, 9, 13, 17 -> {
                proficiencyBonusImprovement!!.visibility = View.VISIBLE
                proficiencyBonusImprovement!!.text = getString(R.string.proficiency_bonus_improvement) + " +" + ((activity as DetailActivity?)!!.proficiencyBonus + 1).toString()
            }
        }
        if (character.getCharacterClass() == "Fighter" && (level == 6 || level == 14)) {
            abilityScoreImprovementHeader!!.visibility = View.VISIBLE
            abilityScoreImprovement1!!.visibility = View.VISIBLE
            abilityScoreImprovement2!!.visibility = View.VISIBLE
        }
        //Prevents a stat from going above 20
        if (character.getStatValues()[0] == 20) {
            abilityScoreAdapter1.remove("Strength")
            abilityScoreAdapter2.remove("Strength")
        } else if (character.getStatValues()[0] == 19) {
            abilityScoreAdapter2.remove("Strength")
        }
        if (character.getStatValues()[1] == 20) {
            abilityScoreAdapter1.remove("Dexterity")
            abilityScoreAdapter2.remove("Dexterity")
        } else if (character.getStatValues()[1] == 19) {
            abilityScoreAdapter2.remove("Dexterity")
        }
        if (character.getStatValues()[2] == 20) {
            abilityScoreAdapter1.remove("Constitution")
            abilityScoreAdapter2.remove("Constitution")
        } else if (character.getStatValues()[2] == 19) {
            abilityScoreAdapter2.remove("Constitution")
        }
        if (character.getStatValues()[3] == 20) {
            abilityScoreAdapter1.remove("Intelligence")
            abilityScoreAdapter2.remove("Intelligence")
        } else if (character.getStatValues()[3] == 19) {
            abilityScoreAdapter2.remove("Intelligence")
        }
        if (character.getStatValues()[4] == 20) {
            abilityScoreAdapter1.remove("Wisdom")
            abilityScoreAdapter2.remove("Wisdom")
        } else if (character.getStatValues()[4] == 19) {
            abilityScoreAdapter2.remove("Wisdom")
        }
        if (character.getStatValues()[5] == 20) {
            abilityScoreAdapter1.remove("Charisma")
            abilityScoreAdapter2.remove("Charisma")
        } else if (character.getStatValues()[5] == 19) {
            abilityScoreAdapter2.remove("Charisma")
        }
        abilityScoreImprovement1!!.adapter = abilityScoreAdapter1
        abilityScoreImprovement2!!.adapter = abilityScoreAdapter2
        when (character.getCharacterClass()) {
            "Barbarian" -> {
                moreHP!!.hint = getString(R.string.barbarian_hit_die)
                hitPointMax = 12
            }
            "Bard" -> {
                moreHP!!.hint = getString(R.string.bard_hit_die)
                hitPointMax = 8
            }
            "Cleric" -> {
                moreHP!!.hint = getString(R.string.cleric_hit_die)
                hitPointMax = 8
            }
            "Druid" -> {
                moreHP!!.hint = getString(R.string.druid_hit_die)
                hitPointMax = 8
            }
            "Fighter" -> {
                moreHP!!.hint = getString(R.string.fighter_hit_die)
                hitPointMax = 10
            }
            "Monk" -> {
                moreHP!!.hint = getString(R.string.monk_hit_die)
                hitPointMax = 8
            }
            "Paladin" -> {
                moreHP!!.hint = getString(R.string.paladin_hit_die)
                hitPointMax = 10
            }
            "Ranger" -> {
                moreHP!!.hint = getString(R.string.ranger_hit_die)
                hitPointMax = 10
            }
            "Rogue" -> {
                moreHP!!.hint = getString(R.string.rogue_hit_die)
                hitPointMax = 8
            }
            "Sorcerer" -> {
                moreHP!!.hint = getString(R.string.sorcerer_hit_die)
                hitPointMax = 6
            }
            "Warlock" -> {
                moreHP!!.hint = getString(R.string.warlock_hit_die)
                hitPointMax = 8
            }
            "Wizard" -> {
                moreHP!!.hint = getString(R.string.wizard_hit_die)
                hitPointMax = 6
            }
        }
        moreHP!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (moreHP!!.text.toString() != "" && moreHP!!.text.toString().toInt() > hitPointMax) {
                    moreHP!!.setText(hitPointMax.toString())
                }
            }
        })

        //Spellcasting bonuses
        if (character.getCharacterClass() == "Ranger" || character.getCharacterClass() == "Paladin") {
            when (level) {
                2 -> {
                    newSpellLevel!!.visibility = View.VISIBLE
                    newSpellLevel!!.text = getString(R.string.new_spell_level) + " 1"
                }
                5 -> {
                    newSpellLevel!!.visibility = View.VISIBLE
                    newSpellLevel!!.text = getString(R.string.new_spell_level) + " 2"
                }
                9 -> {
                    newSpellLevel!!.visibility = View.VISIBLE
                    newSpellLevel!!.text = getString(R.string.new_spell_level) + " 3"
                }
                13 -> {
                    newSpellLevel!!.visibility = View.VISIBLE
                    newSpellLevel!!.text = getString(R.string.new_spell_level) + " 4"
                }
                17 -> {
                    newSpellLevel!!.visibility = View.VISIBLE
                    newSpellLevel!!.text = getString(R.string.new_spell_level) + " 5"
                }
            }
        } else {
            when (character.getCharacterClass()) {
                "Bard", "Druid", "Cleric", "Sorcerer", "Wizard" -> when (level) {
                    3 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.new_spell_level) + " 2"
                    }
                    5 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.new_spell_level) + " 3"
                    }
                    7 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.new_spell_level) + " 4"
                    }
                    9 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.new_spell_level) + " 5"
                    }
                    11 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.new_spell_level) + " 6"
                    }
                    13 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.new_spell_level) + " 7"
                    }
                    15 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.new_spell_level) + " 8"
                    }
                    17 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.new_spell_level) + " 9"
                    }
                }
                "Warlock" -> when (level) {
                    3 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.warlock_spell_slot_improvement) + " 2"
                    }
                    5 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.warlock_spell_slot_improvement) + " 3"
                    }
                    7 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.warlock_spell_slot_improvement) + " 4"
                    }
                    9 -> {
                        newSpellLevel!!.visibility = View.VISIBLE
                        newSpellLevel!!.text = getString(R.string.warlock_spell_slot_improvement) + " 5"
                    }
                }
            }
        }
        val finalLevel = level
        val previousConModifier: Int = (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[2])
        finishButton!!.setOnClickListener { //possibly save the character??
            character.getCurrency()[6] = character.getCurrency()[8]
            if (moreHP!!.text.toString() == "") {
                Toast.makeText(context, "Make sure you roll for more health", Toast.LENGTH_SHORT).show()
            } else if (choice1!!.visibility == View.VISIBLE && choice2!!.visibility == View.VISIBLE && choice1!!.selectedItem.toString() == choice2!!.selectedItem.toString()) {
                Toast.makeText(context, "Make sure you choose 2 different options", Toast.LENGTH_SHORT).show()
            } else {
                val newStatValues = character.getStatValues()
                if (abilityScoreImprovementHeader!!.visibility == View.VISIBLE) {
                    improveAbilityScore(abilityScoreImprovement1!!.selectedItem.toString(), newStatValues)
                    improveAbilityScore(abilityScoreImprovement2!!.selectedItem.toString(), newStatValues)
                }
                var addToHP = moreHP!!.text.toString().toInt()
                if (character.getSubclass() == "Draconic Bloodline" || character.getSubclass() == "Stone Sorcery") {
                    addToHP += 1
                }
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[2]) > previousConModifier) {
                    addToHP += finalLevel
                }
                character.getCurrency()[6] = (addToHP + character.getCurrency()[6]
                        + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[2]))
                character.getCurrency()[8] = (addToHP + character.getCurrency()[8]
                        + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[2]))
                var subclass = character.getSubclass()
                when (character.getCharacterClass()) {
                    "Barbarian" -> if ((finalLevel == 3 || finalLevel == 6 || finalLevel == 14) && character.getSubclass() == "Path of the Totem Warrior") {
                        when (choice1!!.selectedItem.toString()) {
                            "Bear Totem Spirit" -> character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_bear))
                            "Eagle Totem Spirit" -> character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_eagle))
                            "Wolf Totem Spirit" -> character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_wolf))
                            "Tiger Totem Spirit" -> character.getRaceAndClassBonusStats().add(getString(R.string.totem_spirit_tiger))
                            "Aspect of the Bear" -> character.getRaceAndClassBonusStats().add(getString(R.string.aspect_of_the_bear))
                            "Aspect of the Eagle" -> character.getRaceAndClassBonusStats().add(getString(R.string.aspect_of_the_eagle))
                            "Aspect of the Wolf" -> character.getRaceAndClassBonusStats().add(getString(R.string.aspect_of_the_wolf))
                            "Aspect of the Tiger" -> character.getRaceAndClassBonusStats().add(getString(R.string.aspect_of_the_tiger))
                            "Totemic Attunement: Bear" -> character.getRaceAndClassBonusStats().add(getString(R.string.totemic_attunement_bear))
                            "Totemic Attunement: Eagle" -> character.getRaceAndClassBonusStats().add(getString(R.string.totemic_attunement_eagle))
                            "Totemic Attunement: Wolf" -> character.getRaceAndClassBonusStats().add(getString(R.string.totemic_attunement_wolf))
                            "Totemic Attunement: Tiger" -> character.getRaceAndClassBonusStats().add(getString(R.string.totemic_attunement_tiger))
                        }
                    }
                    "Bard" -> if (finalLevel == 3 || finalLevel == 10) {
                        character.getProficiencyChoices().remove(choice1!!.selectedItem.toString())
                        character.getProficiencyChoices().remove(choice2!!.selectedItem.toString())
                        character.getClassBasedBonusStats2().add(choice1!!.selectedItem.toString())
                        character.getClassBasedBonusStats2().add(choice2!!.selectedItem.toString())
                    }
                    "Druid" -> if (finalLevel == 3 && character.getSubclass() == "Circle of the Land") {
                        subclass = subclass + ", " + choice1!!.selectedItem.toString()
                    }
                    "Ranger" -> {
                        if (finalLevel == 6) {
                            character.getClassBasedBonusStats2().add(choice1!!.selectedItem.toString())
                            character.getClassBasedBonusStats2().add(choice2!!.selectedItem.toString())
                        }
                        if (finalLevel == 10) {
                            character.getClassBasedBonusStats2().add(choice1!!.selectedItem.toString())
                        }
                        if (finalLevel == 14) {
                            character.getClassBasedBonusStats2().add(choice2!!.selectedItem.toString())
                        }
                        if (character.getSubclass() == "Battle Master" && (finalLevel == 3 || finalLevel == 7 || finalLevel == 10 || finalLevel == 15)) {
                            when (choice1!!.selectedItem.toString()) {
                                "Commander's Strike" -> character.getClassBasedBonusStats2().add(getString(R.string.commanders_strike))
                                "Disarming Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.disarming_attack))
                                "Distracting Strike" -> character.getClassBasedBonusStats2().add(getString(R.string.distracting_strike))
                                "Evasive Footwork" -> character.getClassBasedBonusStats2().add(getString(R.string.evasive_footwork))
                                "Feinting Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.feinting_attack))
                                "Goading Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.goading_attack))
                                "Lunging Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.lunging_attack))
                                "Maneuvering Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.maneuvering_attack))
                                "Menacing Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.menacing_attack))
                                "Parry" -> character.getClassBasedBonusStats2().add(getString(R.string.parry))
                                "Precision Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.precision_attack))
                                "Pushing Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.pushing_attack))
                                "Rally" -> character.getClassBasedBonusStats2().add(getString(R.string.rally))
                                "Riposte" -> character.getClassBasedBonusStats2().add(getString(R.string.riposte))
                                "Sweeping Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.sweeping_attack))
                                "Trip Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.trip_attack))
                            }
                            when (choice2!!.selectedItem.toString()) {
                                "Commander's Strike" -> character.getClassBasedBonusStats2().add(getString(R.string.commanders_strike))
                                "Disarming Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.disarming_attack))
                                "Distracting Strike" -> character.getClassBasedBonusStats2().add(getString(R.string.distracting_strike))
                                "Evasive Footwork" -> character.getClassBasedBonusStats2().add(getString(R.string.evasive_footwork))
                                "Feinting Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.feinting_attack))
                                "Goading Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.goading_attack))
                                "Lunging Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.lunging_attack))
                                "Maneuvering Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.maneuvering_attack))
                                "Menacing Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.menacing_attack))
                                "Parry" -> character.getClassBasedBonusStats2().add(getString(R.string.parry))
                                "Precision Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.precision_attack))
                                "Pushing Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.pushing_attack))
                                "Rally" -> character.getClassBasedBonusStats2().add(getString(R.string.rally))
                                "Riposte" -> character.getClassBasedBonusStats2().add(getString(R.string.riposte))
                                "Sweeping Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.sweeping_attack))
                                "Trip Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.trip_attack))
                            }
                        }
                        if ((character.getCharacterClass() == "Paladin" || character.getCharacterClass() == "Ranger") && finalLevel == 2
                                || character.getSubclass() == "Champion" && finalLevel == 10) {
                            when (choice1!!.selectedItem.toString()) {
                                "Archery" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.archery))
                                "Defense" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.defense))
                                "Dueling" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.dueling))
                                "Great Weapon Fighting" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.great_weapon_fighting))
                                "Protection" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.protection))
                                "Two-Weapon Fighting" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.two_weapon_fighting))
                            }
                        }
                        if (character.getSubclass() == "Hunter" && (finalLevel == 3 || finalLevel == 7 || finalLevel == 11 || finalLevel == 15)) {
                            when (choice1!!.selectedItem.toString()) {
                                "Colossus Slayer" -> character.getRaceAndClassBonusStats().add(getString(R.string.colossus_slayer))
                                "Giant Killer" -> character.getRaceAndClassBonusStats().add(getString(R.string.giant_killer))
                                "Horde Breaker" -> character.getRaceAndClassBonusStats().add(getString(R.string.horde_breaker))
                                "Escape the Horde" -> character.getRaceAndClassBonusStats().add(getString(R.string.escape_the_horde))
                                "Multiattack Defense" -> character.getRaceAndClassBonusStats().add(getString(R.string.multiattack_defense))
                                "Steel Will" -> character.getRaceAndClassBonusStats().add(getString(R.string.steel_will))
                                "Volley" -> character.getRaceAndClassBonusStats().add(getString(R.string.volley))
                                "Whirlwind Attack" -> character.getRaceAndClassBonusStats().add(getString(R.string.whirlwind_attack))
                                "Evasion" -> character.getRaceAndClassBonusStats().add(getString(R.string.evasion))
                                "Stand Against the Tide" -> character.getRaceAndClassBonusStats().add(getString(R.string.stand_against_the_tide))
                                "Uncanny Dodge" -> character.getRaceAndClassBonusStats().add(getString(R.string.uncanny_dodge))
                            }
                        }
                    }
                    "Fighter" -> {
                        if (character.getSubclass() == "Battle Master" && (finalLevel == 3 || finalLevel == 7 || finalLevel == 10 || finalLevel == 15)) {
                            when (choice1!!.selectedItem.toString()) {
                                "Commander's Strike" -> character.getClassBasedBonusStats2().add(getString(R.string.commanders_strike))
                                "Disarming Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.disarming_attack))
                                "Distracting Strike" -> character.getClassBasedBonusStats2().add(getString(R.string.distracting_strike))
                                "Evasive Footwork" -> character.getClassBasedBonusStats2().add(getString(R.string.evasive_footwork))
                                "Feinting Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.feinting_attack))
                                "Goading Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.goading_attack))
                                "Lunging Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.lunging_attack))
                                "Maneuvering Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.maneuvering_attack))
                                "Menacing Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.menacing_attack))
                                "Parry" -> character.getClassBasedBonusStats2().add(getString(R.string.parry))
                                "Precision Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.precision_attack))
                                "Pushing Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.pushing_attack))
                                "Rally" -> character.getClassBasedBonusStats2().add(getString(R.string.rally))
                                "Riposte" -> character.getClassBasedBonusStats2().add(getString(R.string.riposte))
                                "Sweeping Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.sweeping_attack))
                                "Trip Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.trip_attack))
                            }
                            when (choice2!!.selectedItem.toString()) {
                                "Commander's Strike" -> character.getClassBasedBonusStats2().add(getString(R.string.commanders_strike))
                                "Disarming Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.disarming_attack))
                                "Distracting Strike" -> character.getClassBasedBonusStats2().add(getString(R.string.distracting_strike))
                                "Evasive Footwork" -> character.getClassBasedBonusStats2().add(getString(R.string.evasive_footwork))
                                "Feinting Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.feinting_attack))
                                "Goading Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.goading_attack))
                                "Lunging Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.lunging_attack))
                                "Maneuvering Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.maneuvering_attack))
                                "Menacing Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.menacing_attack))
                                "Parry" -> character.getClassBasedBonusStats2().add(getString(R.string.parry))
                                "Precision Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.precision_attack))
                                "Pushing Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.pushing_attack))
                                "Rally" -> character.getClassBasedBonusStats2().add(getString(R.string.rally))
                                "Riposte" -> character.getClassBasedBonusStats2().add(getString(R.string.riposte))
                                "Sweeping Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.sweeping_attack))
                                "Trip Attack" -> character.getClassBasedBonusStats2().add(getString(R.string.trip_attack))
                            }
                        }
                        if ((character.getCharacterClass() == "Paladin" || character.getCharacterClass() == "Ranger") && finalLevel == 2
                                || character.getSubclass() == "Champion" && finalLevel == 10) {
                            when (choice1!!.selectedItem.toString()) {
                                "Archery" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.archery))
                                "Defense" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.defense))
                                "Dueling" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.dueling))
                                "Great Weapon Fighting" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.great_weapon_fighting))
                                "Protection" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.protection))
                                "Two-Weapon Fighting" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.two_weapon_fighting))
                            }
                        }
                        if (character.getSubclass() == "Hunter" && (finalLevel == 3 || finalLevel == 7 || finalLevel == 11 || finalLevel == 15)) {
                            when (choice1!!.selectedItem.toString()) {
                                "Colossus Slayer" -> character.getRaceAndClassBonusStats().add(getString(R.string.colossus_slayer))
                                "Giant Killer" -> character.getRaceAndClassBonusStats().add(getString(R.string.giant_killer))
                                "Horde Breaker" -> character.getRaceAndClassBonusStats().add(getString(R.string.horde_breaker))
                                "Escape the Horde" -> character.getRaceAndClassBonusStats().add(getString(R.string.escape_the_horde))
                                "Multiattack Defense" -> character.getRaceAndClassBonusStats().add(getString(R.string.multiattack_defense))
                                "Steel Will" -> character.getRaceAndClassBonusStats().add(getString(R.string.steel_will))
                                "Volley" -> character.getRaceAndClassBonusStats().add(getString(R.string.volley))
                                "Whirlwind Attack" -> character.getRaceAndClassBonusStats().add(getString(R.string.whirlwind_attack))
                                "Evasion" -> character.getRaceAndClassBonusStats().add(getString(R.string.evasion))
                                "Stand Against the Tide" -> character.getRaceAndClassBonusStats().add(getString(R.string.stand_against_the_tide))
                                "Uncanny Dodge" -> character.getRaceAndClassBonusStats().add(getString(R.string.uncanny_dodge))
                            }
                        }
                    }
                    "Paladin" -> {
                        if ((character.getCharacterClass() == "Paladin" || character.getCharacterClass() == "Ranger") && finalLevel == 2
                                || character.getSubclass() == "Champion" && finalLevel == 10) {
                            when (choice1!!.selectedItem.toString()) {
                                "Archery" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.archery))
                                "Defense" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.defense))
                                "Dueling" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.dueling))
                                "Great Weapon Fighting" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.great_weapon_fighting))
                                "Protection" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.protection))
                                "Two-Weapon Fighting" -> character.getRaceAndClassBonusStats().add("Fighting Style: " + getString(R.string.two_weapon_fighting))
                            }
                        }
                        if (character.getSubclass() == "Hunter" && (finalLevel == 3 || finalLevel == 7 || finalLevel == 11 || finalLevel == 15)) {
                            when (choice1!!.selectedItem.toString()) {
                                "Colossus Slayer" -> character.getRaceAndClassBonusStats().add(getString(R.string.colossus_slayer))
                                "Giant Killer" -> character.getRaceAndClassBonusStats().add(getString(R.string.giant_killer))
                                "Horde Breaker" -> character.getRaceAndClassBonusStats().add(getString(R.string.horde_breaker))
                                "Escape the Horde" -> character.getRaceAndClassBonusStats().add(getString(R.string.escape_the_horde))
                                "Multiattack Defense" -> character.getRaceAndClassBonusStats().add(getString(R.string.multiattack_defense))
                                "Steel Will" -> character.getRaceAndClassBonusStats().add(getString(R.string.steel_will))
                                "Volley" -> character.getRaceAndClassBonusStats().add(getString(R.string.volley))
                                "Whirlwind Attack" -> character.getRaceAndClassBonusStats().add(getString(R.string.whirlwind_attack))
                                "Evasion" -> character.getRaceAndClassBonusStats().add(getString(R.string.evasion))
                                "Stand Against the Tide" -> character.getRaceAndClassBonusStats().add(getString(R.string.stand_against_the_tide))
                                "Uncanny Dodge" -> character.getRaceAndClassBonusStats().add(getString(R.string.uncanny_dodge))
                            }
                        }
                    }
                    "Rogue" -> if (finalLevel == 2 || finalLevel == 6) {
                        character.getProficiencyChoices().remove(choice1!!.selectedItem.toString())
                        character.getProficiencyChoices().remove(choice2!!.selectedItem.toString())
                        character.getClassBasedBonusStats2().add(choice1!!.selectedItem.toString())
                        character.getClassBasedBonusStats2().add(choice2!!.selectedItem.toString())
                    }
                    "Sorcerer" -> {
                        if (finalLevel == 3) {
                            when (choice1!!.selectedItem.toString()) {
                                "Careful Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.careful_spell))
                                "Distant Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.distant_spell))
                                "Empowered Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.empowered_spell))
                                "Extended Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.extended_spell))
                                "Heightened Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.heightened_spell))
                                "Quickened Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.quickened_spell))
                                "Subtle Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.subtle_spell))
                                "Twinned Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.twinned_spell))
                            }
                            when (choice2!!.selectedItem.toString()) {
                                "Careful Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.careful_spell))
                                "Distant Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.distant_spell))
                                "Empowered Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.empowered_spell))
                                "Extended Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.extended_spell))
                                "Heightened Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.heightened_spell))
                                "Quickened Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.quickened_spell))
                                "Subtle Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.subtle_spell))
                                "Twinned Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.twinned_spell))
                            }
                        }
                        if (finalLevel == 10 || finalLevel == 17) {
                            when (choice1!!.selectedItem.toString()) {
                                "Careful Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.careful_spell))
                                "Distant Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.distant_spell))
                                "Empowered Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.empowered_spell))
                                "Extended Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.extended_spell))
                                "Heightened Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.heightened_spell))
                                "Quickened Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.quickened_spell))
                                "Subtle Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.subtle_spell))
                                "Twinned Spell" -> character.getClassBasedBonusStats2().add(getString(R.string.twinned_spell))
                            }
                        }
                    }
                    "Warlock" -> {
                        if (finalLevel == 2) {
                            when (choice1!!.selectedItem.toString()) {
                                "Agonizing Blast" -> character.getClassBasedBonusStats2().add(getString(R.string.agonizing_blast))
                                "Beguiling Influence" -> character.getClassBasedBonusStats2().add(getString(R.string.beguiling_influence))
                                "Devil's Sight" -> character.getClassBasedBonusStats2().add(getString(R.string.devils_sight))
                                "Eldritch Spear" -> character.getClassBasedBonusStats2().add(getString(R.string.eldritch_spear))
                                "Eyes of the Rune Keeper" -> character.getClassBasedBonusStats2().add(getString(R.string.eyes_of_the_rune_keeper))
                                "Gaze of Two Minds" -> character.getClassBasedBonusStats2().add(getString(R.string.gaze_of_two_minds))
                                "Repelling Blast" -> character.getClassBasedBonusStats2().add(getString(R.string.repelling_blast))
                            }
                            when (choice2!!.selectedItem.toString()) {
                                "Agonizing Blast" -> character.getClassBasedBonusStats2().add(getString(R.string.agonizing_blast))
                                "Beguiling Influence" -> character.getClassBasedBonusStats2().add(getString(R.string.beguiling_influence))
                                "Devil's Sight" -> character.getClassBasedBonusStats2().add(getString(R.string.devils_sight))
                                "Eldritch Spear" -> character.getClassBasedBonusStats2().add(getString(R.string.eldritch_spear))
                                "Eyes of the Rune Keeper" -> character.getClassBasedBonusStats2().add(getString(R.string.eyes_of_the_rune_keeper))
                                "Gaze of Two Minds" -> character.getClassBasedBonusStats2().add(getString(R.string.gaze_of_two_minds))
                                "Repelling Blast" -> character.getClassBasedBonusStats2().add(getString(R.string.repelling_blast))
                            }
                        }
                        if (finalLevel == 3) {
                            when (choice1!!.selectedItem.toString()) {
                                "Pact of the Blade" -> character.getRaceAndClassBonusStats().add(getString(R.string.pact_of_the_blade))
                                "Pact of the Chain" -> character.getRaceAndClassBonusStats().add(getString(R.string.pact_of_the_chain))
                                "Pact of the Talisman" -> character.getRaceAndClassBonusStats().add(getString(R.string.pact_of_the_talisman))
                                "Pact of the Tome" -> character.getRaceAndClassBonusStats().add(getString(R.string.pact_of_the_tome))
                            }
                        }
                        if (finalLevel == 5 || finalLevel == 7 || finalLevel == 9 || finalLevel == 12 || finalLevel == 15 || finalLevel == 18) {
                            when (choice1!!.selectedItem.toString()) {
                                "Agonizing Blast" -> character.getClassBasedBonusStats2().add(getString(R.string.agonizing_blast))
                                "Beguiling Influence" -> character.getClassBasedBonusStats2().add(getString(R.string.beguiling_influence))
                                "Book of Ancient Secrets" -> character.getClassBasedBonusStats2().add(getString(R.string.book_of_ancient_secrets))
                                "Devil's Sight" -> character.getClassBasedBonusStats2().add(getString(R.string.devils_sight))
                                "Eldritch Spear" -> character.getClassBasedBonusStats2().add(getString(R.string.eldritch_spear))
                                "Eye of Yog-Sothoth" -> character.getClassBasedBonusStats2().add(getString(R.string.eye_of_yog_sothoth))
                                "Eyes of the Rune Keeper" -> character.getClassBasedBonusStats2().add(getString(R.string.eyes_of_the_rune_keeper))
                                "Gaze of Two Minds" -> character.getClassBasedBonusStats2().add(getString(R.string.gaze_of_two_minds))
                                "Lifedrinker" -> character.getClassBasedBonusStats2().add(getString(R.string.lifedrinker))
                                "One With Shadows" -> character.getClassBasedBonusStats2().add(getString(R.string.one_with_shadows))
                                "Reality Tear" -> character.getClassBasedBonusStats2().add(getString(R.string.reality_tear))
                                "Repelling Blast" -> character.getClassBasedBonusStats2().add(getString(R.string.repelling_blast))
                                "Thirsting Blade" -> character.getClassBasedBonusStats2().add(getString(R.string.thirsting_blade))
                                "Trickster's Step" -> character.getClassBasedBonusStats2().add(getString(R.string.tricksters_step))
                                "Unbreakable Tether" -> character.getClassBasedBonusStats2().add(getString(R.string.unbreakable_tether))
                                "Voice of the Chain Master" -> character.getClassBasedBonusStats2().add(getString(R.string.voice_of_the_chain_master))
                                "Witch Sight" -> character.getClassBasedBonusStats2().add(getString(R.string.witch_sight))
                            }
                        }
                        (activity as DetailActivity?)!!.spellViewModel.loadAllSpells().observe(viewLifecycleOwner, Observer<List<Spell?>?> { t ->
                            var spell = Spell()
                            if (finalLevel == 11 || finalLevel == 13 || finalLevel == 17) {
                                var i = 0
                                while (i < t!!.size) {
                                    if (t[i]!!.spellName == choice1!!.selectedItem.toString()) {
                                        spell = t[i]!!
                                        break
                                    }
                                    i++
                                }
                                character.getSpellsKnown().add(spell)
                            } else if (finalLevel == 15) {
                                var i = 0
                                while (i < t!!.size) {
                                    if (t[i]!!.spellName == choice2!!.selectedItem.toString()) {
                                        spell = t[i]!!
                                        break
                                    }
                                    i++
                                }
                                character.getSpellsKnown().add(spell)
                            }
                        })
                    }
                    "Wizard" -> if (finalLevel == 18 || finalLevel == 20) {
                        getSubclassSpells(choice1!!.selectedItem.toString())
                        getSubclassSpells(choice2!!.selectedItem.toString())
                        character.getClassBasedBonusStats2().add(choice1!!.selectedItem.toString())
                        character.getClassBasedBonusStats2().add(choice2!!.selectedItem.toString())
                    }
                }
                returnToDetailActivity(Character(finalLevel, character.getRace(), character.getCharacterClass(),
                        character.getAlignment(), character.getName(), newStatValues,
                        character.getProficiencyChoices(), character.getInventoryList(), character.getCurrency(),
                        subclass, character.getSpellsKnown(), character.getSpellSlotsClicked(),
                        character.getRaceAndClassBonusStats(), character.getClassBasedBonusStats2()))
            }
        }
        when (character.getCharacterClass()) {
            "Barbarian" -> when (level) {
                2 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.setText(R.string.reckless_attack)
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.setText(R.string.danger_sense)
                    character.getRaceAndClassBonusStats().add(getString(R.string.reckless_attack))
                    character.getRaceAndClassBonusStats().add(getString(R.string.danger_sense))
                }
                3 -> {
                    val totemChoices = ArrayList<String>()
                    val totemAdapter = ArrayAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item, totemChoices)
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "You have an extra usage of Rage"
                    when (character.getSubclass()) {
                        "Path of the Berserker" -> {
                            bonusStats2!!.visibility = View.VISIBLE
                            bonusStats2!!.text = getString(R.string.frenzy)
                            character.getRaceAndClassBonusStats().add(getString(R.string.frenzy))
                        }
                        "Path of the Totem Warrior" -> {
                            bonusStats2!!.visibility = View.VISIBLE
                            bonusStats2!!.text = "You can now cast Beast Sense and Speak with Animals as a ritual"
                            choiceHeader1!!.visibility = View.VISIBLE
                            choiceHeader1!!.text = "Choose a Totem Spirit"
                            choice1!!.visibility = View.VISIBLE
                            totemChoices.add("Bear Totem Spirit")
                            totemChoices.add("Eagle Totem Spirit")
                            totemChoices.add("Wolf Totem Spirit")
                            totemChoices.add("Tiger Totem Spirit")
                            choice1!!.adapter = totemAdapter
                        }
                    }
                }
                5 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.setText(R.string.extra_attack)
                    character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack))
                }
                6 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "You have an extra usage of Rage"
                    when (character.getSubclass()) {
                        "Path of the Berserker" -> {
                            bonusStats2!!.visibility = View.VISIBLE
                            bonusStats2!!.text = getString(R.string.mindless_rage)
                            character.getRaceAndClassBonusStats().add(getString(R.string.mindless_rage))
                        }
                        "Path of the Totem Warrior" -> {
                            val totemPathChoices = ArrayList<String>()
                            val totemPathAdapter = ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item, totemPathChoices)
                            totemPathChoices.add("Aspect of the Bear")
                            totemPathChoices.add("Aspect of the Eagle")
                            totemPathChoices.add("Aspect of the Wolf")
                            totemPathChoices.add("Aspect of the Tiger")
                            choiceHeader1!!.visibility = View.VISIBLE
                            choiceHeader1!!.text = getString(R.string.totem_spirit)
                            choice1!!.visibility = View.VISIBLE
                            choice1!!.adapter = totemPathAdapter
                        }
                    }
                }
                7 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.setText(R.string.feral_instinct)
                    character.getRaceAndClassBonusStats().add(getString(R.string.feral_instinct))
                }
                9 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.setText(R.string.brutal_critical)
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = "Your rage now deals extra damage"
                    character.getRaceAndClassBonusStats().add(getString(R.string.brutal_critical))
                }
                10 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Path of the Berserker" -> {
                            bonusStats1!!.text = getString(R.string.intimidating_presence)
                            character.getRaceAndClassBonusStats().add(getString(R.string.intimidating_presence))
                        }
                        "Path of the Totem Warrior" -> {
                            bonusStats1!!.text = getString(R.string.spirit_walker)
                            character.getRaceAndClassBonusStats().add(getString(R.string.spirit_walker))
                        }
                    }
                }
                11 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.setText(R.string.relentless_rage)
                    character.getRaceAndClassBonusStats().add(getString(R.string.relentless_rage))
                }
                12 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "You have an extra usage of Rage"
                }
                13 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Brutal critical is now 2 extra damage dice"
                }
                14 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Path of the Berserker" -> {
                            bonusStats1!!.text = getString(R.string.retaliation)
                            character.getRaceAndClassBonusStats().add(getString(R.string.retaliation))
                        }
                        "Path of the Totem Warrior" -> {
                            val totemPathChoices = ArrayList<String>()
                            val totemPathAdapter = ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item, totemPathChoices)
                            totemPathChoices.add("Totemic Attunement: Bear")
                            totemPathChoices.add("Totemic Attunement: Eagle")
                            totemPathChoices.add("Totemic Attunement: Wolf")
                            totemPathChoices.add("Totemic Attunement: Tiger")
                            choiceHeader1!!.visibility = View.VISIBLE
                            choiceHeader1!!.text = getString(R.string.totem_spirit)
                            choice1!!.visibility = View.VISIBLE
                            choice1!!.adapter = totemPathAdapter
                        }
                    }
                }
                15 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.setText(R.string.persistent_rage)
                    character.getRaceAndClassBonusStats().add(getString(R.string.persistent_rage))
                }
                16 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Your rage now deals extra damage"
                }
                17 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "You have an extra usage of Rage"
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = "Brutal critical is now 3 extra damage dice"
                }
                18 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.setText(R.string.indomitable_might)
                    character.getRaceAndClassBonusStats().add(getString(R.string.indomitable_might))
                }
                20 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "You can now use rage an unlimited amount of times."
                    character.getStatValues()[0] = character.getStatValues()[0] + 4
                    character.getStatValues()[2] = character.getStatValues()[2] + 4
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.setText(R.string.primal_champion)
                    character.getRaceAndClassBonusStats().add(getString(R.string.primal_champion))
                }
            }
            "Bard" -> {
                val bardExpertiseChoices = ArrayList<String>()
                val bardExpertiseAdapter = ArrayAdapter(context,
                        android.R.layout.simple_spinner_dropdown_item, bardExpertiseChoices)
                when (level) {
                    2 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.setText(R.string.song_of_rest)
                        bonusStats2!!.visibility = View.VISIBLE
                        bonusStats2!!.setText(R.string.jack_of_all_trades)
                        character.getRaceAndClassBonusStats().add(getString(R.string.song_of_rest))
                        character.getRaceAndClassBonusStats().add(getString(R.string.jack_of_all_trades))
                    }
                    3 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats2!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "College of Lore" -> {
                                bonusStats1!!.text = getString(R.string.cutting_words)
                                character.getRaceAndClassBonusStats().add(getString(R.string.cutting_words))
                                bonusStats2!!.text = "You now have proficiency in 3 more skills of your choice"
                            }
                            "College of Valor" -> {
                                bonusStats1!!.text = getString(R.string.combat_inspiration)
                                character.getRaceAndClassBonusStats().add(getString(R.string.combat_inspiration))
                                bonusStats2!!.text = "You now have proficiency with Medium Armor, Shields, and Martial Weapons"
                            }
                        }
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose two of your proficiencies. Your proficiency bonus will be doubled for any ability checks you make with that skill"
                        choice1!!.visibility = View.VISIBLE
                        choice2!!.visibility = View.VISIBLE
                        bardExpertiseChoices.addAll(character.getProficiencyChoices())
                        choice1!!.adapter = bardExpertiseAdapter
                        choice2!!.adapter = bardExpertiseAdapter
                    }
                    5 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.setText(R.string.font_of_inspiration)
                        bonusStats2!!.visibility = View.VISIBLE
                        bonusStats2!!.text = "Your Bardic Inspiration die is now a d8"
                        character.getRaceAndClassBonusStats().add(getString(R.string.font_of_inspiration))
                    }
                    6 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.setText(R.string.countercharm)
                        character.getRaceAndClassBonusStats().add(getString(R.string.countercharm))
                        bonusStats2!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "College of Lore" -> bonusStats2!!.text = getString(R.string.magical_secrets)
                            "College of Valor" -> {
                                bonusStats2!!.text = getString(R.string.extra_attack)
                                character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack))
                            }
                        }
                    }
                    9 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your Song of Rest die is now a d8"
                    }
                    10 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your Bardic Inspiration die is now a d10"
                        bonusStats2!!.visibility = View.VISIBLE
                        bonusStats2!!.text = getString(R.string.magical_secrets)
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose two of your proficiencies. Your proficiency bonus will be doubled for any ability checks you make with that skill"
                        choice1!!.visibility = View.VISIBLE
                        choice2!!.visibility = View.VISIBLE
                        bardExpertiseChoices.addAll(character.getProficiencyChoices())
                        choice1!!.adapter = bardExpertiseAdapter
                        choice2!!.adapter = bardExpertiseAdapter
                    }
                    13 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your Song of Rest die is now a d10"
                    }
                    14 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.magical_secrets)
                        bonusStats2!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "College of Lore" -> {
                                bonusStats2!!.text = getString(R.string.peerless_skill)
                                character.getRaceAndClassBonusStats().add(getString(R.string.peerless_skill))
                            }
                            "College of Valor" -> {
                                bonusStats2!!.text = getString(R.string.battle_magic)
                                character.getRaceAndClassBonusStats().add(getString(R.string.battle_magic))
                            }
                        }
                    }
                    15 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your Bardic Inspiration die is now a d12"
                    }
                    17 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your Song of Rest die is now a d12"
                    }
                    18 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.magical_secrets)
                    }
                    20 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.setText(R.string.superior_inspiration)
                        character.getRaceAndClassBonusStats().add(getString(R.string.superior_inspiration))
                    }
                }
            }
            "Cleric" -> when (level) {
                2 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.channel_divinity)
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.channel_divinity_turn_undead)
                    character.getRaceAndClassBonusStats().add(getString(R.string.channel_divinity))
                    bonusStats3!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Knowledge" -> bonusStats3!!.text = getString(R.string.channel_divinity_knowledge_of_the_ages)
                        "Life" -> bonusStats3!!.text = getString(R.string.channel_divinity_preserve_life)
                        "Light" -> bonusStats3!!.text = getString(R.string.channel_divinity_radiance_of_the_dawn)
                        "Nature" -> bonusStats3!!.text = getString(R.string.channel_divinity_charm_animals_and_plants)
                        "Tempest" -> bonusStats3!!.text = getString(R.string.channel_divinity_destructive_wrath)
                        "Trickery" -> bonusStats3!!.text = getString(R.string.channel_divinity_invoke_duplicity)
                        "War" -> bonusStats3!!.text = getString(R.string.channel_divinity_guided_strike)
                    }
                }
                5 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.destroy_undead)
                    character.getRaceAndClassBonusStats().add(getString(R.string.destroy_undead))
                }
                6 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "You can now use Channel Divinity twice per short/long rest"
                    bonusStats2!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Knowledge" -> bonusStats2!!.text = getString(R.string.channel_divinity_read_thoughts)
                        "Life" -> {
                            bonusStats2!!.text = getString(R.string.blessed_healer)
                            character.getRaceAndClassBonusStats().add(getString(R.string.blessed_healer))
                        }
                        "Light" -> {
                            bonusStats2!!.text = getString(R.string.improved_flare)
                            character.getRaceAndClassBonusStats().add(getString(R.string.improved_flare))
                        }
                        "Nature" -> {
                            bonusStats2!!.text = getString(R.string.dampen_elements)
                            character.getRaceAndClassBonusStats().add(getString(R.string.dampen_elements))
                        }
                        "Tempest" -> {
                            bonusStats2!!.text = getString(R.string.thunderbolt_strike)
                            character.getRaceAndClassBonusStats().add(getString(R.string.thunderbolt_strike))
                        }
                        "Trickery" -> bonusStats2!!.text = getString(R.string.channel_divinity_cloak_of_shadows)
                        "War" -> bonusStats2!!.text = getString(R.string.channel_divinity_war_gods_blessing)
                    }
                }
                8 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Destroy undead now destroys undead of CR 1 or lower"
                    bonusStats2!!.visibility = View.VISIBLE
                    val divineStrike = StringBuilder()
                    divineStrike.append(getString(R.string.divine_strike))
                    when (character.getSubclass()) {
                        "Knowledge", "Light" -> {
                            bonusStats2!!.text = getString(R.string.potent_spellcasting)
                            character.getRaceAndClassBonusStats().add(getString(R.string.potent_spellcasting))
                        }
                        "Life" -> {
                            divineStrike.append("Radiant")
                            bonusStats2!!.text = divineStrike.toString()
                            character.getRaceAndClassBonusStats().add(divineStrike.toString())
                        }
                        "Nature" -> {
                            divineStrike.append("either Cold, Fire, or Lightning")
                            bonusStats2!!.text = divineStrike.toString()
                            character.getRaceAndClassBonusStats().add(divineStrike.toString())
                        }
                        "Tempest" -> {
                            divineStrike.append("Thunder")
                            bonusStats2!!.text = divineStrike.toString()
                            character.getRaceAndClassBonusStats().add(divineStrike.toString())
                        }
                        "Trickery" -> {
                            divineStrike.append("Poison")
                            bonusStats2!!.text = divineStrike.toString()
                            character.getRaceAndClassBonusStats().add(divineStrike.toString())
                        }
                        "War" -> {
                            divineStrike.append("the same type of damage dealt by the weapon")
                            bonusStats2!!.text = divineStrike.toString()
                            character.getRaceAndClassBonusStats().add(divineStrike.toString())
                        }
                    }
                }
                10 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.divine_intervention)
                    character.getRaceAndClassBonusStats().add(getString(R.string.divine_intervention))
                }
                11 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Destroy undead now destroys undead of CR 2 or lower"
                }
                14 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Destroy undead now destroys undead of CR 3 or lower"
                    when (character.getSubclass()) {
                        "Life", "Nature", "Tempest", "Trickery", "War" -> {
                            bonusStats2!!.visibility = View.VISIBLE
                            bonusStats2!!.text = getString(R.string.improved_divine_strike)
                            character.getRaceAndClassBonusStats().add(getString(R.string.improved_divine_strike))
                        }
                    }
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.improved_divine_strike)
                }
                17 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Destroy undead now destroys undead of CR 4 or lower"
                    bonusStats2!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Knowledge" -> {
                            bonusStats2!!.text = getString(R.string.visions_of_the_past)
                            character.getRaceAndClassBonusStats().add(getString(R.string.visions_of_the_past))
                        }
                        "Life" -> {
                            bonusStats2!!.setText(R.string.supreme_healing)
                            character.getRaceAndClassBonusStats().add(getString(R.string.supreme_healing))
                        }
                        "Light" -> {
                            bonusStats2!!.text = getString(R.string.corona_of_light)
                            character.getRaceAndClassBonusStats().add(getString(R.string.corona_of_light))
                        }
                        "Nature" -> {
                            bonusStats2!!.text = getString(R.string.master_of_nature)
                            character.getRaceAndClassBonusStats().add(getString(R.string.master_of_nature))
                        }
                        "Tempest" -> {
                            bonusStats2!!.text = getString(R.string.stormborn)
                            character.getRaceAndClassBonusStats().add(getString(R.string.stormborn))
                        }
                        "Trickery" -> {
                            bonusStats2!!.text = getString(R.string.improved_duplicity)
                            character.getRaceAndClassBonusStats().add(getString(R.string.improved_duplicity))
                        }
                        "War" -> {
                            bonusStats2!!.text = getString(R.string.avatar_of_battle)
                            character.getRaceAndClassBonusStats().add(getString(R.string.avatar_of_battle))
                        }
                    }
                }
                18 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "You can now use Channel Divinity three times per short/long rest"
                }
                20 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Your Divine Intervention ability now automatically succeeds"
                }
            }
            "Druid" -> when (level) {
                2 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.wild_shape)
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.wild_shape_limits) + "Max CR = 1/4, No Flying/Swimming Speed"
                    character.getRaceAndClassBonusStats().add(getString(R.string.wild_shape))
                    bonusStats3!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Circle of the Land" -> {
                            bonusStats3!!.text = getString(R.string.natural_recovery)
                            character.getRaceAndClassBonusStats().add(getString(R.string.natural_recovery))
                        }
                        "Circle of the Moon" -> {
                            bonusStats3!!.text = getString(R.string.combat_wild_shape)
                            character.getRaceAndClassBonusStats().add(getString(R.string.combat_wild_shape))
                        }
                    }
                }
                3 -> if (character.getSubclass() == "Circle of the Land") {
                    val landCircleChoices = ArrayList<String>()
                    val landCircleAdapter = ArrayAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item, landCircleChoices)
                    choiceHeader1!!.visibility = View.VISIBLE
                    choiceHeader1!!.text = "Choose a type of land. This will provide you with more spells"
                    choice1!!.visibility = View.VISIBLE
                    landCircleChoices.add("Arctic")
                    landCircleChoices.add("Coast")
                    landCircleChoices.add("Desert")
                    landCircleChoices.add("Forest")
                    landCircleChoices.add("Grassland")
                    landCircleChoices.add("Mountain")
                    landCircleChoices.add("Swamp")
                    landCircleChoices.add("Underdark")
                    choice1!!.adapter = landCircleAdapter
                }
                6 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    if (character.getSubclass().contains("Circle of the Land")) {
                        bonusStats1!!.text = getString(R.string.lands_stride)
                        character.getRaceAndClassBonusStats().add(getString(R.string.lands_stride))
                    } else if (character.getSubclass() == "Circle of the Moon") {
                        bonusStats2!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.circle_forms)
                        character.getRaceAndClassBonusStats().add(getString(R.string.circle_forms))
                        bonusStats2!!.text = getString(R.string.primal_strike)
                        character.getRaceAndClassBonusStats().add(getString(R.string.primal_strike))
                    }
                }
                10 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    if (character.getSubclass().contains("Circle of the Land")) {
                        bonusStats1!!.text = getString(R.string.natures_ward)
                        character.getRaceAndClassBonusStats().add(getString(R.string.natures_ward))
                    } else if (character.getSubclass() == "Circle of the Moon") {
                        bonusStats1!!.text = getString(R.string.elemental_wild_shape)
                        character.getRaceAndClassBonusStats().add(getString(R.string.elemental_wild_shape))
                    }
                }
                14 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    if (character.getSubclass().contains("Circle of the Land")) {
                        bonusStats1!!.text = getString(R.string.natures_sanctuary)
                        character.getRaceAndClassBonusStats().add(getString(R.string.natures_sanctuary))
                    } else if (character.getSubclass() == "Circle of the Moon") {
                        bonusStats1!!.text = getString(R.string.thousand_forms)
                        character.getRaceAndClassBonusStats().add(getString(R.string.thousand_forms))
                    }
                }
                18 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.timeless_body_druid)
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.beast_spells)
                    character.getRaceAndClassBonusStats().add(getString(R.string.timeless_body_druid))
                    character.getRaceAndClassBonusStats().add(getString(R.string.beast_spells))
                }
                20 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.archdruid)
                }
            }
            "Fighter" -> {
                val maneuvers: MutableList<String> = ArrayList()
                val maneuverAdapter = ArrayAdapter(context,
                        android.R.layout.simple_spinner_dropdown_item, maneuvers)
                maneuvers.add("Commander's Strike")
                maneuvers.add("Disarming Attack")
                maneuvers.add("Distracting Strike")
                maneuvers.add("Evasive Footwork")
                maneuvers.add("Feinting Attack")
                maneuvers.add("Goading Attack")
                maneuvers.add("Lunging Attack")
                maneuvers.add("Maneuvering Attack")
                maneuvers.add("Menacing Attack")
                maneuvers.add("Parry")
                maneuvers.add("Precision Attack")
                maneuvers.add("Pushing Attack")
                maneuvers.add("Rally")
                maneuvers.add("Riposte")
                maneuvers.add("Sweeping Attack")
                maneuvers.add("Trip Attack")
                var i = 0
                while (i < character.getClassBasedBonusStats2().size) {
                    var j = 0
                    while (j < maneuvers.size) {
                        if (character.getClassBasedBonusStats2()[i].contains(maneuvers[j])) {
                            maneuvers.remove(maneuvers[j])
                        }
                        j++
                    }
                    i++
                }
                when (level) {
                    2 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.action_surge)
                        character.getRaceAndClassBonusStats().add(getString(R.string.action_surge))
                    }
                    3 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Battle Master" -> {
                                bonusStats1!!.text = getString(R.string.battle_master_description)
                                character.getRaceAndClassBonusStats().add(getString(R.string.battle_master_description))
                                choiceHeader1!!.visibility = View.VISIBLE
                                choiceHeader1!!.text = "Choose 2 starting Maneuvers"
                                choice1!!.visibility = View.VISIBLE
                                choice1!!.adapter = maneuverAdapter
                                choice2!!.visibility = View.VISIBLE
                                choice2!!.adapter = maneuverAdapter
                            }
                            "Champion" -> {
                                bonusStats1!!.text = getString(R.string.improved_critical)
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_critical))
                            }
                            "Eldritch Knight" -> {
                                bonusStats1!!.text = "You now have the ability to cast spells. Go to the Spellcasting tab for more information"
                                bonusStats2!!.visibility = View.VISIBLE
                                bonusStats2!!.text = getString(R.string.weapon_bond)
                                character.getRaceAndClassBonusStats().add(getString(R.string.weapon_bond))
                            }
                        }
                    }
                    5 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.extra_attack)
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack))
                    }
                    7 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Battle Master" -> {
                                bonusStats1!!.text = getString(R.string.know_your_enemy)
                                character.getRaceAndClassBonusStats().add(getString(R.string.know_your_enemy))
                                bonusStats2!!.visibility = View.VISIBLE
                                bonusStats2!!.text = "You have another Superiority Die"
                                choiceHeader1!!.visibility = View.VISIBLE
                                choiceHeader1!!.text = "Choose 2 more Maneuvers"
                                choice1!!.visibility = View.VISIBLE
                                choice1!!.adapter = maneuverAdapter
                                choice2!!.visibility = View.VISIBLE
                                choice2!!.adapter = maneuverAdapter
                            }
                            "Champion" -> {
                                bonusStats1!!.text = getString(R.string.remarkable_athlete)
                                character.getRaceAndClassBonusStats().add(getString(R.string.remarkable_athlete))
                            }
                            "Eldritch Knight" -> {
                                bonusStats1!!.text = getString(R.string.war_magic)
                                character.getRaceAndClassBonusStats().add(getString(R.string.war_magic))
                            }
                        }
                    }
                    9 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.indomitable)
                        character.getRaceAndClassBonusStats().add(getString(R.string.indomitable))
                    }
                    10 -> when (character.getSubclass()) {
                        "Battle Master" -> {
                            bonusStats1!!.visibility = View.VISIBLE
                            bonusStats1!!.text = "Your Superiority dice are now d10s"
                            choiceHeader1!!.visibility = View.VISIBLE
                            choiceHeader1!!.text = "Choose 2 more Maneuvers"
                            choice1!!.visibility = View.VISIBLE
                            choice1!!.adapter = maneuverAdapter
                            choice2!!.visibility = View.VISIBLE
                            choice2!!.adapter = maneuverAdapter
                        }
                        "Champion" -> {
                            val fightingStyles: MutableList<String> = ArrayList()
                            val fightingStyleAdapter = ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item, fightingStyles)
                            fightingStyles.add("Archery")
                            fightingStyles.add("Defense")
                            fightingStyles.add("Dueling")
                            fightingStyles.add("Great Weapon Fighting")
                            fightingStyles.add("Protection")
                            fightingStyles.add("Two-Weapon Fighting")
                            var i = 0
                            while (i < fightingStyles.size) {
                                var j = 0
                                while (j < character.getRaceAndClassBonusStats().size) {
                                    if (character.getRaceAndClassBonusStats()[j].contains(fightingStyles[i])) {
                                        fightingStyles.remove(fightingStyles[i])
                                    }
                                    j++
                                }
                                i++
                            }
                            choiceHeader1!!.visibility = View.VISIBLE
                            choiceHeader1!!.text = "Choose a Fighting Style"
                            choice1!!.visibility = View.VISIBLE
                            choice1!!.adapter = fightingStyleAdapter
                        }
                        "Eldritch Knight" -> {
                            bonusStats1!!.visibility = View.VISIBLE
                            bonusStats1!!.text = getString(R.string.eldritch_strike)
                            character.getRaceAndClassBonusStats().add(getString(R.string.eldritch_strike))
                        }
                    }
                    11 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.extra_attack_3)
                        character.getRaceAndClassBonusStats().remove(getString(R.string.extra_attack))
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack_3))
                    }
                    13 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "You have gained another use of Indomitable per long rest"
                    }
                    15 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Battle Master" -> {
                                bonusStats1!!.text = getString(R.string.relentless)
                                character.getRaceAndClassBonusStats().add(getString(R.string.relentless))
                                bonusStats2!!.visibility = View.VISIBLE
                                bonusStats2!!.text = "You have another Superiority Die"
                                choiceHeader1!!.visibility = View.VISIBLE
                                choiceHeader1!!.text = "Choose 2 more Maneuvers"
                                choice1!!.visibility = View.VISIBLE
                                choice1!!.adapter = maneuverAdapter
                                choice2!!.visibility = View.VISIBLE
                                choice2!!.adapter = maneuverAdapter
                            }
                            "Champion" -> {
                                bonusStats1!!.text = getString(R.string.superior_critical)
                                character.getRaceAndClassBonusStats().remove(getString(R.string.improved_critical))
                                character.getRaceAndClassBonusStats().add(getString(R.string.superior_critical))
                            }
                            "Eldritch Knight" -> {
                                bonusStats1!!.text = getString(R.string.arcane_charge)
                                character.getRaceAndClassBonusStats().add(getString(R.string.arcane_charge))
                            }
                        }
                    }
                    17 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "You now have a second use of Action Surge per long rest"
                        bonusStats2!!.visibility = View.VISIBLE
                        bonusStats2!!.text = "You have gained another use of Indomitable per long rest"
                    }
                    18 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Battle Master" -> {
                                bonusStats1!!.visibility = View.VISIBLE
                                bonusStats1!!.text = "Your Superiority dice are now d12s"
                            }
                            "Champion" -> {
                                bonusStats1!!.text = getString(R.string.survivor)
                                character.getRaceAndClassBonusStats().add(getString(R.string.survivor))
                            }
                            "Eldritch Knight" -> {
                                bonusStats1!!.text = getString(R.string.improved_war_magic)
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_war_magic))
                            }
                        }
                    }
                    20 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.extra_attack_4)
                        character.getRaceAndClassBonusStats().remove(getString(R.string.extra_attack_3))
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack_4))
                    }
                }
            }
            "Monk" -> when (level) {
                2 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.ki_points_description)
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.unarmored_movement_description)
                    character.getRaceAndClassBonusStats().add(getString(R.string.unarmored_movement_description))
                }
                3 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.deflect_missiles)
                    character.getRaceAndClassBonusStats().add(getString(R.string.deflect_missiles))
                    bonusStats2!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Way of the Open Hand" -> {
                            bonusStats2!!.text = getString(R.string.open_hand_technique)
                            character.getRaceAndClassBonusStats().add(getString(R.string.open_hand_technique))
                        }
                        "Way of Shadow" -> {
                            bonusStats2!!.text = getString(R.string.shadow_arts)
                            character.getRaceAndClassBonusStats().add(getString(R.string.shadow_arts))
                        }
                    }
                }
                4 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.slow_fall)
                    character.getRaceAndClassBonusStats().add(getString(R.string.slow_fall))
                }
                5 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Your unarmed strikes now deal 1d6 damage"
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.extra_attack)
                    character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack))
                }
                6 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Your Unarmored movement is now +15ft"
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.ki_empowered_strikes)
                    character.getRaceAndClassBonusStats().add(getString(R.string.ki_empowered_strikes))
                    bonusStats3!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Way of the Open Hand" -> {
                            bonusStats3!!.text = getString(R.string.wholeness_of_body)
                            character.getRaceAndClassBonusStats().add(getString(R.string.wholeness_of_body))
                        }
                        "Way of Shadow" -> {
                            bonusStats3!!.text = getString(R.string.shadow_step)
                            character.getRaceAndClassBonusStats().add(getString(R.string.shadow_step))
                        }
                    }
                }
                7 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.evasion)
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.stillness_of_mind)
                    character.getRaceAndClassBonusStats().add(getString(R.string.evasion))
                    character.getRaceAndClassBonusStats().add(getString(R.string.stillness_of_mind))
                }
                10 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Your Unarmored movement is now +20ft"
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.purity_of_body)
                    character.getRaceAndClassBonusStats().add(getString(R.string.purity_of_body))
                }
                11 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Your unarmed strikes now deal 1d8 damage"
                    bonusStats2!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Way of the Open Hand" -> {
                            bonusStats2!!.text = getString(R.string.tranquility)
                            character.getRaceAndClassBonusStats().add(getString(R.string.tranquility))
                        }
                        "Way of Shadow" -> {
                            bonusStats2!!.text = getString(R.string.cloak_of_shadows)
                            character.getRaceAndClassBonusStats().add(getString(R.string.cloak_of_shadows))
                        }
                    }
                }
                13 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.tongue_of_the_sun_and_moon)
                    character.getRaceAndClassBonusStats().add(getString(R.string.tongue_of_the_sun_and_moon))
                }
                14 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Your Unarmored movement is now +25ft"
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.diamond_soul)
                    character.getRaceAndClassBonusStats().add(getString(R.string.diamond_soul))
                }
                15 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.timeless_body_monk)
                    character.getRaceAndClassBonusStats().add(getString(R.string.timeless_body_monk))
                }
                17 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Your unarmed strikes now deal 1d10 damage"
                    bonusStats2!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Way of the Open Hand" -> {
                            bonusStats2!!.text = getString(R.string.quivering_palm)
                            character.getRaceAndClassBonusStats().add(getString(R.string.quivering_palm))
                        }
                        "Way of Shadow" -> {
                            bonusStats2!!.text = getString(R.string.opportunist)
                            character.getRaceAndClassBonusStats().add(getString(R.string.opportunist))
                        }
                    }
                }
                18 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Your Unarmored movement is now +30ft"
                }
                20 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.perfect_self)
                    character.getRaceAndClassBonusStats().add(getString(R.string.perfect_self))
                }
            }
            "Paladin" -> when (level) {
                2 -> {
                    val fightingStyles: MutableList<String> = ArrayList()
                    val fightingStyleAdapter = ArrayAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item, fightingStyles)
                    fightingStyles.add("Defense")
                    fightingStyles.add("Dueling")
                    fightingStyles.add("Great Weapon Fighting")
                    fightingStyles.add("Protection")
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "You now have the ability to cast spells"
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.divine_smite)
                    choiceHeader1!!.visibility = View.VISIBLE
                    choiceHeader1!!.text = "Choose a Fighting Style"
                    choice1!!.visibility = View.VISIBLE
                    choice1!!.adapter = fightingStyleAdapter
                    character.getRaceAndClassBonusStats().add(getString(R.string.divine_smite))
                }
                3 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.divine_health)
                    bonusStats2!!.visibility = View.VISIBLE
                    bonusStats2!!.text = getString(R.string.channel_divinity_paladin)
                    bonusStats3!!.visibility = View.VISIBLE
                    bonusStats4!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Oath of Devotion" -> {
                            bonusStats3!!.text = getString(R.string.channel_divinity_sacred_weapon)
                            bonusStats4!!.text = getString(R.string.channel_divinity_turn_the_unholy)
                        }
                        "Oath of the Ancients" -> {
                            bonusStats3!!.text = getString(R.string.channel_divinity_natures_wrath)
                            bonusStats4!!.text = getString(R.string.channel_divinity_turn_the_faithless)
                        }
                        "Oath of Vengeance" -> {
                            bonusStats3!!.text = getString(R.string.channel_divinity_abjure_enemy)
                            bonusStats4!!.text = getString(R.string.channel_divinity_vow_of_enmity)
                        }
                    }
                    character.getRaceAndClassBonusStats().add(getString(R.string.channel_divinity_paladin))
                    character.getRaceAndClassBonusStats().add(getString(R.string.divine_health))
                }
                5 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.extra_attack)
                    character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack))
                }
                6 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.aura_of_protection)
                    character.getRaceAndClassBonusStats().add(getString(R.string.aura_of_protection))
                }
                7 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Oath of Devotion" -> {
                            bonusStats1!!.text = getString(R.string.aura_of_devotion)
                            character.getRaceAndClassBonusStats().add(getString(R.string.aura_of_devotion))
                        }
                        "Oath of the Ancients" -> {
                            bonusStats1!!.text = getString(R.string.aura_of_warding)
                            character.getRaceAndClassBonusStats().add(getString(R.string.aura_of_warding))
                        }
                        "Oath of Vengeance" -> {
                            bonusStats1!!.text = getString(R.string.relentless_avenger)
                            character.getRaceAndClassBonusStats().add(getString(R.string.relentless_avenger))
                        }
                    }
                }
                10 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.aura_of_courage)
                    character.getRaceAndClassBonusStats().add(getString(R.string.aura_of_courage))
                }
                11 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.improved_divine_smite)
                    character.getRaceAndClassBonusStats().add(getString(R.string.improved_divine_smite))
                }
                14 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = getString(R.string.cleansing_touch)
                    character.getRaceAndClassBonusStats().add(getString(R.string.cleansing_touch))
                }
                15 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Oath of Devotion" -> {
                            bonusStats1!!.text = getString(R.string.purity_of_spirit)
                            character.getRaceAndClassBonusStats().add(getString(R.string.purity_of_spirit))
                        }
                        "Oath of the Ancients" -> {
                            bonusStats3!!.text = getString(R.string.undying_sentinel)
                            character.getRaceAndClassBonusStats().add(getString(R.string.undying_sentinel))
                        }
                        "Oath of Vengeance" -> {
                            bonusStats3!!.text = getString(R.string.soul_of_vengeance)
                            character.getRaceAndClassBonusStats().add(getString(R.string.soul_of_vengeance))
                        }
                    }
                }
                18 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    bonusStats1!!.text = "Your Auras now have a radius of 30 ft."
                }
                20 -> {
                    bonusStats1!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Oath of Devotion" -> {
                            bonusStats1!!.text = getString(R.string.holy_nimbus)
                            character.getRaceAndClassBonusStats().add(getString(R.string.holy_nimbus))
                        }
                        "Oath of the Ancients" -> {
                            bonusStats3!!.text = getString(R.string.elder_champion)
                            character.getRaceAndClassBonusStats().add(getString(R.string.elder_champion))
                        }
                        "Oath of Vengeance" -> {
                            bonusStats3!!.text = getString(R.string.avenging_angel)
                            character.getRaceAndClassBonusStats().add(getString(R.string.avenging_angel))
                        }
                    }
                }
            }
            "Ranger" -> {
                val hunterSubclassChoice: MutableList<String> = ArrayList()
                val favoredTerrainAdapter = ArrayAdapter(context,
                        android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.favored_terrain_array))
                val favoredEnemyAdapter = ArrayAdapter(context,
                        android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.favored_enemies_array))
                when (level) {
                    2 -> {
                        val fightingStyles: MutableList<String> = ArrayList()
                        val fightingStyleAdapter = ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, fightingStyles)
                        fightingStyles.add("Archery")
                        fightingStyles.add("Defense")
                        fightingStyles.add("Dueling")
                        fightingStyles.add("Two-Weapon Fighting")
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose a Fighting Style"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = fightingStyleAdapter
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "You now have the ability to cast spells"
                    }
                    3 -> when (character.getSubclass()) {
                        "Hunter" -> {
                            val hunterSubclassAdapter = ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item, hunterSubclassChoice)
                            hunterSubclassChoice.add("Colossus Slayer")
                            hunterSubclassChoice.add("Giant Killer")
                            hunterSubclassChoice.add("Horde Breaker")
                            choiceHeader1!!.visibility = View.VISIBLE
                            choiceHeader1!!.text = "Choose a subclass option"
                            choice1!!.visibility = View.VISIBLE
                            choice1!!.adapter = hunterSubclassAdapter
                        }
                        "Beast Master" -> {
                            bonusStats1!!.visibility = View.VISIBLE
                            bonusStats1!!.text = getString(R.string.rangers_companion)
                            character.getRaceAndClassBonusStats().add(getString(R.string.rangers_companion))
                        }
                    }
                    5 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.extra_attack)
                        character.getRaceAndClassBonusStats().add(getString(R.string.extra_attack))
                    }
                    6 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose another Favored Terrain"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = favoredTerrainAdapter
                        choiceHeader2!!.visibility = View.VISIBLE
                        choiceHeader2!!.text = "Choose another Favored Enemy"
                        choice2!!.visibility = View.VISIBLE
                        choice2!!.adapter = favoredEnemyAdapter
                    }
                    7 -> when (character.getSubclass()) {
                        "Hunter" -> {
                            val hunterSubclassAdapter = ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item, hunterSubclassChoice)
                            hunterSubclassChoice.add("Escape the Horde")
                            hunterSubclassChoice.add("Multiattack Defense")
                            hunterSubclassChoice.add("Steel Will")
                            choiceHeader1!!.visibility = View.VISIBLE
                            choiceHeader1!!.text = "Choose a subclass option"
                            choice1!!.visibility = View.VISIBLE
                            choice1!!.adapter = hunterSubclassAdapter
                        }
                        "Beast Master" -> {
                            bonusStats1!!.visibility = View.VISIBLE
                            bonusStats1!!.text = getString(R.string.exceptional_training)
                            character.getRaceAndClassBonusStats().add(getString(R.string.exceptional_training))
                        }
                    }
                    8 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.lands_stride)
                        character.getRaceAndClassBonusStats().add(getString(R.string.lands_stride))
                    }
                    10 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose another Favored Terrain"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = favoredTerrainAdapter
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.hide_in_plain_sight)
                        character.getRaceAndClassBonusStats().add(getString(R.string.hide_in_plain_sight))
                    }
                    11 -> when (character.getSubclass()) {
                        "Hunter" -> {
                            val hunterSubclassAdapter = ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item, hunterSubclassChoice)
                            hunterSubclassChoice.add("Volley")
                            hunterSubclassChoice.add("Whirlwind Attack")
                            choiceHeader1!!.visibility = View.VISIBLE
                            choiceHeader1!!.text = "Choose a subclass option"
                            choice1!!.visibility = View.VISIBLE
                            choice1!!.adapter = hunterSubclassAdapter
                        }
                        "Beast Master" -> {
                            bonusStats1!!.visibility = View.VISIBLE
                            bonusStats1!!.text = getString(R.string.bestial_fury)
                            character.getRaceAndClassBonusStats().add(getString(R.string.bestial_fury))
                        }
                    }
                    14 -> {
                        choiceHeader2!!.visibility = View.VISIBLE
                        choiceHeader2!!.text = "Choose another Favored Enemy"
                        choice2!!.visibility = View.VISIBLE
                        choice2!!.adapter = favoredEnemyAdapter
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.vanish)
                        character.getRaceAndClassBonusStats().add(getString(R.string.vanish))
                    }
                    15 -> when (character.getSubclass()) {
                        "Hunter" -> {
                            val hunterSubclassAdapter = ArrayAdapter(context,
                                    android.R.layout.simple_spinner_dropdown_item, hunterSubclassChoice)
                            hunterSubclassChoice.add("Evasion")
                            hunterSubclassChoice.add("Stand Against the Tide")
                            hunterSubclassChoice.add("Uncanny Dodge")
                            choiceHeader1!!.visibility = View.VISIBLE
                            choiceHeader1!!.text = "Choose a subclass option"
                            choice1!!.visibility = View.VISIBLE
                            choice1!!.adapter = hunterSubclassAdapter
                        }
                        "Beast Master" -> {
                            bonusStats1!!.visibility = View.VISIBLE
                            bonusStats1!!.text = getString(R.string.share_spells)
                            character.getRaceAndClassBonusStats().add(getString(R.string.share_spells))
                        }
                    }
                    18 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.feral_senses)
                        character.getRaceAndClassBonusStats().add(getString(R.string.feral_senses))
                    }
                    20 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.foe_slayer)
                        character.getRaceAndClassBonusStats().add(getString(R.string.foe_slayer))
                    }
                }
            }
            "Rogue" -> {
                val rogueExpertiseChoices = ArrayList<String>()
                val rogueExpertiseAdapter = ArrayAdapter(context,
                        android.R.layout.simple_spinner_dropdown_item, rogueExpertiseChoices)
                when (level) {
                    2 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.cunning_action)
                        character.getRaceAndClassBonusStats().add(getString(R.string.cunning_action))
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose two of your proficiencies. Your proficiency bonus will be doubled for any ability checks you make with that skill"
                        choice1!!.visibility = View.VISIBLE
                        choice2!!.visibility = View.VISIBLE
                        rogueExpertiseChoices.addAll(character.getProficiencyChoices())
                        rogueExpertiseChoices.add("Thieve's Tools")
                        choice1!!.adapter = rogueExpertiseAdapter
                        choice2!!.adapter = rogueExpertiseAdapter
                    }
                    3 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your sneak attack now does 2d6 damage"
                        bonusStats2!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Thief" -> {
                                bonusStats2!!.text = getString(R.string.fast_hands)
                                bonusStats3!!.visibility = View.VISIBLE
                                bonusStats3!!.text = getString(R.string.second_story_work)
                                character.getRaceAndClassBonusStats().add(getString(R.string.fast_hands))
                                character.getRaceAndClassBonusStats().add(getString(R.string.second_story_work))
                            }
                            "Assassin" -> {
                                bonusStats2!!.text = getString(R.string.assassinate)
                                character.getRaceAndClassBonusStats().add(getString(R.string.assassinate))
                            }
                            "Arcane Trickster" -> {
                                bonusStats2!!.text = "You now have the ability to cast spells. Go to the Spellcasting tab for more information"
                                bonusStats3!!.visibility = View.VISIBLE
                                bonusStats3!!.text = getString(R.string.mage_hand_legerdemain)
                                character.getRaceAndClassBonusStats().add(getString(R.string.mage_hand_legerdemain))
                            }
                        }
                    }
                    5 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.uncanny_dodge)
                        bonusStats2!!.visibility = View.VISIBLE
                        bonusStats2!!.text = "Your sneak attack now does 3d6 damage"
                        character.getRaceAndClassBonusStats().add(getString(R.string.uncanny_dodge))
                    }
                    6 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose two of your proficiencies. Your proficiency bonus will be doubled for any ability checks you make with that skill"
                        choice1!!.visibility = View.VISIBLE
                        choice2!!.visibility = View.VISIBLE
                        rogueExpertiseChoices.addAll(character.getProficiencyChoices())
                        if (!character.getClassBasedBonusStats2().contains("Thieve's Tools")) {
                            rogueExpertiseChoices.add("Thieve's Tools")
                        }
                        choice1!!.adapter = rogueExpertiseAdapter
                        choice2!!.adapter = rogueExpertiseAdapter
                    }
                    7 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.evasion)
                        bonusStats2!!.visibility = View.VISIBLE
                        bonusStats2!!.text = "Your sneak attack now does 4d6 damage"
                        character.getRaceAndClassBonusStats().add(getString(R.string.evasion))
                    }
                    9 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your sneak attack now does 5d6 damage"
                        bonusStats2!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Thief" -> {
                                bonusStats2!!.text = getString(R.string.supreme_sneak)
                                character.getRaceAndClassBonusStats().add(getString(R.string.supreme_sneak))
                            }
                            "Assassin" -> {
                                bonusStats2!!.text = getString(R.string.infiltration_expertise)
                                character.getRaceAndClassBonusStats().add(getString(R.string.infiltration_expertise))
                            }
                            "Arcane Trickster" -> {
                                bonusStats2!!.text = getString(R.string.magical_ambush)
                                character.getRaceAndClassBonusStats().add(getString(R.string.magical_ambush))
                            }
                        }
                    }
                    11 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.reliable_talent)
                        bonusStats2!!.visibility = View.VISIBLE
                        bonusStats2!!.text = "Your sneak attack now does 6d6 damage"
                        character.getRaceAndClassBonusStats().add(getString(R.string.reliable_talent))
                    }
                    13 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your sneak attack now does 7d6 damage"
                        bonusStats2!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Thief" -> {
                                bonusStats2!!.text = getString(R.string.use_magic_device)
                                character.getRaceAndClassBonusStats().add(getString(R.string.use_magic_device))
                            }
                            "Assassin" -> {
                                bonusStats2!!.text = getString(R.string.imposter)
                                character.getRaceAndClassBonusStats().add(getString(R.string.imposter))
                            }
                            "Arcane Trickster" -> {
                                bonusStats2!!.text = getString(R.string.versatile_trickster)
                                character.getRaceAndClassBonusStats().add(getString(R.string.versatile_trickster))
                            }
                        }
                    }
                    14 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.blindsense)
                        character.getRaceAndClassBonusStats().add(getString(R.string.blindsense))
                    }
                    15 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your sneak attack now does 8d6 damage"
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "You now have proficiency in WIS saving throws"
                    }
                    17 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your sneak attack now does 9d6 damage"
                        bonusStats2!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Thief" -> {
                                bonusStats2!!.text = getString(R.string.thiefs_reflexes)
                                character.getRaceAndClassBonusStats().add(getString(R.string.thiefs_reflexes))
                            }
                            "Assassin" -> {
                                bonusStats2!!.text = getString(R.string.death_strike)
                                character.getRaceAndClassBonusStats().add(getString(R.string.death_strike))
                            }
                            "Arcane Trickster" -> {
                                bonusStats2!!.text = getString(R.string.spell_thief)
                                character.getRaceAndClassBonusStats().add(getString(R.string.spell_thief))
                            }
                        }
                    }
                    18 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.elusive)
                        character.getRaceAndClassBonusStats().add(getString(R.string.elusive))
                    }
                    19 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = "Your sneak attack now does 10d6 damage"
                    }
                    20 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.stroke_of_luck)
                        character.getRaceAndClassBonusStats().add(getString(R.string.stroke_of_luck))
                    }
                }
            }
            "Sorcerer" -> {
                val metamagicOptions: MutableList<String> = ArrayList()
                val metamagicAdapter = ArrayAdapter(context,
                        android.R.layout.simple_spinner_dropdown_item, metamagicOptions)
                metamagicOptions.add("Careful Spell")
                metamagicOptions.add("Distant Spell")
                metamagicOptions.add("Empowered Spell")
                metamagicOptions.add("Extended Spell")
                metamagicOptions.add("Heightened Spell")
                metamagicOptions.add("Quickened Spell")
                metamagicOptions.add("Subtle Spell")
                metamagicOptions.add("Twinned Spell")
                when (level) {
                    2 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.font_of_magic)
                        character.getRaceAndClassBonusStats().add(getString(R.string.font_of_magic))
                    }
                    3 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose 2 Metamagic Options"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = metamagicAdapter
                        choice2!!.visibility = View.VISIBLE
                        choice2!!.adapter = metamagicAdapter
                    }
                    6 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Wild Magic" -> {
                                bonusStats1!!.text = getString(R.string.bend_luck)
                                character.getRaceAndClassBonusStats().add(getString(R.string.bend_luck))
                            }
                            "Draconic Bloodline" -> {
                                bonusStats1!!.text = getString(R.string.elemental_affinity)
                                character.getRaceAndClassBonusStats().add(getString(R.string.elemental_affinity))
                            }
                            "Phoenix Sorcery" -> {
                                bonusStats1!!.text = getString(R.string.phoenix_spark)
                                character.getRaceAndClassBonusStats().add(getString(R.string.phoenix_spark))
                            }
                            "Stone Sorcery" -> {
                                bonusStats1!!.text = getString(R.string.stone_aegis)
                                character.getRaceAndClassBonusStats().add(getString(R.string.stone_aegis))
                            }
                        }
                    }
                    10 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose a new Metamagic Option"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = metamagicAdapter
                    }
                    14 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Wild Magic" -> {
                                bonusStats1!!.text = getString(R.string.controlled_chaos)
                                character.getRaceAndClassBonusStats().add(getString(R.string.controlled_chaos))
                            }
                            "Draconic Bloodline" -> {
                                bonusStats1!!.text = getString(R.string.dragon_wings)
                                character.getRaceAndClassBonusStats().add(getString(R.string.dragon_wings))
                            }
                            "Phoenix Sorcery" -> {
                                bonusStats1!!.text = getString(R.string.nourishing_fire)
                                character.getRaceAndClassBonusStats().add(getString(R.string.nourishing_fire))
                            }
                            "Stone Sorcery" -> {
                                bonusStats1!!.text = getString(R.string.stones_edge)
                                character.getRaceAndClassBonusStats().add(getString(R.string.stones_edge))
                            }
                        }
                    }
                    17 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose a new Metamagic Option"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = metamagicAdapter
                    }
                    18 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "Wild Magic" -> {
                                bonusStats1!!.text = getString(R.string.spell_bombardment)
                                character.getRaceAndClassBonusStats().add(getString(R.string.spell_bombardment))
                            }
                            "Draconic Bloodline" -> {
                                bonusStats1!!.text = getString(R.string.draconic_presence)
                                character.getRaceAndClassBonusStats().add(getString(R.string.draconic_presence))
                            }
                            "Phoenix Sorcery" -> {
                                bonusStats1!!.text = getString(R.string.form_of_the_phoenix)
                                character.getRaceAndClassBonusStats().add(getString(R.string.form_of_the_phoenix))
                            }
                            "Stone Sorcery" -> {
                                bonusStats1!!.text = getString(R.string.earth_masters_aegis)
                                character.getRaceAndClassBonusStats().add(getString(R.string.earth_masters_aegis))
                            }
                        }
                    }
                    20 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.sorcerous_restoration)
                        character.getRaceAndClassBonusStats().add(getString(R.string.sorcerous_restoration))
                    }
                }
            }
            "Warlock" -> {
                val mysticArcanumChoices: ArrayList<String>
                val mysticArcanumAdapter: ArrayAdapter<String>
                val eldritchInvocations = ArrayList<String>()
                eldritchInvocations.add("Beguiling Influence")
                eldritchInvocations.add("Devil's Sight")
                eldritchInvocations.add("Eyes of the Rune Keeper")
                eldritchInvocations.add("Gaze of Two Minds")
                run {
                    var i = 0
                    while (i < character.getSpellsKnown().size) {
                        if (character.getSpellsKnown()[i].spellName.equals("Eldritch Blast", ignoreCase = true)) {
                            eldritchInvocations.add("Agonizing Blast")
                            eldritchInvocations.add("Eldritch Spear")
                            eldritchInvocations.add("Repelling Blast")
                        }
                        i++
                    }
                }
                when {
                    character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_tome)) -> {
                        eldritchInvocations.add("Book of Ancient Secrets")
                    }
                    character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_chain)) -> {
                        eldritchInvocations.add("Voice of the Chain Master")
                    }
                    character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_talisman)) -> {
                        eldritchInvocations.add("Reality Tear")
                    }
                    //eldritchInvocations.sort();
                }
                if (finalLevel >= 5) {
                    eldritchInvocations.add("One With Shadows")
                    if (character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_blade))) {
                        eldritchInvocations.add("Thirsting Blade")
                    } else if (character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_talisman))) {
                        eldritchInvocations.add("Trickster's Step")
                        eldritchInvocations.add("Eye of Yog-Sothoth")
                    }
                }
                if (finalLevel >= 12) {
                    if (character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_blade))) {
                        eldritchInvocations.add("Lifedrinker")
                    }
                }
                if (finalLevel >= 15) {
                    eldritchInvocations.add("Witch Sight")
                    if (character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_chain))) {
                        eldritchInvocations.add("Unbreakable Tether")
                    }
                }
                //eldritchInvocations.sort();
                var i = 0
                while (i < eldritchInvocations.size) {
                    var j = 0
                    while (j < character.getClassBasedBonusStats2().size) {
                        if (character.getClassBasedBonusStats2()[j].contains(eldritchInvocations[i])) {
                            eldritchInvocations.removeAt(i)
                            i--
                            break
                        }
                        j++
                    }
                    i++
                }
                val eldritchInvocationsAdapter = ArrayAdapter(context,
                        android.R.layout.simple_spinner_dropdown_item, eldritchInvocations)
                when (level) {
                    2 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose two Eldritch Invocations"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = eldritchInvocationsAdapter
                        choice2!!.visibility = View.VISIBLE
                        choice2!!.adapter = eldritchInvocationsAdapter
                    }
                    3 -> {
                        val pactBoonChoices = ArrayList<String>()
                        val pactBoonAdapter = ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, pactBoonChoices)
                        pactBoonChoices.add("Pact of the Blade")
                        pactBoonChoices.add("Pact of the Chain")
                        pactBoonChoices.add("Pact of the Talisman")
                        pactBoonChoices.add("Pact of the Tome")
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Your Patron bestows a gift upon you for your loyal service. Choose your Pact Boon"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = pactBoonAdapter
                    }
                    5 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose another Eldritch Invocation"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = eldritchInvocationsAdapter
                    }
                    6 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "The Archfey" -> {
                                bonusStats1!!.text = getString(R.string.misty_escape)
                                character.getRaceAndClassBonusStats().add(getString(R.string.misty_escape))
                            }
                            "The Fiend" -> {
                                bonusStats1!!.text = getString(R.string.dark_ones_own_luck)
                                character.getRaceAndClassBonusStats().add(getString(R.string.dark_ones_own_luck))
                            }
                            "The Great Old One" -> {
                                bonusStats1!!.text = getString(R.string.entropic_ward)
                                character.getRaceAndClassBonusStats().add(getString(R.string.entropic_ward))
                            }
                        }
                    }
                    7 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose another Eldritch Invocation"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = eldritchInvocationsAdapter
                    }
                    9 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose another Eldritch Invocation"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = eldritchInvocationsAdapter
                    }
                    10 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "The Archfey" -> {
                                bonusStats1!!.text = getString(R.string.beguiling_defenses)
                                character.getRaceAndClassBonusStats().add(getString(R.string.beguiling_defenses))
                            }
                            "The Fiend" -> {
                                bonusStats1!!.text = getString(R.string.fiendish_resilience)
                                character.getRaceAndClassBonusStats().add(getString(R.string.fiendish_resilience))
                            }
                            "The Great Old One" -> {
                                bonusStats1!!.text = getString(R.string.thought_shield)
                                character.getRaceAndClassBonusStats().add(getString(R.string.thought_shield))
                            }
                        }
                    }
                    11 -> {
                        mysticArcanumChoices = getSpellsPerLevel(6)
                        mysticArcanumAdapter = ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, mysticArcanumChoices)
                        choiceHeader1!!.visibility = View.VISIBLE
                        choice1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = getString(R.string.mystic_arcanum_choice)
                        choice1!!.adapter = mysticArcanumAdapter
                    }
                    12 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose another Eldritch Invocation"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = eldritchInvocationsAdapter
                    }
                    13 -> {
                        mysticArcanumChoices = getSpellsPerLevel(7)
                        mysticArcanumAdapter = ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, mysticArcanumChoices)
                        choiceHeader1!!.visibility = View.VISIBLE
                        choice1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = getString(R.string.mystic_arcanum_choice)
                        choice1!!.adapter = mysticArcanumAdapter
                    }
                    14 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "The Archfey" -> {
                                bonusStats1!!.text = getString(R.string.dark_delirium)
                                character.getRaceAndClassBonusStats().add(getString(R.string.dark_delirium))
                            }
                            "The Fiend" -> {
                                bonusStats1!!.text = getString(R.string.hurl_through_hell)
                                character.getRaceAndClassBonusStats().add(getString(R.string.hurl_through_hell))
                            }
                            "The Great Old One" -> {
                                bonusStats1!!.text = getString(R.string.create_thrall)
                                character.getRaceAndClassBonusStats().add(getString(R.string.create_thrall))
                            }
                        }
                    }
                    15 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose another Eldritch Invocation"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = eldritchInvocationsAdapter
                        mysticArcanumChoices = getSpellsPerLevel(8)
                        mysticArcanumAdapter = ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, mysticArcanumChoices)
                        choiceHeader2!!.visibility = View.VISIBLE
                        choice2!!.visibility = View.VISIBLE
                        choiceHeader2!!.text = getString(R.string.mystic_arcanum_choice)
                        choice2!!.adapter = mysticArcanumAdapter
                    }
                    17 -> {
                        mysticArcanumChoices = getSpellsPerLevel(9)
                        mysticArcanumAdapter = ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, mysticArcanumChoices)
                        choiceHeader1!!.visibility = View.VISIBLE
                        choice1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = getString(R.string.mystic_arcanum_choice)
                        choice1!!.adapter = mysticArcanumAdapter
                    }
                    18 -> {
                        choiceHeader1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = "Choose another Eldritch Invocation"
                        choice1!!.visibility = View.VISIBLE
                        choice1!!.adapter = eldritchInvocationsAdapter
                    }
                    20 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats1!!.text = getString(R.string.eldritch_master)
                        character.getRaceAndClassBonusStats().add(getString(R.string.eldritch_master))
                    }
                }
            }
            "Wizard" -> {
                val signatureSpells1: ArrayList<String>
                val signatureSpells2: ArrayList<String>
                val signatureSpellAdapter1: ArrayAdapter<String>
                val signatureSpellAdapter2: ArrayAdapter<String>
                when (level) {
                    2 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        bonusStats2!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "School of Abjuration" -> {
                                bonusStats1!!.text = getString(R.string.abjuration_savant)
                                bonusStats2!!.text = getString(R.string.arcane_ward)
                                character.getRaceAndClassBonusStats().add(getString(R.string.abjuration_savant))
                                character.getRaceAndClassBonusStats().add(getString(R.string.arcane_ward))
                            }
                            "School of Conjuration" -> {
                                bonusStats1!!.text = getString(R.string.conjuration_savant)
                                bonusStats2!!.text = getString(R.string.minor_conjuration)
                                character.getRaceAndClassBonusStats().add(getString(R.string.conjuration_savant))
                                character.getRaceAndClassBonusStats().add(getString(R.string.minor_conjuration))
                            }
                            "School of Divination" -> {
                                bonusStats1!!.text = getString(R.string.divination_savant)
                                bonusStats2!!.text = getString(R.string.portent)
                                character.getRaceAndClassBonusStats().add(getString(R.string.divination_savant))
                                character.getRaceAndClassBonusStats().add(getString(R.string.portent))
                            }
                            "School of Enchantment" -> {
                                bonusStats1!!.text = getString(R.string.enchantment_savant)
                                bonusStats2!!.text = getString(R.string.hypnotic_gaze)
                                character.getRaceAndClassBonusStats().add(getString(R.string.enchantment_savant))
                                character.getRaceAndClassBonusStats().add(getString(R.string.hypnotic_gaze))
                            }
                            "School of Evocation" -> {
                                bonusStats1!!.text = getString(R.string.evocation_savant)
                                bonusStats2!!.text = getString(R.string.sculpt_spells)
                                character.getRaceAndClassBonusStats().add(getString(R.string.evocation_savant))
                                character.getRaceAndClassBonusStats().add(getString(R.string.sculpt_spells))
                            }
                            "School of Illusion" -> {
                                bonusStats1!!.text = getString(R.string.illusion_savant)
                                bonusStats2!!.text = getString(R.string.improved_minor_illusion)
                                character.getRaceAndClassBonusStats().add(getString(R.string.illusion_savant))
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_minor_illusion))
                                getSubclassSpells("minor illusion")
                            }
                            "School of Necromancy" -> {
                                bonusStats1!!.text = getString(R.string.necromancy_savant)
                                bonusStats2!!.text = getString(R.string.grim_harvest)
                                character.getRaceAndClassBonusStats().add(getString(R.string.necromancy_savant))
                                character.getRaceAndClassBonusStats().add(getString(R.string.grim_harvest))
                            }
                            "School of Transmutation" -> {
                                bonusStats1!!.text = getString(R.string.transmutation_savant)
                                bonusStats2!!.text = getString(R.string.minor_alchemy)
                                character.getRaceAndClassBonusStats().add(getString(R.string.transmutation_savant))
                                character.getRaceAndClassBonusStats().add(getString(R.string.minor_alchemy))
                            }
                        }
                    }
                    6 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "School of Abjuration" -> {
                                bonusStats1!!.text = getString(R.string.projected_ward)
                                character.getRaceAndClassBonusStats().add(getString(R.string.projected_ward))
                            }
                            "School of Conjuration" -> {
                                bonusStats1!!.text = getString(R.string.benign_transposition)
                                character.getRaceAndClassBonusStats().add(getString(R.string.benign_transposition))
                            }
                            "School of Divination" -> {
                                bonusStats1!!.text = getString(R.string.expert_divination)
                                character.getRaceAndClassBonusStats().add(getString(R.string.expert_divination))
                            }
                            "School of Enchantment" -> {
                                bonusStats1!!.text = getString(R.string.instinctive_charm)
                                character.getRaceAndClassBonusStats().add(getString(R.string.instinctive_charm))
                            }
                            "School of Evocation" -> {
                                bonusStats1!!.text = getString(R.string.potent_cantrip)
                                character.getRaceAndClassBonusStats().add(getString(R.string.potent_cantrip))
                            }
                            "School of Illusion" -> {
                                bonusStats1!!.text = getString(R.string.malleable_illusions)
                                character.getRaceAndClassBonusStats().add(getString(R.string.malleable_illusions))
                            }
                            "School of Necromancy" -> {
                                bonusStats1!!.text = getString(R.string.undead_thralls)
                                character.getRaceAndClassBonusStats().add(getString(R.string.undead_thralls))
                                getSubclassSpells("animate dead")
                            }
                            "School of Transmutation" -> {
                                bonusStats1!!.text = getString(R.string.transmuters_stone)
                                character.getRaceAndClassBonusStats().add(getString(R.string.transmuters_stone))
                            }
                        }
                    }
                    10 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "School of Abjuration" -> {
                                bonusStats1!!.text = getString(R.string.improved_abjuration)
                                character.getRaceAndClassBonusStats().add(getString(R.string.improved_abjuration))
                            }
                            "School of Conjuration" -> {
                                bonusStats1!!.text = getString(R.string.focused_conjuration)
                                character.getRaceAndClassBonusStats().add(getString(R.string.focused_conjuration))
                            }
                            "School of Divination" -> {
                                bonusStats1!!.text = getString(R.string.the_third_eye)
                                character.getRaceAndClassBonusStats().add(getString(R.string.the_third_eye))
                            }
                            "School of Enchantment" -> {
                                bonusStats1!!.text = getString(R.string.split_enchantment)
                                character.getRaceAndClassBonusStats().add(getString(R.string.split_enchantment))
                            }
                            "School of Evocation" -> {
                                bonusStats1!!.text = getString(R.string.empowered_evocation)
                                character.getRaceAndClassBonusStats().add(getString(R.string.empowered_evocation))
                            }
                            "School of Illusion" -> {
                                bonusStats1!!.text = getString(R.string.illusory_self)
                                character.getRaceAndClassBonusStats().add(getString(R.string.illusory_self))
                            }
                            "School of Necromancy" -> {
                                bonusStats1!!.text = getString(R.string.inured_to_undeath)
                                character.getRaceAndClassBonusStats().add(getString(R.string.inured_to_undeath))
                            }
                            "School of Transmutation" -> {
                                bonusStats1!!.text = getString(R.string.shapechanger)
                                character.getRaceAndClassBonusStats().add(getString(R.string.shapechanger))
                                getSubclassSpells("polymorph")
                            }
                        }
                    }
                    14 -> {
                        bonusStats1!!.visibility = View.VISIBLE
                        when (character.getSubclass()) {
                            "School of Abjuration" -> {
                                bonusStats1!!.text = getString(R.string.spell_resistance)
                                character.getRaceAndClassBonusStats().add(getString(R.string.spell_resistance))
                            }
                            "School of Conjuration" -> {
                                bonusStats1!!.text = getString(R.string.durable_summons)
                                character.getRaceAndClassBonusStats().add(getString(R.string.durable_summons))
                            }
                            "School of Divination" -> {
                                bonusStats1!!.text = getString(R.string.greater_portent)
                                character.getRaceAndClassBonusStats().add(getString(R.string.greater_portent))
                            }
                            "School of Enchantment" -> {
                                bonusStats1!!.text = getString(R.string.alter_memories)
                                character.getRaceAndClassBonusStats().add(getString(R.string.alter_memories))
                            }
                            "School of Evocation" -> {
                                bonusStats1!!.text = getString(R.string.overchannel)
                                character.getRaceAndClassBonusStats().add(getString(R.string.overchannel))
                            }
                            "School of Illusion" -> {
                                bonusStats1!!.text = getString(R.string.illusory_reality)
                                character.getRaceAndClassBonusStats().add(getString(R.string.illusory_reality))
                            }
                            "School of Necromancy" -> {
                                bonusStats1!!.text = getString(R.string.command_undead)
                                character.getRaceAndClassBonusStats().add(getString(R.string.command_undead))
                            }
                            "School of Transmutation" -> {
                                bonusStats1!!.text = getString(R.string.master_transmuter)
                                character.getRaceAndClassBonusStats().add(getString(R.string.master_transmuter))
                            }
                        }
                    }
                    18 -> {
                        signatureSpells1 = getSpellsPerLevel(1)
                        signatureSpellAdapter1 = ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, signatureSpells1)
                        signatureSpells2 = getSpellsPerLevel(2)
                        signatureSpellAdapter2 = ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, signatureSpells2)
                        choiceHeader1!!.visibility = View.VISIBLE
                        choice1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = getString(R.string.spell_mastery)
                        choice1!!.adapter = signatureSpellAdapter1
                        choice2!!.visibility = View.VISIBLE
                        choice2!!.adapter = signatureSpellAdapter2
                    }
                    20 -> {
                        signatureSpells1 = getSpellsPerLevel(3)
                        signatureSpellAdapter1 = ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, signatureSpells1)
                        signatureSpells2 = getSpellsPerLevel(3)
                        signatureSpellAdapter2 = ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, signatureSpells2)
                        choiceHeader1!!.visibility = View.VISIBLE
                        choice1!!.visibility = View.VISIBLE
                        choiceHeader1!!.text = getString(R.string.signature_spells)
                        choice1!!.adapter = signatureSpellAdapter1
                        choice2!!.visibility = View.VISIBLE
                        choice2!!.adapter = signatureSpellAdapter2
                    }
                }
            }
        }
        return rootView
    }

    private fun returnToDetailActivity(character: Character) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra((activity as DetailActivity?)!!.CHARACTER, character)
        activity!!.finish()
        startActivity(intent)
    }

    private fun improveAbilityScore(abilityScore: String, abilityScoreList: ArrayList<Int>) {
        when (abilityScore) {
            "Strength" -> abilityScoreList[0] = abilityScoreList[0] + 1
            "Dexterity" -> abilityScoreList[1] = abilityScoreList[1] + 1
            "Constitution" -> abilityScoreList[2] = abilityScoreList[2] + 1
            "Intelligence" -> abilityScoreList[3] = abilityScoreList[3] + 1
            "Wisdom" -> abilityScoreList[4] = abilityScoreList[4] + 1
            "Charisma" -> abilityScoreList[5] = abilityScoreList[5] + 1
        }
    }

    private fun getSpellsPerLevel(spellLevel: Int): ArrayList<String> {
        val spellChoices = ArrayList<String>()
        for (i in 0 until (activity as DetailActivity?)!!.spellViewModel.loadAllSpells().value!!.size) {
            if ((activity as DetailActivity?)!!.spellViewModel.loadAllSpells().value!![i].classList!!.contains(character.getCharacterClass()) && (activity as DetailActivity?)!!.spellViewModel.loadAllSpells().value!![i].level === spellLevel) {
                spellChoices.add((activity as DetailActivity?)!!.spellViewModel.loadAllSpells().value!![i].spellName)
            }
        }
        return spellChoices
    }

    private fun getSubclassSpells(spellName: String) {
        (activity as DetailActivity?)!!.spellViewModel.loadAllSpells().observe(this, Observer<List<Spell?>?> { t ->
            for (i in t!!.indices) {
                if (t[i]!!.spellName.equals(spellName, ignoreCase = true)) {
                    val subclassSpell = t[i]!!
                    character.getSpellsKnown().add(subclassSpell)
                    for (j in 0 until character.getSpellsKnown().size - 1) {
                        if (character.getSpellsKnown()[j].spellName == subclassSpell.spellName) {
                            character.getSpellsKnown().remove(subclassSpell)
                        }
                    }
                }
            }
        })
    }
}