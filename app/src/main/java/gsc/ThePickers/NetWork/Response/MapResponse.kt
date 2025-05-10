package gsc.ThePickers.NetWork.Response

import com.google.gson.annotations.SerializedName
import gsc.ThePickers.data.MapData

data class MapResponse(
    @SerializedName("maps")
    val mapList : List<MapData>
)
