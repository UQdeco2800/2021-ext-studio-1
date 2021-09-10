package com.deco2800.game.areas;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.components.bridge.Bridge;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.NPCFactory;
import com.deco2800.game.entities.factories.ObstacleFactory;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.screens.RagnorakRacer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.utils.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RainbowBridge extends GameArea {

    private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
    private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(1, 8);
    private static final float WALL_WIDTH = 0.1f;
    private static final int NUM_TREES = 7;
    private static final int NUM_OBSTACLES = 12;
    private static final int NUM_HEALTH_OBJECTS = 10;
    private static final int NUM_GHOSTS = 2;
    private static final int NUM_LittleGreen = 5;
    private static final String[] rainbowBridgeTextures = {
            "images/terrain/star-blank.png",
            "images/terrain/star-1.png",
            "images/terrain/star-2.png",
            "images/terrain/star-3.png",
            "images/terrain/water.png",
            "images/terrain/color-1.png",
            "images/terrain/color-2.png",
            "images/terrain/color-3.png",
            "images/terrain/color-4.png",
            "images/new_player_2021.png",
            "images/tree.png",
            "images/ghost_king.png",
            "images/ghost_1.png",
            "images/grass_1.png",
            "images/grass_2.png",
            "images/grass_3.png",
            "images/hex_grass_1.png",
            "images/hex_grass_2.png",
            "images/hex_grass_3.png",
            "images/iso_grass_1.png",
            "images/iso_grass_2.png",
            "images/iso_grass_3.png",
            "images/carObstacle.png",
            "images/stone.png",
            "images/FirstAidKit.png",
            "images/snake.png",
            "images/fire.png",
            "images/food.png",
            "images/dragon.png",
            "images/pixelghost.png",
            "images/pixelghost1.png",
            "images/littlegreen.png",
            "images/attack.png",
            "images/new_player.png"

    };

    private static final String[] rainbowBridgeAtlases = {

            "images/terrain_iso_grass.atlas", "images/ghost.atlas", "images/ghostKing.atlas","images/dragon.atlas","images/littleGreen.atlas", "images/attack.atlas"

    };

    private final TerrainFactory terrainFactory;
    private Bridge rainbowBridge;
    private Entity player;

    public RainbowBridge(TerrainFactory terrainFactory) {
        super();
        this.terrainFactory = terrainFactory;
    }



    @Override
    public void create() {
        loadAssets();
        displayUI();
        spawnTerrain();
        spawnObstables();
        spawnHealthObjects();
        player = spawnPlayer();
        spawnGhostKing();
        spawnLittleGreen();
    }

    private void displayUI() {
        Entity ui = new Entity();
        ui.addComponent(new GameAreaDisplay("Rainbow Bridge"));
        spawnEntity(ui);
    }


    private void spawnTerrain() {
        // Background terrain
        terrain = terrainFactory.createTerrain(TerrainType.RAINBOW_BRIDGE);
        spawnEntity(new Entity().addComponent(terrain));

        // Terrain walls
        float tileSize = terrain.getTileSize();
        GridPoint2 tileBounds = terrain.getMapBounds(0);
        Vector2 worldBounds = new Vector2(100, 100);

        // Returns the rainbowBridge from TerrainComponent
        this.rainbowBridge = terrain.getRainbowBridge();
    }

    private void spawnObstables() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < NUM_OBSTACLES; i++) {
            GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
            switch(i % 3) {
                case 0:
                    Entity car = ObstacleFactory.createCarObstacle();
                    spawnEntityAt(car, randomPos, true, false);
                    break;
                case 1:
                    Entity stone = ObstacleFactory.createStoneObstacle();
                    spawnEntityAt(stone, randomPos, true, false);
                    break;
                case 2:
                    Entity snake = ObstacleFactory.createSnake();
                    spawnEntityAt(snake, randomPos, true, false);
                    break;
                case 3:
                    Entity fire = ObstacleFactory.createFire();
                    spawnEntityAt(fire, randomPos, true, false);
                    break;
            }
        }
    }

    private void spawnHealthObjects() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < NUM_HEALTH_OBJECTS; i++) {
            GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
            switch(i % 2) {
                case 0:
                    Entity food = ObstacleFactory.createFood();
                    spawnEntityAt(food, randomPos, true, false);
                    break;
                case 1:
                    Entity firstAid = ObstacleFactory.createFirstAidKit();
                    spawnEntityAt(firstAid, randomPos, true, false);
                    break;
            }
        }
    }

    private Entity spawnPlayer() {
        Entity newPlayer = PlayerFactory.createPlayer();
        spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
        return newPlayer;
    }

    private void spawnGhosts() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < NUM_GHOSTS; i++) {
            GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
            Entity ghost = NPCFactory.createGhost(player);
            if(randomPos != PLAYER_SPAWN){
                spawnEntityAt(ghost, randomPos, true, true);
            }
        }
    }

    private void spawnLittleGreen() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        for (int i = 0; i < NUM_LittleGreen; i++) {
            GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
            Entity littleGreen = NPCFactory.createLittleGreen(player);
            spawnEntityAt(littleGreen, randomPos, true, true);
        }
    }

    private void spawnGhostKing() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

        GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
        Entity ghostKing = NPCFactory.createGhostKing(player);
        spawnEntityAt(ghostKing, randomPos, true, true);
    }

    public Bridge getRainbowBridge() {
        return this.rainbowBridge;
    }


    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(rainbowBridgeTextures);
        resourceService.loadTextureAtlases(rainbowBridgeAtlases);

        while (!resourceService.loadForMillis(10)) {
            // This could be upgraded to a loading screen
            logger.info("Loading... {}%", resourceService.getProgress());
        }
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(rainbowBridgeTextures);
        resourceService.unloadAssets(rainbowBridgeAtlases);

    }

    @Override
    public void dispose() {
        super.dispose();
        this.unloadAssets();
    }
}
