package me.zuichu.mp4coder.example.google.muxformats;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import me.zuichu.mp4coder.IsoFile;
import me.zuichu.mp4coder.boxes.iso14496.part14.ESDescriptorBox;
import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.builder.DefaultMp4Builder;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;
import me.zuichu.mp4coder.muxer.tracks.mjpeg.OneJpegPerIframe;
import me.zuichu.mp4coder.tools.Hex;
import me.zuichu.mp4coder.tools.Path;

public class MjpegTest {
    public static void main(String[] args) throws IOException {
        IsoFile isofile = new IsoFile("C:\\content\\bbb-small\\output_320x180-mjpeg.mp4");
        ESDescriptorBox esDescriptorBox = Path.getPath(isofile, "/moov[0]/trak[0]/mdia[0]/minf[0]/stbl[0]/stsd[0]/mp4v[0]/esds[0]");
        byte[] d = new byte[esDescriptorBox.getData().rewind().remaining()];
        esDescriptorBox.getData().get(d);
        System.err.println(Hex.encodeHex(d));

        Movie mRef = MovieCreator.build("C:\\content\\bbb-small\\output_320x180_150.mp4");
        Track refTrack = mRef.getTracks().get(0);

        File baseDir = new File("C:\\content\\bbb-small");
        File[] iFrameJpegs = baseDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        });
        Arrays.sort(iFrameJpegs);

        Movie mRes = new Movie();
        mRes.addTrack(new OneJpegPerIframe("iframes", iFrameJpegs, refTrack));

        new DefaultMp4Builder().build(mRes).writeContainer(new FileOutputStream("output-mjpeg.mp4").getChannel());
    }
}
