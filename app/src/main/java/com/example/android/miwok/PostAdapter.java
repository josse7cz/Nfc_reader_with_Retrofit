package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class PostAdapter extends ArrayAdapter<Post> {
    private static final String TAG = "MyActivity";

    public PostAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Post post = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list, parent, false);
        }
        // Lookup view for data population
        TextView tvId = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvId1 = (TextView) convertView.findViewById(R.id.tvName1);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvHome);
        TextView tvTitle1 = (TextView) convertView.findViewById(R.id.tvHome1);

        // Populate the data into the template view using the data object
        tvId.setText("id: " + post.getId());
        tvId1.setText("userId: " + post.getUserId());
        tvTitle.setText("title: " + post.getTitle());
        tvTitle1.setText(post.getText());
        // Log.v(TAG, "pozdarve adapteru" + post.getTitle().toString());
        // Return the completed view to render on screen
        return convertView;
    }
}