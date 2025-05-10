package gsc.ThePickers.NetWork.Repository

import gsc.ThePickers.NetWork.Response.DefaultResponse
import gsc.ThePickers.data.MissionData
import okhttp3.MultipartBody
import retrofit2.Response


interface MissionRepository {
    suspend fun getMissionList(accessToken: Int) : Response<DefaultResponse<List<MissionData>>>
    suspend fun postMission( accessToken: Int) : Response<DefaultResponse<Int>>
    suspend fun completeMission(accessToken: Int, idx: Int, photo: MultipartBody.Part, locationIdx: Int, timestamp: String) : Response<DefaultResponse<MissionData>>


}