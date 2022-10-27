package id.fahrizal.billreminder.data.db

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDatabase @Inject constructor(@ApplicationContext context: Context) {

    val roomDb: AppRoomDatabase by lazy {
        Room.databaseBuilder(context, AppRoomDatabase::class.java, DB_NAME)
            .build()
    }

    companion object {

        const val DB_NAME = "bill_db"
    }
}