package me.zuichu.mp4coder.example.google;


import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Properties;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultFragmenterImpl;
import me.zuichu.mp4coder.muxer.builder.FragmentedMp4Builder;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;
import me.zuichu.mp4coder.muxer.tracks.Amf0Track;

/**
 * Shows a simple use of the AMF0Track
 */
public class MuxVideoWithAmf0 {
    public static void main(String[] args) throws IOException {
        String videoFile = MuxVideoWithAmf0.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/example-sans-amf0.mp4";

        Movie video = MovieCreator.build(videoFile);

        Properties props = new Properties();
        props.load(MuxVideoWithAmf0.class.getResourceAsStream("/amf0track.properties"));
        HashMap<Long, byte[]> samples = new HashMap<Long, byte[]>();
        for (String key : props.stringPropertyNames()) {
            samples.put(Long.parseLong(key), Base64.decodeBase64(props.getProperty(key)));
        }
        Track amf0Track = new Amf0Track(samples);
        amf0Track.getTrackMetaData();
        video.addTrack(amf0Track);

        FragmentedMp4Builder fragmentedMp4Builder = new FragmentedMp4Builder();
        fragmentedMp4Builder.setFragmenter(new DefaultFragmenterImpl(2));

        Container out = fragmentedMp4Builder.build(video);
        FileOutputStream fos = new FileOutputStream(new File(String.format("output.mp4")));

        FileChannel fc = fos.getChannel();
        out.writeContainer(fc);

        fos.close();

    }
}
