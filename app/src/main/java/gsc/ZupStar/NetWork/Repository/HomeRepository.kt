package gsc.ZupStar.NetWork.Repository

import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.data.AccountData
import retrofit2.Response

interface HomeRepository {
    suspend fun getComment(accessToken: Int): Response<DefaultResponse<List<String>>>

    suspend fun getAccount(accessToken: Int): Response<DefaultResponse<AccountData>>
}