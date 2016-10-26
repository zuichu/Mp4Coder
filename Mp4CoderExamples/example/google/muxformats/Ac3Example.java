package me.zuichu.mp4coder.example.google.muxformats;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.FileDataSourceImpl;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.tracks.AC3TrackImpl;

/**
 *
 */
public class Ac3Example {
    public static void main(String[] args) throws IOException {
        Track ac3Track = new AC3TrackImpl(new FileDataSourceImpl("C:\\dev\\mp4parser\\examples\\src\\main\\resources\\count-english.ac3"));
        //Track ac3Track = new AC3TrackImplOld(new BufferedInputStream(new FileInputStream("C:\\dev\\mp4parser\\examples\\src\\main\\resources\\count-english.ac3")));
        Movie m = new Movie();
        m.addTrack(ac3Track);
        DefaultMp4Builder mp4Builder = new DefaultMp4Builder();
        Container out = mp4Builder.build(m);
        FileOutputStream fos = new FileOutputStream("output.mp4");
        FileChannel fc = fos.getChannel();
        out.writeContainer(fc);
        fos.close();
    }
}
