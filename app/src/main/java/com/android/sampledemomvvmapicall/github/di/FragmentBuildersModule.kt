package com.android.sampledemomvvmapicall.github.di

import com.android.sampledemomvvmapicall.github.ui.repo.RepoFragment
import com.android.sampledemomvvmapicall.github.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): RepoFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}
