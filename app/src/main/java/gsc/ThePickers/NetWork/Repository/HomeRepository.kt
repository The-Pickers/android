package gsc.ThePickers.NetWork.Repository

import gsc.ThePickers.NetWork.Response.CommentResponse
import gsc.ThePickers.NetWork.Response.DefaultResponse
import gsc.ThePickers.data.AccountData
import gsc.ThePickers.data.MapData
import retrofit2.Response

interface HomeRepository {
    suspend fun getComment(accessToken: Int): Response<DefaultResponse<CommentResponse>>
    suspend fun getAccount(accessToken: Int): Response<DefaultResponse<AccountData>>
    suspend fun getMapInfo(accessToken: Int): Response<DefaultResponse<List<MapData>>>
}