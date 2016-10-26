package me.zuichu.mp4coder.example.google;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;
import me.zuichu.mp4coder.muxer.tracks.ReplaceSampleTrack;

/**
 * Replaces some samples.
 */
public class ReplaceSomeSamples {
    public static void main(String[] args) throws IOException {
        String audioEnglish = RemoveSomeSamplesExample.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/count-english-audio.mp4";
        Movie originalMovie = MovieCreator.build(audioEnglish);

        Track audio = originalMovie.getTracks().get(0);

        Movie nuMovie = new Movie();

        nuMovie.addTrack(new ReplaceSampleTrack(
                new ReplaceSampleTrack(
                        new ReplaceSampleTrack(
                                audio,
                                25, ByteBuffer.allocate(5)),
                        27, ByteBuffer.allocate(5)),
                29, ByteBuffer.allocate(5)));
        Container out = new DefaultMp4Builder().build(nuMovie);
        FileOutputStream fos = new FileOutputStream(new File("output.mp4"));
        FileChannel fc = fos.getChannel();
        out.writeContainer(fc);
        fos.close();
    }

}
