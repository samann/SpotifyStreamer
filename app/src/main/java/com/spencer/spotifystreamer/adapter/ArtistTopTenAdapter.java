package com.spencer.spotifystreamer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spencer.spotifystreamer.R;
import com.spencer.spotifystreamer.model.TrackInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created on 6/5/15.
 *
 * @author Spencer Amann
 */
public class ArtistTopTenAdapter extends ArrayAdapter<TrackInfo> {

    public ArtistTopTenAdapter(Context context, ArrayList<TrackInfo> topTenArrayList) {
        super(context, R.layout.track_list_item, topTenArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrackInfo trackInfo = getItem(position);
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
        viewHolder.trackNameView.setText(trackInfo.getTrackName());
        viewHolder.albumNameView.setText(trackInfo.getAlbumName());
        Picasso.with(getContext()).load(trackInfo.getImageUrl()).resize(150, 150).placeholder(R.mipmap.spotify_placeholder_image).into(viewHolder.trackImageView);
        return convertView;
    }

    private class ViewHolder {
        TextView trackNameView;
        TextView albumNameView;
        ImageView trackImageView;
    }
}
