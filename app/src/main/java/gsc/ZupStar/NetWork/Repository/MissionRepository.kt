package gsc.ZupStar.NetWork.Repository

import gsc.ZupStar.NetWork.Response.DefaultResponse
import retrofit2.Response
import retrofit2.http.Header

interface MissionRepository {
    suspend fun getMission(
        @Header("Authorization") accessToken: String,
    ) : Response<DefaultResponse>
}