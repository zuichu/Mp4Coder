package me.zuichu.mp4coder.example.google.muxformats;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.FileDataSourceImpl;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.tracks.DTSTrackImpl;

/**
 * Created by sannies on 2/12/14.
 */
public class DTSMuxExample {
    public static void main(String[] args) throws IOException {
        Movie movie = new Movie();
        Track track = new DTSTrackImpl(new FileDataSourceImpl("C:\\Users\\sannies\\Downloads\\Big_Dom_Thl_ENG_5.1_HD_Lossless_1510.dtshd"));
        movie.addTrack(track);

        DefaultMp4Builder builder = new DefaultMp4Builder();
        Container container = builder.build(movie);
        FileOutputStream fos = new FileOutputStream("c:\\dev\\isoparser-dtshd-test.mp4");
        FileChannel fc = fos.getChannel();
        container.writeContainer(fc);
        fos.close();
    }
}
