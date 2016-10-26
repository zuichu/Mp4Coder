package me.zuichu.mp4coder.example.google;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.builder.FragmentedMp4Builder;
import me.zuichu.mp4coder.muxer.builder.SyncSampleIntersectFinderImpl;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;

/**
 * Muxes 2 audio tracks with a video track.
 */
public class MuxMp4SourcesExample {
    public static void main(String[] args) throws IOException {

        String audioDeutsch = MuxMp4SourcesExample.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/count-deutsch-audio.mp4";
        String audioEnglish = MuxMp4SourcesExample.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/count-english-audio.mp4";
        String video = MuxMp4SourcesExample.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/count-video.mp4";


        Movie countVideo = MovieCreator.build(video);
        Movie countAudioDeutsch = MovieCreator.build(audioDeutsch);
        Movie countAudioEnglish = MovieCreator.build(audioEnglish);

        Track audioTrackDeutsch = countAudioDeutsch.getTracks().get(0);
        audioTrackDeutsch.getTrackMetaData().setLanguage("deu");
        Track audioTrackEnglish = countAudioEnglish.getTracks().get(0);
        audioTrackEnglish.getTrackMetaData().setLanguage("eng");

        countVideo.addTrack(audioTrackDeutsch);
        countVideo.addTrack(audioTrackEnglish);

        {
            Container out = new DefaultMp4Builder().build(countVideo);
            FileOutputStream fos = new FileOutputStream(new File("output.mp4"));
            out.writeContainer(fos.getChannel());
            fos.close();
        }
        {
            FragmentedMp4Builder fragmentedMp4Builder = new FragmentedMp4Builder();
            fragmentedMp4Builder.setFragmenter(new SyncSampleIntersectFinderImpl(countVideo, null, -1));
            Container out = fragmentedMp4Builder.build(countVideo);
            FileOutputStream fos = new FileOutputStream(new File("output-frag.mp4"));
            out.writeContainer(fos.getChannel());
            fos.close();
        }
    }

}
