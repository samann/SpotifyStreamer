package com.spencer.spotifystreamer;

/**
 * Created on 6/5/15.
 *
 * @author Spencer Amann
 */
public class ArtistTopTen {
    private String trackName;
    private String imageUrl;

    public ArtistTopTen() {
        super();
    }

    public ArtistTopTen(String trackName, String imageUrl) {
        this.trackName = trackName;
        this.imageUrl = imageUrl;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
