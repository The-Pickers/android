package gsc.ZupStar.data

import com.google.gson.annotations.SerializedName


data class SignUpData(
    @SerializedName("id")
    val id : String,
    @SerializedName("password")
    val password : String,
    @SerializedName("name")
    val name : String,
//    @SerializedName("birthday")
//    val birth : String,
    @SerializedName("team_code")
    val team : String = ""
)
