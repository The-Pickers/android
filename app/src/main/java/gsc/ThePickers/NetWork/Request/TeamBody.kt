package gsc.ThePickers.NetWork.Request

import com.google.gson.annotations.SerializedName

data class TeamBody(
    @SerializedName("team_name")
    val name : String
)
