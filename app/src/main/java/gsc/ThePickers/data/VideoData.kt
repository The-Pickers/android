package gsc.ThePickers.data

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class VideoData(
    @SerializedName("video_url")
    var url: Uri,
    @SerializedName("location_name")
    var location_name : String,
    //"YYYY-MM-DDTHH:mm:ss
    @SerializedName("timestamp")
    var  timestamp : String
)
