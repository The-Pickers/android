package gsc.ZupStar.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionData(
    @SerializedName("mission_index")
    val index : Int,
    // HH:mm:ss
    @SerializedName("mission_time")
    val time : String,
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
