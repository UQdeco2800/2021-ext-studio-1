package com.deco2800.game.components.gameover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.components.mainmenu.MainMenuDisplay;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
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
public class GameOverDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(GameOverDisplay.class);
    private static final float Z_INDEX = 2f;
    protected Table table;
    protected Stack stack;

    @Override
    public void create() {
        super.create();
        addActors();
    }

    /**
     * Visual components
     */
    protected void addActors() {
        stack = new Stack();
        stack.setFillParent(true);
        stack.setTouchable(Touchable.disabled);

        table = new Table();
        table.setFillParent(true);

        //Restart button
        TextButton restartBtn = new TextButton("Restart Game", skin);
        //Button restartBtn = new Button((Drawable) new ImageIcon("images/btn_restart1.png"),
                //(Drawable) new ImageIcon("images/btn_restart2.png"));

        //Exit button
        TextButton exitBtn = new TextButton("Exit", skin);
        //Button exitBtn = new Button((Drawable) new ImageIcon("images/btn_exit1.png"),
                //(Drawable) new ImageIcon("images/btn_exit2.png"));

        //Background
        Image background = new Image(ServiceLocator.getResourceService().getAsset("images/Death_Screen_Background.png",
                Texture.class));
        //background.setScaling(Scaling.stretch);
        //stack.add(background);

        // when the user presses restart button

        restartBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Restart button pressed");
                        entity.getEvents().trigger("restart");
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

        Image playerDeadImage = new Image(ServiceLocator.getResourceService().getAsset("images/Death_Screen_Character.png",
                Texture.class));
        Image gameOverText = new Image(ServiceLocator.getResourceService().getAsset("images/Gameover_Visual_Text.png",
                Texture.class));

        table = new Table();
        table.clear();
        table.add(background);
        table.row();
        table.add(playerDeadImage);
        table.row();
        table.add(gameOverText);
        table.row();
        table.add(restartBtn).padTop(30f);
        table.row();
        table.add(exitBtn).padTop(30f);
        table.row();

        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
        super.dispose();
        stack.clear();
    }
}
