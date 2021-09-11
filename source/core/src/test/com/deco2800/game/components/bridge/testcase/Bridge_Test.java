package com.deco2800.game.components.bridge.testcase;

import com.deco2800.game.components.bridge.Bridge;
import com.deco2800.game.components.bridge.Lane;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Bridge_Test {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("before class");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("before");
    }


    @Test
    public void getOffsetTest() {
        System.out.println("test case offset");
        Bridge bridge1 = new Bridge(0, 1);
        assertEquals(0, bridge1.getOffset());

        Bridge bridge2 = new Bridge(1, 1);
        assertEquals(1, bridge2.getOffset());
    }

    @Test
    public void laneTest() {
        Bridge bridge = new Bridge(0, 1);
        assertEquals(true, bridge.getLanes().isEmpty());

        bridge.createLane();
        assertEquals(1, bridge.getLanes().size());

        if (bridge.getLanes().size() > 0) {
            assertEquals(true, Lane.class.isInstance(bridge.getLanes().get(0)));
        }
    }


    @Test
    public void testgetLastLane() {
        if (this.lanes.size() == 0) {
            throw new IllegalCallerException("Unable to get a lane from an empty list");
        } else {
            return this.lanes.get(this.lanes.size() - 1);
        }
    }

    /**
     * Adds a lane to the bridge
     */
    public void createLane() {
        Lane newLane;
        if (this.lanes.size() == 0) {
            newLane = new Lane(offset, offset + width);
        } else {
            int yCoordinate = this.lanes.get(this.lanes.size() - 1).getY2();
            newLane = new Lane(yCoordinate, yCoordinate + width);
        }
        lanes.add(newLane);
    }

    @Test
    public void testremoveLane() {
        if (this.lanes.size() > 0) {
            this.lanes.remove(this.lanes.size() - 1);
        }
    }

    @Test
    public Map<String, Integer> getBounds() {
        if (this.lanes.size() == 0) {
            throw new IllegalCallerException("Unable to get bridge bounds from a bridge with no lanes");
        } else {
            Map<String, Integer> bounds = new HashMap<>();
            bounds.put("top", this.getLastLane().getTop());
            bounds.put("bot", this.lanes.get(0).getBot());
            return bounds;
        }
    }

    private Lane getLastLane() {
        return null;
    }

}