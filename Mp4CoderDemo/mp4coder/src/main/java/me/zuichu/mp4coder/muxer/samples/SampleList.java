package me.zuichu.mp4coder.muxer.samples;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.muxer.RandomAccessSource;
import me.zuichu.mp4coder.muxer.Sample;
import me.zuichu.mp4coder.tools.Path;

import java.util.AbstractList;
import java.util.List;

/**
 * Creates a list of <code>ByteBuffer</code>s that represent the samples of a given track.
 */
public class SampleList extends AbstractList<Sample> {
    List<Sample> samples;


    public SampleList(long trackId, Container isofile, RandomAccessSource source) {

        if (Path.getPaths(isofile, "moov/mvex/trex").isEmpty()) {
            samples = new DefaultMp4SampleList(trackId, isofile, source);
        } else {
            samples = new FragmentedMp4SampleList(trackId, isofile, source);
        }
    }

    @Override
    public Sample get(int index) {
        return samples.get(index);
    }

    @Override
    public int size() {
        return samples.size();
    }

}
