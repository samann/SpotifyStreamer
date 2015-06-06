package com.spencer.spotifystreamer;

/**
 * Created by DroidOwl on 6/4/15.
 */
public class SpotifyArtist {
    private String name;
    private String imageUrl;
    private String id;

    public SpotifyArtist(String name, String imageUrl, String id) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = id;
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
}
