package gsc.ZupStar.NetWork.RepositoryImpl

import gsc.ZupStar.NetWork.Repository.HomeRepository
import gsc.ZupStar.NetWork.Response.CommentResponse
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.NetWork.Response.MapResponse
import gsc.ZupStar.NetWork.Service.HomeService
import gsc.ZupStar.data.AccountData
import gsc.ZupStar.data.MapData
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    val api: HomeService
) : HomeRepository{
    override suspend fun getComment(accessToken: Int): Response<DefaultResponse<CommentResponse>> {
        return api.getComment(accessToken)
    }

    override suspend fun getAccount(accessToken: Int): Response<DefaultResponse<AccountData>> {
        return api.getAccount(accessToken)
    }

    override suspend fun getMapInfo(accessToken: Int): Response<DefaultResponse<List<MapData>>> {
        return api.getMapInfo(accessToken)
    }
}