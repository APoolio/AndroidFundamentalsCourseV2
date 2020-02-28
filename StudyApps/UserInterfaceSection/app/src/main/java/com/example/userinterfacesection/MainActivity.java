package com.example.userinterfacesection;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
{
    //LinkedList to hold the words our user will supply
    private LinkedList<String> wordList = new LinkedList<>();
    //Adapter for the LinkedList to notify the UI of data changes
    private WordListAdapter wordListAdapter;
    //recyclerView to hold the ViewHolder of the words
    private RecyclerView mRecyclerView;
    //EditText to retrieve the word typed by the user
    private EditText mAddWord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Reference the recycler view
        mRecyclerView = findViewById(R.id.recyclerView);
        //Create a wordListAdapter object
        wordListAdapter = new WordListAdapter(this, wordList);
        //Need to set the adapter to reflect the recyclerView
        mRecyclerView.setAdapter(wordListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Reference to the EditText
        mAddWord = findViewById(R.id.addWord);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int wordListSize = wordList.size();
                String word = mAddWord.getText().toString();
                if(!TextUtils.isEmpty(word))
                {
                    wordList.add("Word " + word);
                    mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
                    mRecyclerView.smoothScrollToPosition(wordListSize);
                }

                else
                    Toast.makeText(MainActivity.this, "Word input is empty.", Toast.LENGTH_SHORT).show();
            }
        });
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
        if (id == R.id.action_settings)
        {
            //Clear the list
            wordList.clear();
            //Notify the adapter that the data has changed
            wordListAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}
