package id.fahrizal.billreminder.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.fahrizal.billreminder.data.dao.BillDao
import id.fahrizal.billreminder.data.db.AppDatabase
import id.fahrizal.billreminder.data.db.AppRoomDatabase

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAppRoomDatabase(appDatabase: AppDatabase): AppRoomDatabase {
        return appDatabase.roomDb
    }

    @Provides
    fun provideBillDao(appRoomDatabase: AppRoomDatabase): BillDao {
        return appRoomDatabase.billDao()
    }
}