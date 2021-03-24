package com.example.android.sklad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class PadakAdapter extends ArrayAdapter<Padak> {
    private static final String TAG = "MyActivity";


    public PadakAdapter(Context context, List<Padak> padaky) {
        super(context, 0, padaky);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Padak padak = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Set the theme color for the list item
        View textContainer = convertView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
//        int color = ContextCompat.getColor(getContext(), mColorResourceId);
//        // Set the background color of the text container View
//        textContainer.setBackgroundColor(color);


        // Lookup view for data population
        TextView evC = (TextView) convertView.findViewById(R.id.a);
        TextView typ = (TextView) convertView.findViewById(R.id.b);

        // Populate the data into the template view using the data object
        evC.setText("ev.č.: "+padak.getEvC());
        typ.setText("typ: "+padak.getTyp());



       // Log.v(TAG, "pozdarve adapteru" + post.getTitle().toString());
        // Return the completed view to render on screen
        return convertView;
    }
}