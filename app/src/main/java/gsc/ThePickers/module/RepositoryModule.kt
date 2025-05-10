package gsc.ThePickers.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import gsc.ThePickers.NetWork.Repository.HomeRepository
import gsc.ThePickers.NetWork.Repository.MissionRepository
import gsc.ThePickers.NetWork.Repository.TeamRepository
import gsc.ThePickers.NetWork.Repository.UserRepository
import gsc.ThePickers.NetWork.RepositoryImpl.HomeRepositoryImpl
import gsc.ThePickers.NetWork.RepositoryImpl.MissionRepositoryImpl
import gsc.ThePickers.NetWork.RepositoryImpl.TeamRepositoryImpl
import gsc.ThePickers.NetWork.RepositoryImpl.UserRepositoryImpl
import gsc.ThePickers.NetWork.Service.HomeService
import gsc.ThePickers.NetWork.Service.MissionService
import gsc.ThePickers.NetWork.Service.TeamService
import gsc.ThePickers.NetWork.Service.UserService

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