package me.zuichu.mp4coder.boxes.sampleentry;

import me.zuichu.mp4coder.Container;
import me.zuichu.mp4coder.ParsableBox;

/**
 * Created by sannies on 30.05.13.
 */
public interface SampleEntry extends ParsableBox, Container {
    int getDataReferenceIndex();

    void setDataReferenceIndex(int dataReferenceIndex);
}
