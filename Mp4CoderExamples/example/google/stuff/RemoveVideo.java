package me.zuichu.mp4coder.example.google.stuff;


import java.io.FileOutputStream;
import java.io.IOException;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;

/**
 * Created by sannies on 03.10.2014.
 */
public class RemoveVideo {
    public static void main(String[] args) throws IOException {
        Movie mWithVideo = MovieCreator.build("C:\\dev\\mp4parser\\examples\\src\\main\\resources\\davidappend\\v1.mp4");
        Movie mWOutVideo = new Movie();
        for (Track track : mWithVideo.getTracks()) {
            if (track.getHandler().equals("soun")) {
                mWOutVideo.addTrack(track);
            }
        }
        DefaultMp4Builder b = new DefaultMp4Builder();
        Container c = b.build(mWOutVideo);
        c.writeContainer(new FileOutputStream("output.mp4").getChannel());
    }
}
