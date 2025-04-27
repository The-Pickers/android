package gsc.ZupStar.data

import com.google.gson.annotations.SerializedName

data class AccountData(
    @SerializedName("user_mission_count")
    val missionCount : Int,
    @SerializedName("score_log_total_score")
    val totalScore : Int,
    @SerializedName("score_log_carbon_reduction")
    val carbonReduction : Float
)