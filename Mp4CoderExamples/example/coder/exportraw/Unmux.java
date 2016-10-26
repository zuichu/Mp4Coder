package me.zuichu.mp4coder.example.coder.exportraw;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Collections;

import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;

public class Unmux {
    public static void main(String[] args) throws IOException {
        File dest = File.createTempFile("Unmux", "main");
        FileOutputStream fos = new FileOutputStream(dest);
        IOUtils.copy(new URL("http://org.mp4parser.s3.amazonaws.com/examples/Cosmos%20Laundromat%20small.mp4").openStream(), fos);
        fos.close();

        Movie m = MovieCreator.build(dest.getAbsolutePath());
        DefaultMp4Builder builder = new DefaultMp4Builder();
        for (Track track : m.getTracks()) {
            Movie singleTrackMovie = new Movie(Collections.singletonList(track));
            builder.build(singleTrackMovie).writeContainer(new RandomAccessFile(track.getHandler() + "_" + track.getTrackMetaData().getTrackId() + ".mp4", "rw").getChannel());
        }
    }
}
