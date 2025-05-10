package gsc.ZupStar.NetWork.Response

import com.google.gson.annotations.SerializedName
import gsc.ZupStar.data.MapData

data class MapResponse(
    @SerializedName("maps")
    val mapList : List<MapData>
)
