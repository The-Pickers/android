package gsc.ZupStar.NetWork.Service

import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.data.ImageData
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.data.VideoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MissionService {
    @GET("/missions")
    suspend fun getMissionList(
        @Header("Authorization") accessToken: Int,
    ) : Response<DefaultResponse<List<MissionData>>>

    @POST("/missions/start")
    suspend fun postMission(
        @Header("Authorization") accessToken: Int
    ) : Response<DefaultResponse<Int>>

    @PATCH("/missions/complete/{mission_index}")
    suspend fun completeMission(
        @Header("Authorization") accessToken: Int,
        @Path("mission_index") idx : Int,
        @Query("mission") data: ImageData
    ) : Response<DefaultResponse<MissionData>>
}