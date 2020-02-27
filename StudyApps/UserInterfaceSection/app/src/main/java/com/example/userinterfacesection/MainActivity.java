package com.example.userinterfacesection;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
{
    //LinkedList to hold the words our user will supply
    private LinkedList<String> wordList = new LinkedList<>();
    //Adapter for the LinkedList to notify the UI of data changes
    private WordListAdapter wordListAdapter;
    //recyclerView to hold the ViewHolder of the words
    private RecyclerView mRecyclerView;

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int wordListSize = wordList.size();
                wordList.add("Word " + wordListSize);
                mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
                mRecyclerView.smoothScrollToPosition(wordListSize);
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
