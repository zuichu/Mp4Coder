package me.zuichu.mp4coder.example.google.muxformats;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.FileDataSourceImpl;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.tracks.h264.H264TrackImpl;

/**
 * Created with IntelliJ IDEA.
 * User: magnus
 * Date: 2012-04-23
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public class H264Example {
    public static void main(String[] args) throws IOException {
        H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl("C:\\dev\\mp4parser\\isoparser\\src\\test\\resources\\count.h264"));
        //AACTrackImpl aacTrack = new AACTrackImpl(new FileInputStream("/home/sannies2/Downloads/lv.aac").getChannel());
        Movie m = new Movie();
        m.addTrack(h264Track);

        //m.addTrack(aacTrack);

        {
            Container out = new DefaultMp4Builder().build(m);
            FileOutputStream fos = new FileOutputStream(new File("h264_output.mp4"));
            FileChannel fc = fos.getChannel();
            out.writeContainer(fc);
            fos.close();
        }
    }
}