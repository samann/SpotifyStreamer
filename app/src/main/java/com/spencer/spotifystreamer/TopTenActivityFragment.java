package com.spencer.spotifystreamer;

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
    private ArrayList<TrackInfo> mTrackInfos;
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
        mTrackInfos = new ArrayList<>();
        mArtistTopTenAdapter = new ArtistTopTenAdapter(
                getActivity(),
                mTrackInfos
        );
        ListView listView = (ListView) rootView.findViewById(R.id.top_ten_listview);
        listView.setAdapter(mArtistTopTenAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrackInfo trackInfo = mArtistTopTenAdapter.getItem(position);
                String trackName = trackInfo.getTrackName();
                String imageUrl = trackInfo.getImageUrl();
                String trackUrl = trackInfo.getTrackUrl();
                Intent trackIntent = new Intent(getActivity(), TrackActivity.class);
                trackIntent.putExtra("trackName", trackName);
                trackIntent.putExtra("imageUrl", imageUrl);
                trackIntent.putExtra("trackUrl", trackUrl);
                startActivity(trackIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

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
                    mTrackInfos.add(new TrackInfo(track.name, url, track.preview_url));
                } catch (Exception e) {
                    mTrackInfos.add(new TrackInfo(track.name, null, track.preview_url));
                }
            }
            mArtistTopTenAdapter.addAll(mTrackInfos);
        }
    }
}

