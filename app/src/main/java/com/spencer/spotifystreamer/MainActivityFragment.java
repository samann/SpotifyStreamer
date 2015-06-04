package com.spencer.spotifystreamer;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.Collections;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_main)
public class MainActivityFragment extends Fragment {

    private ArrayAdapter<String> mArrayAdapter;

    private ArrayList<Artist> mArtists;

    public MainActivityFragment() {
    }

    @AfterInject
    protected void setup() {
        mArtists = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.search_list_item,
                R.id.search_list_view,
                new ArrayList<String>()
        );
        ListView listView = (ListView) rootView.findViewById(R.id.search_list_view);
        listView.setAdapter(mArrayAdapter);
        return rootView;
    }

    private class SpotifyTask extends AsyncTask<String, Void, Artist[]> {

        @Override
        protected Artist[] doInBackground(String... params) {

            return null;
        }

        @Override
        protected void onPostExecute(Artist[] artists) {
            super.onPostExecute(artists);
            Collections.addAll(mArtists, artists);
        }
    }
}
