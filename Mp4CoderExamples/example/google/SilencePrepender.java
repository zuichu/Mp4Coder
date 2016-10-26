package me.zuichu.mp4coder.example.google;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;
import me.zuichu.mp4coder.muxer.tracks.AppendTrack;
import me.zuichu.mp4coder.muxer.tracks.SilenceTrackImpl;

public class SilencePrepender {
    public static void main(String[] args) throws IOException {

        Movie audioMovie = MovieCreator.build("/home/sannies/scm/svn/mp4parser/silence/sample.mp4");


        Movie result = new Movie();
        Track audio = audioMovie.getTracks().get(0);

        Track silence = new SilenceTrackImpl(audio, 2000);

        result.addTrack(new AppendTrack(silence, audio));

        Container isoFile = new DefaultMp4Builder().build(result);

        FileChannel fc = new RandomAccessFile(String.format("output.mp4"), "rw").getChannel();
        fc.position(0);
        isoFile.writeContainer(fc);
        fc.close();

    }
}
