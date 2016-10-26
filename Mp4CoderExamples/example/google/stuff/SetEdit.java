package me.zuichu.mp4coder.example.google.stuff;


import java.io.FileOutputStream;
import java.io.IOException;

import me.zuichu.mp4coder.muxer.Edit;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;

/**
 * Created by sannies on 28.01.2015.
 */
public class SetEdit {
    public static void main(String[] args) throws IOException {
        Movie m = MovieCreator.build("C:\\dev\\mp4parser\\examples\\src\\main\\resources\\1365070453555.mp4");
        for (Track track : m.getTracks()) {
            track.getEdits().clear();;
            track.getEdits().add(new Edit(0, 1, 1, 2.0));
        }
        new DefaultMp4Builder().build(m).writeContainer(new FileOutputStream("output.mp4").getChannel());
    }
}
