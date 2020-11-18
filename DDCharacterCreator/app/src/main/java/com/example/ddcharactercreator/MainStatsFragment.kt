package com.example.ddcharactercreator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife

class MainStatsFragment : Fragment() {
    // TODO: (activity as DetailActivity?)!!.calculateModifier()
    @JvmField
    @BindView(R.id.strength_label)
    var strengthLabel: TextView? = null

    @JvmField
    @BindView(R.id.dexterity_label)
    var dexterityLabel: TextView? = null

    @JvmField
    @BindView(R.id.constitution_label)
    var constitutionLabel: TextView? = null

    @JvmField
    @BindView(R.id.intelligence_label)
    var intelligenceLabel: TextView? = null

    @JvmField
    @BindView(R.id.wisdom_label)
    var wisdomLabel: TextView? = null

    @JvmField
    @BindView(R.id.charisma_label)
    var charismaLabel: TextView? = null

    @JvmField
    @BindView(R.id.character_screen_strength)
    var strengthValue: TextView? = null

    @JvmField
    @BindView(R.id.character_screen_dexterity)
    var dexterityValue: TextView? = null

    @JvmField
    @BindView(R.id.character_screen_constitution)
    var constitutionValue: TextView? = null

    @JvmField
    @BindView(R.id.character_screen_intelligence)
    var intelligenceValue: TextView? = null

    @JvmField
    @BindView(R.id.character_screen_wisdom)
    var wisdomValue: TextView? = null

    @JvmField
    @BindView(R.id.character_screen_charisma)
    var charismaValue: TextView? = null

    @JvmField
    @BindView(R.id.str_modifier)
    var strengthModifier: TextView? = null

    @JvmField
    @BindView(R.id.dex_modifier)
    var dexterityModifier: TextView? = null

    @JvmField
    @BindView(R.id.con_modifier)
    var constitutionModifier: TextView? = null

    @JvmField
    @BindView(R.id.int_modifier)
    var intelligenceModifier: TextView? = null

    @JvmField
    @BindView(R.id.wis_modifier)
    var wisdomModifier: TextView? = null

    @JvmField
    @BindView(R.id.cha_modifier)
    var charismaModifier: TextView? = null

    @JvmField
    @BindView(R.id.proficiency_bonus)
    var proficiencyBonus: TextView? = null

    @JvmField
    @BindView(R.id.speed)
    var speedTextView: TextView? = null

    @JvmField
    @BindView(R.id.passive_perception)
    var passivePerception: TextView? = null

    @JvmField
    @BindView(R.id.armor_class_value)
    var armorClassEditText: EditText? = null

    @JvmField
    @BindView(R.id.skill_modifiers)
    var skillModifiers: TextView? = null

    @JvmField
    @BindView(R.id.temporary_hit_points)
    var temporaryHitPoints: EditText? = null

    @JvmField
    @BindView(R.id.current_hit_points)
    var currentHitPoints: EditText? = null

    @JvmField
    @BindView(R.id.max_hit_points)
    var maxHitPoints: TextView? = null

    @JvmField
    @BindView(R.id.hit_die)
    var hitDieTextView: TextView? = null

    @JvmField
    @BindView(R.id.advantage)
    var advantageRb: RadioButton? = null

    @JvmField
    @BindView(R.id.disadvantage)
    var disadvantageRb: RadioButton? = null

    @JvmField
    @BindView(R.id.normal_roll)
    var normalRollRb: RadioButton? = null

    @JvmField
    @BindView(R.id.plus_amount)
    var plusAmount: EditText? = null

    @JvmField
    @BindView(R.id.minus_amount)
    var minusAmount: EditText? = null

