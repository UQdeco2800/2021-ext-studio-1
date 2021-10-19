package com.deco2800.game.areas;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.tasks.MovementTask;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.areas.terrain.TerrainFactory.TerrainType;
import com.deco2800.game.components.bridge.Bridge;
import com.deco2800.game.components.tasks.MovementTask;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.factories.NPCFactory;
import com.deco2800.game.entities.factories.ObstacleFactory;
import com.deco2800.game.entities.factories.PlayerFactory;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gamearea.GameAreaDisplay;
import org.slf4j.Logger;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;
import com.deco2800.game.components.bridge.Lane;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;


public class RainbowBridge extends GameArea {

    private static final Logger logger = LoggerFactory.getLogger(ForestGameArea.class);
    private static final GridPoint2 PLAYER_SPAWN = new GridPoint2(1, 7);
    private static final int NUM_OBSTACLES = 12;
    private static final int NUM_HEALTH_OBJECTS = 10;
    private static final int NUM_COLLECTABLES = 10;
    private static final int NUM_MONSTER = 10;
    private static final int MAX_CONTENT_POSITION = 120;
    private static int musicSign = 0;
    
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
            "images/RunesGate.gif",
            "images/stone.png",
            "images/FirstAidKit.png",
            "images/snake.png",
            "images/thunderCloud.gif",
            "images/fire.png",
            "images/food.png",
            "images/axe.png",
            "images/sword.png",
            "images/bow.png",
            "images/coin.png",
            "images/diamond.png",
            "images/coin.gif",
            "images/diamond.gif",
            "images/pixelghost.png",
            "images/pixelghost1.png",
            "images/littlegreen1.png",
            "images/attack.png",
            "images/negbuff.png",
            "images/posipuff.png",
            "images/run.png",
            "images/blank.png",
            "images/playercoin.png",
            "images/dragon1.png",
            "images/demon1.png",
            "images/ghost1.png",
            "images/death.png",
            "images/hurt0.png",
            "images/hurt1.png",
            "images/hurt2.png",
            "images/hurt3.png",
            "images/hurt4.png",
    };

    private static final String[] rainbowBridgeSounds = {"sounds/Impact4.ogg"
            , "sounds/buff.ogg", "sounds/buff2.ogg" , "sounds/e.ogg", "sounds" +
            "/attack.ogg", "sounds/buff_recover.ogg", "sounds/coin.ogg", "sounds/kill_enemy.ogg", "sounds/e.ogg", "sounds/death.ogg"};
    private static final String backgroundMusic = "sounds/BGM_03_mp3.mp3";
    private static final String backgroundMusic1 = "sounds/backgroundMusic1.mp3";
    private static final String[] rainbowBridgeMusic = {backgroundMusic, backgroundMusic1};

    private static final String[] rainbowBridgeAtlases = {

            "images/terrain_iso_grass.atlas", "images/ghost1.atlas", "images" +
            "/ghostKing.atlas","images/demon1.atlas","images/dragon1.atlas","images/littlegreen1" +
            ".atlas", "images/attack.atlas", "images/touch.atlas","images" +
            "/negbuff.atlas", "images/posipuff.atlas","images/run.atlas",
            "images/playercoin.atlas", "images/death.atlas"
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
        // spawnWeaponObjects();
        spawnCollectableObjects();
        player = spawnPlayer();
        player.setPosition(player.getPosition().x, 3.5f);
        spawnMonster();
        musicControl();
    }

    private void musicControl(){
        if (musicSign == 0){
            playMusic();
            musicSign++;
        }
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

    private void startMapContentsMovement(Entity entity, int lane_index) {
        List<Lane> lanes = terrain.getRainbowBridge().getLanes();
        int y_target = 0;
        if (lane_index == 0) {  //hard coding y coordinate targets for object movement because lanes.getMid is off
            y_target = 3;
        } else if (lane_index == 1) {
            y_target = 5;
        } else if (lane_index == 2) {
            y_target = 7;
        } else if (lane_index == 3) {
            y_target = 8;
        }

        Vector2 target = new Vector2(-200, y_target);
        MovementTask task = new MovementTask(target);
        
        task.create(() -> entity);
        task.start();
    }

    private int makeContentDisappear(String evt, Fixture fixture, Fixture otherFixture) {
        // logger.debug("fixture: ", fixture);
        // fixture.
//        entity.dispose();
        return 1;
    }

    public static void disposeContent(Entity entity) {
		entity.dispose();
	}

    private void spawnObstables() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        List<Lane> lanes = terrain.getRainbowBridge().getLanes();
        int d = 0;
        while (d < this.NUM_OBSTACLES) {
            for (int i = 0; i < lanes.size(); i++) {
                
                int y_coordinate = lanes.get(i).getMid() ;
                int x_random = ThreadLocalRandom.current().nextInt(0, this.MAX_CONTENT_POSITION);
                int random_index = ThreadLocalRandom.current().nextInt(0, 3);
                GridPoint2 randomPosInLane = new GridPoint2(x_random, y_coordinate);
                // Entity RunesGate = ObstacleFactory.createRunesGate();
                // spawnEntityAt(RunesGate, randomPosInLane, true, false);
                    switch(random_index) {
                        case 0:
                            Entity RunesGate = ObstacleFactory.createRunesGate();

                            // RunesGate.getEvents().addListener("contentReachedEndOfMap", this::makeContentDisappear);
                            // RunesGate.getEvents().addListener("contentReachedEndOfMap", this::disposeContent);
                            spawnEntityAt(RunesGate, randomPosInLane, true, true);
                            this.startMapContentsMovement(RunesGate, i);
                            
                            break;
                        case 1:
                            Entity stone = ObstacleFactory.createStoneObstacle();
                            spawnEntityAt(stone, randomPosInLane, true, true);
                            this.startMapContentsMovement(stone, i);
                            break;
                        case 2:
                            Entity thunderCloud = ObstacleFactory.createthunderCloud();
                            spawnEntityAt(thunderCloud, randomPosInLane, true, true);
                            this.startMapContentsMovement(thunderCloud, i);
                            break;
                        case 3:
                            Entity fire = ObstacleFactory.createFire();
                            spawnEntityAt(fire, randomPosInLane, true, true);
                            this.startMapContentsMovement(fire, i);
                            break;
                    }
            }
            d++;
        }
    }

    private void spawnHealthObjects() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        List<Lane> lanes = terrain.getRainbowBridge().getLanes();
        int d = 0;

        while (d < this.NUM_HEALTH_OBJECTS) {
            for (int i = 0; i < lanes.size(); i++) {
                
                int y_coordinate = lanes.get(i).getMid();
                int x_random = ThreadLocalRandom.current().nextInt(5, this.MAX_CONTENT_POSITION);  // min x=5, max x=30
                int random_index = ThreadLocalRandom.current().nextInt(0, 3);
                GridPoint2 randomPosInLane = new GridPoint2(x_random, y_coordinate);
                switch(random_index) {
                    case 0:
                        Entity food = ObstacleFactory.createFood();
                        spawnEntityAt(food, randomPosInLane, true, true);
                        this.startMapContentsMovement(food, i);
                        break;
                    case 1:
                        Entity firstAid = ObstacleFactory.createFirstAidKit();
                        spawnEntityAt(firstAid, randomPosInLane, true, true);
                        this.startMapContentsMovement(firstAid, i);
                        break;
                    case 2:
                        food = ObstacleFactory.createFood();
                        spawnEntityAt(food, randomPosInLane, true, true);
                        this.startMapContentsMovement(food, i);
                        break;
                    case 3:
                        firstAid = ObstacleFactory.createFirstAidKit();
                        spawnEntityAt(firstAid, randomPosInLane, true, true);
                        this.startMapContentsMovement(firstAid, i);
                        break;
                }
            }
            d++;
        }
    }

    private void spawnCollectableObjects() {
        GridPoint2 minPos = new GridPoint2(0, 0);
        List<Lane> lanes = terrain.getRainbowBridge().getLanes();
        int d = 0;
        while (d < this.NUM_COLLECTABLES) {
            for (int i = 0; i < lanes.size(); i++) {
                int y_coordinate = lanes.get(i).getMid();
                int x_random = ThreadLocalRandom.current().nextInt(5, this.MAX_CONTENT_POSITION);  
                int random_index = ThreadLocalRandom.current().nextInt(0, 3);
                GridPoint2 randomPosInLane = new GridPoint2(x_random, y_coordinate);            
                    switch(random_index) { // create 3 coins for every 1 diamond
                        case 0:
                            Entity coin = ObstacleFactory.createCoin();
                            spawnEntityAt(coin, randomPosInLane, true, true);
                            this.startMapContentsMovement(coin, i);
                            break;
                        case 1:
                            Entity diamond = ObstacleFactory.createDiamond();
                            spawnEntityAt(diamond, randomPosInLane, true, true);
                            this.startMapContentsMovement(diamond, i);
                            break;
                        case 2:
                            coin = ObstacleFactory.createCoin();
                            spawnEntityAt(coin, randomPosInLane, true, true);
                            this.startMapContentsMovement(coin, i);
                            break;
                        case 3:
                            coin = ObstacleFactory.createCoin();
                            spawnEntityAt(coin, randomPosInLane, true, true);
                            this.startMapContentsMovement(coin, i);
                            break;
                    }
            }
            d++;
        }
    }

    private Entity spawnPlayer() {
        Entity newPlayer = PlayerFactory.createPlayer();
        spawnEntityAt(newPlayer, PLAYER_SPAWN, true, true);
        return newPlayer;
    }


    public Bridge getRainbowBridge() {
        return this.rainbowBridge;
    }

    public Entity getPlayer() {
        return this.player;
    }

    private void spawnMonster() {
        List<Lane> lanes = terrain.getRainbowBridge().getLanes();
        int a = 0;
            while (a < NUM_MONSTER) {
                for (int i = 0; i < lanes.size(); i++) {
                    int y_coordinate = lanes.get(i).getMid();
                    int x_random = ThreadLocalRandom.current().nextInt(30, this.MAX_CONTENT_POSITION);
                    GridPoint2 Ghost = new GridPoint2(x_random, y_coordinate);
                    GridPoint2 LittleGreen = new GridPoint2(x_random, y_coordinate);
                    GridPoint2 Dragon = new GridPoint2(x_random, y_coordinate);
                    GridPoint2 Demon = new GridPoint2(x_random, y_coordinate);
                    int random_index = ThreadLocalRandom.current().nextInt(0, 4);
                switch(random_index) {
                    case 0:
                        Entity littleGreen = NPCFactory.createLittleGreen(player);
                        spawnEntityAt(littleGreen, LittleGreen, true, true);
                        break;
                    case 1:
                        Entity ghost = NPCFactory.createGhost(player);
                        spawnEntityAt(ghost, Ghost, true, true);
                        break;
                    case 2:
                        Entity demon = NPCFactory.createDemon(player);
                        spawnEntityAt(demon,Demon, true, true);
                        break;
                    case 3:
                        Entity dragon = NPCFactory.createGhostKing(player);
                        spawnEntityAt(dragon,Dragon, true, true);
                        break;
                }
                a++;
            }
        }
    }


    private void playMusic() {
        Music music = ServiceLocator.getResourceService().getAsset(backgroundMusic1, Music.class);
        music.setLooping(true);
        music.setVolume(0.8f);
        music.play();
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(rainbowBridgeTextures);
        resourceService.loadTextureAtlases(rainbowBridgeAtlases);
        resourceService.loadSounds(rainbowBridgeSounds);
        resourceService.loadMusic(rainbowBridgeMusic);

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
