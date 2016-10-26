package me.zuichu.mp4coder.example.google.muxformats;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.FileDataSourceImpl;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.tracks.EC3TrackImpl;

/**
 *
 */
public class Ec3Example {
    public static void main(String[] args) throws IOException {
        EC3TrackImpl track = new EC3TrackImpl(new FileDataSourceImpl("C:\\Users\\sannies\\Downloads\\audio.ac3"));
        Movie m = new Movie();
        m.addTrack(track);
        DefaultMp4Builder mp4Builder = new DefaultMp4Builder();
        Container isoFile = mp4Builder.build(m);
        FileOutputStream fos = new FileOutputStream("output.mp4");
        FileChannel fc = fos.getChannel();
        isoFile.writeContainer(fc);
        fos.close();
    }
}
