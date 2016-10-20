package me.zuichu.mp4coder.muxer.tracks.webvtt.sampleboxes;

import me.zuichu.mp4coder.Box;
import me.zuichu.mp4coder.tools.IsoTypeWriter;
import me.zuichu.mp4coder.IsoFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class VTTEmptyCueBox implements Box {
    public VTTEmptyCueBox() {
    }

    public long getSize() {
        return 8;
    }

    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        ByteBuffer header = ByteBuffer.allocate(8);
        IsoTypeWriter.writeUInt32(header, getSize());
        header.put(IsoFile.fourCCtoBytes(getType()));
        writableByteChannel.write((ByteBuffer) header.rewind());
    }

    public String getType() {
        return "vtte";
    }
}
