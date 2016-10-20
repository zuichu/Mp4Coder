package me.zuichu.mp4coder.boxes.iso14496.part30;

import me.zuichu.mp4coder.support.AbstractBox;
import me.zuichu.mp4coder.tools.Utf8;
import me.zuichu.mp4coder.tools.IsoTypeReader;

import java.nio.ByteBuffer;

/**
 * Created by sannies on 04.12.2014.
 */
public class WebVTTConfigurationBox extends AbstractBox {
    public static final String TYPE = "vttC";

    String config = "";

    public WebVTTConfigurationBox() {
        super(TYPE);
    }

    @Override
    protected long getContentSize() {
        return Utf8.utf8StringLengthInBytes(config);
    }

    @Override
    protected void getContent(ByteBuffer byteBuffer) {
        byteBuffer.put(Utf8.convert(config));
    }

    @Override
    protected void _parseDetails(ByteBuffer content) {
        config = IsoTypeReader.readString(content, content.remaining());
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
