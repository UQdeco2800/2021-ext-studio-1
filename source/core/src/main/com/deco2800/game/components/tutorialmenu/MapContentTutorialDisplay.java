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
public class MapContentTutorialDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MapContentTutorialDisplay.class);
    private static final float Z_INDEX = 2f;
    protected Table table;
    protected Table tableBtn;
    protected Table tableTitle;
    protected Table tableExit;
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

        tableBtn = new Table();
        tableBtn.top().padTop(15f);
        tableBtn.setFillParent(true);


        tableTitle = new Table();
        tableTitle.top().padTop(15f);
        tableTitle.setFillParent(true);


        tableExit = new Table();
        tableExit.bottom();
        tableExit.setFillParent(true);

        Image background = new Image(ServiceLocator.getResourceService().getAsset("images/space-background.png",
                Texture.class));

        tablebackGround = new Table();
        tablebackGround.setFillParent(true);


        // Player movement tutorial button
        Button.ButtonStyle playerMovementTutorial = new Button.ButtonStyle();
        playerMovementTutorial.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_player1.png"))));
        playerMovementTutorial.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_player2.png"))));
        Button playerMovementTutorialBtn = new Button(playerMovementTutorial);

        // Enemy tutorial button
        Button.ButtonStyle enemyTutorial = new Button.ButtonStyle();
        enemyTutorial.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_enemy1_tutorial.png"))));
        enemyTutorial.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_enemy2_tutorial.png"))));
        Button enemyTutorialBtn = new Button(enemyTutorial);

        // Map content tutorial button
        Button.ButtonStyle mapContentTutorial = new Button.ButtonStyle();
        mapContentTutorial.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_mapContent1_tutorial.png"))));
        mapContentTutorial.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_mapContent2_tutorial.png"))));
        Button mapContentTutorialBtn = new Button(mapContentTutorial);

        Label title = new Label("MAP CONTENT TUTORIAL", skin, "title", "white");
        Label instruction1 = new Label("Press W :  To move up on the lane", skin);
        Label instruction2 = new Label("Press S :  To move down on the lane", skin);
        Label instruction3 = new Label("Press Q :  To jump up to top lane", skin);
        Label instruction4 = new Label("Press E :  To jump down on bottom lane", skin);
        Label instruction5 = new Label("Press ' Space Bar ' to Attack", skin);




        //Exit button
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();
        exitStyle.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_exit1.png"))));
        exitStyle.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_exit2.png"))));
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

        enemyTutorialBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                logger.debug("Enemy Tutorial Button pressed");
                entity.getEvents().trigger("EnemyTutorial");
            }
        });

        playerMovementTutorialBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                logger.debug("Tutorial Button pressed");
                entity.getEvents().trigger("tutorial");
            }
        });
        // table with buttons
        tableBtn.add(playerMovementTutorialBtn).size(200f,80f).padTop(20f);
        tableBtn.add(enemyTutorialBtn).size(200f,80f).padLeft(100f).padTop(20f);
        tableBtn.add(mapContentTutorialBtn).size(200f,80f).padLeft(100f).padTop(20f);

        // table with title
        tableTitle.add(title).expandX().top().padTop(140f);

        // table with contents
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
//        table.add(exitBtn).size(200f,80f).pad(50f, 15f, 15f, 0f);
        // exit button table
        tableExit.add(exitBtn).size(200f,80f).pad(0f, 0f, 30f, 0f);

        tablebackGround.add(background).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addActor(tablebackGround);
        stage.addActor(tableBtn);
        stage.addActor(tableTitle);
        //  stage.addActor(table);
        stage.addActor(tableExit);

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
