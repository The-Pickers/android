package gsc.ZupStar.NetWork.Service

import gsc.ZupStar.NetWork.Request.TeamBody
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.NetWork.Response.TeamResponse
import gsc.ZupStar.data.TeamData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamService {
    @POST("/teams/create")
    suspend fun createTeam(
        @Header("Authorization") accessToken: Int,
        @Body body : TeamData
    ): Response<DefaultResponse<TeamResponse>>

    @POST("/teams/join/{team_index}")
    suspend fun joinTeam(
        @Header("Authorization") accessToken: Int,
        @Path ("team_index") idx : Int
    ): Response<DefaultResponse<Int>>

    @POST("/teams/search")
    suspend fun searchTeam(
        @Header("Authorization") accessToken: Int,
        //@Query ("team_name") name : String
        @Body body : TeamBody
    ): Response<DefaultResponse<List<TeamData>>>
}