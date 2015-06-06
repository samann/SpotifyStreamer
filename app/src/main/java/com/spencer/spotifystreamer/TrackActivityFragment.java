package com.spencer.spotifystreamer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class TrackActivityFragment extends Fragment {


    public TrackActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String trackName = intent.getStringExtra("trackName");
        String imageUrl = intent.getStringExtra("imageUrl");
        String trackUrl = intent.getStringExtra("trackUrl");
        View rootView = inflater.inflate(R.layout.fragment_track, container, false);

        Picasso.with(getActivity())
                .load(imageUrl)
                .placeholder(R.mipmap.place_holder_image)
                .into((ImageView) rootView
                        .findViewById(R.id.track_played_imageview));
        TextView trackText = (TextView) rootView.findViewById(R.id.track_name_textview);
        trackText.setText(trackName);
        
        return rootView;
    }
}
