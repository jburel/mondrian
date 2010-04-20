package org.rosuda.mondrian.core;

import org.rosuda.mondrian.core.DragBox;

import java.awt.*;         // New event model.


public class DataEvent extends AWTEvent {

    public DataEvent(DragBox b) {
        super(b, DATA_EVENT);
    }


    public static final int DATA_EVENT = AWTEvent.RESERVED_ID_MAX + 3;
}