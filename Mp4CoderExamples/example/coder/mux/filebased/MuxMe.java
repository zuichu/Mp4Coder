package me.zuichu.mp4coder.example.coder.mux.filebased;


import java.io.FileOutputStream;
import java.io.IOException;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.FileDataSourceImpl;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.tracks.h264.H264TrackImpl;

/**
 * Created by sannies on 26.10.2015.
 */
public class MuxMe {
    public static void main(String[] args) throws IOException {
        H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl("C:\\dev\\mp4parser\\streaming\\src\\test\\resources\\org\\mp4parser\\streaming\\input\\h264\\tos.h264"));
        Movie m = new Movie();
        m.addTrack(h264Track);
        DefaultMp4Builder builder = new DefaultMp4Builder();
        Container c = builder.build(m);
        c.writeContainer(new FileOutputStream("output-old.mp4").getChannel());
    }
}
