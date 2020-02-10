/**
 *                                                         RoomDatabase
 *
 * Room is a database layer on top of an SQLite database. Room takes care of mundane tasks that you used to handle with a database helper class such as SQLiteOpenHelper.
 *
**/


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomwordssample.Word;

//Annotating to be a RoomDatabase
//exportSchema to false since we are not keeping a history of schema versions (For migrating to databases)
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase
{
    public abstract WordDao wordDao();

    //Used as a singleton so there can not be multiple instances of the database opened
    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase(final Context context)
    {
        //If database instance is null then create it
        if (INSTANCE == null)
        {
            synchronized (WordRoomDatabase.class)
            {
                if (INSTANCE == null)
                {
                    //Using Room's database builder to create the RoomDatabase object with the name word_database
                    //Creating the actual database
                    //Wipes and rebuilds instead of migrating
                    //.fallbackToDestructiveMigration() - if no Migration object. Migration is not part of this practical
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordRoomDatabase.class, "word_database").fallbackToDestructiveMigration().build();
                }
            }
        }

        //return if created or already exists
        return INSTANCE;
    }
}
