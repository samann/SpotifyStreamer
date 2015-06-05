package com.spencer.spotifystreamer;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Pager;
import retrofit.RetrofitError;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private final String TAG = "spotify-streamer";
    private ArrayList<SpotifyArtist> mSpotifyArtists;
    private SpotifyArtistAdapter mArtistAdapter;

    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final EditText searchText = (EditText) rootView.findViewById(R.id.search_edittext);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    searchForArtist(searchText.getText().toString());
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });
        mSpotifyArtists = new ArrayList<>();
        mArtistAdapter = new SpotifyArtistAdapter(getActivity(), mSpotifyArtists);
        ListView listView = (ListView) rootView.findViewById(R.id.search_list_view);
        listView.setAdapter(mArtistAdapter);
        return rootView;
    }

    private void searchForArtist(String artistName) {
        new SpotifyTask().execute(artistName);
    }

    private class SpotifyTask extends AsyncTask<String, Void, List<Artist>> {

        @Override
        protected List<Artist> doInBackground(String... params) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotifyService = api.getService();
            List<Artist> artists;
            try {
                ArtistsPager artistPager = spotifyService.searchArtists(params[0]);
                Pager<Artist> pager = artistPager.artists;
                artists = pager.items;
            } catch (RetrofitError e) {
                SpotifyError error = SpotifyError.fromRetrofitError(e);
                Log.e(TAG, error.getMessage());
                return null;
            }


            return artists;
        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            super.onPostExecute(artists);
            mArtistAdapter.clear();
            String url;
            for (int i = 0; i < artists.size(); i++) {
                try {
                    url = artists.get(i).images.get(0).url;
                } catch (Exception e) {
                    //NOP
                    url = "https://avatars1.githubusercontent.com/u/251374?v=3&s=200";
                }
                mSpotifyArtists.add(new SpotifyArtist(artists.get(i).name, url));
            }
            mArtistAdapter.addAll(mSpotifyArtists);
        }
    }
}
