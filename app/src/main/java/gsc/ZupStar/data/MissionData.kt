package gsc.ZupStar.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionData(
    @SerializedName("user_index")
    val userIndex : Int,
    @SerializedName("mission_index")
    val missionIndex : Int,
    // HH:mm:ss
    @SerializedName("mission_take_time")
    var takenTime : String = "00:00:00",
    @SerializedName("mission_start_time")
    val startTime :String,
    @SerializedName("mission_clear_time")
    val clearTime :String,
    @SerializedName("mission_status")
    val status : Int,
    @SerializedName("mission_carbon_reduction")
    val carbonReduction : Float,
    @SerializedName("mission_detected_waste")
    val detectedWaste : Int,
    @SerializedName("mission_score")
    val score : Int,
    @SerializedName("mission_message")
    val message : String,
    @SerializedName("location_index")
    val location : Int
) : Parcelable
