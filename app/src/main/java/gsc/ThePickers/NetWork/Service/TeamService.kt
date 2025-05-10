package gsc.ThePickers.NetWork.Service

import gsc.ThePickers.NetWork.Request.TeamBody
import gsc.ThePickers.NetWork.Response.DefaultResponse
import gsc.ThePickers.NetWork.Response.TeamResponse
import gsc.ThePickers.data.TeamData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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