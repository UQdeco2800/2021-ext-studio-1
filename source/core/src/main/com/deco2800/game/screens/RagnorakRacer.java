package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.RainbowBridge;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.bridge.Bridge;
import com.deco2800.game.components.gamearea.PerformanceDisplay;
import com.deco2800.game.components.gameover.GameOverDisplay;
import com.deco2800.game.components.maingame.MainGameActions;
import com.deco2800.game.components.maingame.MainGameExitDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.physics.PhysicsEngine;
import com.deco2800.game.physics.PhysicsService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.terminal.Terminal;
import com.deco2800.game.ui.terminal.TerminalDisplay;
import com.deco2800.game.rendering.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.deco2800.game.GdxGame.ScreenType.GAME_WIN;

public class RagnorakRacer extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);

    private static final String[] mainGameTextures = {"images/health_full.png", "images/health_decrease_two.png",
            "images/health_decrease_one.png", "images/health_empty.png", "images/armour_full.png", "images/armour_decrease_two.png",
            "images/armour_decrease_one.png", "images/armour_empty.png", "images/notification.png",
            "images/hurt0.png","images/hurt1.png","images/hurt2.png","images/hurt3.png","images/hurt4.png",
            "images/10coin0.png","images/10coin1.png","images/10coin2.png","images/10coin3.png","images/10coin4.png",
            "images/coincollectortransparentvisual.png", "images/ragnarok_background.png", "images/Gameover_txt.png"};
    private static final Vector2 CAMERA_POSITION = new Vector2(10f, 7.5f);

    private final GdxGame game;
    private final long gameTimer;
    private final Renderer renderer;
    public static Bridge rainbowBridge;
    private final PhysicsEngine physicsEngine;

    public RagnorakRacer(GdxGame game) {
        this.game = game;
        this.gameTimer = 60000;

        logger.debug("Initialising main game screen services");
        ServiceLocator.registerTimeSource(new GameTime());

        PhysicsService physicsService = new PhysicsService();
        ServiceLocator.registerPhysicsService(physicsService);
        ServiceLocator.registerGameService(game); //new line
        physicsEngine = physicsService.getPhysics();

        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerTimeSource(new GameTime());

        this.renderer = RenderFactory.createRenderer();
        this.renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);
        renderer.getDebug().renderPhysicsWorld(physicsEngine.getWorld());

        loadAssets();
        createUI();
        loadAssets();

        TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
        RainbowBridge rainbowBridge = new RainbowBridge(terrainFactory);
        rainbowBridge.create();
        this.rainbowBridge = rainbowBridge.getRainbowBridge();

        rainbowBridge.getPlayer().getEvents().addListener("GameOver", this::gameOver);
    }

    private void isPlayerDead() {
        if (this.rainbowBridge.getPlayer() != null) {
            logger.error("--end--attacker--&&&&&&&&&&&&&&&&&&&&&&&&&&&&{}");
            if (this.rainbowBridge.getPlayer().getComponent(CombatStatsComponent.class).isDead()) {
                AnimationRenderComponent7 animator7 =
                        rainbowBridge.getPlayer().getComponent(AnimationRenderComponent7.class);
                logger.error("--end--attacker--&&&&&&&&&&&&&&&&&&&&&&&&&&&&{}", rainbowBridge.getPlayer());
                animator7.startAnimation("death");
                if(animator7.isFinished()) {
                    logger.error("--end--attacker-----------------------{}", rainbowBridge.getPlayer());
                    game.setScreen(GdxGame.ScreenType.GAMEOVER);
                }
            }
        }
    }

    private void gameOver() {
        game.setScreen(new GameOverScreen(game));
    }

    @Override
    public void render(float delta) {
        if (ServiceLocator.getTimeSource().getTime() >= this.gameTimer) {
            game.setScreen(GAME_WIN);
            // Switch to new Win game screen
        }
        physicsEngine.update();
        ServiceLocator.getEntityService().update();
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
        logger.trace("Resized renderer: ({} x {})", width, height);
    }


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
        logger.info("Game paused");
    }

    @Override
    public void resume() {
        super.resume();
        logger.info("Game resumed");
    }

    @Override
    public void dispose() {

        super.dispose();
        logger.debug("Disposing main game screen");

        renderer.dispose();
        unloadAssets();

        ServiceLocator.getEntityService().dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getResourceService().dispose();

        ServiceLocator.clear();

    }

    /**
     * Creates the main game's ui including components for rendering ui elements to the screen and
     * capturing and handling ui input.
     */
    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        InputComponent inputComponent =
                ServiceLocator.getInputService().getInputFactory().createForTerminal();

        Entity ui = new Entity();
        ui.addComponent(new InputDecorator(stage, 10))
                .addComponent(new PerformanceDisplay())
                .addComponent(new MainGameActions(this.game))
                .addComponent(new MainGameExitDisplay())
                .addComponent(new Terminal())
                .addComponent(inputComponent)
                .addComponent(new TerminalDisplay());


        ServiceLocator.getEntityService().register(ui);
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(mainGameTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(mainGameTextures);
    }
}
