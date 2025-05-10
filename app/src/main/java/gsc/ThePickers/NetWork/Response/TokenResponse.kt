package gsc.ThePickers.NetWork.Response

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("token")
    val token : Int
)
