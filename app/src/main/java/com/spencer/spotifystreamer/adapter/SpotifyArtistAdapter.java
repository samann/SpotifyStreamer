package com.spencer.spotifystreamer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spencer.spotifystreamer.R;
import com.spencer.spotifystreamer.model.SpotifyArtist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DroidOwl on 6/4/15.
 *
 */
public class SpotifyArtistAdapter extends ArrayAdapter {

    private ArrayList<SpotifyArtist> mSpotifyArtists = new ArrayList<>();

    public SpotifyArtistAdapter(Context context, ArrayList<SpotifyArtist> artists) {
        super(context, 0, artists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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
        viewHolder.name.setText(mSpotifyArtists.get(position).getName());
        Picasso.with(getContext()).load(mSpotifyArtists.get(position).getImageUrl()).resize(150, 150).placeholder(R.mipmap.spotify_placeholder_image).into(viewHolder.image);
        return convertView;
    }

    public void addAll(ArrayList<SpotifyArtist> artists) {
        for (SpotifyArtist a : artists) {
            mSpotifyArtists.add(a);
        }
        setNotifyOnChange(true);
        this.notifyDataSetChanged();
    }

    @Override
    public void clear() {
        super.clear();
        mSpotifyArtists.clear();
    }

    private static class ViewHolder {
        TextView name;
        ImageView image;
    }
}
