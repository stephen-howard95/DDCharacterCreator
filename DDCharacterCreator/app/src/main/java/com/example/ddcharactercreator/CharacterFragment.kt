package com.example.ddcharactercreator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

class CharacterFragment : Fragment() {
    @JvmField
    @BindView(R.id.character_name)
    var characterName: TextView? = null

    @JvmField
    @BindView(R.id.character_level)
    var characterLevel: TextView? = null

    @JvmField
    @BindView(R.id.character_class)
    var characterClass: TextView? = null

    @JvmField
    @BindView(R.id.character_race)
    var characterRace: TextView? = null

    @JvmField
    @BindView(R.id.character_alignment)
    var characterAlignment: TextView? = null

    @JvmField
    @BindView(R.id.languages)
    var languagesKnown: EditText? = null

    @JvmField
    @BindView(R.id.subclass_info_textview_1)
    var subclassInfoTextView1: TextView? = null

    @JvmField
    @BindView(R.id.subclass_info_textview_2)
    var subclassInfoTextView2: TextView? = null

    @JvmField
    @BindView(R.id.subclass_info_textview_3)
    var subclassInfoTextView3: TextView? = null

    @JvmField
    @BindView(R.id.subrace_textview_1)
    var subraceInfoTextView1: TextView? = null

    @JvmField
    @BindView(R.id.subrace_textview_2)
    var subraceInfoTextView2: TextView? = null

    @JvmField
    @BindView(R.id.checkboxes_textview_1)
    var checkBoxes1: TextView? = null

    @JvmField
    @BindView(R.id.checkboxes_textview_2)
    var checkBoxes2: TextView? = null

    @JvmField
    @BindView(R.id.checkboxes_textview_3)
    var checkBoxes3: TextView? = null

    @JvmField
    @BindView(R.id.checkboxes_textview_4)
    var checkBoxes4: TextView? = null

    @JvmField
    @BindView(R.id.checkboxes_textview_5)
    var checkBoxes5: TextView? = null

    @JvmField
    @BindView(R.id.subrace_checkboxes_textview)
    var subraceCheckboxTextView: TextView? = null

