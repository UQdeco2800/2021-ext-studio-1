package com.deco2800.game.components.winscreen;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GameWinDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(GameWinDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;


    @Override
    public void create(){
        super.create();
        createGameWin();
    }

    private void createGameWin() {

        table = new Table();
        table.setFillParent(true);
        //Label gameWinLable = new Label("YOU WIN",skin);
        //Label gameWinTextLable = new Label("You have survived the horders of terror on " +
        //        "the Bifrost. Theevil hela awaits you.",skin);
        Image title =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/Win-screen-2-transparent.png", Texture.class));
        TextButton replayButton = new TextButton("Replay", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        replayButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("replay game button clicked");
                        entity.getEvents().trigger("replay");
                    }
                });

        exitButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.info("exit button clicked");
                        entity.getEvents().trigger("exit");
                    }
                });


        table.add(title).top();
        table.row();
        //table.add(replayButton).padTop(-30f);
        table.add(replayButton).padTop(30f);
        table.row();
        table.add(exitButton).padTop(15f);
        //table.setTransform(false);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        table.clear();
        super.dispose();
    }

    @Override
    public float getZIndex() {
        return Z_INDEX;
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}
