package me.zuichu.mp4coder.boxes.iso14496.part30;

import me.zuichu.mp4coder.BoxParser;
import me.zuichu.mp4coder.tools.Path;
import me.zuichu.mp4coder.boxes.sampleentry.AbstractSampleEntry;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Sample Entry for WebVTT subtitles.
 * <pre>
 * class WVTTSampleEntry() extends PlainTextSampleEntry (‘wvtt’){
 *   WebVTTConfigurationBox config;
 *   WebVTTSourceLabelBox label; // recommended
 *   MPEG4BitRateBox (); // optional
 * }
 * </pre>
 */
public class WebVTTSampleEntry extends AbstractSampleEntry {
    public static final String TYPE = "wvtt";

    public WebVTTSampleEntry() {
        super(TYPE);
    }

    @Override
    public void parse(ReadableByteChannel dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
        initContainer(dataSource, contentSize, boxParser);
    }

    @Override
    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        writableByteChannel.write(getHeader());
        writeContainer(writableByteChannel);
    }

    public WebVTTConfigurationBox getConfig() {
        return Path.getPath(this, "vttC");
    }

    public WebVTTSourceLabelBox getSourceLabel() {
        return Path.getPath(this, "vlab");
    }
}
