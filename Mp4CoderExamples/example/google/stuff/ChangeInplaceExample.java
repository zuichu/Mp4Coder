package me.zuichu.mp4coder.example.google.stuff;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import me.zuichu.mp4coder.IsoFile;
import me.zuichu.mp4coder.boxes.iso14496.part12.HandlerBox;
import me.zuichu.mp4coder.tools.Path;

public class ChangeInplaceExample {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        FileChannel rChannel = new RandomAccessFile("/media/scratch/CSI.S12E21.HDTV.x264-LOL.mp4", "r").getChannel();
        FileChannel wChannel = new RandomAccessFile("/media/scratch/ThreeHundredFourtyThreeMB_2.mp4", "rw").getChannel();
        IsoFile isoFile = new IsoFile(rChannel);
        HandlerBox hdlr = Path.getPath(isoFile, "/moov[0]/trak[0]/mdia[0]/hdlr[0]");
        assert hdlr != null;
        hdlr.setName(RandomStringUtils.random(hdlr.getName().length()));
        isoFile.getBox(wChannel);
        rChannel.close();
        wChannel.close();
        System.err.println((System.currentTimeMillis() - start) + "ms");
        new File("/media/scratch/ThreeHundredFourtyThreeMB_2.mp4").delete();
    }
}
