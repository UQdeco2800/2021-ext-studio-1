package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.rendering.*;
import com.deco2800.game.components.player.PlayerActions;

/**
 * A ui component for displaying player stats, e.g. health.
 */
public class PlayerStatsDisplay extends UIComponent {
  Table table;
  Table notification;
  Table heartAnimate;
  Table goldAnimate;
  Table goldBoard;

  private Image heartImage;
  private Image armourImage;
  private Image noImage;
  private Image treatImage;
  private Image goldImage;
  private Image coinCollectorImage;

  private Label healthLabel;
  private Label armourLabel;
  private Label goldLabel;

  private String treatFileName;
  private String goldFileName;

  private final float armourSideLength = 200f;
  private final float heartSideLength = 200f;
  private final float coinSideLength = 200f;
  private final float coinWidth = 60f;
  private final float coinHeight = 60f;

  /**
   * Creates reusable ui styles and adds actors to the stage.
   */
  @Override
  public void create() {
    super.create();
    addActors();
    entity.getEvents().addListener("updateHealth", this::updatePlayerHealthUI);
    entity.getEvents().addListener("updateArmour", this::updatePlayerArmourUI);
    entity.getEvents().addListener("updateGold", this::updatePlayerGoldUI);
  }

  /**
   * Creates actors and positions them on the stage using a table.
   * @see Table for positioning options
   */
  private void addActors() {
    table = new Table();
    table.top().left();
    table.setFillParent(true);
    table.padTop(30f).padLeft(5f);

    // Heart image
    heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/health_full.png", Texture.class));

    // Health text
    int health = entity.getComponent(CombatStatsComponent.class).getHealth();
    CharSequence healthText = String.format("Health: %d", health);
    healthLabel = new Label(healthText, skin, "large");

    //Armour image
    armourImage = new Image(ServiceLocator.getResourceService().getAsset("images/armour_full.png", Texture.class));

    //Coin Collector System
    coinCollectorImage = new Image(ServiceLocator.getResourceService().getAsset("images/coincollectortransparentvisual.png", Texture.class));

    // Armour text
    int armour = entity.getComponent(CombatStatsComponent.class).getArmour();
    CharSequence armourText = String.format("Armour: %d", armour);
    armourLabel = new Label(armourText, skin, "large");
    table.add(heartImage).size(heartSideLength).pad(5);
    table.add(armourImage).size(armourSideLength).padLeft(15);
    table.add(goldLabel).size(0).padLeft(15);
    stage.addActor(table);

    // Gold board
    entity.getComponent(InventoryComponent.class).setGold(0);
    int gold = entity.getComponent(InventoryComponent.class).getGold();
    CharSequence goldText = String.format("Gold: %d", gold);
    goldLabel = new Label(goldText, skin, "large");
    goldBoard = new Table();
    goldBoard.top().left();
    goldBoard.setFillParent(true);
    goldBoard.padTop(20).padLeft(30);
    goldBoard.add(goldLabel).padLeft(50);
    stage.addActor(goldBoard);

    Stack goldCount = new Stack();

    coinCollectorImage.setSize(coinWidth, coinHeight);

    goldCount.add(coinCollectorImage);
    goldCount.add(goldBoard);
    goldCount.setSize(coinWidth, coinHeight);
    table.add(goldCount).size(coinWidth, coinHeight).padTop(30).padLeft(-775);
    stage.addActor(table);

    // Notification
    notification = new Table();
    notification.top();
    notification.setFillParent(true);
    notification.padTop(0).padLeft(5f);
    float noWidth = 519f;
    float noHeight = 160f;
    noImage = new Image(ServiceLocator.getResourceService().getAsset("images/notification.png", Texture.class));
    notification.add(noImage).size(noWidth, noHeight).pad(5);
    stage.addActor(notification);
    notification.setVisible(false);
  }

  /**
   * Player get hurt Animation
   */
  private void hurtAnimate() {
    heartAnimate =  new Table();
    heartAnimate.top().left();
    heartAnimate.setFillParent(true);
    heartAnimate.padTop(70f).padLeft(210f);
    new Thread() {
      public void run() {
        try {
          for (int i = 0; i <= 4;i++) {
            heartAnimate.clearChildren();
            treatFileName = String.format("images/hurt%d.png",i);
            treatImage = new Image(ServiceLocator.getResourceService().getAsset(treatFileName, Texture.class));
            heartAnimate.add(treatImage).size(50f,50f).pad(-15);
            Thread.sleep(70);
            heartAnimate.clearChildren();
            stage.addActor(heartAnimate);
          }
        }
        catch (Exception e) {
          //pass
        }
      }
    }.start();

  }

