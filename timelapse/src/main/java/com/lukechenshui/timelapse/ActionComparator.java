package com.lukechenshui.timelapse;

import java.util.Comparator;

/**
 * Created by luke on 9/14/16.
 */
public class ActionComparator implements Comparator<Action> {
    @Override
    public int compare(Action o1, Action o2) {
        return o1.startTime.compareTo(o2.startTime);
    }
}
