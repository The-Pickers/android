package gsc.ThePickers.NetWork.Response

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("message")
    val message : String
)
