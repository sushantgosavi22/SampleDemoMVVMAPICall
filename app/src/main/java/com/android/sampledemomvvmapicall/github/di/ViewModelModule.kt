package com.android.sampledemomvvmapicall.github.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.android.sampledemomvvmapicall.github.ui.repo.RepoViewModel
import com.android.sampledemomvvmapicall.github.ui.search.SearchViewModel
import com.android.sampledemomvvmapicall.github.viewmodel.GithubViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    abstract fun bindRepoViewModel(repoViewModel: RepoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}
