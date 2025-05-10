package gsc.ZupStar.NetWork.Repository

import gsc.ZupStar.NetWork.Request.TeamBody
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.NetWork.Response.TeamResponse
import gsc.ZupStar.data.TeamData
import retrofit2.Response

interface TeamRepository {
    suspend fun createTeam( accessToken: Int, body : TeamData): Response<DefaultResponse<TeamResponse>>
    suspend fun joinTeam( accessToken: Int, idx : Int): Response<DefaultResponse<Int>>
    suspend fun searchTeam(accessToken: Int, name : TeamBody): Response<DefaultResponse<List<TeamData>>>

}