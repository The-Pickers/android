package gsc.ZupStar.NetWork.Repository

import gsc.ZupStar.NetWork.Response.CommentResponse
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.data.AccountData
import gsc.ZupStar.data.MapData
import retrofit2.Response

interface HomeRepository {
    suspend fun getComment(accessToken: Int): Response<DefaultResponse<CommentResponse>>
    suspend fun getAccount(accessToken: Int): Response<DefaultResponse<AccountData>>
    suspend fun getMapInfo(accessToken: Int): Response<DefaultResponse<List<MapData>>>
}