    @JvmField
    @BindView(R.id.subrace_checkbox)
    var subraceCheckBox: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_1_1)
    var checkBox1_1: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_1_2)
    var checkBox1_2: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_1_3)
    var checkBox1_3: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_1_4)
    var checkBox1_4: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_1_5)
    var checkBox1_5: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_1_6)
    var checkBox1_6: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_2_1)
    var checkBox2_1: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_2_2)
    var checkBox2_2: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_2_3)
    var checkBox2_3: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_2_4)
    var checkBox2_4: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_2_5)
    var checkBox2_5: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_2_6)
    var checkBox2_6: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_3_1)
    var checkBox3_1: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_3_2)
    var checkBox3_2: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_3_3)
    var checkBox3_3: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_4_1)
    var checkBox4_1: CheckBox? = null

    @JvmField
    @BindView(R.id.checkbox_5_1)
    var checkBox5_1: CheckBox? = null

    @JvmField
    @BindView(R.id.subclass_info_edittext_textview)
    var editTextTextView: TextView? = null

    @JvmField
    @BindView(R.id.subclass_info_edittext)
    var subclassInfoEditText: EditText? = null

    @JvmField
    @BindView(R.id.subclass_info_listview_header)
    var subclassInfoHeader: TextView? = null

    @JvmField
    @BindView(R.id.subclass_info_listview)
    var subclassInfoListView: RecyclerView? = null

    @JvmField
    @BindView(R.id.extra_info_listview_header)
    var extraInfoHeader: TextView? = null

    @JvmField
    @BindView(R.id.extra_info_listview)
    var bonusStatsList: RecyclerView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character, container, false)
        val character: Character = (activity as DetailActivity?)!!.character
        val level = character.getLevel()
        ButterKnife.bind(this, rootView)

        //Setting RecyclerView LayoutManager and Dividers
        val layoutManager1 = LinearLayoutManager(context)
        val layoutManager2 = LinearLayoutManager(context)
        val dividerItemDecoration1 = DividerItemDecoration(context,
                layoutManager1.orientation)
        val dividerItemDecoration2 = DividerItemDecoration(context,
                layoutManager2.orientation)
        subclassInfoListView!!.layoutManager = layoutManager1
        subclassInfoListView!!.addItemDecoration(dividerItemDecoration1)
        bonusStatsList!!.layoutManager = layoutManager2
        bonusStatsList!!.addItemDecoration(dividerItemDecoration2)

        //Setting Character name, level, character class, race, and alignment
        characterName!!.text = character.getName()
        characterLevel!!.text = "Level $level"
        characterClass!!.text = character.getCharacterClass()
        characterRace!!.text = character.getRace()
        characterAlignment!!.text = character.getAlignment()

        //Adding in Racial Benefits and languages
        //TODO: remove language from bonusStats list. It shouldn't display in the racial/class bonus list. (Maybe languages should go in a different list?)
        val bonusStats = character.getRaceAndClassBonusStats()
        languagesKnown!!.setText(bonusStats[0])
        if (character.getRace() == "Dragonborn") {
            subraceInfoTextView1!!.visibility = View.VISIBLE
            subraceInfoTextView2!!.visibility = View.VISIBLE
            subraceCheckboxTextView!!.visibility = View.VISIBLE
            subraceCheckBox!!.visibility = View.VISIBLE
            if (character.getLevel() < 6) {
                subraceInfoTextView1!!.text = "Breath Weapon Damage: 2d6"
            } else if (character.getLevel() < 11) {
                subraceInfoTextView1!!.text = "Breath Weapon Damage: 3d6"
            } else if (character.getLevel() < 16) {
                subraceInfoTextView1!!.text = "Breath Weapon Damage: 4d6"
            } else {
                subraceInfoTextView1!!.text = "Breath Weapon Damage: 5d6"
            }
            subraceInfoTextView2!!.text = java.lang.String.format("Breath Weapon save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[2]))
            subraceCheckboxTextView!!.text = "Breath Weapon"
        } else if (character.getRace() == "Half-Orc") {
            subraceCheckboxTextView!!.visibility = View.VISIBLE
            subraceCheckboxTextView!!.text = "Relentless Endurance: "
            subraceCheckBox!!.visibility = View.VISIBLE
        } else if (character.getRace() == "Goliath") {
            subraceCheckboxTextView!!.visibility = View.VISIBLE
            subraceCheckboxTextView!!.text = "Stone's Endurance: "
            subraceCheckBox!!.visibility = View.VISIBLE
        }
        when (character.getCharacterClass()) {
            "Barbarian" -> {
                subclassInfoTextView1!!.visibility = View.VISIBLE
                subclassInfoTextView1!!.text = String.format("%s +2", getString(R.string.rage_damage))
                checkBoxes1!!.visibility = View.VISIBLE
                checkBoxes1!!.setText(R.string.rage_uses)
                checkBox1_1!!.visibility = View.VISIBLE
                checkBox1_2!!.visibility = View.VISIBLE
                if (level >= 3) {
                    checkBox1_3!!.visibility = View.VISIBLE
                }
                if (level >= 6) {
                    checkBox1_4!!.visibility = View.VISIBLE
                }
                if (level >= 9) {
                    subclassInfoTextView1!!.text = String.format("%s +3", getString(R.string.rage_damage))
                }
                if (level >= 11) {
                    val relentlessRage = intArrayOf(10)
                    subclassInfoTextView2!!.visibility = View.VISIBLE
                    subclassInfoTextView2!!.text = getString(R.string.relentless_rage_DC) + relentlessRage[0].toString()
                    subclassInfoTextView2!!.setOnClickListener {
                        relentlessRage[0] += 5
                        subclassInfoTextView2!!.text = getString(R.string.relentless_rage_DC) + relentlessRage[0].toString()
                    }
                }
                if (level >= 12) {
                    checkBox1_5!!.visibility = View.VISIBLE
                }
                if (level >= 16) {
                    subclassInfoTextView1!!.text = String.format("%s +4", getString(R.string.rage_damage))
                }
                if (level >= 17) {
                    checkBox1_6!!.visibility = View.VISIBLE
                }
                if (level == 20) {
                    checkBox1_1!!.visibility = View.INVISIBLE
                    checkBox1_2!!.visibility = View.INVISIBLE
                    checkBox1_3!!.visibility = View.INVISIBLE
                    checkBox1_4!!.visibility = View.INVISIBLE
                    checkBox1_5!!.visibility = View.INVISIBLE
                    checkBox1_6!!.visibility = View.INVISIBLE
                    checkBoxes1!!.text = "Rage Uses: Infinite"
                }
            }
            "Bard" -> {
                subclassInfoTextView1!!.visibility = View.VISIBLE
                subclassInfoTextView1!!.text = String.format("%s d6", getString(R.string.bardic_inspiration_die))
                checkBoxes1!!.visibility = View.VISIBLE
                checkBoxes1!!.text = getString(R.string.bardic_inspiration_uses)
                checkBox1_1!!.visibility = View.VISIBLE
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 2) {
                    checkBox1_2!!.visibility = View.VISIBLE
                }
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 3) {
                    checkBox1_3!!.visibility = View.VISIBLE
                }
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 4) {
                    checkBox1_4!!.visibility = View.VISIBLE
                }
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 5) {
                    checkBox1_5!!.visibility = View.VISIBLE
                }
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 6) {
                    checkBox1_6!!.visibility = View.VISIBLE
                }
                if (level >= 2) {
                    subclassInfoTextView2!!.visibility = View.VISIBLE
                    subclassInfoTextView2!!.text = String.format("%s d6", getString(R.string.song_of_rest_die))
                }
                if (level >= 5) {
                    subclassInfoTextView1!!.text = String.format("%s d8", getString(R.string.bardic_inspiration_die))
                }
                if (level >= 9) {
                    subclassInfoTextView2!!.text = String.format("%s d8", getString(R.string.song_of_rest_die))
                }
                if (level >= 10) {
                    subclassInfoTextView1!!.text = String.format("%s d10", getString(R.string.bardic_inspiration_die))
                }
                if (level >= 13) {
                    subclassInfoTextView2!!.text = String.format("%s d10", getString(R.string.song_of_rest_die))
                }
                if (level >= 15) {
                    subclassInfoTextView1!!.text = String.format("%s d12", getString(R.string.bardic_inspiration_die))
                }
                if (level >= 17) {
                    subclassInfoTextView2!!.text = String.format("%s d12", getString(R.string.song_of_rest_die))
                }
            }
            "Cleric" -> {
                val channelDivinityUses = ArrayList<String>()
                val channelDivinityAdapter = ListAdapter(context!!, channelDivinityUses, false)
                subclassInfoTextView1!!.visibility = View.VISIBLE
                subclassInfoTextView1!!.text = String.format("Divine Domain: %s", character.getSubclass())
                when (character.getSubclass()) {
                    "Light" -> {
                        checkBoxes2!!.visibility = View.VISIBLE
                        checkBoxes2!!.text = getString(R.string.warding_flare_uses)
                        checkBox2_1!!.visibility = View.VISIBLE
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 2) {
                            checkBox2_2!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 3) {
                            checkBox2_3!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 4) {
                            checkBox2_4!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 5) {
                            checkBox2_5!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 6) {
                            checkBox2_6!!.visibility = View.VISIBLE
                        }
                    }
                    "Tempest" -> {
                        checkBoxes2!!.visibility = View.VISIBLE
                        checkBoxes2!!.text = getString(R.string.wrath_of_the_storm_uses)
                        checkBox2_1!!.visibility = View.VISIBLE
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 2) {
                            checkBox2_2!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 3) {
                            checkBox2_3!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 4) {
                            checkBox2_4!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 5) {
                            checkBox2_5!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 6) {
                            checkBox2_6!!.visibility = View.VISIBLE
                        }
                    }
                    "War" -> {
                        checkBoxes2!!.visibility = View.VISIBLE
                        checkBoxes2!!.text = getString(R.string.war_priest_uses)
                        checkBox2_1!!.visibility = View.VISIBLE
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 2) {
                            checkBox2_2!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 3) {
                            checkBox2_3!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 4) {
                            checkBox2_4!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 5) {
                            checkBox2_5!!.visibility = View.VISIBLE
                        }
                        if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]) >= 6) {
                            checkBox2_6!!.visibility = View.VISIBLE
                        }
                    }
                }
                if (level >= 2) {
                    checkBoxes1!!.visibility = View.VISIBLE
                    checkBoxes1!!.text = getString(R.string.channel_divinity_uses)
                    checkBox1_1!!.visibility = View.VISIBLE
                    subclassInfoHeader!!.visibility = View.VISIBLE
                    subclassInfoHeader!!.text = getString(R.string.channel_divinity_abilities)
                    subclassInfoListView!!.visibility = View.VISIBLE
                    channelDivinityUses.add(getString(R.string.channel_divinity_turn_undead))
                    when (character.getSubclass()) {
                        "Knowledge" -> channelDivinityUses.add(getString(R.string.channel_divinity_knowledge_of_the_ages))
                        "Life" -> channelDivinityUses.add(getString(R.string.channel_divinity_preserve_life))
                        "Light" -> channelDivinityUses.add(getString(R.string.channel_divinity_radiance_of_the_dawn))
                        "Nature" -> channelDivinityUses.add(getString(R.string.channel_divinity_charm_animals_and_plants))
                        "Tempest" -> channelDivinityUses.add(getString(R.string.channel_divinity_destructive_wrath))
                        "Trickery" -> channelDivinityUses.add(getString(R.string.channel_divinity_invoke_duplicity))
                        "War" -> channelDivinityUses.add(getString(R.string.channel_divinity_guided_strike))
                    }
                }
                if (level >= 5) {
                    subclassInfoTextView2!!.visibility = View.VISIBLE
                    subclassInfoTextView2!!.text = getString(R.string.destroy_undead_threshold) + "1/2 or lower"
                }
                if (level >= 6) {
                    checkBox1_2!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Knowledge" -> channelDivinityUses.add(getString(R.string.channel_divinity_read_thoughts))
                        "Trickery" -> channelDivinityUses.add(getString(R.string.channel_divinity_cloak_of_shadows))
                        "War" -> channelDivinityUses.add(getString(R.string.channel_divinity_war_gods_blessing))
                    }
                }
                if (level >= 8) {
                    subclassInfoTextView2!!.text = String.format("%s 1 or lower", getString(R.string.destroy_undead_threshold))
                }
                if (level >= 10) {
                    checkBoxes3!!.visibility = View.VISIBLE
                    checkBox3_1!!.visibility = View.VISIBLE
                    checkBoxes3!!.text = getString(R.string.divine_intervention_use)
                    subclassInfoTextView3!!.visibility = View.VISIBLE
                    subclassInfoTextView3!!.text = getString(R.string.divine_intervention_success_range) + character.getLevel() + " or lower"
                }
                if (level >= 11) {
                    subclassInfoTextView2!!.text = String.format("%s 2 or lower", getString(R.string.destroy_undead_threshold))
                }
                if (level >= 14) {
                    subclassInfoTextView2!!.text = String.format("%s 3 or lower", getString(R.string.destroy_undead_threshold))
                }
                if (level >= 17) {
                    subclassInfoTextView2!!.text = String.format("%s 4 or lower", getString(R.string.destroy_undead_threshold))
                    when (character.getSubclass()) {
                        "Knowledge" -> {
                            checkBoxes4!!.visibility = View.VISIBLE
                            checkBox4_1!!.visibility = View.VISIBLE
                            checkBoxes4!!.text = getString(R.string.visions_of_the_past_use)
                        }
                    }
                }
                if (level >= 18) {
                    checkBox1_3!!.visibility = View.VISIBLE
                }
                if (level == 20) {
                    subclassInfoTextView3!!.text = String.format("%s Guaranteed Success", getString(R.string.divine_intervention_success_range))
                }
                subclassInfoListView!!.adapter = channelDivinityAdapter
            }
            "Druid" -> {
                if (level >= 2) {
                    checkBoxes1!!.visibility = View.VISIBLE
                    checkBoxes1!!.text = getString(R.string.wild_shape_uses)
                    checkBox1_1!!.visibility = View.VISIBLE
                    checkBox1_2!!.visibility = View.VISIBLE
                    subclassInfoTextView1!!.visibility = View.VISIBLE
                    subclassInfoTextView1!!.text = String.format("%s Max CR = 1/4, No Flying/Swimming Speed", getString(R.string.wild_shape_limits))
                    subclassInfoTextView2!!.visibility = View.VISIBLE
                    subclassInfoTextView2!!.text = getString(R.string.wild_shape_time_limit) + level / 2 + "hours"
                    if (character.getSubclass().contains("Circle of the Land")) {
                        subclassInfoTextView3!!.visibility = View.VISIBLE
                        subclassInfoTextView3!!.text = getString(R.string.natural_recovery_amount) + "<= " + level / 2
                        checkBoxes2!!.visibility = View.VISIBLE
                        checkBoxes2!!.text = "Natural Recovery: "
                        checkBox2_1!!.visibility = View.VISIBLE
                    } else if (character.getSubclass() == "Circle of the Moon") {
                        subclassInfoTextView1!!.text = String.format("%s Max CR = 1, No Flying/Swimming Speed", getString(R.string.wild_shape_limits))
                    }
                }
                if (level >= 4) {
                    subclassInfoTextView1!!.text = String.format("%s Max CR = 1/2, No Flying Speed", getString(R.string.wild_shape_limits))
                    if (character.getSubclass() == "Circle of the Moon") {
                        subclassInfoTextView1!!.text = String.format("%s Max CR = 1, No Flying Speed", getString(R.string.wild_shape_limits))
                    }
                }
                if (level >= 6) {
                    if (character.getSubclass() == "Circle of the Moon") {
                        subclassInfoTextView1!!.text = getString(R.string.wild_shape_limits) + "Max CR = " + level / 3
                    }
                }
                if (level >= 8) {
                    subclassInfoTextView1!!.text = String.format("%s Max CR = 1", getString(R.string.wild_shape_limits))
                    if (character.getSubclass() == "Circle of the Moon") {
                        subclassInfoTextView1!!.text = getString(R.string.wild_shape_limits) + "Max CR = " + level / 3
                    }
                }
                if (level == 20) {
                    checkBox1_2!!.visibility = View.GONE
                    checkBox1_1!!.visibility = View.GONE
                    checkBoxes1!!.text = "Wild Shape Uses: Infinite"
                }
            }
            "Fighter" -> {
                val maneuverAdapter = ListAdapter(context!!, character.getClassBasedBonusStats2(), false)
                subclassInfoTextView1!!.visibility = View.VISIBLE
                subclassInfoTextView1!!.text = getString(R.string.fighting_style)
                checkBoxes4!!.visibility = View.VISIBLE
                checkBox4_1!!.visibility = View.VISIBLE
                checkBoxes4!!.text = getString(R.string.second_wind_amount) + "1d10 + " + character.getLevel()
                var superiorityDice = "d8"
                if (level >= 2) {
                    checkBoxes3!!.visibility = View.VISIBLE
                    checkBoxes3!!.text = getString(R.string.action_surge_uses)
                    checkBox3_1!!.visibility = View.VISIBLE
                }
                if (level >= 3 && character.getSubclass() == "Battle Master") {
                    subclassInfoHeader!!.visibility = View.VISIBLE
                    subclassInfoHeader!!.text = "Maneuvers"
                    subclassInfoListView!!.visibility = View.VISIBLE
                    checkBoxes1!!.visibility = View.VISIBLE
                    checkBox1_1!!.visibility = View.VISIBLE
                    checkBox1_2!!.visibility = View.VISIBLE
                    checkBox1_3!!.visibility = View.VISIBLE
                    checkBox1_4!!.visibility = View.VISIBLE
                    checkBoxes1!!.text = String.format("%s Superiority Dice: ", superiorityDice)
                }
                if (level >= 7 && character.getSubclass() == "Battle Master") {
                    checkBox1_5!!.visibility = View.VISIBLE
                }
                if (level >= 9) {
                    checkBoxes2!!.visibility = View.VISIBLE
                    checkBoxes2!!.text = getString(R.string.indomitable_uses)
                    checkBox2_1!!.visibility = View.VISIBLE
                }
                if (level >= 10 && character.getSubclass() == "Battle Master") {
                    superiorityDice = "d10"
                    checkBoxes1!!.text = String.format("%s Superiority Dice: ", superiorityDice)
                }
                if (level >= 13) {
                    checkBox2_2!!.visibility = View.VISIBLE
                }
                if (level >= 15 && character.getSubclass() == "Battle Master") {
                    checkBox1_6!!.visibility = View.VISIBLE
                }
                if (level >= 17) {
                    checkBox3_2!!.visibility = View.VISIBLE
                    checkBox2_3!!.visibility = View.VISIBLE
                }
                if (level >= 18 && character.getSubclass() == "Battle Master") {
                    superiorityDice = "d12"
                    checkBoxes1!!.text = String.format("%s Superiority Dice: ", superiorityDice)
                }
                subclassInfoListView!!.layoutManager = LinearLayoutManager(context)
                subclassInfoListView!!.adapter = maneuverAdapter
            }
            "Monk" -> {
                val kiPointAdapter = ListAdapter(context!!, character.getClassBasedBonusStats2(), false)
                subclassInfoTextView1!!.visibility = View.VISIBLE
                subclassInfoTextView1!!.text = String.format("%s 1d4", getString(R.string.unarmed_strike_damage))
                if (level >= 2) {
                    editTextTextView!!.visibility = View.VISIBLE
                    editTextTextView!!.text = getString(R.string.ki_points) + " " + character.getLevel()
                    subclassInfoEditText!!.visibility = View.VISIBLE
                    subclassInfoEditText!!.setText(character.getLevel().toString())
                    subclassInfoHeader!!.visibility = View.VISIBLE
                    subclassInfoHeader!!.text = getString(R.string.ki_point_abilities)
                    subclassInfoListView!!.visibility = View.VISIBLE
                    character.getClassBasedBonusStats2().add(getString(R.string.flurry_of_blows))
                    character.getClassBasedBonusStats2().add(getString(R.string.patient_defense))
                    character.getClassBasedBonusStats2().add(getString(R.string.step_of_the_wind))
                    subclassInfoTextView2!!.visibility = View.VISIBLE
                    subclassInfoTextView2!!.text = String.format("%s +10ft", getString(R.string.unarmored_movement))
                }
                if (level >= 3) {
                    character.getClassBasedBonusStats2().add(getString(R.string.deflect_missiles_ki))
                }
                if (level >= 5) {
                    subclassInfoTextView1!!.text = String.format("%s 1d6", getString(R.string.unarmed_strike_damage))
                    character.getClassBasedBonusStats2().add(getString(R.string.stunning_strike))
                }
                if (level >= 6) {
                    subclassInfoTextView2!!.text = String.format("%s +15ft", getString(R.string.unarmored_movement))
                    if (character.getSubclass() == "Way of the Open Hand") {
                        checkBoxes1!!.visibility = View.VISIBLE
                        checkBoxes1!!.text = getString(R.string.wholeness_of_body_use)
                        checkBox1_1!!.visibility = View.VISIBLE
                    }
                }
                if (level >= 10) {
                    subclassInfoTextView2!!.text = String.format("%s +20ft", getString(R.string.unarmored_movement))
                }
                if (level >= 11) {
                    subclassInfoTextView1!!.text = String.format("%s 1d8", getString(R.string.unarmed_strike_damage))
                }
                if (level >= 14) {
                    subclassInfoTextView2!!.text = String.format("%s +25ft", getString(R.string.unarmored_movement))
                    character.getClassBasedBonusStats2().add(getString(R.string.diamond_soul_ki))
                }
                if (level >= 17) {
                    subclassInfoTextView1!!.text = String.format("%s 1d10", getString(R.string.unarmed_strike_damage))
                }
                if (level >= 18) {
                    subclassInfoTextView2!!.text = String.format("%s +30ft", getString(R.string.unarmored_movement))
                    character.getClassBasedBonusStats2().add(getString(R.string.empty_body))
                    character.getClassBasedBonusStats2().add(getString(R.string.empty_body_2))
                }
                subclassInfoListView!!.adapter = kiPointAdapter
            }
            "Paladin" -> {
                checkBoxes1!!.visibility = View.VISIBLE
                checkBoxes1!!.setText(R.string.divine_sense_uses)
                checkBox1_1!!.visibility = View.VISIBLE
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 1) {
                    checkBox1_2!!.visibility = View.VISIBLE
                }
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 2) {
                    checkBox1_3!!.visibility = View.VISIBLE
                }
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 3) {
                    checkBox1_4!!.visibility = View.VISIBLE
                }
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 4) {
                    checkBox1_5!!.visibility = View.VISIBLE
                }
                if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 5) {
                    checkBox1_6!!.visibility = View.VISIBLE
                }
                editTextTextView!!.visibility = View.VISIBLE
                editTextTextView!!.setText(R.string.lay_on_hands_pool)
                subclassInfoEditText!!.visibility = View.VISIBLE
                subclassInfoEditText!!.setText((character.getLevel() * 5).toString())
                val channelDivinityUsesPaladin = ArrayList<String>()
                val channelDivinityAdapterPaladin = ListAdapter(context!!, channelDivinityUsesPaladin, false)
                if (level >= 3) {
                    subclassInfoHeader!!.visibility = View.VISIBLE
                    subclassInfoHeader!!.text = getString(R.string.channel_divinity_abilities)
                    subclassInfoListView!!.visibility = View.VISIBLE
                    checkBoxes5!!.visibility = View.VISIBLE
                    checkBoxes5!!.text = getString(R.string.channel_divinity_paladin_use)
                    checkBox5_1!!.visibility = View.VISIBLE
                    when (character.getSubclass()) {
                        "Oath of Devotion" -> {
                            channelDivinityUsesPaladin.add(getString(R.string.channel_divinity_sacred_weapon))
                            channelDivinityUsesPaladin.add(getString(R.string.channel_divinity_turn_the_unholy))
                        }
                        "Oath of the Ancients" -> {
                            channelDivinityUsesPaladin.add(getString(R.string.channel_divinity_natures_wrath))
                            channelDivinityUsesPaladin.add(getString(R.string.channel_divinity_turn_the_faithless))
                        }
                        "Oath of Vengeance" -> {
                            channelDivinityUsesPaladin.add(getString(R.string.channel_divinity_abjure_enemy))
                            channelDivinityUsesPaladin.add(getString(R.string.channel_divinity_vow_of_enmity))
                        }
                    }
                }
                if (level >= 6) {
                    subclassInfoTextView1!!.visibility = View.VISIBLE
                    subclassInfoTextView1!!.text = "Aura Radius: 10ft"
                }
                if (level >= 14) {
                    checkBoxes2!!.visibility = View.VISIBLE
                    checkBoxes2!!.text = getString(R.string.cleansing_touch_uses)
                    checkBox2_1!!.visibility = View.VISIBLE
                    if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 2) {
                        checkBox2_2!!.visibility = View.VISIBLE
                    }
                    if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 3) {
                        checkBox2_3!!.visibility = View.VISIBLE
                    }
                    if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 4) {
                        checkBox2_4!!.visibility = View.VISIBLE
                    }
                    if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 5) {
                        checkBox2_5!!.visibility = View.VISIBLE
                    }
                    if ((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]) >= 6) {
                        checkBox2_6!!.visibility = View.VISIBLE
                    }
                }
                if (level >= 15) {
                    when (character.getSubclass()) {
                        "Oath of the Ancients" -> {
                            checkBoxes3!!.visibility = View.VISIBLE
                            checkBox3_1!!.visibility = View.VISIBLE
                            checkBoxes3!!.text = getString(R.string.undying_sentinel_use)
                        }
                    }
                }
                if (level >= 18) {
                    subclassInfoTextView1!!.visibility = View.VISIBLE
                    subclassInfoTextView1!!.text = "Aura Radius: 30ft"
                }
                if (level == 20) {
                    when (character.getSubclass()) {
                        "Oath of Devotion" -> {
                            checkBoxes3!!.visibility = View.VISIBLE
                            checkBox3_1!!.visibility = View.VISIBLE
                            checkBoxes3!!.text = getString(R.string.holy_nimbus_use)
                        }
                        "Oath of the Ancients" -> {
                            checkBoxes4!!.visibility = View.VISIBLE
                            checkBox4_1!!.visibility = View.VISIBLE
                            checkBoxes4!!.text = getString(R.string.elder_champion_use)
                        }
                        "Oath of Vengeance" -> {
                            checkBoxes3!!.visibility = View.VISIBLE
                            checkBox3_1!!.visibility = View.VISIBLE
                            checkBoxes3!!.text = getString(R.string.avenging_angel_use)
                        }
                    }
                }
                subclassInfoListView!!.adapter = channelDivinityAdapterPaladin
            }
            "Ranger" -> {
                subclassInfoTextView1!!.visibility = View.VISIBLE
                subclassInfoTextView1!!.text = String.format("Favored Terrain: %s", character.getClassBasedBonusStats2()[0])
                subclassInfoTextView2!!.visibility = View.VISIBLE
                subclassInfoTextView2!!.text = String.format("Favored Enemies: %s", character.getClassBasedBonusStats2()[1])
                if (level >= 6) {
                    subclassInfoTextView1!!.text = String.format("Favored Terrain: %s, %s", character.getClassBasedBonusStats2()[0], character.getClassBasedBonusStats2()[2])
                    subclassInfoTextView2!!.text = String.format("Favored Enemies: %s, %s", character.getClassBasedBonusStats2()[1], character.getClassBasedBonusStats2()[3])
                }
                if (level >= 10) {
                    subclassInfoTextView1!!.text = String.format("Favored Terrain: %s, %s, %s", character.getClassBasedBonusStats2()[0], character.getClassBasedBonusStats2()[2], character.getClassBasedBonusStats2()[4])
                }
                if (level >= 14) {
                    subclassInfoTextView2!!.text = String.format("Favored Enemies: %s, %s, %s", character.getClassBasedBonusStats2()[1], character.getClassBasedBonusStats2()[3], character.getClassBasedBonusStats2()[5])
                }
            }
            "Rogue" -> {
                subclassInfoTextView1!!.visibility = View.VISIBLE
                subclassInfoTextView1!!.text = getString(R.string.sneak_attack_damage) + ((character.getLevel() + 1) / 2).toString() + "d6"
                if (level >= 17) {
                    if (character.getSubclass() == "Arcane Trickster") {
                        checkBoxes1!!.visibility = View.VISIBLE
                        checkBoxes1!!.text = getString(R.string.spell_thief_use)
                        checkBox1_1!!.visibility = View.VISIBLE
                    }
                }
                if (level == 20) {
                    checkBoxes2!!.visibility = View.VISIBLE
                    checkBoxes2!!.text = getString(R.string.stroke_of_luck_use)
                    checkBox2_1!!.visibility = View.VISIBLE
                }
            }
            "Sorcerer" -> {
                val metamagicAdapter = ListAdapter(context!!, character.getClassBasedBonusStats2(), false)
                subclassInfoTextView1!!.visibility = View.VISIBLE
                subclassInfoTextView1!!.text = String.format("Sorcerous Origin: %s", character.getSubclass())
                when (character.getSubclass()) {
                    "Wild Magic" -> {
                        checkBoxes1!!.visibility = View.VISIBLE
                        checkBoxes1!!.text = getString(R.string.tides_of_chaos_use)
                        checkBox1_1!!.visibility = View.VISIBLE
                    }
                    "Phoenix Sorcery" -> {
                        checkBoxes1!!.visibility = View.VISIBLE
                        checkBoxes1!!.text = getString(R.string.mantle_of_flame_use)
                        checkBox1_1!!.visibility = View.VISIBLE
                    }
                }
                if (level >= 2) {
                    editTextTextView!!.visibility = View.VISIBLE
                    subclassInfoEditText!!.visibility = View.VISIBLE
                    editTextTextView!!.text = getString(R.string.sorcery_points) + " " + character.getLevel()
                    subclassInfoEditText!!.setText(character.getLevel().toString())
                }
                if (level >= 3) {
                    subclassInfoHeader!!.visibility = View.VISIBLE
                    subclassInfoHeader!!.text = getString(R.string.metamagic_options)
                    subclassInfoListView!!.visibility = View.VISIBLE
                }
                subclassInfoListView!!.layoutManager = LinearLayoutManager(context)
                subclassInfoListView!!.adapter = metamagicAdapter
            }
            "Warlock" -> {
                val eldritchInvocationsAdapter = ListAdapter(context!!, character.getClassBasedBonusStats2(), false)
                subclassInfoTextView1!!.visibility = View.VISIBLE
                subclassInfoTextView1!!.text = String.format("Otherworldly Patron: %s", character.getSubclass())
                if (level >= 2) {
                    subclassInfoListView!!.visibility = View.VISIBLE
                    subclassInfoHeader!!.visibility = View.VISIBLE
                    subclassInfoHeader!!.text = "Eldritch Invocations"
                }
                if (character.getSubclass() == "The Archfey") {
                    checkBoxes1!!.visibility = View.VISIBLE
                    checkBoxes1!!.text = getString(R.string.fey_presence_use)
                    checkBox1_1!!.visibility = View.VISIBLE
                }
                if (level >= 6) {
                    when (character.getSubclass()) {
                        "The Archfey" -> {
                            checkBoxes2!!.visibility = View.VISIBLE
                            checkBoxes2!!.text = getString(R.string.misty_escape_use)
                            checkBox2_1!!.visibility = View.VISIBLE
                        }
                        "The Fiend" -> {
                            checkBoxes2!!.visibility = View.VISIBLE
                            checkBoxes2!!.text = getString(R.string.dark_ones_own_luck_use)
                            checkBox2_1!!.visibility = View.VISIBLE
                        }
                        "The Great Old One" -> {
                            checkBoxes2!!.visibility = View.VISIBLE
                            checkBoxes2!!.text = getString(R.string.entropic_ward_use)
                            checkBox2_1!!.visibility = View.VISIBLE
                        }
                    }
                }
                if (level >= 14) {
                    when (character.getSubclass()) {
                        "The Archfey" -> {
                            checkBoxes3!!.visibility = View.VISIBLE
                            checkBoxes3!!.text = getString(R.string.dark_delirium_use)
                            checkBox3_1!!.visibility = View.VISIBLE
                        }
                        "The Fiend" -> {
                            checkBoxes3!!.visibility = View.VISIBLE
                            checkBoxes3!!.text = getString(R.string.hurl_through_hell_use)
                            checkBox3_1!!.visibility = View.VISIBLE
                        }
                    }
                }
                if (level == 20) {
                    checkBoxes4!!.visibility = View.VISIBLE
                    checkBox4_1!!.visibility = View.VISIBLE
                    checkBoxes4!!.text = getString(R.string.eldritch_master_use)
                }
                subclassInfoListView!!.layoutManager = LinearLayoutManager(context)
                subclassInfoListView!!.adapter = eldritchInvocationsAdapter
            }
            "Wizard" -> {
                subclassInfoTextView1!!.visibility = View.VISIBLE
                subclassInfoTextView1!!.text = String.format("Arcane Recovery Amount: %s", (level + 1) / 2)
                when (character.getSubclass()) {
                    "School of Abjuration" -> {
                        editTextTextView!!.visibility = View.VISIBLE
                        editTextTextView!!.text = getString(R.string.arcane_ward_max_hp) + java.lang.String.valueOf(2 * character.getLevel() + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[3]))
                        subclassInfoEditText!!.visibility = View.VISIBLE
                        subclassInfoEditText!!.setText(java.lang.String.valueOf(2 * character.getLevel() + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[3])))
                    }
                    "School of Conjuration" -> if (level >= 6) {
                        checkBoxes1!!.visibility = View.VISIBLE
                        checkBoxes1!!.text = getString(R.string.benign_transposition_use)
                        checkBox1_1!!.visibility = View.VISIBLE
                    }
                    "School of Divination" -> {
                        editTextTextView!!.visibility = View.VISIBLE
                        editTextTextView!!.text = getString(R.string.portent_uses)
                        subclassInfoEditText!!.visibility = View.VISIBLE
                        subclassInfoEditText!!.setText("20, 20")
                    }
                    "School of Illusion" -> if (level >= 10) {
                        checkBoxes1!!.visibility = View.VISIBLE
                        checkBoxes1!!.text = getString(R.string.illusory_self_use)
                        checkBox1_1!!.visibility = View.VISIBLE
                    }
                    "School of Transmutation" -> if (level >= 10) {
                        checkBoxes1!!.visibility = View.VISIBLE
                        checkBoxes1!!.text = getString(R.string.shapechanger_use)
                        checkBox1_1!!.visibility = View.VISIBLE
                    }
                }
                if (level >= 18) {
                    subclassInfoTextView2!!.visibility = View.VISIBLE
                    subclassInfoTextView2!!.text = String.format("%s %s %s", getString(R.string.spell_mastery_choices), character.getClassBasedBonusStats2()[0], character.getClassBasedBonusStats2()[1])
                }
            }
        }
        val adapter = ListAdapter(context!!, bonusStats, false)
        bonusStatsList!!.adapter = adapter
        return rootView
    }

    override fun onPause() {
        super.onPause()
        (activity as DetailActivity?)!!.character.getRaceAndClassBonusStats().set(0, languagesKnown!!.text.toString())
    }
}