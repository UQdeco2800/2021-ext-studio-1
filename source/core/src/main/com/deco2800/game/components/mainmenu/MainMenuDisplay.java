package com.deco2800.game.components.mainmenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.services.ServiceLocator;
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

/**
 * A ui component for displaying the Main menu.
 */
public class MainMenuDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(MainMenuDisplay.class);
  private static final float Z_INDEX = 2f;
  protected Table table;
  protected Table tablebackGround;
  protected Stack stack;

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {
    table = new Table();
    table.setFillParent(true);

      tablebackGround = new Table();
      tablebackGround.setFillParent(true);

      Image background = new Image(ServiceLocator.getResourceService().getAsset("images/space-background.png",
              Texture.class));

    Image title =
        new Image(
            ServiceLocator.getResourceService()
                .getAsset("images/purple-title-2.png", Texture.class));

      // Adding Image buttons
      // start button

      Button.ButtonStyle startStyle = new Button.ButtonStyle();
      startStyle.up= new TextureRegionDrawable(new TextureRegion(
              new Texture(Gdx.files.internal("images/FDS_btn_start1.png"))));
      startStyle.over= new TextureRegionDrawable(new TextureRegion(
              new Texture(Gdx.files.internal("images/FDS_btn_start2.png"))));
      Button startBtn = new Button(startStyle);

      // Setting Button
      Button.ButtonStyle settingStyle = new Button.ButtonStyle();
      settingStyle.up= new TextureRegionDrawable(new TextureRegion(
              new Texture(Gdx.files.internal("images/FDS_btn_setting1.png"))));
      settingStyle.over= new TextureRegionDrawable(new TextureRegion(
              new Texture(Gdx.files.internal("images/FDS_btn_setting2.png"))));
      Button settingsBtn = new Button(settingStyle);

      // Tutorial button
    Button.ButtonStyle tutorialStyle = new Button.ButtonStyle();
    tutorialStyle.up= new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("images/FDS_btn_tutorial1.png"))));
    tutorialStyle.over= new TextureRegionDrawable(new TextureRegion(
            new Texture(Gdx.files.internal("images/FDS_btn_tutorial2.png"))));
    Button tutorialBtn = new Button(tutorialStyle);

      // exit button
      Button.ButtonStyle exitStyle = new Button.ButtonStyle();
      exitStyle.up= new TextureRegionDrawable(new TextureRegion(
              new Texture(Gdx.files.internal("images/FDS_btn_exit1.png"))));
      exitStyle.over= new TextureRegionDrawable(new TextureRegion(
              new Texture(Gdx.files.internal("images/FDS_btn_exit2.png"))));
      Button exitBtn = new Button(exitStyle);

        // Triggers an event when the button is pressed
        startBtn.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Start button clicked");
                entity.getEvents().trigger("start");
              }
            });

        // for tutorial
    tutorialBtn.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {
                logger.debug("Tutorial button clicked");
                entity.getEvents().trigger("tutorial");
              }
            });

    settingsBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Settings button clicked");
            entity.getEvents().trigger("settings");
          }
        });

        exitBtn.addListener(
            new ChangeListener() {
              @Override
              public void changed(ChangeEvent changeEvent, Actor actor) {

                logger.debug("Exit button clicked");
                entity.getEvents().trigger("exit");
              }
            });

    table.add(title).padBottom(60f);
    table.row();

    table.add(startBtn).size(200f,80f).padTop(20f);
    table.row();
    table.add(settingsBtn).size(200f,80f).padTop(15f);
    table.row();
    table.add(tutorialBtn).size(200f,80f).padTop(15f);
    table.row();
    table.add(exitBtn).size(200f,80f).padTop(15f);

      tablebackGround.add(background).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

      stage.addActor(tablebackGround);

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
  }
}
