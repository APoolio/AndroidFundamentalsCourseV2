package com.example.roomwordssample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private RecyclerView mRecylerView;
    private WordListAdapter mAdapter;
    private WordViewModel mWordViewModel;

    //Request Code
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Once the FAB is clicked the NewWordActivity is called with an intent and if a word is inputed by the user onActivityResult will be called
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        //Handle to RecyclerView
        mRecylerView = findViewById(R.id.recyclerview);
        //initializing the adapter
        mAdapter = new WordListAdapter(this);
        //Connect the adapter with the recycle view above
        mRecylerView.setAdapter(mAdapter);
        //Giving the recycle view a layout manager
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));

        //Associating our ViewModel with our UI controller
        //ViewModelProviders creates and manages ViewModels
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        //Observer for the LiveData that is returned by the getAllWords() method in WordViewModel
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>()
        {
            //This then updates the words in the adapter which then updates the recycler view
            @Override
            public void onChanged(@Nullable final List<Word> words)
            {
                // Update the cached copy of the words in the adapter.
                mAdapter.setWords(words);
            }
        });

        // Adding the functionality to swipe items in the recycler view to delete a word
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }


            //When user swipes left or right on a word to delete it
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                //Get position of the viewHolder that was swiped
                int position = viewHolder.getAdapterPosition();
                //Get word at position using our getWordAtPosition method we defined
                Word myWord = mAdapter.getWordAtPosition(position);
                Toast.makeText(MainActivity.this, "Deleting " + myWord.getWord(), Toast.LENGTH_LONG).show();

                //Delete the word via view model which calls the repo which calls the dao query
                mWordViewModel.deleteWord(myWord);
            }
        });

        helper.attachToRecyclerView(mRecylerView);
    }


    //If the user put a word in then onActivityResult will be called after the return of the intent and will call insert from the WordViewModel
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
        {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data)
        {
            Toast.makeText(this, "Clearing the data...", Toast.LENGTH_SHORT).show();
            mWordViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editButton(View view)
    {
        
    }
}
