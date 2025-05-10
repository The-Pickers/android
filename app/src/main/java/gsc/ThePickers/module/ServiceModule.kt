package gsc.ThePickers.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gsc.ThePickers.NetWork.Service.HomeService
import gsc.ThePickers.NetWork.Service.MissionService
import gsc.ThePickers.NetWork.Service.TeamService
import gsc.ThePickers.NetWork.Service.UserService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    private inline fun <reified T> Retrofit.buildService(): T {
        return this.create(T::class.java)
    }
    @Provides
    fun missionApi(@NetworkModule.BaseRetrofit retrofit: Retrofit): MissionService{
        return retrofit.buildService()
    }

    @Provides
    fun homeApi(@NetworkModule.BaseRetrofit retrofit: Retrofit): HomeService{
        return retrofit.buildService()
    }

    @Provides
    fun userApi(@NetworkModule.BaseRetrofit retrofit: Retrofit): UserService{
        return retrofit.buildService()
    }

    @Provides
    fun teamApi(@NetworkModule.BaseRetrofit retrofit: Retrofit): TeamService{
        return retrofit.buildService()
    }
}