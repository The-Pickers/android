package gsc.ZupStar.NetWork.Service

import gsc.ZupStar.NetWork.Response.CommentResponse
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.NetWork.Response.MapResponse
import gsc.ZupStar.data.AccountData
import gsc.ZupStar.data.MapData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeService {

    @GET("/users/impact")
    suspend fun getComment(
        @Header("Authorization") accessToken: Int
    ): Response<DefaultResponse<CommentResponse>>

    @GET("/users/home")
    suspend fun getAccount(
        @Header("Authorization") accessToken:Int
    ): Response<DefaultResponse<AccountData>>

    @GET("/maps")
    suspend fun getMapInfo(
        @Header("Authorization") accessToken: Int
    ): Response<DefaultResponse<List<MapData>>>
}