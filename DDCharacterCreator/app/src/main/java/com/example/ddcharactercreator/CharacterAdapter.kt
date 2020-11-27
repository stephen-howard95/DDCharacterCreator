package com.example.ddcharactercreator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ddcharactercreator.CharacterAdapter.CharacterViewHolder
import java.util.*

class CharacterAdapter(private val context: Context, characters: List<Character>?) : RecyclerView.Adapter<CharacterViewHolder>() {
    private var characters: List<Character>? = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.character_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters!![position]
        holder.nameTextView.text = character.getName()
        holder.levelTextView.text = character.getLevel().toString()
        holder.raceTextView.text = character.getRace()
        holder.classTextView.text = character.getCharacterClass()
        holder.deleteCharacterImageView.setOnClickListener {
            mDb = CharacterDatabase.getInstance(context)
            val adb = AlertDialog.Builder(context)
            adb.setTitle("Delete?")
            adb.setMessage("Are you sure you want to delete " + character.getName() + "? This will be permanent.")
            adb.setNegativeButton("Cancel", null)
            adb.setPositiveButton("Ok") { _, _ ->
                val thisCharacter = characters!![position]
                mDb!!.characterDao().deleteCharacter(thisCharacter)
            }
            adb.show()
        }
        holder.parentView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra((this as DetailActivity?)!!.CHARACTER, character)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if (characters != null) {
            characters!!.size
        } else {
            0
        }
    }

    fun setCharacters(characters: List<Character>?) {
        this.characters = characters
        notifyDataSetChanged()
    }

    inner class CharacterViewHolder(val parentView: View) : RecyclerView.ViewHolder(parentView) {
        val nameTextView: TextView = parentView.findViewById(R.id.character_name)
        val levelTextView: TextView = parentView.findViewById(R.id.character_level)
        val raceTextView: TextView = parentView.findViewById(R.id.character_race)
        val classTextView: TextView = parentView.findViewById(R.id.character_class)
        val deleteCharacterImageView: ImageView = parentView.findViewById(R.id.delete_character_button)

    }

    companion object {
        private var mDb: CharacterDatabase? = null
    }

    init {
        this.characters = characters
    }
}