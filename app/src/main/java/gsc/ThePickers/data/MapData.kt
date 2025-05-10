package gsc.ThePickers.data

import com.google.gson.annotations.SerializedName

data class MapData(
    @SerializedName("location_index")
    val location : Int,
    @SerializedName("mission_count")
    var mission : Int
)