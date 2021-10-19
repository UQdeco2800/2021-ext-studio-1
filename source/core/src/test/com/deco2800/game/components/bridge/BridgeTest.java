package com.deco2800.game.components.bridge;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * This code was created by @Sheershak and was helped by @Adrian to fix some bugs! This is a unit testing class for
 * Bridge.java and Lane.java and provides optimal testing of these classes. There are a total of 9 tests, and we made
 * sure that each test is passing hence making sure our Bridge adn Lane classes are working efficiently.
*/
public class BridgeTest {

    /**
     * Here we include two private instances Lane and bridge that we will use for testing Bridge and Lane classes
     */
    private Lane lanes ;
    private static Bridge bridge;

    /**
     *This method is called before everything else and is responsible for setting up the bridge and testing the lane
     * class.
     * @throws Exception
     */
    @BeforeAll
    public static void setUp() throws Exception {
        System.out.println("setup bridge");
        bridge = new Bridge(0, 1);
        bridge.createLane();
    }
    /**
     * This is the class responsible for setting up the bridge and hence its x and y coordinates with the number of
     * lanes.
     * @param offset
     * @param width
     * @param laneAmount
     * @return
     */
    public Bridge setUpBridge(int offset, int width, int laneAmount) {
        Bridge bridge = new Bridge(offset, width);
        for(int i = 0; i < laneAmount; i++) {
            bridge.createLane();
        }
        return bridge;
    }

    /**
     * 1. This Test module tests the x and y coordinates of the lane and creates 2 new lanes
     */
    @Test
     void getAttributeTest() {
        Bridge bridge1 = setUpBridge(1, 1, 1);
        Bridge bridge2 = setUpBridge(2, 1, 1);
        assertEquals(1, bridge1.getOffset());
        assertEquals(2, bridge2.getOffset());
    }

    /**
     * 2. This Test module tests the lanes by creating 2 new lanes at different instances of time.
     */
    @Test
     void createLaneTest() {
        Bridge bridge = setUpBridge(1, 1, 0);
        assertEquals(true, bridge.getLanes().isEmpty());

        bridge.createLane();
        assertEquals(1, bridge.getLanes().size());

        bridge.createLane();
        assertEquals(2, bridge.getLanes().size());
    }

    /**
     * 3. This Test module checks whether the lane created are properly overlapping with one another and thus there is
     * no overlapping of the lanes in any given coordinates.
     */
    @Test
    void getLaneCoords() {
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

    /**
     *4. This Test module tests the successful removal of the lanes created in the createLaneTest().
     */
    @Test
     void testRemoveLane() {
        Bridge bridge = setUpBridge(1, 1, 3);
        assertEquals(3, bridge.getLanes().size());
        bridge.removeLane();
        assertEquals(2, bridge.getLanes().size());
        bridge.removeLane();
        assertEquals(1, bridge.getLanes().size());
        bridge.removeLane();
        assertEquals(0, bridge.getLanes().size());
    }

    /**
     * 5. This Test module tests the x and y coordinates in a similar fashion as in getAttributeTest().
     */
    @Test
     void getOffsetTest() {
        Bridge bridge1 = new Bridge(0, 1);
        assertEquals(0, bridge1.getOffset());

        Bridge bridge2 = new Bridge(1, 1);
        assertEquals(1, bridge2.getOffset());
    }

    /**
     * 6. This Test module tests the lanes created and also that atleast one lane is created during the gameplay.
     */
    @Test
     void laneTest() {
        Bridge bridge = new Bridge(0, 1);
        assertEquals(true, bridge.getLanes().isEmpty());

        bridge.createLane();
        assertEquals(1, bridge.getLanes().size());

        if (bridge.getLanes().size() > 0) {
            assertEquals(true, Lane.class.isInstance(bridge.getLanes().get(0)));
        }
    }

    /**
     * 7. This Test module tests that the lanes are correctly getting created in the vertical direction.
     */
    @Test
     void testGetLastLane() {
        Bridge bridge = setUpBridge(1, 1, 3);
        Lane lane = bridge.getLastLane();
        assertEquals(true, Lane.class.isInstance(lane));
    }

    /**
     * 8. This Test module tests the top and bottom of the lane is getting created in a proper fashion.
     */
    @Test
     void testGetBounds() {
        bridge.createLane();
        Map<String, Integer> bounds = new HashMap<>();
        bounds.put("top", bridge.getLanes().get(0).getTop());
        bounds.put("bot", bridge.getLanes().get(0).getBot());
        assertEquals(1, bounds.get("top"));
        assertEquals(0, bounds.get("bot"));

    }

    /**
     * 9. This Test module tests the removal of the lane successfully upon creation in a similar fashion as in
     * testRemoveLane()
     */
    @Test
     void testRemoveLane1() {
        if (bridge.getLanes().size() > 0) {
            bridge.getLanes().remove(bridge.getLanes().size() - 1);
            assertEquals(0, bridge.getLanes().size());
        }
    }

}

