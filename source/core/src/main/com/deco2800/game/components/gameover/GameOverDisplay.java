package com.deco2800.game.components.gameover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.components.mainmenu.MainMenuDisplay;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


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

        //Restart button
        //TextButton restartBtn = new TextButton("Restart Game", skin);
        Button restartBtn = new Button((Drawable) new ImageIcon("images/btn_restart1.png"),
                (Drawable) new ImageIcon("images/btn_restart2.png"));

        //Exit button
        //TextButton exitBtn = new TextButton("Exit", skin);
        Button exitBtn = new Button((Drawable) new ImageIcon("images/btn_exit1.png"),
                (Drawable) new ImageIcon("images/btn_exit2.png"));

        //Background
        Image background = new Image(ServiceLocator.getResourceService().getAsset("Death_Screen_Background.png",
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

        Image playerDeadImage = new Image(ServiceLocator.getResourceService().getAsset("Death_Screen_Character.png",
                Texture.class));
        Image gameOverText = new Image(ServiceLocator.getResourceService().getAsset("Gameover_Visual_Text.png",
                Texture.class));
        Image gameOverBackground = new Image(ServiceLocator.getResourceService().getAsset("Death_Screen_Background",
                Texture.class));

        Table table = new Table();
        table.clear();
        table.add(gameOverBackground);
        table.row();
        table.add(playerDeadImage);
        table.row();
        table.add(gameOverText);
        table.row();
        table.add(restartBtn).padTop(30f);
        table.row();
        table.add(exitBtn).padTop(30f);
        table.row();

        //stack.setVisible(false);
        //table.setVisible(false);
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
    public void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
        super.dispose();
        stack.clear();
        this.unloadAssets();
    }

    public void showDeath() {
        stack.setVisible(true);
        table.setVisible(true);
    }
}
