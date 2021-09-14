package com.deco2800.game.components.bridge;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BridgeTest {

    private Lane lanes ;

    public Bridge setUpBridge(int offset, int width, int laneAmount) {
        Bridge bridge = new Bridge(offset, width);
        for(int i = 0; i < laneAmount; i++) {
            bridge.createLane();
        }
        return bridge;
    }
    private static Bridge bridge;

    @BeforeAll
    public static void setUp() throws Exception {
        System.out.println("setup bridge");
        bridge = new Bridge(0, 1);
        bridge.createLane();
    }



    @Test
    public void getAttributeTest() {
        Bridge bridge1 = setUpBridge(1, 1, 1);
        Bridge bridge2 = setUpBridge(2, 1, 1);
        assertEquals(1, bridge1.getOffset());
        assertEquals(2, bridge2.getOffset());
    }

    @Test
    public void createLaneTest() {
        Bridge bridge = setUpBridge(1, 1, 0);
        assertEquals(true, bridge.getLanes().isEmpty());

        bridge.createLane();
        assertEquals(1, bridge.getLanes().size());

        bridge.createLane();
        assertEquals(2, bridge.getLanes().size());
    }

    @Test
    public void getLaneCoords() {
        Bridge bridge = setUpBridge(1, 3, 3);
        assertEquals(true, bridge.getLanes().get(0).getTop() > bridge.getLanes().get(0).getMid());
        assertEquals(true, bridge.getLanes().get(0).getMid() > bridge.getLanes().get(0).getBot());
        assertEquals(true, bridge.getLanes().get(1).getBot() == bridge.getLanes().get(0).getTop());
        assertEquals(true, bridge.getLanes().get(1).getMid() > bridge.getLanes().get(1).getBot());
        assertEquals(true, bridge.getLanes().get(1).getTop() > bridge.getLanes().get(1).getMid());
        assertEquals(true, bridge.getLanes().get(2).getBot() == bridge.getLanes().get(1).getTop());
        assertEquals(true, bridge.getLanes().get(2).getMid() > bridge.getLanes().get(2).getBot());
        assertEquals(true, bridge.getLanes().get(2).getTop() > bridge.getLanes().get(2).getMid());
    }

    @Test
    public void testRemoveLane() {
        Bridge bridge = setUpBridge(1, 1, 3);
        assertEquals(3, bridge.getLanes().size());
        bridge.removeLane();
        assertEquals(2, bridge.getLanes().size());
        bridge.removeLane();
        assertEquals(1, bridge.getLanes().size());
        bridge.removeLane();
        assertEquals(0, bridge.getLanes().size());
    }
    @Test
    public void getOffsetTest() {
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
        Lane lane = bridge.getLanes().get(bridge.getLanes().size() - 1);
        assertEquals(0, lane.getY1());
        assertEquals(1, lane.getY2());
    }

    @Test
    public void testgetBounds() {
        bridge.createLane();
        Map<String, Integer> bounds = new HashMap<>();
        bounds.put("top", bridge.getLanes().get(0).getTop());
        bounds.put("bot", bridge.getLanes().get(0).getBot());
        assertEquals(1, bounds.get("top"));
        assertEquals(0, bounds.get("bot"));

    }

    @Test
    public void testremoveLane() {
        if (bridge.getLanes().size() > 0) {
            bridge.getLanes().remove(bridge.getLanes().size() - 1);
            assertEquals(0, bridge.getLanes().size());
        }
    }

}

