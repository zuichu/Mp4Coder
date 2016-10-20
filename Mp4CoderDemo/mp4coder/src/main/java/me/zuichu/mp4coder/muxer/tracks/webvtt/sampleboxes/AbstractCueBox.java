package me.zuichu.mp4coder.muxer.tracks.webvtt.sampleboxes;

import me.zuichu.mp4coder.Box;
import me.zuichu.mp4coder.tools.IsoTypeWriter;
import me.zuichu.mp4coder.IsoFile;
import me.zuichu.mp4coder.tools.Utf8;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

import static me.zuichu.mp4coder.tools.CastUtils.l2i;

public abstract class AbstractCueBox implements Box {
    String content = "";
    String type;

    public AbstractCueBox(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSize() {
        return 8 + Utf8.utf8StringLengthInBytes(content);
    }

    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        ByteBuffer header = ByteBuffer.allocate(l2i(getSize()));
        IsoTypeWriter.writeUInt32(header, getSize());
        header.put(IsoFile.fourCCtoBytes(getType()));
        header.put(Utf8.convert(content));
        writableByteChannel.write((ByteBuffer) header.rewind());
    }

    public String getType() {
        return type;
    }
}
