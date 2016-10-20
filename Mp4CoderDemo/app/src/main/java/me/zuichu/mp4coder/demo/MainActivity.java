package me.zuichu.mp4coder.demo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.FileDataSourceImpl;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.tracks.AACTrackImpl;
import me.zuichu.mp4coder.muxer.tracks.h264.H264TrackImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl(Environment.getExternalStorageDirectory() + "/test1.h264"));
            AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl(Environment.getExternalStorageDirectory() + "/test.aac"));
            Movie movie = new Movie();
            movie.addTrack(h264Track);
            movie.addTrack(aacTrack);
            Container mp4file = new DefaultMp4Builder().build(movie);
            FileChannel fc = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/encode.mp4")).getChannel();
            mp4file.writeContainer(fc);
            fc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
