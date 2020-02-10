/**
 *                                              Dao (Data Access Object)
 *
 * The data access object, or Dao, is an annotated class where you specify SQL queries and associate them with method calls.
 * The compiler checks the SQL for errors, then generates queries from the annotations. For common queries, the libraries
 * provide convenience annotations such as @Insert.
 * **/


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomwordssample.Word;

import java.util.List;

@Dao //Identifies the class as a DAO
public interface WordDao
{
    @Insert //SQL done for you
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll(); //deletes all words

    //List of words wrapped in a LiveData class to help our app respond to data changes
    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords(); //gets all words
}

