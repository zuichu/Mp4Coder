package me.zuichu.mp4coder.streaming.extensions;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.zuichu.mp4coder.streaming.SampleExtension;

public class CompositionTimeSampleExtension implements SampleExtension {
    public static Map<Long, CompositionTimeSampleExtension> pool =
            Collections.synchronizedMap(new HashMap<Long, CompositionTimeSampleExtension>());
    private long ctts;

    public static CompositionTimeSampleExtension create(long offset) {
        CompositionTimeSampleExtension c = pool.get(offset);
        if (c == null) {
            c = new CompositionTimeSampleExtension();
            c.ctts = offset;
            pool.put(offset, c);
        }
        return c;
    }

    /**
     * This value provides the offset between decoding time and composition time. The offset is expressed as
     * signed long such that CT(n) = DT(n) + CTTS(n). This method is
     *
     * @return offset between decoding time and composition time.
     */
    public long getCompositionTimeOffset() {
        return ctts;
    }

    @Override
    public String toString() {
        return "ctts=" + ctts;
    }
}
