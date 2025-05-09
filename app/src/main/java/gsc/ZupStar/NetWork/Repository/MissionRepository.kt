package gsc.ZupStar.NetWork.Repository

import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.data.ImageData
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.data.VideoData
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Part


interface MissionRepository {
    suspend fun getMissionList(accessToken: Int) : Response<DefaultResponse<List<MissionData>>>
    suspend fun postMission( accessToken: Int) : Response<DefaultResponse<Int>>
    suspend fun completeMission(accessToken: Int, idx: Int, photo: MultipartBody.Part, locationIdx: Int, timestamp: String) : Response<DefaultResponse<MissionData>>


}