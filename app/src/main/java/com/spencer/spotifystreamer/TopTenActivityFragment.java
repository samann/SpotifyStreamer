package com.spencer.spotifystreamer;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * A placeholder fragment containing a simple view.
 */
public class TopTenActivityFragment extends Fragment {

    public TopTenActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_top_ten, container, false);
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String name = intent.getExtras().getString(Intent.EXTRA_TEXT);
            searchTopTen(name);
        }
        return rootView;
    }

    private void searchTopTen(String name) {
        new TopTenTask().execute(name);
    }

    private class TopTenTask extends AsyncTask<String, Void, Tracks> {

        @Override
        protected Tracks doInBackground(String... params) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService service = api.getService();
            Tracks tracks = service.getArtistTopTrack(params[0]);
            return tracks;
        }

        @Override
        protected void onPostExecute(Tracks tracks) {
            super.onPostExecute(tracks);

        }
    }
}

