package com.sushant.sampledemomvvmapicall.di

import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.FeedRepository
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import com.sushant.sampledemomvvmapicall.service.provider.IServiceProvider
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module(includes = [NetworkModule::class])
@Suppress("unused")
object RepositoryModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideIFeedRepository(iServiceProvider: IServiceProvider): IFeedRepository {
        return FeedRepository(iServiceProvider)
    }

}