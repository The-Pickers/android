package gsc.ThePickers.data

import com.google.gson.annotations.SerializedName

data class RankListData (
    @SerializedName("my_rank")
    val data : RankData,
    @SerializedName("others_rank")
    val others : List<RankData>
){
    data class RankData (
        @SerializedName("index")
        val idx : Int,
        @SerializedName("name")
        val name : String,
        @SerializedName("rank")
        val rank : Int,
        @SerializedName("info")
        val info : String,
    )
}