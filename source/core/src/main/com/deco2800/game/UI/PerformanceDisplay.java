package com.deco2800.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.deco2800.game.services.ServiceLocator;

public class PerformanceDisplay extends UIComponent {
  private static final float zIndex = 5f;
  private Label profileLabel;

  @Override
  public void create() {
    super.create();
    addActors();
  }

  private void addActors() {
    profileLabel = new Label(getStats(), defaultWhiteText);
    stage.addActor(profileLabel);
  }

  @Override
  public void draw(SpriteBatch batch) {
    if (ServiceLocator.getRenderService().getDebug().getActive()) {
      profileLabel.setVisible(true);
      profileLabel.setText(getStats());

      int screenHeight = stage.getViewport().getScreenHeight();
      float padding = 5f;
      float offsetY = 180f;
      profileLabel.setPosition(padding, screenHeight - offsetY + padding);
    } else {
      profileLabel.setVisible(false);
    }
  }

  private String getStats() {
    String message = "Debug\n";
    message =
        message
            .concat(String.format("FPS: %d fps\n", Gdx.graphics.getFramesPerSecond()))
            .concat(String.format("RAM: %d MB\n", Gdx.app.getJavaHeap() / 1000000));
    return message;
  }

  @Override
  public float getZIndex() {
    return zIndex;
  }

  @Override
  public void dispose() {
    super.dispose();
    profileLabel.remove();
  }
}
