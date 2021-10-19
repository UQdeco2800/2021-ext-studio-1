package com.deco2800.game.components.player;

import com.badlogic.gdx.utils.Array;
import com.deco2800.game.entities.Entity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class PlayerActionsTest {
    private PlayerActions playerActions;
    private boolean attackTrigger = false;
    private int attackCount = 0;

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
     void findNearestTargetsTest1() {
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

    @Test
    void findNearestTargetsTest2() {
        float minDstEnemy = 1.8f;
        float dst = 0;
        Entity entityFind = new Entity();
        entity.setPosition(0, 0);
        entity1.setPosition(2, 0);
        entities1.add(entity1);
        for (Entity en : entities1) {
            dst = entity.getPosition().dst(en.getPosition());
            if (minDstEnemy > dst) {
                entityFind = en;
            }
        }
        assertFalse(minDstEnemy > dst);
        assertNull(entityFind.getType());
    }

    @Test
     void findNearestTargetsTest3() {
        float minDstEnemy = 1.8f;
        float dst = 0;
        Entity entityFind = new Entity();
        entity.setPosition(0, 0);
        entity2.setPosition(1, 0);
        entities2.add(entity2);
        for (Entity en : entities2) {
            dst = entity.getPosition().dst(en.getPosition());
            if (minDstEnemy > dst) {
                entityFind = en;
            }
        }
        assertTrue(minDstEnemy > dst);
        assertEquals("GHOSTKING", entityFind.getType().toString());
    }

    @Test
     void findNearestTargetsTest4() {
        float minDstEnemy = 1.8f;
        float dst = 0;
        Entity entityFind = new Entity();
        entity.setPosition(0, 0);
        entity2.setPosition(8, 0);
        entities2.add(entity2);
        for (Entity en : entities2) {
            dst = entity.getPosition().dst(en.getPosition());
            if (minDstEnemy > dst) {
                entityFind = en;
            }
        }
        assertFalse(minDstEnemy > dst);
        assertNull(entityFind.getType());
    }

    @Test
     void findNearestTargetsTest5() {
        float minDstObstacle = 1.8f;
        float dst = 0;
        Entity entityFind = new Entity();
        entity.setPosition(0, 0);
        entity3.setPosition(1, 0);
        entities3.add(entity3);
        for (Entity en : entities3) {
            dst = entity.getPosition().dst(en.getPosition());
            if (minDstObstacle > dst) {
                entityFind = en;
            }
        }
        assertTrue(minDstObstacle > dst);
        assertEquals("OBSTACLE", entityFind.getType().toString());
    }

    @Test
     void findNearestTargetsTest6() {
        float minDstObstacle = 1.8f;
        float dst = 0;
        Entity entityFind = new Entity();
        entity.setPosition(0, 0);
        entity3.setPosition(5, 0);
        entities3.add(entity3);
        for (Entity en : entities3) {
            dst = entity.getPosition().dst(en.getPosition());
            if (minDstObstacle > dst) {
                entityFind = en;
            }
        }
        assertFalse(minDstObstacle > dst);
        assertNull(entityFind.getType());
    }

    @Test
     void findNearestTargetsTest7() {
        float minDstObstacle = 1.8f;
        float dst = 0;
        Entity entityFind = new Entity();
        entity.setPosition(0, 0);
        entity4.setPosition(1, 0);
        entities4.add(entity4);
        for (Entity en : entities4) {
            dst = entity.getPosition().dst(en.getPosition());
            if (minDstObstacle > dst) {
                entityFind = en;
            }
        }
        assertTrue(minDstObstacle > dst);
        assertEquals("BUFF", entityFind.getType().toString());
    }

    @Test
     void findNearestTargetsTest8() {
        float minDstObstacle = 1.8f;
        float dst = 0;
        Entity entityFind = new Entity();
        entity.setPosition(0, 0);
        entity4.setPosition(4, 0);
        entities4.add(entity4);
        for (Entity en : entities4) {
            dst = entity.getPosition().dst(en.getPosition());
            if (minDstObstacle > dst) {
                entityFind = en;
            }
        }
        assertFalse(minDstObstacle > dst);
        assertNull(entityFind.getType());
    }

    @Test
    public void attackFrequencyTest1() {
        if(attackTrigger) {
            attackCount += 1;
        }
        assertFalse(attackTrigger);
        assertEquals(0, attackCount);
    }

    @Test
    public void attackFrequencyTest2() {
        if (attackCount > 150 || !attackTrigger) {
            attackTrigger = true;
            if(attackTrigger) {
                attackCount += 1;
            }
        }
        assertTrue(attackTrigger);
        assertEquals(1, attackCount);
    }

    @Test
    public void attackFrequencyTest3() {
        if (attackCount > 150 || !attackTrigger) {
            attackTrigger = true;
            if(attackTrigger) {
                attackCount += 1;
            }
        }
        if (attackCount > 150 || !attackTrigger) {
            if(attackTrigger) {
                attackCount += 100;
            }
        }
        assertTrue(attackTrigger);
        assertEquals(1, attackCount);
    }

    @Test
    public void attackFrequencyTest4() {
        if (attackCount > 150 || !attackTrigger) {
            attackTrigger = true;
            while (true) {
                attackCount += 1;
                if (attackCount == 150) {
                    break;
                }
            }
        }
        assertTrue(attackTrigger);
        assertEquals(150, attackCount);
    }

    @Test
    public void attackFrequencyTest5() {
        if (attackCount > 150 || !attackTrigger) {
            attackTrigger = true;
            while (true) {
                attackCount += 1;
                if (attackCount > 150) {
                    break;
                }
            }
        }
        assertTrue(attackTrigger);
        assertEquals(151, attackCount);
    }

    @Test
    public void attackFrequencyTest6() {
        if (attackCount > 150 || !attackTrigger) {
            attackTrigger = true;
            while (true) {
                attackCount += 1;
                if (attackCount > 150) {
                    break;
                }
            }
        }
        attackCount = 0;
        assertTrue(attackTrigger);
        assertEquals(0, attackCount);
    }

    @Test
    public void attackFrequencyTest7() {
        if (attackCount > 150 || !attackTrigger) {
            attackTrigger = true;
            while (true) {
                attackCount += 1;
                if (attackCount > 150) {
                    break;
                }
            }
        }
        if (attackCount > 150 || !attackTrigger) {
            attackTrigger = false;
        }
        assertFalse(attackTrigger);
        assertEquals(151, attackCount);
    }

    @Test
    public void attackFrequencyTest8() {
        if (attackCount > 150 || !attackTrigger) {
            attackTrigger = true;
            while (true) {
                attackCount += 1;
                if (attackCount > 150) {
                    break;
                }
            }
        }
        if (attackCount > 150) {
            attackCount += 1000;
        }
        assertTrue(attackTrigger);
        assertEquals(1151, attackCount);
    }

    @Test
    public void attackFrequencyTest9() {
        if (attackCount > 150 || !attackTrigger) {
            attackTrigger = true;
            while (true) {
                attackCount += 1;
                if (attackCount > 150) {
                    break;
                }
            }
        }
        if (!attackTrigger) {
            attackCount += 1000;
        }
        assertTrue(attackTrigger);
        assertEquals(151, attackCount);
    }
}
