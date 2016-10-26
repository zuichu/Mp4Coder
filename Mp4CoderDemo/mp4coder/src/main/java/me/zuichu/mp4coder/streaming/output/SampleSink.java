package me.zuichu.mp4coder.streaming.output;


import java.io.Closeable;
import java.io.IOException;

import me.zuichu.mp4coder.streaming.StreamingSample;
import me.zuichu.mp4coder.streaming.StreamingTrack;

/**
 * Controls the creation of media files.
 *
 * @see FragmentedMp4Writer
 * @see StreamingTrack#setSampleSink(SampleSink)
 */
public interface SampleSink extends Closeable {
    /**
     * Free all resources blocked and interrupts the process of
     * writing the output. An implementation should flush all samples
     * that have not yet been written and write the file footer -
     * if exists - before actually freeing the resources.
     *
     * @throws IOException if closing fails
     */
    void close() throws IOException;

    /**
     * Adds a samples to the SampleSink. This might or might not cause writing the sample any output stream or channel.
     * Once this method is called the <code>StreamingTrack</code> must be ready and accept calls to any method.
     *
     * @throws IOException if writing (or reading) fails.
     */
    void acceptSample(StreamingSample streamingSample, StreamingTrack streamingTrack) throws IOException;
}
