package com.example.userinterfacesection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>
{
    private LayoutInflater mInflater;
    private LinkedList<String> mWordList;

    public WordListAdapter(Context context, LinkedList<String> wordList)
    {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    //Inflating the actual view holder layout
    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mItemView = mInflater.inflate(R.layout.iconword_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    //Data to be put into the individual view holders
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position)
    {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
        holder.iconItemView.setImageResource(R.drawable.ic_cake);
    }

    @Override
    public int getItemCount()
    {
        return mWordList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final TextView wordItemView;
        public final ImageView iconItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, WordListAdapter adapter)
        {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.wordItem);
            iconItemView = itemView.findViewById(R.id.iconItem);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {

        }
    }
}
