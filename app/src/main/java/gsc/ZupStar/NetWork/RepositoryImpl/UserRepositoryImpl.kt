package gsc.ZupStar.NetWork.RepositoryImpl

import android.net.Uri
import gsc.ZupStar.NetWork.Repository.UserRepository
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.NetWork.Service.UserService
import gsc.ZupStar.data.LoginData
import gsc.ZupStar.data.SignUpData
import gsc.ZupStar.data.UserData
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api : UserService
) : UserRepository{
    override suspend fun signUp(body: SignUpData): Response<DefaultResponse<Int>> {
        return api.signUp(body)
    }

    override suspend fun login(body: LoginData): Response<DefaultResponse<Int>> {
       return api.login(body)
    }

    override suspend fun getUserInfo(accessToken: String): Response<DefaultResponse<UserData>> {
       return api.getUserInfo(accessToken)
    }

    override suspend fun updateProfileImg(
        accessToken: String,
        body: Uri
    ): Response<DefaultResponse<String>> {
       return api.updateProfileImg(accessToken, body)
    }

    override suspend fun updateProfile(
        accessToken: String,
        body: UserData
    ): Response<DefaultResponse<String>> {
      return api.updateProfile(accessToken,body)
    }
}