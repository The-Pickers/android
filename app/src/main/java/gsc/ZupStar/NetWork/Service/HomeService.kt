package gsc.ZupStar.NetWork.Service

import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.data.AccountData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeService {

    @GET("/users/impact")
    suspend fun getComment(
        @Header("Authorization") accessToken: String
    ): Response<DefaultResponse<List<String>>>

    @GET("/user/home")
    suspend fun getAccount(
        @Header("Authorization") accessToken: String
    ): Response<DefaultResponse<AccountData>>
}