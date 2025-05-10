package gsc.ThePickers.NetWork.Repository

import android.net.Uri
import gsc.ThePickers.NetWork.Response.DefaultResponse
import gsc.ThePickers.NetWork.Response.TokenResponse
import gsc.ThePickers.data.LoginData
import gsc.ThePickers.data.SignUpData
import gsc.ThePickers.data.UserData
import retrofit2.Response

interface UserRepository {
    suspend fun signUp( body : SignUpData ) : Response<DefaultResponse<Int>>
    suspend fun login( body : LoginData ): Response<DefaultResponse<TokenResponse>>
    suspend fun getUserInfo( accessToken: Int ) : Response<DefaultResponse<UserData>>
    suspend fun updateProfileImg(accessToken: Int, body : Uri ) : Response<DefaultResponse<String>>
    suspend fun updateProfile(accessToken: Int, body : UserData) : Response<DefaultResponse<String>>
}