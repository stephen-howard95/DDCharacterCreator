package com.example.ddcharactercreator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class SpellChooserFragment : Fragment() {
    private var completeSpellsList: LiveData<List<Spell>>? = null
    private val spellsList = ArrayList<Spell>()
    private val cantripsList = ArrayList<Spell>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.activity_spell, container, false)
        val imageButton = rootView.findViewById<ImageButton>(R.id.end_button)
        completeSpellsList = (this as DetailActivity?)!!.spellViewModel.loadAllSpells()
        Collections.sort(completeSpellsList!!.value, Spell.spellNameComparator)
        spellViewModel.loadAllSpells().observe(this, Observer<List<Spell>> { spells ->
            when (character.getCharacterClass()) {
                "Bard" -> when (character.getLevel()) {
                    6 -> if (character.getSubclass() == "College of Lore") {
                        getMagicalSecrets(spells)
                    }
                    10, 14, 18 -> getMagicalSecrets(spells)
                }
                "Fighter", "Rogue" -> spellcastingClass = "Wizard"
                "Sorcerer" -> if (character.getSubclass() == "Stone Sorcery") {
                    addSpellsToClassList("compelled duel", spells)
                    addSpellsToClassList("searing smite", spells)
                    addSpellsToClassList("thunderous smite", spells)
                    addSpellsToClassList("wrathful smite", spells)
                    addSpellsToClassList("branding smite", spells)
                    addSpellsToClassList("magic weapon", spells)
                    addSpellsToClassList("blinding smite", spells)
                    addSpellsToClassList("elemental weapon", spells)
                    addSpellsToClassList("staggering smite", spells)
                }
                "Warlock" -> {
                    when (character.getSubclass()) {
                        "The Archfey" -> {
                            addSpellsToClassList("faerie fire", spells)
                            addSpellsToClassList("sleep", spells)
                            addSpellsToClassList("calm emotions", spells)
                            addSpellsToClassList("phantasmal force", spells)
                            addSpellsToClassList("blink", spells)
                            addSpellsToClassList("plant growth", spells)
                            addSpellsToClassList("dominate beast", spells)
                            addSpellsToClassList("greater invisibility", spells)
                            addSpellsToClassList("dominate person", spells)
                            addSpellsToClassList("seeming", spells)
                        }
                        "The Fiend" -> {
                            addSpellsToClassList("burning hands", spells)
                            addSpellsToClassList("command", spells)
                            addSpellsToClassList("blindness/deafness", spells)
                            addSpellsToClassList("scorching ray", spells)
                            addSpellsToClassList("fireball", spells)
                            addSpellsToClassList("stinking cloud", spells)
                            addSpellsToClassList("fire shield", spells)
                            addSpellsToClassList("wall of fire", spells)
                            addSpellsToClassList("flame strike", spells)
                            addSpellsToClassList("hallow", spells)
                        }
                        "The Great Old One" -> {
                            addSpellsToClassList("dissonant whispers", spells)
                            addSpellsToClassList("hideous laughter", spells)
                            addSpellsToClassList("detect thoughts", spells)
                            addSpellsToClassList("phantasmal force", spells)
                            addSpellsToClassList("clairvoyance", spells)
                            addSpellsToClassList("sending", spells)
                            addSpellsToClassList("dominate beast", spells)
                            addSpellsToClassList("black tentacles", spells)
                            addSpellsToClassList("dominate person", spells)
                            addSpellsToClassList("telekinesis", spells)
                        }
                    }
                    if (character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_tome))) {
                        getSpellsPerLevel(0, spells)
                    }
                }
            }
            val characterSpellsList: ArrayList<Spell> = character.getSpellsKnown()

            // Separates Cantrips and Spells
            for (i in characterSpellsList.indices) {
                val spell = characterSpellsList[i]
                if (spell.level == 0 && !cantripsList.contains(spell)) {
                    cantripsList.add(spell)
                } else if (!cantripsList.contains(spell) && !spellsList.contains(spell)) {
                    spellsList.add(spell)
                }
            }

            //Limits the available spells for the character based on their level and character class
            if (completeSpellsList != null && spells.isNotEmpty()) {
                if (character.getCharacterClass() == "Ranger" || character.getCharacterClass() == "Paladin") {
                    var i = 0
                    while (i < spells.size) {
                        if (4 * spells[i].level - 3 > character.getLevel() || !spells[i].classList!!.contains(spellcastingClass)) {
                            spells.drop(i)
                            i--
                        }
                        i++
                    }
                } else if (character.getSubclass() == "Eldritch Knight" || character.getSubclass() == "Arcane Trickster") {
                    var i = 0
                    while (i < spells.size) {
                        if (6 * spells[i].level - 6 >= character.getLevel() || !spells[i].classList!!.contains(spellcastingClass)) {
                            spells.drop(i)
                            i--
                        }
                        i++
                    }
                } else {
                    var i = 0
                    while (i < spells.size) {
                        if (character.getCharacterClass() == "Warlock" && spells[i].level >= 6 || 2 * spells[i].level - 1 > character.getLevel() || !spells[i].classList!!.contains(spellcastingClass)) {
                            spells.drop(i)
                            i--
                        }
                        i++
                    }
                }
                if (cantripsList.size >= cantripCount) {
                    var i = 0
                    while (i < spells.size) {
                        if (spells[i].level == 0) {
                            spells.drop(i)
                            i--
                        }
                        i++
                    }
                }
                if (spellsList.size >= spellCount) {
                    var i = 0
                    while (i < spells.size) {
                        if (spells[i].level >= 1) {
                            spells.drop(i)
                            i--
                        }
                        i++
                    }
                }
                val newSpellList = ArrayList(spells)
                val mAdapter = SpellAdapter(context!!, newSpellList, true)
                val spellListView: RecyclerView = rootView.findViewById(R.id.list)
                val layoutManager = LinearLayoutManager(context)
                val dividerItemDecoration = DividerItemDecoration(context,
                        layoutManager.orientation)
                spellListView.addItemDecoration(dividerItemDecoration)
                spellListView.layoutManager = layoutManager
                spellListView.adapter = mAdapter
            } else {
                Toast.makeText(context, "Spell Database is empty. Try again later", Toast.LENGTH_LONG).show()
            }
        })
        imageButton.visibility = View.VISIBLE
        imageButton.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra((this as DetailActivity?)!!.CHARACTER, character)
            activity!!.finish()
            startActivity(intent)
        }
        return rootView
    }

    //Allows certain character classes to add a specific spell to their class list
    private fun addSpellsToClassList(spellName: String, spells: List<Spell>) {
        var classList: String?
        for (i in spells.indices) {
            if (spells[i].spellName.equals(spellName, ignoreCase = true) && !spells[i].classList!!.contains((this as DetailActivity?)!!.spellcastingClass)) {
                classList = spells[i].classList
                spells[i].setClassList(classList + spellcastingClass)
            }
        }
    }

    //Allows certain character classes access to several spells outside their class list
    private fun getSpellsPerLevel(spellLevel: Int, spells: List<Spell>) {
        for (i in spells.indices) {
            if (!spells[i].classList!!.contains((this as DetailActivity?)!!.spellcastingClass) && spells[i].level == spellLevel) {
                spells[i].setClassList(spells[i].classList + spellcastingClass)
            }
        }
    }

    //Allows bards access to spells outside of their character class list per the Magical Secrets Feature
    private fun getMagicalSecrets(spells: List<Spell>) {
        for (i in spells.indices) {
            if (!spells[i].classList!!.contains("Bard") && spells[i].level != 0) {
                spells[i].setClassList(spells[i].classList + (this as DetailActivity?)!!.character.getCharacterClass())
            }
        }
    }
}