    @JvmField
    @BindView(R.id.final_roll)
    var finalRoll: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_stats, container, false)
        ButterKnife.bind(this, rootView)
        val character: Character = (activity as DetailActivity?)!!.character

        //Setting Stats and Modifiers
        strengthValue!!.text = character.getStatValues()[0].toString()
        dexterityValue!!.text = character.getStatValues()[1].toString()
        constitutionValue!!.text = character.getStatValues()[2].toString()
        intelligenceValue!!.text = character.getStatValues()[3].toString()
        wisdomValue!!.text = character.getStatValues()[4].toString()
        charismaValue!!.text = character.getStatValues()[5].toString()
        strengthModifier!!.text = java.lang.String.valueOf((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[0]))
        dexterityModifier!!.text = java.lang.String.valueOf((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[1]))
        constitutionModifier!!.text = java.lang.String.valueOf((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[2]))
        intelligenceModifier!!.text = java.lang.String.valueOf((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[3]))
        wisdomModifier!!.text = java.lang.String.valueOf((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]))
        charismaModifier!!.text = java.lang.String.valueOf((activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]))

        //Setting Proficiency Bonus and speed values
        proficiencyBonus!!.text = String.format("Proficiency Bonus: %s", (activity as DetailActivity?)!!.proficiencyBonus)
        if (character.getRace() == "Dwarf" || character.getRace() == "Halfling" || character.getRace() == "Gnome") {
            speedTextView!!.text = "Speed: 25ft"
        } else if (character.getRace() == "Elf") {
            speedTextView!!.text = "Speed: 35ft"
        } else {
            speedTextView!!.text = "Speed: 30ft"
        }

        //Setting Passive Perception and Armor Class
        if (character.getProficiencyChoices().contains("Perception")) {
            passivePerception!!.text = java.lang.String.format("Passive Perception: %s", 10 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]))
        } else {
            passivePerception!!.text = java.lang.String.format("Passive Perception: %s", 10 + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]))
        }
        armorClassEditText!!.setText(character.getCurrency()[0].toString())
        armorClassEditText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (armorClassEditText!!.text.toString() != "") {
                    character.getCurrency()[0] = armorClassEditText!!.text.toString().toInt()
                }
            }
        })

        //Set Skill proficiencies TextView and onClick method
        skillModifiers!!.setOnClickListener { //Launch new Fragment with the list of skills and their associated modifiers.
            val intent = Intent(activity, SkillModifiersActivity::class.java)
            startActivity(intent)
        }

        //Setting Temporary/Current/Max HP, hit die values, and saving throw proficiencies.
        temporaryHitPoints!!.setText(character.getCurrency()[7].toString())
        currentHitPoints!!.setText(character.getCurrency()[6].toString())
        maxHitPoints!!.text = character.getCurrency()[8].toString()
        when (character.getCharacterClass()) {
            "Barbarian" -> {
                hitDieTextView!!.text = String.format("d12 (%s)", character.getLevel())
                strengthLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                strengthValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                strengthModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                constitutionLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                constitutionValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                constitutionModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
            }
            "Bard" -> {
                hitDieTextView!!.text = String.format("d8 (%s)", character.getLevel())
                dexterityLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                dexterityValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                dexterityModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
            }
            "Druid" -> {
                hitDieTextView!!.text = String.format("d8 (%s)", character.getLevel())
                intelligenceLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                intelligenceValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                intelligenceModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                wisdomLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                wisdomValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                wisdomModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
            }
            "Fighter" -> {
                hitDieTextView!!.text = String.format("d10 (%s)", character.getLevel())
                strengthLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                strengthValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                strengthModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                constitutionLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                constitutionValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                constitutionModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
            }
            "Monk" -> {
                hitDieTextView!!.text = String.format("d8 (%s)", character.getLevel())
                strengthLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                strengthValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                strengthModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                dexterityLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                dexterityValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                dexterityModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                if (character.getLevel() >= 14) {
                    constitutionLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    constitutionValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    constitutionModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    intelligenceLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    intelligenceValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    intelligenceModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    wisdomLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    wisdomValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    wisdomModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    charismaLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    charismaValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    charismaModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                }
            }
            "Paladin" -> {
                hitDieTextView!!.text = String.format("d10 (%s)", character.getLevel())
                wisdomLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                wisdomValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                wisdomModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
            }
            "Ranger" -> {
                hitDieTextView!!.text = String.format("d10 (%s)", character.getLevel())
                strengthLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                strengthValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                strengthModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                dexterityLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                dexterityValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                dexterityModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
            }
            "Rogue" -> {
                hitDieTextView!!.text = String.format("d8 (%s)", character.getLevel())
                dexterityLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                dexterityValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                dexterityModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                intelligenceLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                intelligenceValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                intelligenceModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                if (character.getLevel() >= 15) {
                    wisdomLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    wisdomValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                    wisdomModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                }
            }
            "Sorcerer" -> {
                hitDieTextView!!.text = String.format("d6 (%s)", character.getLevel())
                constitutionLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                constitutionValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                constitutionModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
            }
            "Cleric", "Warlock" -> {
                hitDieTextView!!.text = String.format("d8 (%s)", character.getLevel())
                wisdomLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                wisdomValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                wisdomModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                charismaModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
            }
            "Wizard" -> {
                hitDieTextView!!.text = String.format("d6 (%s)", character.getLevel())
                intelligenceLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                intelligenceValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                intelligenceModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                wisdomLabel!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                wisdomValue!!.setTextColor(resources.getColor(R.color.proficiency_blue))
                wisdomModifier!!.setTextColor(resources.getColor(R.color.proficiency_blue))
            }
        }
        if (currentHitPoints!!.text.toString() != "") {
            if (currentHitPoints!!.text.toString().toInt() > maxHitPoints!!.text.toString().toInt() * .5) {
                currentHitPoints!!.setTextColor(resources.getColor(R.color.green))
            } else if (currentHitPoints!!.text.toString().toInt() > maxHitPoints!!.text.toString().toInt() * .3) {
                currentHitPoints!!.setTextColor(resources.getColor(R.color.yellow))
            } else if (currentHitPoints!!.text.toString().toInt() > maxHitPoints!!.text.toString().toInt() * .1) {
                currentHitPoints!!.setTextColor(resources.getColor(R.color.orange))
            } else {
                currentHitPoints!!.setTextColor(resources.getColor(R.color.red))
            }
        }
        temporaryHitPoints!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (temporaryHitPoints!!.text.toString() != "") {
                    character.getCurrency()[7] = temporaryHitPoints!!.text.toString().toInt()
                }
            }
        })
        //Changes the color of the Text to denote how much/little health the character has remaining.
        currentHitPoints!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (currentHitPoints!!.text.toString() != "") {
                    character.getCurrency()[6] = currentHitPoints!!.text.toString().toInt()
                    if (currentHitPoints!!.text.toString().toInt() > maxHitPoints!!.text.toString().toInt() * .5) {
                        currentHitPoints!!.setTextColor(resources.getColor(R.color.green))
                    } else if (currentHitPoints!!.text.toString().toInt() > maxHitPoints!!.text.toString().toInt() * .3) {
                        currentHitPoints!!.setTextColor(resources.getColor(R.color.yellow))
                    } else if (currentHitPoints!!.text.toString().toInt() > maxHitPoints!!.text.toString().toInt() * .1) {
                        currentHitPoints!!.setTextColor(resources.getColor(R.color.orange))
                    } else {
                        currentHitPoints!!.setTextColor(resources.getColor(R.color.red))
                    }
                }
            }
        })

        //Dice Roller
        normalRollRb!!.isChecked = true
        //do something for a natural 1/20??
        finalRoll!!.setOnClickListener {
            if (normalRollRb!!.isChecked) {
                finalRoll!!.text = ((Math.random() * 20).toInt() + 1 + plusAmount!!.text.toString().toInt() - minusAmount!!.text.toString().toInt()).toString()
            } else if (advantageRb!!.isChecked) {
                val roll1 = (Math.random() * 20).toInt() + 1 + plusAmount!!.text.toString().toInt() - minusAmount!!.text.toString().toInt()
                val roll2 = (Math.random() * 20).toInt() + 1 + plusAmount!!.text.toString().toInt() - minusAmount!!.text.toString().toInt()
                if (roll1 >= roll2) {
                    finalRoll!!.text = roll1.toString()
                } else {
                    finalRoll!!.text = roll2.toString()
                }
            } else if (disadvantageRb!!.isChecked) {
                val roll1 = (Math.random() * 20).toInt() + 1 + plusAmount!!.text.toString().toInt() - minusAmount!!.text.toString().toInt()
                val roll2 = (Math.random() * 20).toInt() + 1 + plusAmount!!.text.toString().toInt() - minusAmount!!.text.toString().toInt()
                if (roll1 <= roll2) {
                    finalRoll!!.text = roll1.toString()
                } else {
                    finalRoll!!.text = roll2.toString()
                }
            }
        }
        return rootView
    }
}