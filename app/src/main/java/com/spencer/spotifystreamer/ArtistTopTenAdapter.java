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
 * Created on 6/5/15.
 *
 * @author Spencer Amann
 */
public class ArtistTopTenAdapter extends ArrayAdapter<TrackInfo> {

    public ArtistTopTenAdapter(Context context, ArrayList<TrackInfo> topTenArrayList) {
        super(context, 0, topTenArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrackInfo track = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.track_list_item, parent, false);
            viewHolder.trackNameView = (TextView) convertView.findViewById(R.id.track_item_textview);
            viewHolder.albumNameView = (TextView) convertView.findViewById(R.id.track_album_item_textview);
            viewHolder.trackImageView = (ImageView) convertView.findViewById(R.id.track_item_imageview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.trackNameView.setText(track.getTrackName());
        viewHolder.albumNameView.setText(track.getAlbumName());
        Picasso.with(getContext()).load(track.getImageUrl()).resize(150, 150).placeholder(R.mipmap.spotify_placeholder_image).into(viewHolder.trackImageView);
        return convertView;
    }

    private class ViewHolder {
        TextView trackNameView;
        TextView albumNameView;
        ImageView trackImageView;
    }
}
