package gsc.ZupStar.NetWork.RepositoryImpl

import gsc.ZupStar.NetWork.Repository.MissionRepository
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.NetWork.Service.MissionService
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.data.VideoData
import retrofit2.Response
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val api : MissionService
) : MissionRepository{
    override suspend fun getMission(accessToken: String): Response<DefaultResponse<MissionData>> {
        return api.getMission(accessToken)
    }

    override suspend fun postMission(
        accessToken: String,
        data: VideoData
    ): Response<DefaultResponse<Int>> {
       return api.postMission(accessToken,data)
    }

    override suspend fun completeMission(
        accessToken: String,
        idx : Int,
        data: VideoData
    ): Response<DefaultResponse<MissionData>> {
       return api.completeMission(accessToken,idx,data)
    }

}