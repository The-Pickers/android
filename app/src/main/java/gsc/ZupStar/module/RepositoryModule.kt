package gsc.ZupStar.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import gsc.ZupStar.NetWork.Repository.HomeRepository
import gsc.ZupStar.NetWork.Repository.MissionRepository
import gsc.ZupStar.NetWork.Repository.TeamRepository
import gsc.ZupStar.NetWork.Repository.UserRepository
import gsc.ZupStar.NetWork.RepositoryImpl.HomeRepositoryImpl
import gsc.ZupStar.NetWork.RepositoryImpl.MissionRepositoryImpl
import gsc.ZupStar.NetWork.RepositoryImpl.TeamRepositoryImpl
import gsc.ZupStar.NetWork.RepositoryImpl.UserRepositoryImpl
import gsc.ZupStar.NetWork.Service.HomeService
import gsc.ZupStar.NetWork.Service.MissionService
import gsc.ZupStar.NetWork.Service.TeamService
import gsc.ZupStar.NetWork.Service.UserService

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @ViewModelScoped
    @Provides
    fun providesMissionRepository(
        missionService: MissionService
    ) : MissionRepository = MissionRepositoryImpl(missionService)

    @ViewModelScoped
    @Provides
    fun providesHomeRepository(
        homeService: HomeService
    ): HomeRepository = HomeRepositoryImpl(homeService)

    @ViewModelScoped
    @Provides
    fun providesUserRepository(
        userService: UserService
    ): UserRepository = UserRepositoryImpl(userService)

    @ViewModelScoped
    @Provides
    fun providesTeamRepository(
        teamService: TeamService
    ): TeamRepository = TeamRepositoryImpl(teamService)
}