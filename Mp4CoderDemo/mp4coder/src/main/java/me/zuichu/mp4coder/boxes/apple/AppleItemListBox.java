package me.zuichu.mp4coder.boxes.apple;

import me.zuichu.mp4coder.support.AbstractContainerBox;

/**
 * <h1>4cc = "{@value #TYPE}"</h1>
 * undocumented iTunes MetaData Box.
 */
public class AppleItemListBox extends AbstractContainerBox {
    public static final String TYPE = "ilst";

    public AppleItemListBox() {
        super(TYPE);
    }

}
