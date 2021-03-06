package com.spencer.spotifystreamer.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.spencer.spotifystreamer.R;
import com.spencer.spotifystreamer.activities.PlayerActivity;
import com.spencer.spotifystreamer.adapter.ArtistTopTenAdapter;
import com.spencer.spotifystreamer.model.TrackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    private static final String TAG = "top-ten-tracks";
    private ArrayList<TrackInfo> mTrackInfoList;
    private ArtistTopTenAdapter mArtistTopTenAdapter;
    private ListView mListView;
    public TopTenActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mTrackInfoList = savedInstanceState.getParcelableArrayList(getString(R.string.saved_track_list));
            bindView();
        } else {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top_ten, container, false);
        Intent intent = getActivity().getIntent();

            if (getArguments() != null) {
                String artistId = getArguments().getString("artistId");
                searchTopTen(artistId);
            } else if (intent.hasExtra("artistId")) {
                String artistId = intent.getStringExtra("artistId");
                searchTopTen(artistId);
            }



        mListView = (ListView) rootView.findViewById(R.id.top_ten_listview);

        mTrackInfoList = new ArrayList<>();
        bindView();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrackInfo trackInfo = mArtistTopTenAdapter.getItem(position);
                String trackName = trackInfo.getTrackName();
                String imageUrl = trackInfo.getImageUrl();
                String trackUrl = trackInfo.getTrackUrl();
                String artistName = trackInfo.getArtistName();
                Intent trackIntent = new Intent(getActivity(), PlayerActivity.class);

                trackIntent.putExtra("trackName", trackName);
                trackIntent.putExtra("imageUrl", imageUrl);
                trackIntent.putExtra("trackUrl", trackUrl);
                trackIntent.putExtra("artistName", artistName);

                startActivity(trackIntent);
            }
        });
        return rootView;
    }

    private void bindView() {
        mArtistTopTenAdapter = new ArtistTopTenAdapter(
                getActivity(),
                mTrackInfoList
        );
        if (mListView != null) {
            mListView.setAdapter(mArtistTopTenAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.saved_track_list), mTrackInfoList);
        Log.d("top-ten", "saved " + !(mTrackInfoList.isEmpty()));
        super.onSaveInstanceState(outState);
    }

    private void searchTopTen(String name) {
        new TopTenTask().execute(name);
    }

    private class TopTenTask extends AsyncTask<String, Void, Tracks> {

        @Override
        protected Tracks doInBackground(String... params) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService service = api.getService();
            Map<String, Object> country = new HashMap<>();
            country.put("country", Locale.getDefault().getCountry());
            try {
                return service.getArtistTopTrack(params[0], country);
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
            mArtistTopTenAdapter.clear();
            if (tracks == null) {
                Log.e(TAG, "tracks is null");
                return;
            }
            for (Track track : tracks.tracks) {
                try {
                    String url;
                    int width = track.album.images.get(0).width;
                    if (width > 300) {
                        url = track.album.images.get(1).url;
                    } else {
                        url = track.album.images.get(0).url;
                    }
                    mTrackInfoList.add(new TrackInfo(track.name, track.album.name, url, track.preview_url, track.artists.get(0).name));
                } catch (Exception e) {
                    mTrackInfoList.add(new TrackInfo(track.name, track.album.name, null, track.preview_url, track.artists.get(0).name));
                }
            }
            mArtistTopTenAdapter.addAll(mTrackInfoList);
        }
    }
}

