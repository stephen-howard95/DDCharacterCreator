package com.example.ddcharactercreator

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "characterList")
class Character: Serializable{

    private var mLevel: Int = 0
    private var mRace: String = ""
    private var mCharacterClass: String = ""
    private var mAlignment: String = ""
    @PrimaryKey
    @NonNull
    private var mName: String = ""
    private var mStatValues: ArrayList<Int> = ArrayList<Int>()
    private var mProficiencyChoices: ArrayList<String> = ArrayList<String>()
    private var mInventoryList: ArrayList<String> = ArrayList<String>()
    private var mCurrency: ArrayList<Int> = ArrayList<Int>()
    private var mSubclass: String = ""
    private var mSpellsKnown: ArrayList<Spell> = ArrayList<Spell>()
    private var mSpellSlotsClicked: ArrayList<String> = ArrayList<String>()
    private var mRaceAndClassBonusStats: ArrayList<String> = ArrayList<String>()
    private var mClassBasedBonusStats2: ArrayList<String> = ArrayList<String>()

    @Ignore
    constructor(){
    }

    @Ignore
    constructor(level: Int, race: String, characterClass: String, alignment: String, name: String, statValues: ArrayList<Int>){
        mLevel = level
        mRace = race
        mCharacterClass = characterClass
        mAlignment = alignment
        mName = name
        mStatValues = statValues
    }

    @Ignore
    constructor(level: Int, race: String, characterClass: String, alignment: String, name: String, statValues: ArrayList<Int>, proficiencyChoices: ArrayList<String>,
                inventoryList: ArrayList<String>, currency:ArrayList<Int>){
        mLevel = level
        mRace = race
        mCharacterClass = characterClass
        mAlignment = alignment
        mName = name
        mStatValues = statValues
        mProficiencyChoices = proficiencyChoices
        mInventoryList = inventoryList
        mCurrency = currency
    }

    constructor(level: Int, race: String, characterClass: String, alignment: String, name: String, statValues: ArrayList<Int>, proficiencyChoices: ArrayList<String>,
                inventoryList: ArrayList<String>, currency:ArrayList<Int>, subclass: String, spellsKnown: ArrayList<Spell>, spellSlotsClicked: ArrayList<String>,
                raceAndClassBonusStats: ArrayList<String>, classBasedBonusStats2: ArrayList<String>){
        mLevel = level
        mRace = race
        mCharacterClass = characterClass
        mAlignment = alignment
        mName = name
        mStatValues = statValues
        mProficiencyChoices = proficiencyChoices
        mInventoryList = inventoryList
        mCurrency = currency
        mSubclass = subclass
        mSpellsKnown = spellsKnown
        mSpellSlotsClicked = spellSlotsClicked
        mRaceAndClassBonusStats = raceAndClassBonusStats
        mClassBasedBonusStats2 = classBasedBonusStats2
    }

    fun getLevel(): Int{
        return mLevel
    }
    fun getRace(): String{
        return mRace
    }
    fun getCharacterClass(): String{
        return mCharacterClass
    }
    fun getAlignment(): String{
        return mAlignment
    }
    fun getName(): String{
        return mName
    }
    fun getStatValues(): ArrayList<Int>{
        return mStatValues
    }
    fun getProficiencyChoices(): ArrayList<String>{
        return mProficiencyChoices
    }
    fun getInventoryList(): ArrayList<String>{
        return mInventoryList
    }
    fun getCurrency(): ArrayList<Int>{
        return mCurrency
    }
    fun getSubclass(): String{
        return mSubclass
    }
    fun getSpellsKnown(): ArrayList<Spell>{
        return mSpellsKnown
    }
    fun getSpellSlotsClicked(): ArrayList<String>{
        return mSpellSlotsClicked
    }
    fun getRaceAndClassBonusStats(): ArrayList<String>{
        return mRaceAndClassBonusStats
    }
    fun getClassBasedBonusStats2(): ArrayList<String>{
        return mClassBasedBonusStats2
    }
}