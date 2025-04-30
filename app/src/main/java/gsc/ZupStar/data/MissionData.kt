package gsc.ZupStar.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionData(
    @SerializedName("mission_index")
    val index : Int,
    @SerializedName("mission_title")
    val title: String,
    // HH:mm:ss
    @SerializedName("mission_time")
    var takenTime : String = "00:00:00",
    @SerializedName("mission_start_time")
    val startTime :String,
    @SerializedName("completed")
    val completed : Boolean,
    @SerializedName("carbon_reduction")
    val carbonReduction : Float,
    @SerializedName("message")
    val message : String,
    @SerializedName("detected_waste")
    val detectedWaste : Int,
    @SerializedName("score")
    val score : Int
) : Parcelable
