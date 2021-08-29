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
        TextureRegion starBlank =
                new TextureRegion(resourceService.getAsset("images/terrain/star-blank.png", Texture.class));
        TextureRegion star1 =
                new TextureRegion(resourceService.getAsset("images/terrain/star-1.png", Texture.class));
        TextureRegion star2 =
                new TextureRegion(resourceService.getAsset("images/terrain/star-2.png", Texture.class));
        TextureRegion star3 =
                new TextureRegion(resourceService.getAsset("images/terrain/star-3.png", Texture.class));

        ArrayList<TextureRegion> stars = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
          stars.add(starBlank);
      }

        stars.add(star1);
        stars.add(star2);
        stars.add(star3);

        TextureRegion water =
                new TextureRegion(resourceService.getAsset("images/terrain/water.png", Texture.class));
        TextureRegion color1 =
                new TextureRegion(resourceService.getAsset("images/terrain/color-1.png", Texture.class));
        TextureRegion color2 =
                new TextureRegion(resourceService.getAsset("images/terrain/color-2.png", Texture.class));
        TextureRegion color3 =
                new TextureRegion(resourceService.getAsset("images/terrain/color-3.png", Texture.class));
        TextureRegion color4 =
                new TextureRegion(resourceService.getAsset("images/terrain/color-4.png", Texture.class));
        return createRainbowBridgeTerrain(0.5f, stars, water, color1, color2, color3, color4);

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
          ArrayList<TextureRegion> stars,
          TextureRegion water,
          TextureRegion color1,
          TextureRegion color2,
          TextureRegion color3,
          TextureRegion color4
  ) {
    GridPoint2 tilePixelSize = new GridPoint2(stars.get(1).getRegionWidth(), stars.get(1).getRegionHeight());

    // Magic numbers, need to be set in a config file in future
    // offset - y coordinate to start drawing the bridge
    // width - width of a lane
    int offset = 6;
    int width = 3;
    int laneCount = 4;

    // Fills the tile of the screen, also creates an instance of Bridge
    TiledMap tiledMap = createRainbowBridgeTiles(
            tilePixelSize,
            offset,
            width,
            laneCount,
            stars,
            color1,
            color2,
            color3,
            color4
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
   * @param stars
   * @param color1
   * @param color2
   * @param color3
   * @param color4
   * @return
   */
  private TiledMap createRainbowBridgeTiles(
          GridPoint2 tileSize,
          int offset,
          int width,
          int laneCount,
          ArrayList<TextureRegion> stars,
          TextureRegion color1,
          TextureRegion color2,
          TextureRegion color3,
          TextureRegion color4

  ) {
    TiledMap tiledMap = new TiledMap();
    ArrayList<TerrainTile> starTiles = new ArrayList<>();
    for (TextureRegion starTexture : stars) {
        starTiles.add(new TerrainTile(starTexture));
    }

    // Places a coloured tile in a List
    List<TerrainTile> bridgeTileColours = new ArrayList<>();
    bridgeTileColours.add(new TerrainTile(color4));
    bridgeTileColours.add(new TerrainTile(color3));
    bridgeTileColours.add(new TerrainTile(color2));
    bridgeTileColours.add(new TerrainTile(color1));

    TiledMapTileLayer layer = new TiledMapTileLayer(MAP_SIZE.x, MAP_SIZE.y, tileSize.x, tileSize.y);

    fillTilesRandomly(layer, MAP_SIZE, starTiles);

    Bridge bridge = new Bridge(offset, width);
    for (int i = 0; i < laneCount; i++) {
      bridge.createLane();
      Lane lane = bridge.getLastLane();
      fillTilesInRange(layer, bridgeTileColours.get(i), lane.getBot(), lane.getTop(), 0, MAP_SIZE.x);
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
