package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.tutorialmenu.TutorialActions;

import com.deco2800.game.components.tutorialmenu.TutorialDisplay;
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

public class TutorialScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TutorialScreen.class);

    private final GdxGame game;
    private final Renderer renderer;
    private static final String[] TutorialScreenTextures = {"images/Untitled design.png"};

    public TutorialScreen(GdxGame game) {

        this.game = game;
        logger.debug("drawing game win ui");
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
        logger.trace("Resized renderer: ({} x {})", width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        logger.debug("Disposing win game screen");
        renderer.dispose();
        ServiceLocator.clear();
    }

    private void loadAssets() {

        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(TutorialScreenTextures);
        ServiceLocator.getResourceService().loadAll();

        while (!resourceService.loadForMillis(10)) {
            // This could be upgraded to a loading screen
            logger.info("Loading... {}%", resourceService.getProgress());
        }

    }
    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(TutorialScreenTextures);
    }

    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new TutorialDisplay())
                .addComponent(new InputDecorator(stage, 10))
                .addComponent(new TutorialActions(game));
        ServiceLocator.getEntityService().register(ui);
    }
}
