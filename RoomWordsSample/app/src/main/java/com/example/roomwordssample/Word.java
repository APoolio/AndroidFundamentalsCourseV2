/**
 *                                  Creating the Entity class
 *
 * The data for this app is words, and each word is represented by an entity in the database.
 * In this task you create the Word class and annotate it so Room can create a database table
 * from it. The diagram below shows a word_table database table. The table has one word column,
 * which also acts as the primary key, and two rows, one each for "Hello" and "World."
 *
 * **/



package com.example.roomwordssample;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Word
{
    @PrimaryKey //Unique key for each entity
    @NonNull //Can't be null
    @ColumnInfo(name = "word") //Column it is under
    private String mWord;

    //Constructor
    public Word(@NonNull String word) {this.mWord = word; }

    //Getter
    public String getWord() {return this.mWord; }
}
