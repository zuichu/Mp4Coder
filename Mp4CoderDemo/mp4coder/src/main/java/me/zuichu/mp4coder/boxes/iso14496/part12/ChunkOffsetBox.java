package me.zuichu.mp4coder.boxes.iso14496.part12;

import me.zuichu.mp4coder.support.AbstractFullBox;

/**
 * Abstract Chunk Offset Box
 */
public abstract class ChunkOffsetBox extends AbstractFullBox {

    public ChunkOffsetBox(String type) {
        super(type);
    }

    public abstract long[] getChunkOffsets();

    public abstract void setChunkOffsets(long[] chunkOffsets);

    public String toString() {
        return this.getClass().getSimpleName() + "[entryCount=" + getChunkOffsets().length + "]";
    }

}
