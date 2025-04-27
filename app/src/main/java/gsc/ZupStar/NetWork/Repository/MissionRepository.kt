package gsc.ZupStar.NetWork.Repository

import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.data.VideoData
import retrofit2.Response


interface MissionRepository {
    suspend fun getMission(accessToken: String) : Response<DefaultResponse<MissionData>>
    suspend fun postMission( accessToken: String, data: VideoData) : Response<DefaultResponse<Int>>
    suspend fun completeMission(accessToken: String, idx : Int, data: VideoData) : Response<DefaultResponse<MissionData>>


}