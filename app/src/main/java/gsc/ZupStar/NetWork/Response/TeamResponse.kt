package gsc.ZupStar.NetWork.Response

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("team_index")
    val idx : Int
)
