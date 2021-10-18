package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.gamestory.GameStoryActions;
import com.deco2800.game.components.gamestory.GameStoryDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.services.GameTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.deco2800.game.GdxGame.ScreenType.MAIN_GAME;
/**
 * The game screen containing the game's story.
 */
public class GameStoryScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(GameStoryScreen.class);
    private final GdxGame game;
    private final Renderer renderer;
    private final long gameTimer;
    private static final String[] storyScreenTextures = {"images/story-screen-bg.png"};

    public GameStoryScreen(GdxGame game) {
        this.game = game;
        this.gameTimer = 15000;
        logger.debug("Initialising story screen services");
        ServiceLocator.registerTimeSource(new GameTime());
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        ServiceLocator.registerTimeSource(new GameTime());
        renderer = RenderFactory.createRenderer();

        loadAssets();
        createUI();
    }

    @Override
    public void render(float delta) {
        if (ServiceLocator.getTimeSource().getTime() >= this.gameTimer) {
            game.setScreen(MAIN_GAME);
            // Switch to new MAIN game screen
        }
        ServiceLocator.getEntityService().update();
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
        logger.trace("Resized renderer: ({} x {})", width, height);
    }

    @Override
    public void pause() {
        logger.info("Game paused");
    }

    @Override
    public void resume() {
        logger.info("Game resumed");
    }

    @Override
    public void dispose() {
        logger.debug("Disposing main menu screen");

        renderer.dispose();
        unloadAssets();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();

        ServiceLocator.clear();
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(storyScreenTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(storyScreenTextures);
    }

    /**
     * Creates the main menu's ui including components for rendering ui elements to the screen and
     * capturing and handling ui input.
     */
    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new GameStoryDisplay())
                .addComponent(new InputDecorator(stage, 10))
                .addComponent(new GameStoryActions(game));
        ServiceLocator.getEntityService().register(ui);
    }

}
