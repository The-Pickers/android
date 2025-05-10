package gsc.ZupStar.NetWork.RepositoryImpl

import gsc.ZupStar.NetWork.Repository.HomeRepository
import gsc.ZupStar.NetWork.Repository.TeamRepository
import gsc.ZupStar.NetWork.Request.TeamBody
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.NetWork.Response.TeamResponse
import gsc.ZupStar.NetWork.Service.HomeService
import gsc.ZupStar.NetWork.Service.TeamService
import gsc.ZupStar.data.TeamData
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