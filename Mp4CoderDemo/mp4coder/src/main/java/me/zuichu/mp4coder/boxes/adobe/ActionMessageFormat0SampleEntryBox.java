package me.zuichu.mp4coder.boxes.adobe;

import me.zuichu.mp4coder.tools.IsoTypeWriter;
import me.zuichu.mp4coder.BoxParser;
import me.zuichu.mp4coder.boxes.sampleentry.AbstractSampleEntry;
import me.zuichu.mp4coder.tools.IsoTypeReader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * <h1>4cc = "{@value #TYPE}"</h1>
 * Sample Entry as used for Action Message Format tracks.
 */
public class ActionMessageFormat0SampleEntryBox extends AbstractSampleEntry {
    public static final String TYPE = "amf0";

    public ActionMessageFormat0SampleEntryBox() {
        super(TYPE);
    }

    @Override
    public void parse(ReadableByteChannel dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(8);
        dataSource.read(bb);
        bb.position(6);// ignore 6 reserved bytes;
        dataReferenceIndex = IsoTypeReader.readUInt16(bb);
        initContainer(dataSource, contentSize - 8, boxParser);
    }

    @Override
    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        writableByteChannel.write(getHeader());
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.position(6);
        IsoTypeWriter.writeUInt16(bb, dataReferenceIndex);
        writableByteChannel.write((ByteBuffer) bb.rewind());
        writeContainer(writableByteChannel);
    }

    @Override
    public long getSize() {
        long s = getContainerSize();
        long t = 8; // bytes to container start
        return s + t + ((largeBox || (s + t) >= (1L << 32)) ? 16 : 8);
    }


}
