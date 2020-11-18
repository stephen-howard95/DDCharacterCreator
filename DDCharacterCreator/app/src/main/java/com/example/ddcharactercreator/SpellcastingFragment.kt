package com.example.ddcharactercreator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import java.util.*

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class SpellcastingFragment : Fragment() {
    private var spellsAdapter: SpellAdapter? = null
    private var cantripsAdapter: SpellAdapter? = null
    private val fullSpellsList: ArrayList<Spell> = (activity as DetailActivity?)!!.character.getSpellsKnown()
    private val spellsList = ArrayList<Spell>()
    private val cantripsList = ArrayList<Spell>()
    private val spellSlotsClicked: ArrayList<String> = (activity as DetailActivity?)!!.character.getSpellSlotsClicked()

    @JvmField
    @BindView(R.id.spellcasting_ability)
    var spellcastingAbility: TextView? = null

    @JvmField
    @BindView(R.id.spell_save_dc)
    var spellSaveDC: TextView? = null

    @JvmField
    @BindView(R.id.spell_attack_bonus)
    var spellAttackBonus: TextView? = null

    @JvmField
    @BindView(R.id.spell_slots)
    var spellSlots: ScrollView? = null

    @JvmField
    @BindView(R.id.add_spells_to_list)
    var addSpellsTextView: TextView? = null

    @JvmField
    @BindView(R.id.spells_known)
    var spellsKnown: RecyclerView? = null

    @JvmField
    @BindView(R.id.spells_known_label)
    var spellsKnownLabel: TextView? = null

    @JvmField
    @BindView(R.id.cantrips_known)
    var cantripsKnown: RecyclerView? = null

    @JvmField
    @BindView(R.id.cantrips_known_label)
    var cantripsKnownLabel: TextView? = null

    @JvmField
    @BindView(R.id.spell_slot_1)
    var spellSlot1: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_2)
    var spellSlot2: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_3)
    var spellSlot3: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_4)
    var spellSlot4: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_5)
    var spellSlot5: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_6)
    var spellSlot6: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_7)
    var spellSlot7: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_8)
    var spellSlot8: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_9)
    var spellSlot9: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_10)
    var spellSlot10: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_11)
    var spellSlot11: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_12)
    var spellSlot12: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_13)
    var spellSlot13: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_14)
    var spellSlot14: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_15)
    var spellSlot15: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_16)
    var spellSlot16: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_17)
    var spellSlot17: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_18)
    var spellSlot18: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_19)
    var spellSlot19: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_20)
    var spellSlot20: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_21)
    var spellSlot21: CheckBox? = null

    @JvmField
    @BindView(R.id.spell_slot_22)
    var spellSlot22: CheckBox? = null

    @JvmField
    @BindView(R.id.signature_spell_1)
    var signatureSpell1: CheckBox? = null

    @JvmField
    @BindView(R.id.signature_spell_2)
    var signatureSpell2: CheckBox? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_spellcasting, container, false)
        val character: Character = (activity as DetailActivity?)!!.character
        ButterKnife.bind(this, rootView)

        //Separates Spells and Cantrips
        for (i in fullSpellsList.indices) {
            val spell = fullSpellsList[i]
            if (spell.level == 0 && !cantripsList.contains(spell)) {
                cantripsList.add(spell)
            } else if (!cantripsList.contains(spell) && !spellsList.contains(spell)) {
                spellsList.add(spell)
            }
        }
        if (character.getCharacterClass() == "Barbarian" && character.getSubclass() != "Path of the Totem Warrior" || character.getCharacterClass() == "Fighter" && character.getSubclass() != "Eldritch Knight" || character.getCharacterClass() == "Monk" && character.getSubclass() != "Way of Shadow" || character.getCharacterClass() == "Rogue" && character.getSubclass() != "Arcane Trickster") {
            spellcastingAbility!!.visibility = View.GONE
            spellSaveDC!!.visibility = View.GONE
            spellAttackBonus!!.visibility = View.GONE
            spellSlots!!.visibility = View.GONE
            spellsKnown!!.visibility = View.GONE
            spellsKnownLabel!!.visibility = View.GONE
            cantripsKnown!!.visibility = View.GONE
            cantripsKnownLabel!!.visibility = View.GONE
            addSpellsTextView!!.text = "This character class does not have access to Spellcasting features"
            addSpellsTextView!!.textSize = 48f
        } else if ((character.getSubclass() == "Path of the Totem Warrior" || character.getSubclass() == "Eldritch Knight" || character.getSubclass() == "Way of Shadow" || character.getSubclass() == "Arcane Trickster") && character.getLevel() < 3) {
            spellcastingAbility!!.visibility = View.GONE
            spellSaveDC!!.visibility = View.GONE
            spellAttackBonus!!.visibility = View.GONE
            spellSlots!!.visibility = View.GONE
            spellsKnown!!.visibility = View.GONE
            spellsKnownLabel!!.visibility = View.GONE
            cantripsKnown!!.visibility = View.GONE
            cantripsKnownLabel!!.visibility = View.GONE
            addSpellsTextView!!.text = "This character class does not have access to Spellcasting features"
            addSpellsTextView!!.textSize = 48f
        } else {
            when (character.getCharacterClass()) {
                "Barbarian" -> {
                    spellcastingAbility!!.visibility = View.GONE
                    spellSaveDC!!.visibility = View.GONE
                    spellAttackBonus!!.visibility = View.GONE
                    spellSlot1!!.visibility = View.GONE
                    spellSlot2!!.visibility = View.GONE
                    getSubclassSpells("beast sense")
                    getSubclassSpells("speak with animals")
                    if (character.getLevel() >= 10) {
                        getSubclassSpells("commune with nature")
                    }
                }
                "Bard", "Sorcerer" -> {
                    primarySpellcasterSlotsPerLevel()
                    spellcastingAbility!!.text = "Spellcasting Ability: CHA"
                    spellSaveDC!!.text = java.lang.String.format("Spell Save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]))
                    spellAttackBonus!!.text = java.lang.String.format("Spell Attack Bonus: %s", (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]))
                }
                "Cleric" -> {
                    primarySpellcasterSlotsPerLevel()
                    when (character.getSubclass()) {
                        "Knowledge" -> when (character.getLevel()) {
                            1 -> {
                                getSubclassSpells("command")
                                getSubclassSpells("identify")
                            }
                            3 -> {
                                getSubclassSpells("augury")
                                getSubclassSpells("suggestion")
                            }
                            5 -> {
                                getSubclassSpells("nondetection")
                                getSubclassSpells("speak with dead")
                            }
                            7 -> {
                                getSubclassSpells("arcane eye")
                                getSubclassSpells("confusion")
                            }
                            9 -> {
                                getSubclassSpells("legend lore")
                                getSubclassSpells("scrying")
                            }
                        }
                        "Life" -> when (character.getLevel()) {
                            1 -> {
                                getSubclassSpells("bless")
                                getSubclassSpells("cure wounds")
                            }
                            3 -> {
                                getSubclassSpells("lesser restoration")
                                getSubclassSpells("spiritual weapon")
                            }
                            5 -> {
                                getSubclassSpells("beacon of hope")
                                getSubclassSpells("revivify")
                            }
                            7 -> {
                                getSubclassSpells("death ward")
                                getSubclassSpells("guardian of faith")
                            }
                            9 -> {
                                getSubclassSpells("mass cure wounds")
                                getSubclassSpells("raise dead")
                            }
                        }
                        "Light" -> when (character.getLevel()) {
                            1 -> {
                                getSubclassSpells("light")
                                getSubclassSpells("burning hands")
                                getSubclassSpells("faerie fire")
                            }
                            3 -> {
                                getSubclassSpells("flaming sphere")
                                getSubclassSpells("scorching ray")
                            }
                            5 -> {
                                getSubclassSpells("daylight")
                                getSubclassSpells("fireball")
                            }
                            7 -> {
                                getSubclassSpells("guardian of faith")
                                getSubclassSpells("wall of fire")
                            }
                            9 -> {
                                getSubclassSpells("flame strike")
                                getSubclassSpells("scrying")
                            }
                        }
                        "Nature" -> when (character.getLevel()) {
                            1 -> {
                                getSubclassSpells("druidcraft")
                                getSubclassSpells("animal friendship")
                                getSubclassSpells("speak with animals")
                            }
                            3 -> {
                                getSubclassSpells("barkskin")
                                getSubclassSpells("spike growth")
                            }
                            5 -> {
                                getSubclassSpells("plant growth")
                                getSubclassSpells("wind wall")
                            }
                            7 -> {
                                getSubclassSpells("dominate beast")
                                getSubclassSpells("grasping vine")
                            }
                            9 -> {
                                getSubclassSpells("insect plague")
                                getSubclassSpells("tree stride")
                            }
                        }
                        "Tempest" -> when (character.getLevel()) {
                            1 -> {
                                getSubclassSpells("fog cloud")
                                getSubclassSpells("thunderwave")
                            }
                            3 -> {
                                getSubclassSpells("gust of wind")
                                getSubclassSpells("shatter")
                            }
                            5 -> {
                                getSubclassSpells("call lightning")
                                getSubclassSpells("sleet storm")
                            }
                            7 -> {
                                getSubclassSpells("control water")
                                getSubclassSpells("ice storm")
                            }
                            9 -> {
                                getSubclassSpells("destructive wave")
                                getSubclassSpells("insect plague")
                            }
                        }
                        "Trickery" -> when (character.getLevel()) {
                            1 -> {
                                getSubclassSpells("charm person")
                                getSubclassSpells("disguise self")
                            }
                            3 -> {
                                getSubclassSpells("mirror image")
                                getSubclassSpells("pass without trace")
                            }
                            5 -> {
                                getSubclassSpells("blink")
                                getSubclassSpells("dispel magic")
                            }
                            7 -> {
                                getSubclassSpells("dimension door")
                                getSubclassSpells("polymorph")
                            }
                            9 -> {
                                getSubclassSpells("dominate person")
                                getSubclassSpells("modify memory")
                            }
                        }
                        "War" -> when (character.getLevel()) {
                            1 -> {
                                getSubclassSpells("divine favor")
                                getSubclassSpells("shield of faith")
                            }
                            3 -> {
                                getSubclassSpells("magic weapon")
                                getSubclassSpells("spiritual weapon")
                            }
                            5 -> {
                                getSubclassSpells("crusader's mantle")
                                getSubclassSpells("spirit guardians")
                            }
                            7 -> {
                                getSubclassSpells("freedom of movement")
                                getSubclassSpells("stoneskin")
                            }
                            9 -> {
                                getSubclassSpells("flame strike")
                                getSubclassSpells("hold monster")
                            }
                        }
                    }
                    spellcastingAbility!!.text = "Spellcasting Ability: WIS"
                    spellSaveDC!!.text = java.lang.String.format("Spell Save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]))
                    spellAttackBonus!!.text = java.lang.String.format("Spell Attack Bonus: %s", (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]))
                }
                "Druid" -> {
                    primarySpellcasterSlotsPerLevel()
                    when (character.getSubclass()) {
                        "Circle of the Land, Arctic" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("hold person")
                                getSubclassSpells("spike growth")
                            }
                            5 -> {
                                getSubclassSpells("sleet storm")
                                getSubclassSpells("slow")
                            }
                            7 -> {
                                getSubclassSpells("freedom of movement")
                                getSubclassSpells("ice storm")
                            }
                            9 -> {
                                getSubclassSpells("commune with nature")
                                getSubclassSpells("cone of cold")
                            }
                        }
                        "Circle of the Land, Coast" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("mirror image")
                                getSubclassSpells("misty step")
                            }
                            5 -> {
                                getSubclassSpells("water breathing")
                                getSubclassSpells("water walk")
                            }
                            7 -> {
                                getSubclassSpells("control water")
                                getSubclassSpells("freedom of movement")
                            }
                            9 -> {
                                getSubclassSpells("conjure elemental")
                                getSubclassSpells("scrying")
                            }
                        }
                        "Circle of the Land, Desert" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("blur")
                                getSubclassSpells("silence")
                            }
                            5 -> {
                                getSubclassSpells("create food and water")
                                getSubclassSpells("protection from energy")
                            }
                            7 -> {
                                getSubclassSpells("blight")
                                getSubclassSpells("hallucinatory terrain")
                            }
                            9 -> {
                                getSubclassSpells("insect plague")
                                getSubclassSpells("wall of stone")
                            }
                        }
                        "Circle of the Land, Forest" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("barkskin")
                                getSubclassSpells("spider climb")
                            }
                            5 -> {
                                getSubclassSpells("call lightning")
                                getSubclassSpells("plant growth")
                            }
                            7 -> {
                                getSubclassSpells("divination")
                                getSubclassSpells("freedom of movement")
                            }
                            9 -> {
                                getSubclassSpells("commune with nature")
                                getSubclassSpells("tree stride")
                            }
                        }
                        "Circle of the Land, Grassland" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("invisibility")
                                getSubclassSpells("pass without trace")
                            }
                            5 -> {
                                getSubclassSpells("daylight")
                                getSubclassSpells("haste")
                            }
                            7 -> {
                                getSubclassSpells("divination")
                                getSubclassSpells("freedom of movement")
                            }
                            9 -> {
                                getSubclassSpells("dream")
                                getSubclassSpells("insect plague")
                            }
                        }
                        "Circle of the Land, Mountain" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("spider climb")
                                getSubclassSpells("spike growth")
                            }
                            5 -> {
                                getSubclassSpells("lightning bolt")
                                getSubclassSpells("meld into stone")
                            }
                            7 -> {
                                getSubclassSpells("stone shape")
                                getSubclassSpells("stoneskin")
                            }
                            9 -> {
                                getSubclassSpells("passwall")
                                getSubclassSpells("wall of stone")
                            }
                        }
                        "Circle of the Land, Swamp" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("darkness")
                                getSubclassSpells("acid arrow")
                            }
                            5 -> {
                                getSubclassSpells("water walk")
                                getSubclassSpells("stinking cloud")
                            }
                            7 -> {
                                getSubclassSpells("freedom of movement")
                                getSubclassSpells("locate creature")
                            }
                            9 -> {
                                getSubclassSpells("insect plague")
                                getSubclassSpells("scrying")
                            }
                        }
                        "Circle of the Land, Underdark" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("spider climb")
                                getSubclassSpells("web")
                            }
                            5 -> {
                                getSubclassSpells("gaseous form")
                                getSubclassSpells("stinking cloud")
                            }
                            7 -> {
                                getSubclassSpells("greater invisibility")
                                getSubclassSpells("stone shape")
                            }
                            9 -> {
                                getSubclassSpells("cloudkill")
                                getSubclassSpells("insect plague")
                            }
                        }
                    }
                    spellcastingAbility!!.text = "Spellcasting Ability: WIS"
                    spellSaveDC!!.text = java.lang.String.format("Spell Save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]))
                    spellAttackBonus!!.text = java.lang.String.format("Spell Attack Bonus: %s", (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]))
                }
                "Fighter" -> {
                    tertiarySpellcasterSlotsPerLevel()
                    spellcastingAbility!!.text = "Spellcasting Ability: INT"
                    spellSaveDC!!.text = java.lang.String.format("Spell Save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[3]))
                    spellAttackBonus!!.text = java.lang.String.format("Spell Attack Bonus: %s", (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[3]))
                }
                "Monk" -> {
                    spellSlot1!!.visibility = View.GONE
                    spellSlot2!!.visibility = View.GONE
                    spellcastingAbility!!.text = "Spellcasting Ability: WIS"
                    spellSaveDC!!.text = java.lang.String.format("Spell Save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]))
                    spellAttackBonus!!.visibility = View.GONE
                    getSubclassSpells("minor illusion")
                    getSubclassSpells("darkness")
                    getSubclassSpells("pass without trace")
                    getSubclassSpells("silence")
                }
                "Paladin" -> {
                    secondarySpellcasterSlotsPerLevel()
                    when (character.getSubclass()) {
                        "Oath of Devotion" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("protection from evil and good")
                                getSubclassSpells("sanctuary")
                            }
                            5 -> {
                                getSubclassSpells("lesser restoration")
                                getSubclassSpells("zone of truth")
                            }
                            9 -> {
                                getSubclassSpells("beacon of hope")
                                getSubclassSpells("dispel magic")
                            }
                            13 -> {
                                getSubclassSpells("freedom of movement")
                                getSubclassSpells("guardian of faith")
                            }
                            17 -> {
                                getSubclassSpells("commune")
                                getSubclassSpells("flame strike")
                            }
                        }
                        "Oath of the Ancients" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("ensnaring strike")
                                getSubclassSpells("speak with animals")
                            }
                            5 -> {
                                getSubclassSpells("misty step")
                                getSubclassSpells("moonbeam")
                            }
                            9 -> {
                                getSubclassSpells("plant growth")
                                getSubclassSpells("protection from energy")
                            }
                            13 -> {
                                getSubclassSpells("ice storm")
                                getSubclassSpells("stoneskin")
                            }
                            17 -> {
                                getSubclassSpells("commune with nature")
                                getSubclassSpells("tree stride")
                            }
                        }
                        "Oath of Vengeance" -> when (character.getLevel()) {
                            3 -> {
                                getSubclassSpells("bane")
                                getSubclassSpells("hunter's mark")
                            }
                            5 -> {
                                getSubclassSpells("hold person")
                                getSubclassSpells("misty step")
                            }
                            9 -> {
                                getSubclassSpells("haste")
                                getSubclassSpells("protection from energy")
                            }
                            13 -> {
                                getSubclassSpells("banishment")
                                getSubclassSpells("dimension door")
                            }
                            17 -> {
                                getSubclassSpells("hold monster")
                                getSubclassSpells("scrying")
                            }
                        }
                    }
                    spellcastingAbility!!.text = "Spellcasting Ability: CHA"
                    spellSaveDC!!.text = java.lang.String.format("Spell Save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]))
                    spellAttackBonus!!.text = java.lang.String.format("Spell Attack Bonus: %s", (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]))
                    cantripsKnown!!.visibility = View.GONE
                    cantripsKnownLabel!!.visibility = View.GONE
                }
                "Ranger" -> {
                    secondarySpellcasterSlotsPerLevel()
                    spellcastingAbility!!.text = "Spellcasting Ability: WIS"
                    spellSaveDC!!.text = java.lang.String.format("Spell Save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]))
                    spellAttackBonus!!.text = java.lang.String.format("Spell Attack Bonus: %s", (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[4]))
                    cantripsKnown!!.visibility = View.GONE
                    cantripsKnownLabel!!.visibility = View.GONE
                }
                "Rogue" -> {
                    tertiarySpellcasterSlotsPerLevel()
                    getSubclassSpells("Mage Hand")
                    spellcastingAbility!!.text = "Spellcasting Ability: INT"
                    spellSaveDC!!.text = java.lang.String.format("Spell Save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[3]))
                    spellAttackBonus!!.text = java.lang.String.format("Spell Attack Bonus: %s", (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[3]))
                }
                "Warlock" -> {
                    spellSlot2!!.visibility = View.GONE
                    warlockSpellSlotsPerLevel()
                    if (character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_chain))) {
                        getSubclassSpells("find familiar")
                    }
                    spellcastingAbility!!.text = "Spellcasting Ability: CHA"
                    spellSaveDC!!.text = java.lang.String.format("Spell Save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]))
                    spellAttackBonus!!.text = java.lang.String.format("Spell Attack Bonus: %s", (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[5]))
                }
                "Wizard" -> {
                    primarySpellcasterSlotsPerLevel()
                    spellcastingAbility!!.text = "Spellcasting Ability: INT"
                    spellSaveDC!!.text = java.lang.String.format("Spell Save DC: %s", 8 + (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[3]))
                    spellAttackBonus!!.text = java.lang.String.format("Spell Attack Bonus: %s", (activity as DetailActivity?)!!.proficiencyBonus + (activity as DetailActivity?)!!.calculateModifier(character.getStatValues()[3]))
                    if (character.getLevel() == 20) {
                        signatureSpell1!!.visibility = View.VISIBLE
                        signatureSpell2!!.visibility = View.VISIBLE
                        signatureSpell1!!.text = character.getClassBasedBonusStats2()[2]
                        signatureSpell2!!.text = character.getClassBasedBonusStats2()[3]
                    }
                }
            }
            val layoutManager1 = LinearLayoutManager(context)
            val dividerItemDecoration1 = DividerItemDecoration(context,
                    layoutManager1.orientation)
            val layoutManager2 = LinearLayoutManager(context)
            val dividerItemDecoration2 = DividerItemDecoration(context,
                    layoutManager2.orientation)
            spellsAdapter = SpellAdapter(context!!, spellsList, false)
            cantripsAdapter = SpellAdapter(context!!, cantripsList, false)
            addSpellsTextView!!.setOnClickListener {
                if (character.getCharacterClass() == "Paladin" && character.getLevel() < 2) {
                    Toast.makeText(context, "You do not have access to the Spellcasting feature yet", Toast.LENGTH_SHORT).show()
                } else if (character.getCharacterClass() == "Ranger" && character.getLevel() < 2) {
                    Toast.makeText(context, "You do not have access to the Spellcasting feature yet", Toast.LENGTH_SHORT).show()
                } else if (character.getSubclass() == "Path of the Totem Warrior" || character.getSubclass() == "Way of Shadow") {
                    Toast.makeText(context, "Due to your class, you cannot learn any spells.", Toast.LENGTH_SHORT).show()
                } else if (spellsList.size >= (activity as DetailActivity?)!!.spellCount && cantripsList.size >= (activity as DetailActivity?)!!.cantripCount) {
                    Toast.makeText(context, "You cannot learn any more spells or cantrips", Toast.LENGTH_SHORT).show()
                } else {
                    addSpellToList()
                }
            }
            spellsKnown!!.adapter = spellsAdapter
            spellsKnown!!.addItemDecoration(dividerItemDecoration1)
            spellsKnown!!.layoutManager = layoutManager1
            cantripsKnown!!.adapter = cantripsAdapter
            cantripsKnown!!.addItemDecoration(dividerItemDecoration2)
            cantripsKnown!!.layoutManager = layoutManager2

            //Spell Slots
            spellSlot1!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[0] = "yes"
                } else {
                    spellSlotsClicked[0] = "no"
                }
            }
            spellSlot2!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[1] = "yes"
                } else {
                    spellSlotsClicked[1] = "no"
                }
            }
            spellSlot3!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[2] = "yes"
                } else {
                    spellSlotsClicked[2] = "no"
                }
            }
            spellSlot4!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[3] = "yes"
                } else {
                    spellSlotsClicked[3] = "no"
                }
            }
            spellSlot5!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[4] = "yes"
                } else {
                    spellSlotsClicked[4] = "no"
                }
            }
            spellSlot6!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[5] = "yes"
                } else {
                    spellSlotsClicked[5] = "no"
                }
            }
            spellSlot7!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[6] = "yes"
                } else {
                    spellSlotsClicked[6] = "no"
                }
            }
            spellSlot8!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[7] = "yes"
                } else {
                    spellSlotsClicked[7] = "no"
                }
            }
            spellSlot9!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[8] = "yes"
                } else {
                    spellSlotsClicked[8] = "no"
                }
            }
            spellSlot10!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[9] = "yes"
                } else {
                    spellSlotsClicked[9] = "no"
                }
            }
            spellSlot11!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[10] = "yes"
                } else {
                    spellSlotsClicked[10] = "no"
                }
            }
            spellSlot12!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[11] = "yes"
                } else {
                    spellSlotsClicked[11] = "no"
                }
            }
            spellSlot13!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[12] = "yes"
                } else {
                    spellSlotsClicked[12] = "no"
                }
            }
            spellSlot14!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[13] = "yes"
                } else {
                    spellSlotsClicked[13] = "no"
                }
            }
            spellSlot15!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[14] = "yes"
                } else {
                    spellSlotsClicked[14] = "no"
                }
            }
            spellSlot16!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[15] = "yes"
                } else {
                    spellSlotsClicked[15] = "no"
                }
            }
            spellSlot17!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[16] = "yes"
                } else {
                    spellSlotsClicked[16] = "no"
                }
            }
            spellSlot18!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[17] = "yes"
                } else {
                    spellSlotsClicked[17] = "no"
                }
            }
            spellSlot19!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[18] = "yes"
                } else {
                    spellSlotsClicked[18] = "no"
                }
            }
            spellSlot20!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[19] = "yes"
                } else {
                    spellSlotsClicked[19] = "no"
                }
            }
            spellSlot21!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[20] = "yes"
                } else {
                    spellSlotsClicked[20] = "no"
                }
            }
            spellSlot22!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    spellSlotsClicked[21] = "yes"
                } else {
                    spellSlotsClicked[21] = "no"
                }
            }
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        val tabLayout: TabLayout = activity!!.findViewById(R.id.tabs)
        tabLayout.visibility = View.VISIBLE
        if (fullSpellsList.size > 0) {
            for (i in fullSpellsList.indices) {
                val spell = fullSpellsList[i]
                if (spell.level == 0 && !cantripsList.contains(spell)) {
                    cantripsList.add(spell)
                } else if (!cantripsList.contains(spell) && !spellsList.contains(spell)) {
                    spellsList.add(spell)
                }
            }
            spellsAdapter!!.notifyDataSetChanged()
            cantripsAdapter!!.notifyDataSetChanged()
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(0).equals("yes")) {
            spellSlot1!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(1).equals("yes")) {
            spellSlot2!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(2).equals("yes")) {
            spellSlot3!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(3).equals("yes")) {
            spellSlot4!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(4).equals("yes")) {
            spellSlot5!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(5).equals("yes")) {
            spellSlot6!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(6).equals("yes")) {
            spellSlot7!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(7).equals("yes")) {
            spellSlot8!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(8).equals("yes")) {
            spellSlot9!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(9).equals("yes")) {
            spellSlot10!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(10).equals("yes")) {
            spellSlot11!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(11).equals("yes")) {
            spellSlot12!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(12).equals("yes")) {
            spellSlot13!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(13).equals("yes")) {
            spellSlot14!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(14).equals("yes")) {
            spellSlot15!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(15).equals("yes")) {
            spellSlot16!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(16).equals("yes")) {
            spellSlot17!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(17).equals("yes")) {
            spellSlot18!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(18).equals("yes")) {
            spellSlot19!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(19).equals("yes")) {
            spellSlot20!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(20).equals("yes")) {
            spellSlot21!!.isChecked = true
        }
        if ((activity as DetailActivity?)!!.character.getSpellSlotsClicked().get(21).equals("yes")) {
            spellSlot22!!.isChecked = true
        }
    }

    private fun primarySpellcasterSlotsPerLevel() {
        when ((activity as DetailActivity?)!!.character.getLevel()) {
            20 -> {
                spellSlot20!!.visibility = View.VISIBLE
                spellSlot18!!.visibility = View.VISIBLE
                spellSlot16!!.visibility = View.VISIBLE
                spellSlot22!!.visibility = View.VISIBLE
                spellSlot21!!.visibility = View.VISIBLE
                spellSlot19!!.visibility = View.VISIBLE
                spellSlot17!!.visibility = View.VISIBLE
                spellSlot15!!.visibility = View.VISIBLE
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            19 -> {
                spellSlot18!!.visibility = View.VISIBLE
                spellSlot16!!.visibility = View.VISIBLE
                spellSlot22!!.visibility = View.VISIBLE
                spellSlot21!!.visibility = View.VISIBLE
                spellSlot19!!.visibility = View.VISIBLE
                spellSlot17!!.visibility = View.VISIBLE
                spellSlot15!!.visibility = View.VISIBLE
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            18 -> {
                spellSlot16!!.visibility = View.VISIBLE
                spellSlot22!!.visibility = View.VISIBLE
                spellSlot21!!.visibility = View.VISIBLE
                spellSlot19!!.visibility = View.VISIBLE
                spellSlot17!!.visibility = View.VISIBLE
                spellSlot15!!.visibility = View.VISIBLE
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            17 -> {
                spellSlot22!!.visibility = View.VISIBLE
                spellSlot21!!.visibility = View.VISIBLE
                spellSlot19!!.visibility = View.VISIBLE
                spellSlot17!!.visibility = View.VISIBLE
                spellSlot15!!.visibility = View.VISIBLE
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            16, 15 -> {
                spellSlot21!!.visibility = View.VISIBLE
                spellSlot19!!.visibility = View.VISIBLE
                spellSlot17!!.visibility = View.VISIBLE
                spellSlot15!!.visibility = View.VISIBLE
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            14, 13 -> {
                spellSlot19!!.visibility = View.VISIBLE
                spellSlot17!!.visibility = View.VISIBLE
                spellSlot15!!.visibility = View.VISIBLE
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            12, 11 -> {
                spellSlot17!!.visibility = View.VISIBLE
                spellSlot15!!.visibility = View.VISIBLE
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            10 -> {
                spellSlot15!!.visibility = View.VISIBLE
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            9 -> {
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            8 -> {
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            7 -> {
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            6 -> {
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            5 -> {
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            4 -> {
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            3 -> {
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
            }
            2 -> spellSlot3!!.visibility = View.VISIBLE
        }
    }

    private fun secondarySpellcasterSlotsPerLevel() {
        when ((activity as DetailActivity?)!!.character.getLevel()) {
            20, 19 -> {
                spellSlot15!!.visibility = View.VISIBLE
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            18, 17 -> {
                spellSlot13!!.visibility = View.VISIBLE
                spellSlot14!!.visibility = View.VISIBLE
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            16, 15 -> {
                spellSlot12!!.visibility = View.VISIBLE
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            14, 13 -> {
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            12, 11 -> {
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            10, 9 -> {
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            8, 7 -> {
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            6, 5 -> {
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            4, 3 -> {
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            2 -> {
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
        }
    }

    private fun tertiarySpellcasterSlotsPerLevel() {
        when ((activity as DetailActivity?)!!.character.getLevel()) {
            20, 19 -> {
                spellSlot11!!.visibility = View.VISIBLE
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            18, 17, 16 -> {
                spellSlot10!!.visibility = View.VISIBLE
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            15, 14, 13 -> {
                spellSlot8!!.visibility = View.VISIBLE
                spellSlot9!!.visibility = View.VISIBLE
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            12, 11, 10 -> {
                spellSlot7!!.visibility = View.VISIBLE
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            9, 8, 7 -> {
                spellSlot4!!.visibility = View.VISIBLE
                spellSlot5!!.visibility = View.VISIBLE
                spellSlot6!!.visibility = View.VISIBLE
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            6, 5, 4 -> {
                spellSlot3!!.visibility = View.VISIBLE
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
            3 -> {
                spellSlot1!!.visibility = View.VISIBLE
                spellSlot2!!.visibility = View.VISIBLE
            }
        }
    }

    private fun warlockSpellSlotsPerLevel() {
        if ((activity as DetailActivity?)!!.character.getLevel() >= 2) {
            spellSlot2!!.visibility = View.VISIBLE
        }
        if ((activity as DetailActivity?)!!.character.getLevel() >= 3) {
            spellSlot1!!.text = getString(R.string.second_level_spell)
            spellSlot2!!.text = getString(R.string.second_level_spell)
        }
        if ((activity as DetailActivity?)!!.character.getLevel() >= 5) {
            spellSlot1!!.text = getString(R.string.third_level_spell)
            spellSlot2!!.text = getString(R.string.third_level_spell)
        }
        if ((activity as DetailActivity?)!!.character.getLevel() >= 7) {
            spellSlot1!!.text = getString(R.string.fourth_level_spell)
            spellSlot2!!.text = getString(R.string.fourth_level_spell)
        }
        if ((activity as DetailActivity?)!!.character.getLevel() >= 9) {
            spellSlot1!!.text = getString(R.string.fifth_level_spell)
            spellSlot2!!.text = getString(R.string.fifth_level_spell)
        }
        if ((activity as DetailActivity?)!!.character.getLevel() >= 11) {
            spellSlot3!!.visibility = View.VISIBLE
            spellSlot3!!.text = getString(R.string.fifth_level_spell)
            spellSlot17!!.visibility = View.VISIBLE
            for (i in 0 until (activity as DetailActivity?)!!.character.getSpellsKnown().size) {
                if ((activity as DetailActivity?)!!.character.getSpellsKnown().get(i).level === 6) {
                    spellSlot17!!.setText((activity as DetailActivity?)!!.character.getSpellsKnown().get(i).spellName)
                }
            }
        }
        if ((activity as DetailActivity?)!!.character.getLevel() >= 13) {
            spellSlot19!!.visibility = View.VISIBLE
            for (i in 0 until (activity as DetailActivity?)!!.character.getSpellsKnown().size) {
                if ((activity as DetailActivity?)!!.character.getSpellsKnown().get(i).level === 7) {
                    spellSlot19!!.setText((activity as DetailActivity?)!!.character.getSpellsKnown().get(i).spellName)
                }
            }
        }
        if ((activity as DetailActivity?)!!.character.getLevel() >= 15) {
            spellSlot21!!.visibility = View.VISIBLE
            for (i in 0 until (activity as DetailActivity?)!!.character.getSpellsKnown().size) {
                if ((activity as DetailActivity?)!!.character.getSpellsKnown().get(i).level === 8) {
                    spellSlot21!!.setText((activity as DetailActivity?)!!.character.getSpellsKnown().get(i).spellName)
                }
            }
        }
        if ((activity as DetailActivity?)!!.character.getLevel() >= 17) {
            spellSlot4!!.visibility = View.VISIBLE
            spellSlot4!!.text = getString(R.string.fifth_level_spell)
            spellSlot22!!.visibility = View.VISIBLE
            for (i in 0 until (activity as DetailActivity?)!!.character.getSpellsKnown().size) {
                if ((activity as DetailActivity?)!!.character.getSpellsKnown().get(i).level === 9) {
                    spellSlot22!!.setText((activity as DetailActivity?)!!.character.getSpellsKnown().get(i).spellName)
                }
            }
        }
    }

    private fun getSubclassSpells(spellName: String) {
        (activity as DetailActivity?)!!.spellViewModel.loadAllSpells().observe(this, Observer<List<Spell>> { spells ->
            for (i in spells.indices) {
                if (spells[i].spellName.equals(spellName, ignoreCase = true)) {
                    val subclassSpell = spells[i]
                    (activity as DetailActivity?)!!.character.getSpellsKnown().add(subclassSpell)
                    for (j in 0 until (activity as DetailActivity?)!!.character.getSpellsKnown().size - 1) {
                        if ((activity as DetailActivity?)!!.character.getSpellsKnown().get(j).spellName == subclassSpell.spellName) {
                            (activity as DetailActivity?)!!.character.getSpellsKnown().remove(subclassSpell)
                        }
                    }
                }
            }
        })
    }

    private fun addSpellToList() {
        val tabLayout: TabLayout = activity!!.findViewById(R.id.tabs)
        val fab: FloatingActionButton = activity!!.findViewById(R.id.fab_main)
        tabLayout.visibility = View.INVISIBLE
        fab.visibility = View.INVISIBLE
        val nextFrag = SpellChooserFragment()
        activity!!.supportFragmentManager.beginTransaction()
                .remove(activity!!.supportFragmentManager.fragments[1])
                .replace(R.id.tabsLayout, nextFrag, "spellChooser")
                .addToBackStack(null)
                .commit()
    }
}