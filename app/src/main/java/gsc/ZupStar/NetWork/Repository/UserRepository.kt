package gsc.ZupStar.NetWork.Repository

import android.net.Uri
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.data.LoginData
import gsc.ZupStar.data.SignUpData
import gsc.ZupStar.data.UserData
import retrofit2.Response

interface UserRepository {
    suspend fun signUp( body : SignUpData ) : Response<DefaultResponse<Int>>
    suspend fun login( body : LoginData ): Response<DefaultResponse<Int>>
    suspend fun getUserInfo( accessToken: String ) : Response<DefaultResponse<UserData>>
    suspend fun updateProfileImg(accessToken: String, body : Uri ) : Response<DefaultResponse<String>>
    suspend fun updateProfile(accessToken: String, body : UserData) : Response<DefaultResponse<String>>
}