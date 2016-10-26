package me.zuichu.mp4coder.streaming.output.mp4;


import java.util.LinkedList;
import java.util.List;

import me.zuichu.mp4coder.Box;
import me.zuichu.mp4coder.boxes.iso14496.part12.DataEntryUrlBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.DataInformationBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.DataReferenceBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.FileTypeBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.HandlerBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.HintMediaHeaderBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.MediaBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.MediaInformationBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.NullMediaHeaderBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.SampleSizeBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.SampleTableBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.SampleToChunkBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.SoundMediaHeaderBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.StaticChunkOffsetBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.SubtitleMediaHeaderBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.TimeToSampleBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.TrackBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.TrackHeaderBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.VideoMediaHeaderBox;
import me.zuichu.mp4coder.streaming.StreamingTrack;
import me.zuichu.mp4coder.streaming.extensions.DimensionTrackExtension;
import me.zuichu.mp4coder.streaming.extensions.TrackIdTrackExtension;

public abstract class DefaultBoxes {

    public Box createFtyp() {
        List<String> minorBrands = new LinkedList<String>();
        minorBrands.add("isom");
        minorBrands.add("iso2");
        minorBrands.add("avc1");
        minorBrands.add("iso6");
        minorBrands.add("mp41");
        return new FileTypeBox("isom", 512, minorBrands);
    }

    protected Box createMdiaHdlr(StreamingTrack streamingTrack) {
        HandlerBox hdlr = new HandlerBox();
        hdlr.setHandlerType(streamingTrack.getHandler());
        return hdlr;
    }

    protected Box createMdia(StreamingTrack streamingTrack) {
        MediaBox mdia = new MediaBox();
        mdia.addBox(createMdhd(streamingTrack));
        mdia.addBox(createMdiaHdlr(streamingTrack));
        mdia.addBox(createMinf(streamingTrack));
        return mdia;
    }

    abstract protected Box createMdhd(StreamingTrack streamingTrack);

    abstract protected Box createMvhd();

    protected Box createMinf(StreamingTrack streamingTrack) {
        MediaInformationBox minf = new MediaInformationBox();
        if (streamingTrack.getHandler().equals("vide")) {
            minf.addBox(new VideoMediaHeaderBox());
        } else if (streamingTrack.getHandler().equals("soun")) {
            minf.addBox(new SoundMediaHeaderBox());
        } else if (streamingTrack.getHandler().equals("text")) {
            minf.addBox(new NullMediaHeaderBox());
        } else if (streamingTrack.getHandler().equals("subt")) {
            minf.addBox(new SubtitleMediaHeaderBox());
        } else if (streamingTrack.getHandler().equals("hint")) {
            minf.addBox(new HintMediaHeaderBox());
        } else if (streamingTrack.getHandler().equals("sbtl")) {
            minf.addBox(new NullMediaHeaderBox());
        }
        minf.addBox(createDinf());
        minf.addBox(createStbl(streamingTrack));
        return minf;
    }

    protected Box createStbl(StreamingTrack streamingTrack) {
        SampleTableBox stbl = new SampleTableBox();

        stbl.addBox(streamingTrack.getSampleDescriptionBox());
        stbl.addBox(new TimeToSampleBox());
        stbl.addBox(new SampleToChunkBox());
        stbl.addBox(new SampleSizeBox());
        stbl.addBox(new StaticChunkOffsetBox());
        return stbl;
    }

    protected DataInformationBox createDinf() {
        DataInformationBox dinf = new DataInformationBox();
        DataReferenceBox dref = new DataReferenceBox();
        dinf.addBox(dref);
        DataEntryUrlBox url = new DataEntryUrlBox();
        url.setFlags(1);
        dref.addBox(url);
        return dinf;
    }

    protected Box createTrak(StreamingTrack streamingTrack) {
        TrackBox trackBox = new TrackBox();
        trackBox.addBox(createTkhd(streamingTrack));
        trackBox.addBox(createMdia(streamingTrack));
        return trackBox;
    }

    protected Box createTkhd(StreamingTrack streamingTrack) {
        TrackHeaderBox tkhd = new TrackHeaderBox();
        tkhd.setTrackId(streamingTrack.getTrackExtension(TrackIdTrackExtension.class).getTrackId());
        DimensionTrackExtension dte = streamingTrack.getTrackExtension(DimensionTrackExtension.class);
        if (dte != null) {
            tkhd.setHeight(dte.getHeight());
            tkhd.setWidth(dte.getWidth());
        }
        return tkhd;
    }

}
