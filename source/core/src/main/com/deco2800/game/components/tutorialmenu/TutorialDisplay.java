package com.deco2800.game.components.tutorialmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tutorial display
 */
public class TutorialDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(TutorialDisplay.class);
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

        Label title = new Label("PLAYER MOVEMENT TUTORIAL", skin, "title", "white");

        Button.ButtonStyle content1 = new Button.ButtonStyle();
        content1.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/PW-1.png"))));
        content1.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/PW-2.png"))));
        Button content1Btn = new Button(content1);

        Button.ButtonStyle content2 = new Button.ButtonStyle();
        content2.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/PS-2.png"))));
        content2.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/PS-1.png"))));
        Button content2Btn = new Button(content2);

        Button.ButtonStyle content3 = new Button.ButtonStyle();
        content3.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/PE-2.png"))));
        content3.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/PE-1.png"))));
        Button content3Btn = new Button(content3);

        Button.ButtonStyle content4 = new Button.ButtonStyle();
        content4.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/PQ-1.png"))));
        content4.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/PQ-2.png"))));
        Button content4Btn = new Button(content4);

        Button.ButtonStyle content5 = new Button.ButtonStyle();
        content5.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/PAttack1.png"))));
        content5.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/PAttack2.png"))));
        Button content5Btn = new Button(content5);

        //Exit button
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();
        exitStyle.up= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_exit1.png"))));
        exitStyle.over= new TextureRegionDrawable(new TextureRegion(
                new Texture(Gdx.files.internal("images/FDS_btn_exit2.png"))));
        Button exitBtn = new Button(exitStyle);

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

        mapContentTutorialBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                logger.debug("Map Content Tutorial Button pressed");
                entity.getEvents().trigger("MapContentTutorial");
            }
        });
        // table with buttons
        tableBtn.add(playerMovementTutorialBtn).size(200f,80f).padTop(20f);
        tableBtn.add(enemyTutorialBtn).size(200f,80f).padLeft(100f).padTop(20f);
        tableBtn.add(mapContentTutorialBtn).size(200f,80f).padLeft(100f).padTop(20f);

        // table with title
        tableTitle.add(title).expandX().top().padTop(140f);

        // table with contents
        table.add(content1Btn).padLeft(20f).size(250f, 450f);
        table.add(content2Btn).padLeft(20f).size(250f, 450f);
        table.add(content3Btn).padLeft(20f).size(250f, 450f);
        table.add(content4Btn).padLeft(20f).size(250f, 450f);
        table.add(content5Btn).padLeft(20f).size(250f, 450f);

        // exit button table
        tableExit.add(exitBtn).size(200f,80f).pad(0f, 0f, 30f, 0f);

        tablebackGround.add(background).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addActor(tablebackGround);
        stage.addActor(tableBtn);
        stage.addActor(tableTitle);
        stage.addActor(table);
        stage.addActor(tableExit);

    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void dispose() {
        super.dispose();
        stack.clear();
    }
}
