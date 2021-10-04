package com.deco2800.game.areas.terrain;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.deco2800.game.areas.terrain.TerrainComponent.TerrainOrientation;
import com.deco2800.game.components.CameraComponent;
import com.deco2800.game.components.bridge.Bridge;
import com.deco2800.game.components.bridge.Lane;
import com.deco2800.game.utils.math.RandomUtils;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

/** Factory for creating game terrains. */
public class TerrainFactory {
  private static final GridPoint2 MAP_SIZE = new GridPoint2(30, 30);
  private static final int TUFT_TILE_COUNT = 30;
  private static final int ROCK_TILE_COUNT = 30;

  private final OrthographicCamera camera;
  private final TerrainOrientation orientation;

  private Bridge rainbowBridge;

  /**
   * Create a terrain factory with Orthogonal orientation
   *
   * @param cameraComponent Camera to render terrains to. Must be ortographic.
   */
  public TerrainFactory(CameraComponent cameraComponent) {
    this(cameraComponent, TerrainOrientation.ORTHOGONAL);
  }

  /**
   * Create a terrain factory
   *
   * @param cameraComponent Camera to render terrains to. Must be orthographic.
   * @param orientation orientation to render terrain at
   */
  public TerrainFactory(CameraComponent cameraComponent, TerrainOrientation orientation) {
    this.camera = (OrthographicCamera) cameraComponent.getCamera();
    this.orientation = orientation;
  }

  /**
   * Create a terrain of the given type, using the orientation of the factory. This can be extended
   * to add additional game terrains.
   *
   * @param terrainType Terrain to create
   * @return Terrain component which renders the terrain
   */
  public TerrainComponent createTerrain(TerrainType terrainType) {
    ResourceService resourceService = ServiceLocator.getResourceService();
    switch (terrainType) {
      case RAINBOW_BRIDGE:
        TextureRegion star1 =
                new TextureRegion(resourceService.getAsset("images/terrain/star-blank.png", Texture.class));
        TextureRegion star2 =
                new TextureRegion(resourceService.getAsset("images/terrain/star-1.png", Texture.class));
        TextureRegion star3 =
                new TextureRegion(resourceService.getAsset("images/terrain/star-2.png", Texture.class));
        TextureRegion star4 =
                new TextureRegion(resourceService.getAsset("images/terrain/star-3.png", Texture.class));
        TextureRegion lane1 =
                new TextureRegion(resourceService.getAsset("images/terrain/color-1.png", Texture.class));
        TextureRegion lane2 =
                new TextureRegion(resourceService.getAsset("images/terrain/color-2.png", Texture.class));
        TextureRegion lane3 =
                new TextureRegion(resourceService.getAsset("images/terrain/color-3.png", Texture.class));
        TextureRegion lane4 =
                new TextureRegion(resourceService.getAsset("images/terrain/color-4.png", Texture.class));
        return createRainbowBridgeTerrain(
                0.5f,
                star1,
                star2,
                star3,
                star4,
                lane1,
                lane2,
                lane3,
                lane4
        );

      case FOREST_DEMO:
        TextureRegion orthoGrass =
                new TextureRegion(resourceService.getAsset("images/grass_1.png", Texture.class));
        TextureRegion orthoTuft =
                new TextureRegion(resourceService.getAsset("images/grass_2.png", Texture.class));
        TextureRegion orthoRocks =
                new TextureRegion(resourceService.getAsset("images/grass_3.png", Texture.class));
        return createForestDemoTerrain(0.5f, orthoGrass, orthoTuft, orthoRocks);
      case FOREST_DEMO_ISO:
        TextureRegion isoGrass =
                new TextureRegion(resourceService.getAsset("images/iso_grass_1.png", Texture.class));
        TextureRegion isoTuft =
                new TextureRegion(resourceService.getAsset("images/iso_grass_2.png", Texture.class));
        TextureRegion isoRocks =
                new TextureRegion(resourceService.getAsset("images/iso_grass_3.png", Texture.class));
        return createForestDemoTerrain(1f, isoGrass, isoTuft, isoRocks);
      case FOREST_DEMO_HEX:
        TextureRegion hexGrass =
                new TextureRegion(resourceService.getAsset("images/hex_grass_1.png", Texture.class));
        TextureRegion hexTuft =
                new TextureRegion(resourceService.getAsset("images/hex_grass_2.png", Texture.class));
        TextureRegion hexRocks =
                new TextureRegion(resourceService.getAsset("images/hex_grass_3.png", Texture.class));
        return createForestDemoTerrain(1f, hexGrass, hexTuft, hexRocks);
      default:
        return null;
    }
  }

