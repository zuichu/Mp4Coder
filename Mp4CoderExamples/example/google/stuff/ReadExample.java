package me.zuichu.mp4coder.example.google.stuff;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import me.zuichu.mp4coder.IsoFile;
import me.zuichu.mp4coder.boxes.iso14496.part12.XmlBox;
import me.zuichu.mp4coder.tools.Path;

/**
 *
 */
public class ReadExample {
    public static void main(String[] args) throws IOException {
        FileChannel channel = new FileInputStream("/home/sannies2/Mission_Impossible_Ghost_Protocol_Feature_SDUV_480p_16avg192max.uvu").getChannel();
        IsoFile isoFile = new IsoFile(channel);
        //isoFile = new IsoFile(Channels.newChannel(new FileInputStream(this.filePath)));
        //Path path = new Path(isoFile);
        XmlBox xmlBox = Path.getPath(isoFile, "/moov/meta/xml ");
        assert xmlBox != null;
        String xml = xmlBox.getXml();
        System.err.println(xml);


    }


}
