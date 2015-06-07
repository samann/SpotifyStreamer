package com.spencer.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 6/5/15.
 *
 * @author Spencer Amann
 */
public class TrackInfo implements Parcelable {
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
    private String imageUrl;
    private String trackUrl;

    public TrackInfo() {
        super();
    }

    public TrackInfo(String trackName, String imageUrl, String trackUri) {
        this.trackName = trackName;
        this.imageUrl = imageUrl;
        this.trackUrl = trackUri;
    }

    private TrackInfo(Parcel in) {
        trackName = in.readString();
        imageUrl = in.readString();
        trackUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trackName);
        dest.writeString(imageUrl);
        dest.writeString(trackUrl);
    }

    public String getTrackName() {
        return trackName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTrackUrl() { return trackUrl; }
}
