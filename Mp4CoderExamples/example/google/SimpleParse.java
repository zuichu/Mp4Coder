package me.zuichu.mp4coder.example.google;


import java.io.IOException;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;

/**
 * Created by sannies on 24.07.2015.
 */
public class SimpleParse {
    public static void main(String[] args) throws IOException {
        Movie m = MovieCreator.build("C:\\Users\\sannies\\Downloads\\3ae39746-7e83-4653-860b-78a59e6ef474 (3).mp4");
        for (Track track : m.getTracks()) {
            System.err.print(track.getHandler());
            System.err.print(track.getSamples().size());
        }
    }
}
