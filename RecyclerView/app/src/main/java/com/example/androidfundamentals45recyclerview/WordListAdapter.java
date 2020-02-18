package com.example.androidfundamentals45recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;


/* Building the adapter that bridges the gap between the data in the word list and the RecyclerView that is going to display it */
/* Used to put the data into the adapter */
public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>
{
    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater; /* LayoutInflator is used to inflate the XML. Reads a layout XML description and converts it into the corresponding View items. */


    /* Inner class for the view holder*/
    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        @Override
        public void onClick(View view)
        {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            String element = mWordList.get(mPosition);
            // Change the word in the mWordList.
            mWordList.set(mPosition, "Clicked! " + element);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }

        /* Constructor initializing the variables above */
        public WordViewHolder(View itemView, WordListAdapter adapter)
        {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }
    }

    /* Constructor */
    /* Used to initilize the word list from the data. */
    public WordListAdapter(Context context, LinkedList<String> wordList)
    {
        /* Context (According to stackoverflow)
        * As the name suggests, it's the context of current state of the application/object.
        * It lets newly-created objects understand what has been going on. Typically you
        * call it to get information regarding another part of your program
        * (activity and package/application). */

        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    /* Inflates the item layout and returns a ViewHolder with the layout and the adapter (data)*/
    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mItemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    /* Connects the data to the view holder */
    @Override
    public void onBindViewHolder(WordViewHolder holder, int position)
    {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override public int getItemCount() { return mWordList.size();}
}


