package gsc.ZupStar.NetWork.Service

import gsc.ZupStar.NetWork.Response.DefaultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface MissionService {
    @GET("/missions")
    suspend fun getMission(
        @Header("Authorization") accessToken: String,
    ) : Response<DefaultResponse>
}