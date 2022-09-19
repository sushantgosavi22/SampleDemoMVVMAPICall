package com.android.sampledemomvvmapicall.github.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.sampledemomvvmapicall.github.vo.Contributor
import com.android.sampledemomvvmapicall.github.vo.Repo
import com.android.sampledemomvvmapicall.github.vo.RepoSearchResult
/**
 * Main database description.
 */
@Database(
    entities = [
        Repo::class,
        Contributor::class,
        RepoSearchResult::class],
    version = 3,
    exportSchema = false
)
abstract class GithubDb : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}
