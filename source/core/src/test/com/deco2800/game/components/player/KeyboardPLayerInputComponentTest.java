package com.deco2800.game.components.player;

import com.badlogic.gdx.Input;
import com.deco2800.game.components.bridge.Bridge;
import com.deco2800.game.components.bridge.Lane;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.screens.RagnorakRacer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


public class KeyboardPLayerInputComponentTest {
    private Lane lanes ;
    public static Bridge bridge = new Bridge(6, 3);

    @BeforeAll
    public static void setUp() throws Exception {
        for (int i = 0; i < 4; i++){
            bridge.createLane();
        }
    }

    /**
     * This test module tests the movement moved up from the middle of lane 0 to the middle of lane 1.
     */
    @Test
    public void moveUpTest(){
        List<Lane> lanes = bridge.getLanes();
        Entity entity = new Entity();
        entity.setPosition(1, 3.8f);
        entity.setPosition(entity.getPosition().x, lanes.get(0).getMid() - (1.6f * (0 + 1)));
        assertEquals(5.4f, entity.getPosition().y);
    }

    /**
     * This test module tests the movement from player moved down from the middle of lane 3 to the middle of lane 2
     */
    @Test
    public void moveDownTest(){
        List<Lane> lanes = bridge.getLanes();
        Entity entity = new Entity();
        entity.setPosition(1, 8.2f);
        entity.setPosition(entity.getPosition().x, lanes.get(3 - 1).getMid() - (1.6f * (3 + 1)));
        assertEquals(6.6f, entity.getPosition().y);
    }

    /**
     * This test module tests the movement from player moved to the very top lane from any of the other lanes.
     */
    @Test
    public void moveTopTest(){
        List<Lane> lanes = bridge.getLanes();
        Entity entity = new Entity();
        entity.setPosition(1, 3.8f);
        entity.setPosition(entity.getPosition().x, lanes.get(3).getMid() - (1.6f * (4 + 1)));
        assertEquals(8.0f, entity.getPosition().y);
    }

    /**
     * This test module tests the movement from player moved to the very bottom lane from any of the other lanes.
     */
    @Test
    public void moveBottomTest(){
        List<Lane> lanes = bridge.getLanes();
        Entity entity = new Entity();
        entity.setPosition(1, 8.2f);
        entity.setPosition(entity.getPosition().x, lanes.get(0).getMid() - (1.6f * (1 + 1)));
        assertEquals(3.8f, entity.getPosition().y);
    }

}
