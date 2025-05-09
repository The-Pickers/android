package gsc.ZupStar.NetWork.RepositoryImpl

import gsc.ZupStar.NetWork.Repository.MissionRepository
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.NetWork.Service.MissionService
import gsc.ZupStar.data.ImageData
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.data.VideoData
import retrofit2.Response
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val api : MissionService
) : MissionRepository{
    override suspend fun getMissionList(accessToken: Int): Response<DefaultResponse<List<MissionData>>> {
        return api.getMissionList(accessToken)
    }

    override suspend fun postMission(
        accessToken: Int,
    ): Response<DefaultResponse<Int>> {
       return api.postMission(accessToken)
    }

    override suspend fun completeMission(
        accessToken: Int,
        idx : Int,
        data: ImageData
    ): Response<DefaultResponse<MissionData>> {
       return api.completeMission(accessToken,idx,data)
    }

}