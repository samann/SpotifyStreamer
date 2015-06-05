package com.spencer.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DroidOwl on 6/4/15.
 */
public class SpotifyArtistAdapter extends ArrayAdapter {

    public SpotifyArtistAdapter(Context context, ArrayList<SpotifyArtist> artists) {
        super(context, 0, artists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpotifyArtist spotifyArtist = (SpotifyArtist) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.search_list_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.spotify_item_textview);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.spotify_item_imageview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(spotifyArtist.getName());
        Picasso.with(getContext()).load(spotifyArtist.getImageUrl()).resize(150, 150).into(viewHolder.image);
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        ImageView image;
    }
}
