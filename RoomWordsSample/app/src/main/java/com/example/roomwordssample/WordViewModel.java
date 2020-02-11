package com.example.roomwordssample; /**
 *                                                  ViewModel
 *
 * A ViewModel holds your app's UI data in a way that survives configuration changes. Separating your app's UI data from your Activity and Fragment
 * classes lets you better follow the single responsibility principle: Your activities and fragments are responsible for drawing data to the screen,
 * while your ViewModel is responsible for holding and processing all the data needed for the UI.
 *
 * **/


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel
{
    //Reference to the repo
    private WordRepository mRepository;

    //Used to cache the list of words
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(Application application)
    {
        super(application);

        //Getting a list of all words from the WordRepo
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    //Getter method that gets all the words and hides the implementation from the UI
    LiveData<List<Word>> getAllWords() { return mAllWords; }

    //Inserting a word into the repo which inserts it into the db
    public void insert(Word word) { mRepository.insert(word); }
}
