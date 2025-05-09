package gsc.ZupStar.NetWork.RepositoryImpl

import android.net.Uri
import gsc.ZupStar.NetWork.Repository.UserRepository
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.NetWork.Response.TokenResponse
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

    override suspend fun login(body: LoginData): Response<DefaultResponse<TokenResponse>> {
       return api.login(body)
    }

    override suspend fun getUserInfo(accessToken: Int): Response<DefaultResponse<UserData>> {
       return api.getUserInfo(accessToken)
    }

    override suspend fun updateProfileImg(
        accessToken: Int,
        body: Uri
    ): Response<DefaultResponse<String>> {
       return api.updateProfileImg(accessToken, body)
    }

    override suspend fun updateProfile(
        accessToken: Int,
        body: UserData
    ): Response<DefaultResponse<String>> {
      return api.updateProfile(accessToken,body)
    }
}