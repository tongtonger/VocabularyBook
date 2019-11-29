package com.example.vocabularybook;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class WordAdapter extends ArrayAdapter<Words> {

    private int resourceId;
    public WordAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Words> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Words words=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView vocabulary=(TextView)view.findViewById(R.id.vocabulary);
        vocabulary.setText(words.getName());
        

        return view;
    }
}
