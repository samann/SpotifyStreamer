package com.spencer.spotifystreamer;

/**
 * Created on 6/5/15.
 *
 * @author Spencer Amann
 */
public class TrackInfo {
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

    public String getTrackName() {
        return trackName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTrackUrl() { return trackUrl; }
}
