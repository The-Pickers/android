package gsc.ThePickers.data

import com.google.gson.annotations.SerializedName

data class AccountData(
    @SerializedName("mission_count")
    val missionCount : Int,
    @SerializedName("total_score")
    val totalScore : Int,
    @SerializedName("carbon_reduction")
    val carbonReduction : Float
)