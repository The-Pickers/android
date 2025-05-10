package gsc.ThePickers.data

import com.google.gson.annotations.SerializedName

data class TeamData(
    @SerializedName("team_index")
    val idx : Int,
    @SerializedName("team_name")
    val name : String,
    @SerializedName("team_description")
    val info : String =""
)
