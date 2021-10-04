package com.deco2800.game.components.maingame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.deco2800.game.GdxGame;
import com.deco2800.game.GdxGame.ScreenType;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.utils.StringDecorator;



/**
 * Displays a button to exit the Main Game screen to the Main Menu screen.
 */
public class MainGameExitDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(MainGameExitDisplay.class);
  private static final float Z_INDEX = 2f;
  private Table table;
  private Label timerLabel;
  public boolean exitx = false;


  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {
    table = new Table();
    table.top().right();
    table.setFillParent(true);

    CharSequence timerText = String.format("Timer: 60 sec");
    timerLabel = new Label(timerText, skin, "large");

    Button.ButtonStyle exitStyle = new Button.ButtonStyle();
    exitStyle.up= new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("images/NewBtn_exit1.png"))));
    exitStyle.over= new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("images/NewBtn_exit2.png"))));
    Button mainMenuBtn = new Button(exitStyle);

    // Triggers an event when the button is pressed.
    mainMenuBtn.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Exit button clicked");
                entity.getEvents().trigger("exit");
                exitx=true;
              }
            });

    new Thread() {
      public void run() {
        try {
          for(int i=60;i>0;i--){
            timerLabel.setText("Timer :"+ i+" sec");
            Thread.sleep(1000);
            if(exitx == true){
              break;
            }
          }
        }
        catch (InterruptedException e) {}
      }
    }.start();



    table.add(mainMenuBtn).size(200f,80f).padTop(10f).padRight(10f);
    table.row();
    table.add(timerLabel).pad(20);
    stage.addActor(table);
  }


  @Override
  public void draw(SpriteBatch batch) {
    // draw is handled by the stage
  }

  @Override
  public float getZIndex() {
    return Z_INDEX;
  }

  @Override
  public void dispose() {
    table.clear();
    super.dispose();
    timerLabel.remove();
  }
}