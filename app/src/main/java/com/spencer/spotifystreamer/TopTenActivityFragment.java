package com.spencer.spotifystreamer;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.RetrofitError;

/**
 * A placeholder fragment containing a simple view.
 */
public class TopTenActivityFragment extends Fragment {

    private ArrayList<ArtistTopTen> mArtistTopTens;
    private ArtistTopTenAdapter mArtistTopTenAdapter;

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
        mArtistTopTens = new ArrayList<>();
        mArtistTopTenAdapter = new ArtistTopTenAdapter(
                getActivity(),
                mArtistTopTens
        );
        ListView listView = (ListView) rootView.findViewById(R.id.top_ten_listview);
        listView.setAdapter(mArtistTopTenAdapter);
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
            try {
                Tracks tracks = service.getArtistTopTrack(params[0]);
                return tracks;
            }
            catch (RetrofitError e) {
                SpotifyError error = SpotifyError.fromRetrofitError(e);
                Log.e("track error", error.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Tracks tracks) {
            super.onPostExecute(tracks);
            for (Track track : tracks.tracks) {
                try {
                    mArtistTopTens.add(new ArtistTopTen(track.name, track.album.images.get(0).url));
                } catch (Exception e) {
                    mArtistTopTens.add(new ArtistTopTen(track.name, "https://avatars1.githubusercontent.com/u/251374?v=3&s=200"));
                }
            }
            mArtistTopTenAdapter.addAll(mArtistTopTens);
        }
    }
}

