package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.gameover.GameOverActions;
import com.deco2800.game.components.gameover.GameOverDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameOverScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(GameOverScreen.class);

    private final GdxGame game;
    private final int gold;
    private final Renderer renderer;

    private static final String[] gameOverTextures = {"images/ragnarok_background.png", "images/Death_Screen_Character.png", "images/Gameover_txt.png",
            "images/btn_restart1.png","images/btn_exit1.png", "images/Gameover_Coincollector.png"};

    public GameOverScreen(GdxGame game, int gold) {
        this.game = game;
        this.gold = gold;

        logger.debug("Initialising GameOver screen services");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        renderer = RenderFactory.createRenderer();

        loadAssets();
        createUI();
    }


    @Override
    public void render(float delta) {
        ServiceLocator.getEntityService().update();
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void dispose() {
        unloadAssets();
        renderer.dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();

        ServiceLocator.clear();
    }

    public int getFinalGold(){
        return this.gold;
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(gameOverTextures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(gameOverTextures);
    }


    /**
     * Creates the setting screen's ui including components for rendering ui elements to the screen
     * and capturing and handling ui input.
     */
    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new GameOverDisplay(getFinalGold()))
                .addComponent(new InputDecorator(stage, 10))
                .addComponent(new GameOverActions(game));
        ServiceLocator.getEntityService().register(ui);
    }
}
