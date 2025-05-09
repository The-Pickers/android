package gsc.ZupStar.data

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("id")
    val id : String,
    @SerializedName("password")
    val password : String
)
