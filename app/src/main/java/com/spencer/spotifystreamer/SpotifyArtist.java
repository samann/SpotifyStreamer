package com.spencer.spotifystreamer;

/**
 * Created by DroidOwl on 6/4/15.
 */
public class SpotifyArtist {
    private String name;
    private String imageUrl;

    public SpotifyArtist(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
