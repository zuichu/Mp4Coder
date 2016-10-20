/*
 * Copyright 2012 Sebastian Annies, Hamburg
 *
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an AS IS BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.zuichu.mp4coder.muxer;

import me.zuichu.mp4coder.boxes.iso14496.part12.CompositionTimeToSample;
import me.zuichu.mp4coder.boxes.iso14496.part12.SampleDependencyTypeBox;
import me.zuichu.mp4coder.boxes.iso14496.part12.SubSampleInformationBox;
import me.zuichu.mp4coder.boxes.samplegrouping.GroupEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class AbstractTrack implements Track {
    String name;
    List<Edit> edits = new ArrayList<Edit>();
    Map<GroupEntry, long[]> sampleGroups = new HashMap<GroupEntry, long[]>();

    public AbstractTrack(String name) {
        this.name = name;
    }

    public List<CompositionTimeToSample.Entry> getCompositionTimeEntries() {
        return null;
    }

    public long[] getSyncSamples() {
        return null;
    }

    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return null;
    }

    public SubSampleInformationBox getSubsampleInformationBox() {
        return null;
    }

    public long getDuration() {
        long duration = 0;
        for (long delta : getSampleDurations()) {
            duration += delta;
        }
        return duration;
    }

    public String getName() {
        return this.name;
    }

    public List<Edit> getEdits() {
        return edits;
    }

    public Map<GroupEntry, long[]> getSampleGroups() {
        return sampleGroups;
    }
}
