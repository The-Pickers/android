package gsc.ThePickers.NetWork.RepositoryImpl

import gsc.ThePickers.NetWork.Repository.HomeRepository
import gsc.ThePickers.NetWork.Response.CommentResponse
import gsc.ThePickers.NetWork.Response.DefaultResponse
import gsc.ThePickers.NetWork.Service.HomeService
import gsc.ThePickers.data.AccountData
import gsc.ThePickers.data.MapData
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