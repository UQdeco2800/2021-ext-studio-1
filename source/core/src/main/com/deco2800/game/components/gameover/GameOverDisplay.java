package com.deco2800.game.components.gameover;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.components.mainmenu.MainMenuDisplay;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * GameOver display
 */
public class GameOverDisplay extends MainMenuDisplay {
    private static final Logger logger = LoggerFactory.getLogger(GameOverDisplay.class);
    private final String[] gameOverScreenTextures = new String[]{
            ""
    };

    @Override
    public void create() {
        loadAssets();
        super.create();
    }

    /**
     * Visual components
     */
    @Override
    protected void addActors() {
        super.addActors();

        TextButton restartBtn = new TextButton("Restart Game", skin);
        TextButton exitBtn = new TextButton("Quit", skin);
        Image background = new Image(ServiceLocator.getResourceService().getAsset("",
                Texture.class));
        background.setScaling(Scaling.stretch);
        stack.add(background);

        // when the user presses restart button
        restartBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Restart button pressed");
                        entity.getEvents().trigger("start");
                    }
                });

        // when the user presses exit button
        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button pressed");
                        entity.getEvents().trigger("exit");
                    }
                });

        Image playerDeadImage = new Image(ServiceLocator.getResourceService().getAsset("",
                Texture.class));

        table.clear();
        table.add(playerDeadImage);
        table.row();
        table.add(restartBtn).padTop(30f);
        table.row();
        table.add(exitBtn).padTop(30f);
        table.row();
    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        ServiceLocator.getResourceService().loadTextures(gameOverScreenTextures);
        while (resourceService.loadForMillis(10)) {
            logger.info("Loading... {}%", resourceService.getProgress());
        }
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(gameOverScreenTextures);
    }

    @Override
    public void dispose() {
        super.dispose();
        stack.clear();
        this.unloadAssets();
    }
}
