package gsc.ThePickers.NetWork.RepositoryImpl

import gsc.ThePickers.NetWork.Repository.TeamRepository
import gsc.ThePickers.NetWork.Request.TeamBody
import gsc.ThePickers.NetWork.Response.DefaultResponse
import gsc.ThePickers.NetWork.Response.TeamResponse
import gsc.ThePickers.NetWork.Service.TeamService
import gsc.ThePickers.data.TeamData
import retrofit2.Response
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    val api: TeamService
) : TeamRepository {
    override suspend fun createTeam(
        accessToken: Int,
        body: TeamData
    ): Response<DefaultResponse<TeamResponse>> {
        return api.createTeam(accessToken, body)
    }

    override suspend fun joinTeam(
        accessToken: Int,
        idx: Int
    ): Response<DefaultResponse<Int>> {
        return api.joinTeam(accessToken, idx)
    }

    override suspend fun searchTeam(
        accessToken: Int,
        name: TeamBody
    ): Response<DefaultResponse<List<TeamData>>> {
        return api.searchTeam(accessToken, name)
    }
}