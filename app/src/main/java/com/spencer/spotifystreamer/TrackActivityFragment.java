package com.spencer.spotifystreamer;

import android.app.Fragment;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class TrackActivityFragment extends Fragment {

    private Boolean isPlayingNow = false;
    public TrackActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String trackName = intent.getStringExtra("trackName");
        String imageUrl = intent.getStringExtra("imageUrl");
        String trackUrl = intent.getStringExtra("trackUrl");
        View rootView = inflater.inflate(R.layout.fragment_track, container, false);
        final Button playButton = (Button) rootView.findViewById(R.id.play_button);
        Picasso.with(getActivity())
                .load(imageUrl)
                .placeholder(R.mipmap.place_holder_image)
                .into((ImageView) rootView
                        .findViewById(R.id.track_played_imageview));
        TextView trackText = (TextView) rootView.findViewById(R.id.track_name_textview);
        trackText.setText(trackName);

        final MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        playButton.setClickable(false);

        try {
            mediaPlayer.setDataSource(trackUrl);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            //nop
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                playButton.setClickable(true);
                playButton.setText("PAUSE");
                mediaPlayer.start();
                isPlayingNow = true;

                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isPlayingNow) {
                            mediaPlayer.start();
                            playButton.setText("PAUSE");
                            isPlayingNow = true;
                        } else {
                            mediaPlayer.pause();
                            playButton.setText("PLAY");
                            isPlayingNow = false;
                        }
                    }
                });
            }
        });

        return rootView;
    }
}
