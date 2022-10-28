package id.fahrizal.billreminder.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.fahrizal.billreminder.data.repository.BillEntityRepository
import id.fahrizal.billreminder.data.repository.BillRepository
import id.fahrizal.billreminder.data.repository.source.BillEntityData
import id.fahrizal.billreminder.data.repository.source.ReminderEntityData
import id.fahrizal.billreminder.data.repository.source.local.LocalBillEntityData
import id.fahrizal.billreminder.data.repository.source.local.LocalReminderEntityData
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class BillModule {

    @Provides
    @Singleton
    fun provideBillRepository(billEntityRepository: BillEntityRepository): BillRepository {
        return billEntityRepository
    }

    @Provides
    @Singleton
    fun provideBillEntityData(localBillEntityData: LocalBillEntityData): BillEntityData {
        return localBillEntityData
    }

    @Provides
    @Singleton
    fun provideReminderEntityData(localReminderEntityData: LocalReminderEntityData): ReminderEntityData {
        return localReminderEntityData
    }
}