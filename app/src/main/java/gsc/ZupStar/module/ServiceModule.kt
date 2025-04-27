package gsc.ZupStar.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gsc.ZupStar.NetWork.Repository.MissionRepository
import gsc.ZupStar.NetWork.RepositoryImpl.MissionRepositoryImpl
import gsc.ZupStar.NetWork.Service.MissionService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    private inline fun <reified T> Retrofit.buildService(): T {
        return this.create(T::class.java)
    }
    @Provides
    fun missionAPI(
        missionService: MissionService
    ) : MissionRepository = MissionRepositoryImpl(missionService)
}