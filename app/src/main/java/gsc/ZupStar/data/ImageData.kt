package gsc.ZupStar.data


import android.net.Uri
import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("photo")
    val uri: Uri,
    @SerializedName("location_idx")
    var location_idx : Int,
    //"YYYY-MM-DDTHH:mm:ss
    @SerializedName("timestamp")
    var  timestamp : String
)