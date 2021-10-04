package com.deco2800.game.components.tutorialmenu;

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
public class TutorialDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(TutorialDisplay.class);
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

        Label title = new Label("Tutorial", skin, "title");
        Label instruction1 = new Label("Press W :  To move up on the lane", skin);
        Label instruction2 = new Label("Press S :  To move down on the lane", skin);
        Label instruction3 = new Label("Press Q :  To jump up to top lane", skin);
        Label instruction4 = new Label("Press E :  To jump down on bottom lane", skin);
        Label instruction5 = new Label("Press ' Space Bar ' to Attack", skin);

        stack = new Stack();
        stack.setFillParent(true);
        stack.setTouchable(Touchable.disabled);

        table = new Table();
        table.setFillParent(true);

        tablebackGround = new Table();
        tablebackGround.setFillParent(true);


        //Exit button
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();
        exitStyle.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/btn_exit1.png"))));
        exitStyle.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/btn_exit2.png"))));
        Button exitBtn = new Button(exitStyle);

       // TextButton exitBtn = new TextButton("Exit", skin);



        // when the user presses exit button
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                logger.debug("Exit button pressed");
                entity.getEvents().trigger("exit");
            }
        });


        table.add(title).expandX().top().padTop(20f);
        table.row();
        table.add(instruction1).expandX().padTop(80f);
        table.row();
        table.add(instruction2).expandX().padTop(30f);
        table.row();
        table.add(instruction3).expandX().padTop(30f);
        table.row();
        table.add(instruction4).expandX().padTop(30f);
        table.row();
        table.add(instruction5).expandX().padTop(30f);
        table.row();
        table.add(exitBtn).size(200f,80f).pad(50f, 15f, 15f, 0f);


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
