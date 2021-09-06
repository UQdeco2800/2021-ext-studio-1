package com.deco2800.game.areas;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.GridPoint3;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.components.bridge.Bridge;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.NPCFactory;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import com.deco2800.game.utils.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ForestGameArea extends GameArea {
  private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
  private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(1, 8);
  private static final GridPoint2 GHOST_SPAWN = new GridPoint2(2, 8);
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
          "images/new_player.png",
          "images/attack.png",
          "images/ghost.png",
          "images/resized dragon.png",
          "images/littlegreen.png"

  };

  private static final String[] rainbowBridgeAtlases = {
          "images/attack.atlas","images/ghost.atlas","images/littleGreen.atlas", "images/dragon.atlas"
  };
  private static final int NUM_GHOSTS = 6;
  private static final int NUM_DRAGON = 2;
  private static final int NUM_LITTLEGREEN = 10;
  private Entity player;

  private final TerrainFactory terrainFactory;
  private Bridge rainbowBridge;

  public ForestGameArea(TerrainFactory terrainFactory) {
    super();
    this.terrainFactory = terrainFactory;
  }



  @Override
  public void create() {
    loadAssets();
    displayUI();
    spawnTerrain();
    player = spawnPlayer();
    spawnGhosts();
    spawnDragon();
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

  public Bridge getRainbowBridge() {
    return this.rainbowBridge;
  }

  private Entity spawnPlayer() {
    Entity newPlayer = PlayerFactory.createPlayer();
    spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
    return newPlayer;
  }

  private void spawnGhosts() {

    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(10, 2);


    for (int i = 0; i < NUM_GHOSTS; i++) {
      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
      Entity ghost = NPCFactory.createGhost(player);
      spawnEntityAt(ghost, randomPos, true, true);
    }
  }

  private void spawnDragon() {

    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    for (int i = 0; i < NUM_DRAGON; i++) {
      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
      Entity dragon = NPCFactory.createGhostKing(player);
      spawnEntityAt(dragon, randomPos, true, true);
    }
  }

  private void spawnLittleGreen() {

    GridPoint2 minPos = new GridPoint2(0, 0);
    GridPoint2 maxPos = terrain.getMapBounds(0).sub(2, 2);

    for (int i = 0; i < NUM_LITTLEGREEN; i++) {
      GridPoint2 randomPos = RandomUtils.random(minPos, maxPos);
      Entity littleGreen = NPCFactory.createLittleGreen(player);
      spawnEntityAt(littleGreen, randomPos, true, true);
    }
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
