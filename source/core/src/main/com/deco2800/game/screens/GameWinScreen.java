package com.deco2800.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.winscreen.GameWinActions;
import com.deco2800.game.components.winscreen.GameWinDisplay;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameWinScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(GameWinScreen.class);

    private final GdxGame game;
    private final Renderer renderer;
    private static final String[] GameWinScreenTextures = {"images/Win-screen-2-transparent.png", "images/btn_restart1.png","images/btn_exit1.png", "images/FDS_btn_exit1.png", "images/ragnarok_background.png"};

    private static final Sound winSound = Gdx.audio.newSound(Gdx.files.internal("sounds/win.ogg"));

    public GameWinScreen(GdxGame game) {

        this.game = game;
        logger.debug("drawing game win ui");
        ServiceLocator.registerInputService(new InputService());

        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();

        winSound.play(1.0f);

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
        logger.trace("Resized renderer: ({} x {})", width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        logger.debug("Disposing win game screen");
        renderer.dispose();
        ServiceLocator.clear();
        winSound.stop();
    }

    private void loadAssets() {

        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(GameWinScreenTextures);
        ServiceLocator.getResourceService().loadAll();

        while (!resourceService.loadForMillis(10)) {
            // This could be upgraded to a loading screen
            logger.info("Loading... {}%", resourceService.getProgress());
        }

    }
    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(GameWinScreenTextures);
    }

//    private void createUI() {
//        logger.debug("Creating ui");
//        Stage stage = ServiceLocator.getRenderService().getStage();
//        Entity ui = new Entity();
//        ui.addComponent(new GameOverDisplay())
//                .addComponent(new InputDecorator(stage, 10))
//                .addComponent(new GameOverActions(game));
//        ServiceLocator.getEntityService().register(ui);
//    }

    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new GameWinDisplay())
                .addComponent(new InputDecorator(stage, 10))
                .addComponent(new GameWinActions(game));
        ServiceLocator.getEntityService().register(ui);
    }

//    private void createUI() {
//        logger.debug("Creating ui");
//        Stage stage = ServiceLocator.getRenderService().getStage();
//        Entity ui = new Entity();
//        ui.addComponent(new GameWinDisplay())
//                .addComponent(new InputDecorator(stage, 10))
//                .addComponent(new GameWinActions(game));
//        ServiceLocator.getEntityService().register(ui);
//    }
}
