package com.dragonfruit.codinghouse.soundcart;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ben on 2015-01-29.
 */
public class soundAdapter extends ArrayAdapter<sButton> {
    Context context;
    int resource;
    public soundAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;



        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);
        }
        sButton b = (sButton) v.findViewById(R.id.button);
        b = new sButton(context);
        b.setOnClickListener(new MainActivity());
        b.setOnLongClickListener(new MainActivity());

        return v;
    }
}