  private TerrainComponent createForestDemoTerrain(
          float tileWorldSize, TextureRegion grass, TextureRegion grassTuft, TextureRegion rocks) {
    GridPoint2 tilePixelSize = new GridPoint2(grass.getRegionWidth(), grass.getRegionHeight());
    TiledMap tiledMap = createForestDemoTiles(tilePixelSize, grass, grassTuft, rocks);
    TiledMapRenderer renderer = createRenderer(tiledMap, tileWorldSize / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation, tileWorldSize);
  }

  private TerrainComponent createRainbowBridgeTerrain(
          float tileWorldSize,
          TextureRegion star1,
          TextureRegion star2,
          TextureRegion star3,
          TextureRegion star4,
          TextureRegion lane1,
          TextureRegion lane2,
          TextureRegion lane3,
          TextureRegion lane4
  ) {
    GridPoint2 tilePixelSize = new GridPoint2(star1.getRegionWidth(), star1.getRegionHeight());

    // Magic numbers, need to be set in a config file in future
    // offset - y coordinate to start drawing the bridge
    // width - width of a lane
    // laneCount - amount of lanes to generate
    // starPopulation - Number of stars to appear in the sky (based on probability, higher value = less stars)
    int offset = 6;
    int width = 3;
    int laneCount = 4;
    int starPopulation = 20;

    // Fills the tile of the screen, also creates an instance of Bridge
    TiledMap tiledMap = createRainbowBridgeTiles(
            tilePixelSize,
            offset,
            width,
            laneCount,
            starPopulation,
            star1,
            star2,
            star3,
            star4,
            lane1,
            lane2,
            lane3,
            lane4
    );

    TiledMapRenderer renderer = createRenderer(tiledMap, tileWorldSize / tilePixelSize.x);
    return new TerrainComponent(camera, tiledMap, renderer, orientation, tileWorldSize, rainbowBridge);
  }

  private TiledMapRenderer createRenderer(TiledMap tiledMap, float tileScale) {
    switch (orientation) {
      case ORTHOGONAL:
        return new OrthogonalTiledMapRenderer(tiledMap, tileScale);
      case ISOMETRIC:
        return new IsometricTiledMapRenderer(tiledMap, tileScale);
      case HEXAGONAL:
        return new HexagonalTiledMapRenderer(tiledMap, tileScale);
      default:
        return null;
    }
  }

  private TiledMap createForestDemoTiles(
          GridPoint2 tileSize, TextureRegion grass, TextureRegion grassTuft, TextureRegion rocks) {
    TiledMap tiledMap = new TiledMap();
    TerrainTile grassTile = new TerrainTile(grass);
    TerrainTile grassTuftTile = new TerrainTile(grassTuft);
    TerrainTile rockTile = new TerrainTile(rocks);
    TiledMapTileLayer layer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y, tileSize.x, tileSize.y);

    // Create base grass
    fillTiles(layer, MAP_SIZE, grassTile);

    // Add some grass and rocks
    fillTilesAtRandom(layer, MAP_SIZE, grassTuftTile, TUFT_TILE_COUNT);
    fillTilesAtRandom(layer, MAP_SIZE, rockTile, ROCK_TILE_COUNT);

