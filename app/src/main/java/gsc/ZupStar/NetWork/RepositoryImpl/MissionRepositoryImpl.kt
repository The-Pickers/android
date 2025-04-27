package gsc.ZupStar.NetWork.RepositoryImpl

import gsc.ZupStar.NetWork.Repository.MissionRepository
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.NetWork.Service.MissionService
import retrofit2.Response
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val api : MissionService
) : MissionRepository{
    override suspend fun getMission(accessToken: String): Response<DefaultResponse> {
        return api.getMission(accessToken)
    }

}