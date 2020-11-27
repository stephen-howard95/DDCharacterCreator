package com.example.ddcharactercreator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ddcharactercreator.SpellAdapter.SpellViewHolder
import java.util.*

class SpellAdapter(private val context: Context, spells: List<Spell>, isDatabaseSpell: Boolean) : RecyclerView.Adapter<SpellViewHolder>() {
    private var spells: List<Spell> = ArrayList()
    private val isDatabaseSpell: Boolean
    private val character: Character = (this as DetailActivity?)!!.character
    private val cantripsList = ArrayList<Spell>()
    private val spellsList = ArrayList<Spell>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        return SpellViewHolder(LayoutInflater.from(context).inflate(R.layout.spell_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        for (i in character.getSpellsKnown().indices) {
            val thisSpell = character.getSpellsKnown()[i]
            if (thisSpell.level == 0 && !cantripsList.contains(thisSpell)) {
                cantripsList.add(thisSpell)
            } else if (!cantripsList.contains(thisSpell) && !spellsList.contains(thisSpell)) {
                spellsList.add(thisSpell)
            }
        }
        val spell = spells[position]
        holder.spellNameTextView.text = spell.spellName
        if (spell.level > 0) {
            holder.spellLevelTextView.text = java.lang.String.valueOf(spell.level)
        } else {
            holder.spellLevelTextView.visibility = View.GONE
        }
        when (spell.school) {
            "Abjuration" -> holder.spellNameTextView.setTextColor(context.resources.getColor(R.color.abjuration))
            "Conjuration" -> holder.spellNameTextView.setTextColor(context.resources.getColor(R.color.conjuration))
            "Divination" -> holder.spellNameTextView.setTextColor(context.resources.getColor(R.color.divination))
            "Enchantment" -> holder.spellNameTextView.setTextColor(context.resources.getColor(R.color.enchantment))
            "Evocation" -> holder.spellNameTextView.setTextColor(context.resources.getColor(R.color.evocation))
            "Illusion" -> holder.spellNameTextView.setTextColor(context.resources.getColor(R.color.illusion))
            "Necromancy" -> holder.spellNameTextView.setTextColor(context.resources.getColor(R.color.necromancy))
            "Transmutation" -> holder.spellNameTextView.setTextColor(context.resources.getColor(R.color.transmutation))
        }
        holder.parentView.setOnClickListener {
            val intent = Intent(context, SpellDetailActivity::class.java)
            intent.putExtra(SpellDetailActivity.SPELL, spell)
            context.startActivity(intent)
        }
        if (isDatabaseSpell) {
            holder.parentView.setOnLongClickListener {
                var i = 0
                var alreadyKnowsSpell = false
                do {
                    if (character.getSpellsKnown().size == 0) {
                        break
                    }
                    val knownSpell = character.getSpellsKnown()[i]
                    if (spell.spellName == knownSpell.spellName) {
                        alreadyKnowsSpell = true
                        deleteSpell(spell)
                        break
                    }
                    i++
                } while (i < character.getSpellsKnown().size)
                if (!alreadyKnowsSpell) {
                    if (spell.level == 0) {
                        if (cantripsList.size >= (this as DetailActivity?)!!.cantripCount) {
                            Toast.makeText(context, "You are at your max cantrip count. You cannot add any more cantrips", Toast.LENGTH_SHORT).show()
                        } else {
                            addSpell(spell)
                        }
                    } else if (spell.level > 0) {
                        if (spellsList.size >= (this as DetailActivity?)!!.spellCount) {
                            Toast.makeText(context, "You are at your max spell count. You cannot add any more spells", Toast.LENGTH_SHORT).show()
                        } else {
                            addSpell(spell)
                        }
                    }
                }
                true
            }
        } else {
            holder.parentView.setOnLongClickListener {
                val adb = AlertDialog.Builder(context)
                adb.setTitle("Delete?")
                adb.setMessage("Are you sure you want to delete " + spell.spellName)
                adb.setNegativeButton("Cancel", null)
                adb.setPositiveButton("Ok") { _, _ ->
                    character.getSpellsKnown().remove(spell)
                    spells.drop(position)
                    notifyDataSetChanged()
                }
                adb.show()
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return spells.size
    }

    fun setSpells(spells: List<Spell>) {
        this.spells = spells
        notifyDataSetChanged()
    }

    inner class SpellViewHolder(val parentView: View) : RecyclerView.ViewHolder(parentView) {
        val spellNameTextView: TextView = parentView.findViewById(R.id.spell_name)
        val spellLevelTextView: TextView = parentView.findViewById(R.id.spell_level)

    }

    private fun addSpell(spell: Spell) {
        val adb = AlertDialog.Builder(context)
        adb.setTitle("Add " + spell.spellName)
        adb.setMessage(String.format("Are you sure you want to add %s to your prepared spells?", spell.spellName))
        adb.setNegativeButton("Cancel", null)
        adb.setPositiveButton("Ok") { _, _ ->
            character.getSpellsKnown().add(spell)
            if (spell.level == 0) {
                cantripsList.add(spell)
            } else {
                spellsList.add(spell)
            }
            notifyDataSetChanged()
            Toast.makeText(context, String.format("%s added to spell list", spell.spellName), Toast.LENGTH_SHORT).show()
        }
        adb.show()
    }

    private fun deleteSpell(spell: Spell) {
        val adb = AlertDialog.Builder(context)
        adb.setTitle("Delete?")
        adb.setMessage(String.format("You already know %s. Do you want to delete it?", spell.spellName))
        adb.setNegativeButton("Cancel", null)
        adb.setPositiveButton("Ok") { _, _ ->
            character.getSpellsKnown().remove(spell)
            if (spell.level == 0) {
                cantripsList.remove(spell)
            } else {
                spellsList.remove(spell)
            }
            notifyDataSetChanged()
            Toast.makeText(context, String.format("%s removed from spell list", spell.spellName), Toast.LENGTH_SHORT).show()
        }
        adb.show()
    }

    init {
        this.spells = spells
        this.isDatabaseSpell = isDatabaseSpell
    }
}