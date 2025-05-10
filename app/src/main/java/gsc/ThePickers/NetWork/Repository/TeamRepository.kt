package gsc.ThePickers.NetWork.Repository

import gsc.ThePickers.NetWork.Request.TeamBody
import gsc.ThePickers.NetWork.Response.DefaultResponse
import gsc.ThePickers.NetWork.Response.TeamResponse
import gsc.ThePickers.data.TeamData
import retrofit2.Response

interface TeamRepository {
    suspend fun createTeam( accessToken: Int, body : TeamData): Response<DefaultResponse<TeamResponse>>
    suspend fun joinTeam( accessToken: Int, idx : Int): Response<DefaultResponse<Int>>
    suspend fun searchTeam(accessToken: Int, name : TeamBody): Response<DefaultResponse<List<TeamData>>>

}