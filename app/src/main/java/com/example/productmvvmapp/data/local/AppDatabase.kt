package com.example.productmvvmapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.productmvvmapp.data.model.CarEntity
import com.example.productmvvmapp.data.model.ProductEntity

@Database(entities = [ProductEntity::class,CarEntity::class], version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object{

        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context):AppDatabase{
            INSTANCE = INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "product_table"
            ).fallbackToDestructiveMigration().build()

            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}