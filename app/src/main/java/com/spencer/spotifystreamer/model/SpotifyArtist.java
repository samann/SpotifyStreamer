package com.spencer.spotifystreamer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DroidOwl on 6/4/15.
 */
public class SpotifyArtist implements Parcelable {
    public static final Creator<SpotifyArtist> CREATOR = new Creator<SpotifyArtist>() {
        @Override
        public SpotifyArtist createFromParcel(Parcel in) {
            return new SpotifyArtist(in);
        }

        @Override
        public SpotifyArtist[] newArray(int size) {
            return new SpotifyArtist[size];
        }
    };
    private String name;
    private String imageUrl;
    private String id;

    public SpotifyArtist(String name, String imageUrl, String id) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    private SpotifyArtist(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(id);
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

    /**
     * Created on 6/5/15.
     *
     * @author Spencer Amann
     */
    public static class TrackInfo implements Parcelable {
        public static final Creator<TrackInfo> CREATOR = new Creator<TrackInfo>() {
            @Override
            public TrackInfo createFromParcel(Parcel in) {
                return new TrackInfo(in);
            }

            @Override
            public TrackInfo[] newArray(int size) {
                return new TrackInfo[size];
            }
        };
        private String trackName;
        private String albumName;
        private String imageUrl;
        private String trackUrl;
        private String artistName;

        public TrackInfo() {
            super();
        }

        public TrackInfo(String trackName, String albumName, String imageUrl, String trackUri, String artistName) {
            this.trackName = trackName;
            this.albumName = albumName;
            this.imageUrl = imageUrl;
            this.trackUrl = trackUri;
            this.artistName = artistName;
        }

        private TrackInfo(Parcel in) {
            trackName = in.readString();
            albumName = in.readString();
            imageUrl = in.readString();
            trackUrl = in.readString();
            artistName = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(trackName);
            dest.writeString(albumName);
            dest.writeString(imageUrl);
            dest.writeString(trackUrl);
            dest.writeString(artistName);
        }

        public String getTrackName() {
            return trackName;
        }

        public String getAlbumName() {
            return albumName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getTrackUrl() {
            return trackUrl;
        }

        public String getArtistName() {
            return artistName;
        }
    }
}
