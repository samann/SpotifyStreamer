package com.spencer.spotifystreamer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created on 6/5/15.
 *
 * @author Spencer Amann
 */
public class ArtistTopTenAdapter extends ArrayAdapter<ArtistTopTen> {

    public ArtistTopTenAdapter(Context context, ArrayList<ArtistTopTen> topTenArrayList) {
        super(context, 0, topTenArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return super.getView(position, convertView, parent);
    }

    private class ViewHolder {
        TextView trackNameView;
        ImageView trackImageView;
    }
}
