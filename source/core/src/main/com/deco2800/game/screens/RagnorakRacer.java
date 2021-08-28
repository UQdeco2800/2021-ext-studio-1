package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.GdxGame;
import com.deco2800.game.areas.RainbowBridge;
import com.deco2800.game.areas.terrain.TerrainFactory;
import com.deco2800.game.components.bridge.Bridge;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;

public class RagnorakRacer extends ScreenAdapter {

    private final GdxGame game;
    private final Renderer renderer;
    private Bridge rainbowBridge;

    private static final Vector2 CAMERA_POSITION = new Vector2(7.5f, 7.5f);

    public RagnorakRacer(GdxGame game) {
        this.game = game;

        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        this.renderer = RenderFactory.createRenderer();
        this.renderer.getCamera().getEntity().setPosition(CAMERA_POSITION);

        TerrainFactory terrainFactory = new TerrainFactory(renderer.getCamera());
        RainbowBridge rainbowBridge = new RainbowBridge(terrainFactory);
        rainbowBridge.create();
        rainbowBridge.getRainbowBridge();
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
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
