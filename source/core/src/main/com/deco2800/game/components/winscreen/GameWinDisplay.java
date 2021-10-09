package com.deco2800.game.components.winscreen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GameWinDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(GameWinDisplay.class);
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
        Button.ButtonStyle restartStyle = new Button.ButtonStyle();
        restartStyle.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/btn_restart1.png"))));
        restartStyle.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/btn_restart2.png"))));
        Button restartBtn = new Button(restartStyle);

        //Exit button
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();
        exitStyle.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/btn_exit1.png"))));
        exitStyle.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/btn_exit2.png"))));
        Button exitBtn = new Button(exitStyle);

        //Background
        Image background = new Image(ServiceLocator.getResourceService().getAsset("images/ragnarok_background.png",
                Texture.class));

        // when the user presses restart button
        restartBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                logger.debug("Restart button pressed");
                entity.getEvents().trigger("replay");
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

        Image winMessage = new Image(ServiceLocator.getResourceService().getAsset("images/Win-screen-2-transparent.png",
                Texture.class));

        table.add(winMessage).top();
        table.row();
        table.add(restartBtn).padTop(30f).size(200f, 80f);
        table.row();
        table.add(exitBtn).padTop(15f).size(200f, 80f);;

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

    @Override
    public float getZIndex() {
        return Z_INDEX;
    }

}
