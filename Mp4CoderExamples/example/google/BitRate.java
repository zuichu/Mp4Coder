package me.zuichu.mp4coder.example.google;


import java.io.File;
import java.io.IOException;

import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;

/**
 * Created by sannies on 02.05.2015.
 */
public class BitRate {
    public static void main(String[] args) throws IOException {
        Movie m = MovieCreator.build("c:\\content\\big_buck_bunny_1080p_h264-2min-handbraked.mp4");
        double movieDuration = 0;
        for (Track track : m.getTracks()) {
            movieDuration = Math.max((double) track.getDuration() / track.getTrackMetaData().getTimescale(), movieDuration);
        }
        // We got the full duration in seconds
        System.err.println("Bitrate in bit/s: " +
                (new File("c:\\content\\big_buck_bunny_1080p_h264-2min-handbraked.mp4").length() * 8 /movieDuration));
    }
}
