package me.zuichu.mp4coder.example.google;


import java.io.FileOutputStream;
import java.io.IOException;

import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;
import me.zuichu.mp4coder.support.Matrix;

/**
 * Created by sannies on 01.02.2015.
 */
public class Rotate {
    public static void main(String[] args) throws IOException {

        String f1 = AppendExample.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/1365070268951.mp4";

        Movie inMovie = MovieCreator.build(f1);
        inMovie.setMatrix(Matrix.ROTATE_90);

        new DefaultMp4Builder().build(inMovie).writeContainer(new FileOutputStream("output.mp4").getChannel());
    }
}
