package gsc.ZupStar.data

import com.google.gson.annotations.SerializedName

data class MapData(
    @SerializedName("location_index")
    val location : Int,
    @SerializedName("mission_count")
    val mission : Int
)