    tiledMap.getLayers().add(layer);
    return tiledMap;
  }

  /**
   * Fills the screen with tiles that makes up the Ragnorak Racer Screen
   * @param tileSize
   * @param offset
   * @param width
   * @param laneCount
   * @param starPopulation
   * @param star1
   * @param star2
   * @param star3
   * @param star4
   * @param lane1
   * @param lane2
   * @param lane3
   * @param lane4
   * @return
   */
  private TiledMap createRainbowBridgeTiles(
          GridPoint2 tileSize,
          int offset,
          int width,
          int laneCount,
          int starPopulation,
          TextureRegion star1,
          TextureRegion star2,
          TextureRegion star3,
          TextureRegion star4,
          TextureRegion lane1,
          TextureRegion lane2,
          TextureRegion lane3,
          TextureRegion lane4

  ) {
    TiledMap tiledMap = new TiledMap();

    // Places a star tile in a list
    ArrayList<TerrainTile> starTiles = new ArrayList<>();
    // add duplicate star1(empty star tile) to the list to increase the probability that a star tile is empty
    for (int i = 0; i < starPopulation; i++) {
      starTiles.add(new TerrainTile(star1));
    }
    // add the rest of the star tile
    starTiles.add(new TerrainTile(star2));
    starTiles.add(new TerrainTile(star3));
    starTiles.add(new TerrainTile(star4));


    // Places a coloured tile in a List
    List<TerrainTile> bridgeTileColours = new ArrayList<>();
    bridgeTileColours.add(new TerrainTile(lane4));
    bridgeTileColours.add(new TerrainTile(lane3));
    bridgeTileColours.add(new TerrainTile(lane2));
    bridgeTileColours.add(new TerrainTile(lane1));

    TiledMapTileLayer layer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y, tileSize.x, tileSize.y);

    // randomly fills the sky with star tiles
    fillTilesRandomly(layer, MAP_SIZE, starTiles);

    // Create a bridge class to store information about the bridge
    Bridge bridge = new Bridge(offset, width);

    //Draw the lane and add the lane in the bridge class
    for (int i = 0; i < laneCount; i++) {
      bridge.createLane();
      Lane lane = bridge.getLastLane();
      fillTilesInRange(layer, bridgeTileColours.get(i), (int) lane.getBot(), (int) lane.getTop(), 0, MAP_SIZE.x);
    }

    tiledMap.getLayers().add(layer);
    this.rainbowBridge = bridge;
    return tiledMap;
  }

  /**
   * Fills a specific range of tiles using start and end x and y coordinates
   * @param layer
   * @param tile
   * @param y1
   * @param y2
   * @param x1
   * @param x2
   */
  private static void fillTilesInRange(TiledMapTileLayer layer, TerrainTile tile, int y1, int y2, int x1, int x2) {
    for (int x = x1; x < x2; x++) {
      for (int y = y1; y < y2 ; y++) {
        Cell cell = new Cell();
        cell.setTile(tile);
        layer.setCell(x, y, cell);
      }
    }
  }

  private static void fillTilesAtRandom(
          TiledMapTileLayer layer, GridPoint2 mapSize, TerrainTile tile, int amount) {
    GridPoint2 min = new GridPoint2(0, 0);
    GridPoint2 max = new GridPoint2(mapSize.x - 1, mapSize.y - 1);

    for (int i = 0; i < amount; i++) {
      GridPoint2 tilePos = RandomUtils.random(min, max);
      Cell cell = layer.getCell(tilePos.x, tilePos.y);
      cell.setTile(tile);
    }
  }

  private static void fillTiles(TiledMapTileLayer layer, GridPoint2 mapSize, TerrainTile tile) {
    for (int x = 0; x < mapSize.x; x++) {
      for (int y = 0; y < mapSize.y; y++) {
        Cell cell = new Cell();
        cell.setTile(tile);
        layer.setCell(x, y, cell);
      }
    }
  }

  /**
   * fills the sky with stars randomly
   * @param layer
   * @param mapSize
   * @param tiles
   */
  private static void fillTilesRandomly(TiledMapTileLayer layer, GridPoint2 mapSize, ArrayList<TerrainTile> tiles) {
    for (int x = 0; x < mapSize.x; x++) {
      for (int y = 0; y < mapSize.y; y++) {
        Cell cell = new Cell();
        int index = (int) (Math.random() * tiles.size());
        cell.setTile(tiles.get(index));
        layer.setCell(x, y, cell);
      }
    }
  }

  /**
   * This enum should contain the different terrains in your game, e.g. forest, cave, home, all with
   * the same oerientation. But for demonstration purposes, the base code has the same level in 3
   * different orientations.
   */
  public enum TerrainType {
    FOREST_DEMO,
    FOREST_DEMO_ISO,
    FOREST_DEMO_HEX,
    RAINBOW_BRIDGE
  }
}
