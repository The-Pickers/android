package gsc.ThePickers.NetWork.RepositoryImpl

import gsc.ThePickers.NetWork.Repository.MissionRepository
import gsc.ThePickers.NetWork.Response.DefaultResponse
import gsc.ThePickers.NetWork.Service.MissionService
import gsc.ThePickers.data.MissionData
import okhttp3.MultipartBody
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
        idx: Int,
        photo: MultipartBody.Part,
        locationIdx: Int,
        timestamp: String
    ): Response<DefaultResponse<MissionData>> {
        return api.completeMission( accessToken = accessToken, idx = idx, photo = photo, locationIdx = locationIdx, timestamp = timestamp)
    }


}