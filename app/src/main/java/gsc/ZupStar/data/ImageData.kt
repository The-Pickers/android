package gsc.ZupStar.data


import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class ImageData(
    @SerializedName("photo")
    val photo: MultipartBody.Part,
    @SerializedName("location_idx")
    var location_idx : Int,
    //"YYYY-MM-DDTHH:mm:ss
    @SerializedName("timestamp")
    var  timestamp : String
)