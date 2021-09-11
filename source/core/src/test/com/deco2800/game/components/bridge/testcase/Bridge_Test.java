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

    private InsnList lanes;
    Bridge_Test BT = new Bridge_Test();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("before class");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("before");
    }

    @Test
    public testBridge(int offset, int width)
    {
        System.out.println("test case width,lanes and offset");
        assertEquals(offset,BT.testgetOffset());
        assertEquals(lanes, Bridge.);
        assertEquals(width,Bridge.width());

    }

    @Test
    public double testgetOffset() {
        System.out.println("test case offset");
        assertEquals(offset, BT.testgetOffset());

        return 0;
    }

    @Test
    public List<Lane> getLanes() {
        System.out.println("test case offset");
        assertEquals(List, Bridge.listIterator());
    }

    @Test
    public Lane testgetLastLane() {
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