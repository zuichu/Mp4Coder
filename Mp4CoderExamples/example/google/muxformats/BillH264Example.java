package me.zuichu.mp4coder.example.google.muxformats;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.DataSource;
import me.zuichu.mp4coder.muxer.FileDataSourceImpl;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.tracks.AACTrackImpl;
import me.zuichu.mp4coder.muxer.tracks.h264.H264TrackImpl;

/**
 * user: sannies
 */
public class BillH264Example {
    public static void main(String[] args) throws IOException {
        DataSource video_file = new FileDataSourceImpl("c:/dev/mp4parser2/source_video.h264");
        DataSource audio_file = new FileDataSourceImpl("c:/dev/mp4parser2/source_audio.aac");
        int duration = 30472;
        H264TrackImpl h264Track = new H264TrackImpl(video_file, "eng", 15000, 1001); //supplied duration for the attached file was
        AACTrackImpl aacTrack = new AACTrackImpl(audio_file);
        Movie movie = new Movie();
        movie.addTrack(h264Track);
        //movie.addTrack(aacTrack);

        Container out = new DefaultMp4Builder().build(movie);
        FileOutputStream fos = new FileOutputStream(new File("c:/dev/mp4parser2/checkme.mp4"));
        out.writeContainer(fos.getChannel());
        fos.close();

    }
}