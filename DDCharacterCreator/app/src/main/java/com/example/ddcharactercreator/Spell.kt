package com.example.ddcharactercreator

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import kotlin.Comparator

@Entity(tableName = "spellsList")
class Spell : Serializable {
    @PrimaryKey
    @SerializedName("name")
    var spellName: String = ""
        private set

    @SerializedName("desc")
    var description: String? = null
        private set

    @SerializedName("higher_level")
    var higherLevel: String? = null
        private set

    @SerializedName("range")
    var range: String? = null
        private set

    @SerializedName("duration")
    var duration: String? = null
        private set

    @SerializedName("concentration")
    var concentration: String? = null
        private set

    @SerializedName("casting_time")
    var castingTime: String? = null
        private set

    @SerializedName("level_int")
    var level = 0
        private set

    @SerializedName("dnd_class")
    var classList: String? = null
        private set

    @SerializedName("school")
    var school: String? = null
        private set

    @Ignore
    constructor() {
    }

    @Ignore
    constructor(name: String, level: Int) {
        spellName = name
        this.level = level
    }

    constructor(spellName: String, description: String?, higherLevel: String?, range: String?, duration: String?,
                concentration: String?, castingTime: String?, level: Int, classList: String?, school: String?) {
        this.spellName = spellName
        this.description = description
        this.higherLevel = higherLevel
        this.range = range
        this.duration = duration
        this.concentration = concentration
        this.castingTime = castingTime
        this.level = level
        this.classList = classList
        this.school = school
    }

    fun setClassList(newClassList: String?): String? {
        classList = newClassList
        return newClassList
    }

    companion object {
        @kotlin.jvm.JvmField
        var spellNameComparator = Comparator<Spell> { spell1, spell2 ->
            val spellName1 = spell1.spellName.toUpperCase()
            val spellName2 = spell2.spellName.toUpperCase()
            spellName1.compareTo(spellName2)
        }
    }
}