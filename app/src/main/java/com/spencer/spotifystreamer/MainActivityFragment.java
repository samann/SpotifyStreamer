package com.spencer.spotifystreamer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
    private ListView mListView;
    private String mSearchQueryText;

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

        mListView = (ListView) rootView.findViewById(R.id.search_list_view);
        mSpotifyArtists = new ArrayList<>();
        bindView();

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    mSearchQueryText = searchText.getText().toString();
                    searchForArtist(mSearchQueryText);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpotifyArtist artist = (SpotifyArtist) mArtistAdapter.getItem(position);
                String artistId = artist.getId();
                Intent intent = new Intent(getActivity(), TopTenActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, artistId)
                        .putExtra("artistName", artist.getName());

                startActivity(intent);
            }
        });
        return rootView;
    }

    private void bindView() {
        mArtistAdapter = new SpotifyArtistAdapter(getActivity(), mSpotifyArtists);

        mListView.setAdapter(mArtistAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSpotifyArtists != null) {
            outState.putParcelableArrayList(getString(R.string.saved_artist_list), mSpotifyArtists);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mSpotifyArtists = savedInstanceState.getParcelableArrayList(getString(R.string.saved_artist_list));
            bindView();
        }
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
                    int width = artists.get(i).images.get(0).width;
                    if (width > 300) {
                        url = artists.get(i).images.get(1).url;
                    } else {
                        url = artists.get(i).images.get(0).url;
                    }
                } catch (Exception e) {
                    //NOP
                    url = null;
                }
                mSpotifyArtists.add(new SpotifyArtist(artists.get(i).name, url, artists.get(i).id));
            }
            mArtistAdapter.addAll(mSpotifyArtists);
        }
    }
}
