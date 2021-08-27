package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import javax.swing.*;

/**
 * A ui component for displaying player stats, e.g. health.
 */
public class PlayerStatsDisplay extends UIComponent {
  Table table;
  Table notification;
  Table heartAnimat;
  private Image heartImage;
  private Label healthLabel;
  private Image armourImage;
  private Label armourLabel;
  private Image noImage;
  private Image treatImage;


  /**
   * Creates reusable ui styles and adds actors to the stage.
   */
  @Override
  public void create() {
    super.create();
    addActors();

    entity.getEvents().addListener("updateHealth", this::updatePlayerHealthUI);
  }

  /**
   * Creates actors and positions them on the stage using a table.
   * @see Table for positioning options
   */
  private void addActors() {
    table = new Table();
    table.top().left();
    table.setFillParent(true);
    table.padTop(30).padLeft(5f);

    // Heart image
    float heartSideLength = 200f;
    heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/health_full.png", Texture.class));

    // Health text
    int health = entity.getComponent(CombatStatsComponent.class).getHealth();
    CharSequence healthText = String.format("Health: %d", health);
    healthLabel = new Label(healthText, skin, "large");

    // Armour image for when there is an image
    //float armourSideLength = 60f;
    //armourImage = new Image(ServiceLocator.getResourceService().getAsset("images/armour.png", Texture.class));

    // Armour text
    int armour = entity.getComponent(CombatStatsComponent.class).getArmour();
    CharSequence armourText = String.format("Armour: %d", armour);
    armourLabel = new Label(armourText, skin, "large");

    table.add(heartImage).size(heartSideLength).pad(5);
    stage.addActor(table);

    Table healthStats = new Table();
    healthStats.top().left();
    healthStats.setFillParent(true);
    healthStats.padTop(100f).padLeft(5f);

    healthStats.add(healthLabel).pad(20);
    //table.add(armourImage).size(armourSideLength).pad(5);
    healthStats.add(armourLabel);
    stage.addActor(healthStats);

    // Notification
    notification = new Table();
    notification.top();
    notification.setFillParent(true);
    notification.padTop(0).padLeft(5f);
    float noWidth = 519f;
    float noHeight = 213f;
    noImage = new Image(ServiceLocator.getResourceService().getAsset("images/notification.png", Texture.class));

    notification.add(noImage).size(noWidth, noHeight).pad(5);
    stage.addActor(notification);
    notification.setVisible(false);

    //Player get treat Animation
    heartAnimat =  new Table();
    heartAnimat.top();
    heartAnimat.padTop(0f).padRight(1500f);
    heartAnimat.setFillParent(true);
    treatImage = new Image(ServiceLocator.getResourceService().getAsset("images/treat0.png", Texture.class));

    heartAnimat.add(treatImage).size(64f,64f).pad(5);
    stage.addActor(heartAnimat);
    heartAnimat.setVisible(false);
  }

  @Override
  public void draw(SpriteBatch batch)  {
    // draw is handled by the stage
  }

  /**
   * Updates the player's health on the ui.
   * @param health player health
   */
  public void updatePlayerHealthUI(int health) {
    CharSequence text = String.format("Health: %d", health);
    healthLabel.setText(text);

    //Notification appears and disposes
    new Thread() {
      public void run() {
        try {
          notification.setVisible(true);
          heartAnimat.setVisible(true);
          Thread.sleep(1500);
          notification.setVisible(false);
          heartAnimat.setVisible(false);
        }
        catch (InterruptedException e) {}
      }
    }.start();
  }

  @Override
  public void dispose() {
    super.dispose();
    heartImage.remove();
    healthLabel.remove();
    noImage.remove();
  }


}

