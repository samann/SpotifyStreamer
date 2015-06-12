package com.spencer.spotifystreamer.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.spencer.spotifystreamer.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends Fragment {

    private Boolean isPlayingNow = false;
    private android.os.Handler mHandler = new android.os.Handler();
    private MediaPlayer mMediaPlayer;

    public PlayerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        String trackUrl = intent.getStringExtra("trackUrl");
        View rootView = inflater.inflate(R.layout.fragment_track, container, false);
        final Button playButton = (Button) rootView.findViewById(R.id.play_button);
        final SeekBar seekBar = (SeekBar) rootView.findViewById(R.id.track_seekbar);
        Picasso.with(getActivity())
                .load(imageUrl)
                .placeholder(R.mipmap.spotify_placeholder_image)
                .into((ImageView) rootView
                        .findViewById(R.id.track_played_imageview));

        startMediaPlayer(trackUrl, playButton, seekBar);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    private void startMediaPlayer(String trackUrl, final Button playButton, final SeekBar seekBar) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        playButton.setClickable(false);

        try {
            mMediaPlayer.setDataSource(trackUrl);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("Track Player", e.getMessage());
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {

                playButton.setClickable(true);
                playButton.setText("PAUSE");
                mp.start();
                seekBar.setMax(mp.getDuration());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMediaPlayer != null) {
                            int currentPosition = mp.getCurrentPosition();
                            seekBar.setProgress(currentPosition);
                            mHandler.postDelayed(this, 1000);
                        }
                    }

                });
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mp.seekTo(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });


                isPlayingNow = true;

                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isPlayingNow) {
                            mMediaPlayer.start();
                            playButton.setText("PAUSE");
                            isPlayingNow = true;
                        } else {
                            mMediaPlayer.pause();
                            playButton.setText("PLAY");
                            isPlayingNow = false;
                        }
                    }
                });
            }
        });
    }
}
