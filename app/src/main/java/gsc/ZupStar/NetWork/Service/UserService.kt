package gsc.ZupStar.NetWork.Service

import android.net.Uri
import gsc.ZupStar.NetWork.Response.DefaultResponse
import gsc.ZupStar.data.UserData
import gsc.ZupStar.data.LoginData
import gsc.ZupStar.data.SignUpData
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
    ): Response<DefaultResponse<Int>>

    @GET("/users")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String
    ) : Response<DefaultResponse<UserData>>

    @PATCH("/users/profile")
    suspend fun updateProfileImg(
        @Header("Authorization") accessToken: String,
        @Body body : Uri
    ) : Response<DefaultResponse<String>>

    @PATCH("/users")
    suspend fun updateProfile(
        @Header("Authorization") accessToken: String,
        @Body body : UserData
    ) :Response<DefaultResponse<String>>

}