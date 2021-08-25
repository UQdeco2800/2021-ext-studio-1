package com.deco2800.game.components.bridge;

import java.util.ArrayList;
import java.util.List;

/**
 * The Bridge class representing the rainbow bridge in Ragnorak Racer.
 * @author  Adrian Low
 *
 * A Bridge consists of multiple lane classes and defines the bounds and location of the bridge on screen
 */
public class Bridge {

    /**
     * Offsets the location of the bridge to be
     * Used to change where the bridge is drawn on-screen
     * */
    private int offset;

    /** A list of lanes on a bridge */
    private List<Lane> lanes;

    public Bridge(int offset) {
        this.offset = offset;
        this.lanes = new ArrayList<>();
    }

    /**
     * Returns the bridge offset
     * @return offset
     */
    public int getOffset() {
        return this.offset;
    }

    /**
     * Returns all lanes on the bridge
     * @return lanes
     */
    public List<Lane> getLanes() {
        return this.lanes;
    }

    /**
     * Returns a lane at a given bridge position
     * @param pos position on the bridge
     * @return  lane
     */
    public Lane getLane(int pos) {
        if (pos >= this.lanes.size() || pos < 0) {
            throw new IllegalArgumentException("pos out of bounds of bridge");
        } else {
            return this.lanes.get(pos);
        }
    }

    /**
     * Adds a Lane to the bridge
     * @param lane
     */
    public void addLane(Lane lane) {
        lanes.add(lane);
    }
}
