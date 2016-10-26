package me.zuichu.mp4coder.example.google;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;
import me.zuichu.mp4coder.muxer.tracks.AppendTrack;
import me.zuichu.mp4coder.muxer.tracks.ClippedTrack;

/**
 * Removes some samples in the middle
 */
public class RemoveSomeSamplesExample {
    public static void main(String[] args) throws IOException {
        String audioEnglish = RemoveSomeSamplesExample.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/count-english-audio.mp4";
        Movie originalMovie = MovieCreator.build(audioEnglish);

        Track audio = originalMovie.getTracks().get(0);

        Movie nuMovie = new Movie();
        nuMovie.addTrack(new AppendTrack(new ClippedTrack(audio, 0, 10), new ClippedTrack(audio, 100, audio.getSamples().size())));

        Container out = new DefaultMp4Builder().build(nuMovie);
        FileOutputStream fos = new FileOutputStream(new File("output.mp4"));
        out.writeContainer(fos.getChannel());
        fos.close();
    }

}
