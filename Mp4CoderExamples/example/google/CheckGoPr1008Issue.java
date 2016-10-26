package me.zuichu.mp4coder.example.google;


import java.io.IOException;

import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;

/**
 * Created by sannies on 18.02.2015.
 */
public class CheckGoPr1008Issue {
    public static void main(String[] args) throws IOException {
        Movie m = MovieCreator.build("C:\\Users\\sannies\\Downloads\\GOPR1008.MP4");
        for (Track track : m.getTracks()) {
            System.err.println(track);
        }
    }
}
