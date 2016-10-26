package me.zuichu.mp4coder.streaming.extensions;


import me.zuichu.mp4coder.streaming.TrackExtension;

/**
 * Gives a track a name.
 */
public class NameTrackExtension implements TrackExtension {
    private String name;

    public static NameTrackExtension create(String name) {
        NameTrackExtension nameTrackExtension = new NameTrackExtension();
        nameTrackExtension.name = name;
        return nameTrackExtension;
    }

    public String getName() {
        return name;
    }
}
