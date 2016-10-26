package me.zuichu.mp4coder.example.google.stuff;


import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Properties;

import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Sample;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;


public class DumpAmf0TrackToPropertyFile {
    public static void main(String[] args) throws IOException {
        Movie movie = MovieCreator.build(DumpAmf0TrackToPropertyFile.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/example.f4v");


        for (Track track : movie.getTracks()) {
            if (track.getHandler().equals("data") ) {
                long time = 0;
                Iterator<Sample> samples = track.getSamples().iterator();
                Properties properties = new Properties();
                File f = File.createTempFile(DumpAmf0TrackToPropertyFile.class.getSimpleName(), "" + track.getTrackMetaData().getTrackId());
                for (long decodingTime : track.getSampleDurations()) {
                    ByteBuffer sample = samples.next().asByteBuffer();
                    byte[] sampleBytes = new byte[sample.limit()];
                    sample.reset();
                    sample.get(sampleBytes);
                    properties.put("" + time, new String(Base64.encodeBase64(sampleBytes, false, false)));
                    time += decodingTime;
                }
                FileOutputStream fos = new FileOutputStream(f);
                System.err.println(properties);
                properties.store(fos, "");

            }
        }
    }


}