  /**
   * Player get gold Animation
   */
  private void goldAnimate() {
    goldAnimate =  new Table();
    goldAnimate.top().left();
    goldAnimate.setFillParent(true);
    goldAnimate.padTop(125f).padLeft(180f);
    new Thread() {
      public void run() {
        try {
          for (int i = 0; i <= 4;i++) {
            goldAnimate.clearChildren();
            goldFileName =String.format("images/10coin%d.png",i);
            goldImage = new Image(ServiceLocator.getResourceService().getAsset(goldFileName, Texture.class));
            goldAnimate.add(goldImage).size(70f,70f).pad(-15);
            Thread.sleep(70);
            goldAnimate.clearChildren();
          }
        }
        catch (InterruptedException e) {
          //pass
        }
      }
    }.start();
    stage.addActor(goldAnimate);
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
    //Update the number of health
    //CharSequence text = String.format("Health: %d", health);
    //healthLabel.setText(text);

    //Update the health bar & Armour Bar
    if(health>=0) {
      table.removeActor(heartImage);
      heartImage.remove();
      if (health == 3) {
        heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/health_full.png", Texture.class));
      }
      if (health == 2) {
        heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/health_decrease_one.png", Texture.class));
      }
      if (health == 1) {
        heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/health_decrease_two.png", Texture.class));
      }
      if (health <= 0) {
        heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/health_empty.png", Texture.class));
        refreshDisplay();
        getEntity().getEvents().trigger("death");


        return;
      }
      refreshDisplay();
    }

    //Hurt animation
    if (health >= 0) {
      hurtAnimate();
    }

    //Notification appears and disposes
    if(health>=0) {
      new Thread() {
        public void run() {
          try {
            notification.setVisible(true);
            Thread.sleep(1500);
            notification.setVisible(false);
          }
          catch (InterruptedException e) {
            //pass
          }
        }
      }.start();
    }
  }

  /**
   * Updates the player's armour on the ui.
   * @param armour player armour
   */
  public void updatePlayerArmourUI(int armour) {
    //Update the number of armour
    //CharSequence text = String.format("Armour: %d", armour);
    //armourLabel.setText(text);

    if (armour >= 0) {
      table.removeActor(armourImage);
      armourImage.remove();
      if (armour == 3) {
        armourImage = new Image(ServiceLocator.getResourceService().getAsset("images/armour_full.png", Texture.class));
      }
      if (armour == 2) {
        armourImage = new Image(ServiceLocator.getResourceService().getAsset("images/armour_decrease_one.png", Texture.class));
      }
      if (armour == 1) {
        armourImage = new Image(ServiceLocator.getResourceService().getAsset("images/armour_decrease_two.png", Texture.class));
      }
      if (armour == 0) {
        armourImage = new Image(ServiceLocator.getResourceService().getAsset("images/armour_empty.png", Texture.class));
      }
      refreshDisplay();
    }
  }

  /**
   * Updates the player's gold on the ui.
   */
  public void updatePlayerGoldUI(int newGold) {
    // entity.getComponent(InventoryComponent.class).setGold(entity.getComponent(InventoryComponent.class).getGold() + 10);
    // int gold = entity.getComponent(InventoryComponent.class).getGold();
    System.out.println("inside updategoldui " + newGold);
    CharSequence text = String.format("Gold: %d", newGold);
    goldLabel.setText(text);
    goldAnimate();
    refreshDisplay();
  }

  @Override
  public void dispose() {
    super.dispose();
    heartImage.remove();
    healthLabel.remove();
    armourImage.remove();
    armourLabel.remove();
    noImage.remove();
    heartAnimate.remove();
    treatImage.remove();
    goldLabel.remove();
  }

  //Refreshes the display redrawing all components
  public void refreshDisplay() {

    Stack goldCount = new Stack();

    coinCollectorImage.setSize(coinWidth, coinHeight);
    goldCount.add(coinCollectorImage);
    goldCount.add(goldBoard);
    goldCount.setSize(coinWidth, coinHeight);

    table.reset();
    table.top().left();
    table.setFillParent(true);
    table.padTop(30f).padLeft(5f);
    table.add(heartImage).size(heartSideLength).pad(5);
    table.add(armourImage).size(armourSideLength).padLeft(15);
    table.add(goldCount).size(coinWidth, coinHeight).padTop(30).padLeft(-775);

  }
}

