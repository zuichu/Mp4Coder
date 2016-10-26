package me.zuichu.mp4coder.example.google.muxformats;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.FileDataSourceImpl;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.tracks.AACTrackImpl;
import me.zuichu.mp4coder.muxer.tracks.h264.H264TrackImpl;

/**
 * Created with IntelliJ IDEA.
 * User: magnus
 * Date: 2012-04-20
 * Time: 13:22
 * To change this template use File | Settings | File Templates.
 */
public class AacExample {
    public static void main(String[] args) throws IOException {

        AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl("C:\\content\\Cosmos Laundromat small.aac"));
        H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl("C:\\content\\Cosmos Laundromat small.264"));
        Movie m = new Movie();
        m.addTrack(aacTrack);
        m.addTrack(h264Track);
        DefaultMp4Builder mp4Builder = new DefaultMp4Builder();
        Container out = mp4Builder.build(m);
        FileOutputStream fos = new FileOutputStream("output.mp4");
        FileChannel fc = fos.getChannel();
        out.writeContainer(fc);

        fos.close();
    }
}
