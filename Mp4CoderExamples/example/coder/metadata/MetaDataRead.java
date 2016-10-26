package me.zuichu.mp4coder.example.coder.metadata;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.zuichu.mp4coder.IsoFile;
import me.zuichu.mp4coder.boxes.apple.AppleNameBox;
import me.zuichu.mp4coder.tools.Path;

/**
 * Change metadata and make sure chunkoffsets are corrected.
 */
public class MetaDataRead {


    public static void main(String[] args) throws IOException {
        MetaDataRead cmd = new MetaDataRead();
        String xml = cmd.read("C:\\content\\Mobile_H264.mp4");
        System.err.println(xml);
    }

    public String read(String videoFilePath) throws IOException {

        File videoFile = new File(videoFilePath);
        if (!videoFile.exists()) {
            throw new FileNotFoundException("File " + videoFilePath + " not exists");
        }

        if (!videoFile.canRead()) {
            throw new IllegalStateException("No read permissions to file " + videoFilePath);
        }
        IsoFile isoFile = new IsoFile(new FileInputStream(videoFilePath).getChannel());

        AppleNameBox nam = Path.getPath(isoFile, "/moov[0]/udta[0]/meta[0]/ilst/©nam");
        String xml = nam.getValue();
        isoFile.close();
        return xml;
    }
}
