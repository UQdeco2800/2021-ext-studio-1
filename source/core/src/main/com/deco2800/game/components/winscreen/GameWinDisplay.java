package com.deco2800.game.components.winscreen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
                new Texture(Gdx.files.internal("images/btn_exit1.png"))));
        exitStyle.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/btn_exit2.png"))));
        Button exitBtn = new Button(exitStyle);

        //Background
        Image background = new Image(ServiceLocator.getResourceService().getAsset("images/Win-screen-2-transparent.png",
                Texture.class));
        //background.setScaling(Scaling.stretch);
        //stack.add(background);

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
//        Image gameOverText = new Image(ServiceLocator.getResourceService().getAsset("images/Gameover_Visual_Text.png",
//                Texture.class));

//        table.add(playerDeadImage).size(350f,300f).padBottom(50f);
//        table.row();
//        table.add(gameOverText).size(500f,50f);
//        table.row();

//        table.add(winMessage).top();
//        table.row();
//        table.add(restartBtn).padTop(30f).size(200f,80f).padTop(50f);
//        table.row();
//        table.add(exitBtn).padTop(30f).size(200f,80f);
//
//        tablebackGround.add(background).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        stage.addActor(tablebackGround);
//        stage.addActor(table);
        table.add(winMessage).top();
        table.row();
        table.add(restartBtn).padTop(30f).size(200f, 80f);
        table.row();
        table.add(exitBtn).padTop(15f).size(200f, 80f);;

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

//    private static final Logger logger = LoggerFactory.getLogger(GameWinDisplay.class);
//    private static final float Z_INDEX = 2f;
//    private Table table;
//
//
//    @Override
//    public void create(){
//        super.create();
//        createGameWin();
//    }
//
//    private void createGameWin() {
//
//        table = new Table();
//        table.setFillParent(true);
//        Image title =
//                new Image(
//                        ServiceLocator.getResourceService()
//                                .getAsset("images/Win-screen-2-transparent.png", Texture.class));
//        TextButton replayButton = new TextButton("Replay", skin);
//        TextButton exitButton = new TextButton("Exit", skin);
//
//        replayButton.addListener(
//                new ChangeListener() {
//                    @Override
//                    public void changed(ChangeEvent changeEvent, Actor actor) {
//                        logger.info("replay game button clicked");
//                        entity.getEvents().trigger("replay");
//                    }
//                });
//
//        exitButton.addListener(
//                new ChangeListener() {
//                    @Override
//                    public void changed(ChangeEvent changeEvent, Actor actor) {
//                        logger.info("exit button clicked");
//                        entity.getEvents().trigger("exit");
//                    }
//                });
//
//
//        table.add(title).top();
//        table.row();
//        table.add(replayButton).padTop(30f);
//        table.row();
//        table.add(exitButton).padTop(15f);;
//
//        stage.addActor(table);
//    }
//
//    @Override
//    public void dispose() {
//        table.clear();
//        super.dispose();
//    }
//
//    @Override
//    public float getZIndex() {
//        return Z_INDEX;
//    }
//
//    @Override
//    protected void draw(SpriteBatch batch) {
//
//    }
}
