package com.deco2800.game.components.player;

import com.badlogic.gdx.utils.Array;
import com.deco2800.game.entities.Entity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerActionsTest {
    private PlayerActions playerActions;

    private static Entity entity = new Entity();
    private Entity entity1 = new Entity(Entity.Type.GHOST);
    private Entity entity2 = new Entity(Entity.Type.GHOSTKING);
    private Entity entity3 = new Entity(Entity.Type.OBSTACLE);
    private Entity entity4 = new Entity(Entity.Type.BUFF);

    private Array<Entity> entities1 = new Array<>(1);
    private Array<Entity> entities2 = new Array<>(1);
    private Array<Entity> entities3 = new Array<>(1);
    private Array<Entity> entities4 = new Array<>(1);

    @Test
    public void findNearestTargetsTest1() throws NullPointerException{
        float minDstEnemy = 1.8f;
        float dst = 0;
        Entity entityFind = new Entity();
        entity.setPosition(0, 0);
        entity1.setPosition(1, 0);
        entities1.add(entity1);
        for (Entity en : entities1) {
            dst = entity.getPosition().dst(en.getPosition());
            if (minDstEnemy > dst) {
                entityFind = en;
            }
        }
        assertTrue(minDstEnemy > dst);
        assertEquals("GHOST", entityFind.getType().toString());
    }
}
