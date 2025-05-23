package gsc.ThePickers.NetWork.Service

import android.net.Uri
import gsc.ThePickers.NetWork.Response.DefaultResponse
import gsc.ThePickers.NetWork.Response.TokenResponse
import gsc.ThePickers.data.UserData
import gsc.ThePickers.data.LoginData
import gsc.ThePickers.data.SignUpData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserService {

    @POST("/signup")
    suspend fun signUp(
        @Body body : SignUpData
    ) : Response<DefaultResponse<Int>>

    @POST("/login")
    suspend fun login(
        @Body body : LoginData
    ): Response<DefaultResponse<TokenResponse>>

    @GET("/users")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: Int
    ) : Response<DefaultResponse<UserData>>

    @PATCH("/users/profile")
    suspend fun updateProfileImg(
        @Header("Authorization") accessToken: Int,
        @Body body : Uri
    ) : Response<DefaultResponse<String>>

    @PATCH("/users")
    suspend fun updateProfile(
        @Header("Authorization") accessToken: Int,
        @Body body : UserData
    ) :Response<DefaultResponse<String>>

}