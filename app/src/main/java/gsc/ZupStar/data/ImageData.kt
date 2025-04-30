package gsc.ZupStar.data

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class ImageData(
    val bitmap: Bitmap,
    @SerializedName("location_name")
    var location_name : String,
    //"YYYY-MM-DDTHH:mm:ss
    @SerializedName("timestamp")
    var  timestamp : String
)