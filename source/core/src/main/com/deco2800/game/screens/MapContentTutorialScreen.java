package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.tutorialmenu.MapContentTutorialActions;

import com.deco2800.game.components.tutorialmenu.MapContentTutorialDisplay;
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

public class MapContentTutorialScreen extends ScreenAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MapContentTutorialScreen.class);

    private final GdxGame game;
    private final Renderer renderer;
    private static final String[] MapContentTutorialScreenTextures = {"images/Untitled design.png", "images/btn_exit1.png", "images/FDS_btn_exit1.png", "images/space-background.png"};

    public MapContentTutorialScreen(GdxGame game) {

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
        logger.debug("Disposing map content tutorial screen");
        renderer.dispose();
        ServiceLocator.clear();
    }

    private void loadAssets() {

        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(MapContentTutorialScreenTextures);
        ServiceLocator.getResourceService().loadAll();

        while (!resourceService.loadForMillis(10)) {
            // This could be upgraded to a loading screen
            logger.info("Loading... {}%", resourceService.getProgress());
        }

    }
    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(MapContentTutorialScreenTextures);
    }

    private void createUI() {
        logger.debug("Creating ui");
        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        ui.addComponent(new MapContentTutorialDisplay())
                .addComponent(new InputDecorator(stage, 10))
                .addComponent(new MapContentTutorialActions(game));
        ServiceLocator.getEntityService().register(ui);
    }
}
