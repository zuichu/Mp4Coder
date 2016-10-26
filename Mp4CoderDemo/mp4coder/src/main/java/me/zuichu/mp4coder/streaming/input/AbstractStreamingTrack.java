package me.zuichu.mp4coder.streaming.input;


import java.util.HashMap;

import me.zuichu.mp4coder.boxes.iso14496.part12.TrackHeaderBox;
import me.zuichu.mp4coder.streaming.StreamingTrack;
import me.zuichu.mp4coder.streaming.TrackExtension;
import me.zuichu.mp4coder.streaming.output.SampleSink;

public abstract class AbstractStreamingTrack implements StreamingTrack {
    protected TrackHeaderBox tkhd;
    protected HashMap<Class<? extends TrackExtension>, TrackExtension> trackExtensions = new HashMap<Class<? extends TrackExtension>, TrackExtension>();

    protected SampleSink sampleSink;

    public AbstractStreamingTrack() {
        tkhd = new TrackHeaderBox();
        tkhd.setTrackId(1);
    }

    public void setSampleSink(SampleSink sampleSink) {
        this.sampleSink = sampleSink;
    }


    public <T extends TrackExtension> T getTrackExtension(Class<T> clazz) {
        return (T) trackExtensions.get(clazz);
    }

    public void addTrackExtension(TrackExtension trackExtension) {

        trackExtensions.put(trackExtension.getClass(), trackExtension);
    }

    public void removeTrackExtension(Class<? extends TrackExtension> clazz) {
        trackExtensions.remove(clazz);
    }
}
