package com.example.zaitoneh.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


/**
 * A database that stores SleepNight information.
 * And a global method to get access to the database.
 *
 * This pattern is pretty much the same for any database,
 * so you can reuse it.
 */


@Database(entities = [Item::class,Store::class,Employee::class,Supplier::class,Receipt::class,Department::class,ReceiptDetail::class], version = 26, exportSchema = true)


abstract class StoreDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val itemDatabaseDao: ItemDatabaseDao
    abstract val storeDatabaseDao: StoreDatabaseDao

    abstract val employeeDatabaseDao: EmployeeDatabaseDao

    abstract val supplierDatabaseDao: SupplierDatabaseDao
    abstract val receiptDatabaseDao : ReceiptDatabaseDao
    abstract val departmentDatabaseDao : DepartmentDatabaseDao
    abstract val receiptDetailDatabaseDao : ReceiptDetailDatabaseDao


    /**
     * Define a companion object, this allows us to add functions on the StoreDatabase class.
     *
     * For example, clients can call `StoreDatabase.getInstance(context)` to instantiate
     * a new StoreDatabase.
     */
    companion object {
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: StoreDatabase? = null

        /**
         * Helper function to get the database.
         *
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         *
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         *
         * This is an example of a simple Singleton pattern that takes another Singleton as an
         * argument in Kotlin.
         *
         * To learn more about Singleton read the wikipedia article:
         * https://en.wikipedia.org/wiki/Singleton_pattern
         *
         * @param context The application context Singleton, used to get access to the filesystem.
         */
        fun getInstance(context: Context): StoreDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StoreDatabase::class.java,
                        "store_history_database"
                    )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration()
                        //.addMigrations(MIGRATION_27_28, MIGRATION_28_29)
                        //.addMigrations(MIGRATION_23_24)
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}


val MIGRATION_28_29: Migration = object : Migration(28, 29) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE items_table "
                    + " ADD COLUMN test TEXT  NOT NULL DEFAULT 's' "
        )
    }
}


val MIGRATION_27_28: Migration = object : Migration(27, 28) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE items_table "
                    + " ADD COLUMN test INTEGER NOT NULL DEFAULT 's' "
        )
    }
}

val MIGRATION_23_24: Migration = object : Migration(26, 27) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Since we didn't alter the table, there's nothing else to do here.
    }
}
