package me.zuichu.mp4coder.example.coder.syncsamples;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import me.zuichu.mp4coder.muxer.Movie;
import me.zuichu.mp4coder.muxer.Track;
import me.zuichu.mp4coder.muxer.container.mp4.MovieCreator;


public class ListSyncSamples {

    public static void main(String[] args) throws IOException {
        Map<String, long[]> ss = new LinkedHashMap<String, long[]>();
        int maxIndex = 0;
        for (String arg : args) {
            File f = new File(arg);
            if (f.exists()) {
                Movie m = MovieCreator.build(arg);
                for (Track track : m.getTracks()) {
                    if ("vide".equals(track.getHandler())) {
                        ss.put(f.getName() + track.getTrackMetaData().getTrackId(), track.getSyncSamples());
                        maxIndex = Math.max(maxIndex, track.getSyncSamples().length);
                    }
                }
            } else {
                System.err.println("Input file " + arg + " does not exist. Ignoring.");
            }
        }
        for (String s : ss.keySet()) {
            System.out.print(String.format("|%10s", s));
        }
        System.out.println("|");

        for (int i = 0; i < maxIndex; i++) {
            for (String s : ss.keySet()) {
                long[] syncSamples = ss.get(s);
                try {
                    System.out.print(String.format("|%10d", syncSamples[i]));
                } catch (IndexOutOfBoundsException e) {
                    System.out.print(String.format("|%10s", ""));
                }
            }
            System.out.println("|");
        }

    }
}
