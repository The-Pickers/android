package gsc.ThePickers.data

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("user_index")
    val idx : Int,
    @SerializedName("id")
    val id : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("team_name")
    val team : String= "",
    @SerializedName("profile_image_url")
    val profile : String =""
)
