package gsc.ZupStar.NetWork.Response

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class TokenResponse(
    @SerializedName("token")
    val token : Int
)
