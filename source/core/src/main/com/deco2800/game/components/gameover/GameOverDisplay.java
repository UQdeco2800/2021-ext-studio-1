package com.deco2800.game.components.gameover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * GameOver display
 */
public class GameOverDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(GameOverDisplay.class);
    private static final float Z_INDEX = 2f;
    protected Table table;
    protected Table tablebackGround;
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

        tablebackGround = new Table();
        tablebackGround.setFillParent(true);

        //Restart button
        //TextButton restartBtn = new TextButton("Restart Game", skin);
        Button.ButtonStyle restartStyle = new Button.ButtonStyle();
        restartStyle.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/btn_restart1.png"))));
        restartStyle.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/btn_restart2.png"))));
        Button restartBtn = new Button(restartStyle);

        //Exit button
        //TextButton exitBtn = new TextButton("Exit", skin);
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();
        exitStyle.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_exit1.png"))));
        exitStyle.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_exit2.png"))));
        Button exitBtn = new Button(exitStyle);

        //Background
        Image background = new Image(ServiceLocator.getResourceService().getAsset("images/ragnarok_background.png",
                Texture.class));
        //background.setScaling(Scaling.stretch);
        //stack.add(background);

        // when the user presses restart button
        restartBtn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent e,float x,float y) {
                        logger.debug("Restart button pressed");
                        entity.getEvents().trigger("restarting game");
                    }
                });

        // when the user presses exit button
        exitBtn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent e, float x, float y) {
                        logger.debug("Exit button pressed");
                        entity.getEvents().trigger("exit");
                    }
                });

        Image playerDeadImage = new Image(ServiceLocator.getResourceService().getAsset("images/Death_Screen_Character.png",
                Texture.class));
        Image gameOverText = new Image(ServiceLocator.getResourceService().getAsset("images/Gameover_txt.png",
                Texture.class));

        table.add(playerDeadImage).size(350f,300f).padBottom(50f);
        table.row();
        table.add(gameOverText).size(500f,50f);
        table.row();
        table.add(restartBtn).padTop(30f).size(200f,80f).padTop(50f);
        table.row();
        table.add(exitBtn).padTop(30f).size(200f,80f);

        tablebackGround.add(background).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addActor(tablebackGround);
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
