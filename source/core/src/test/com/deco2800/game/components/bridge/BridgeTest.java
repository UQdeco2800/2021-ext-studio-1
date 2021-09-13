package com.deco2800.game.components.bridge;

import org.junit.jupiter.api.Test;

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
    public void testremoveLane() {
        Bridge bridge = setUpBridge(1, 1, 3);
        assertEquals(3, bridge.getLanes().size());
        bridge.removeLane();
        assertEquals(2, bridge.getLanes().size());
        bridge.removeLane();
        assertEquals(1, bridge.getLanes().size());
        bridge.removeLane();
        assertEquals(0, bridge.getLanes().size());
    }
}