package gsc.ZupStar.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gsc.ZupStar.NetWork.Repository.MissionRepository
import gsc.ZupStar.NetWork.RepositoryImpl.MissionRepositoryImpl
import gsc.ZupStar.NetWork.Service.HomeService
import gsc.ZupStar.NetWork.Service.MissionService
import gsc.ZupStar.NetWork.Service.TeamService
import gsc.ZupStar.NetWork.Service.UserService
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