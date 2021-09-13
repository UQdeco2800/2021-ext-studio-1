package com.deco2800.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.utils.math.Vector2Utils;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {
  private final Vector2 walkDirection = Vector2.Zero.cpy();

  public KeyboardPlayerInputComponent() {
    super(5);
  }

  /**
   * Triggers player events on specific keycodes.
   *
   * @return whether the input was processed
   * @see InputProcessor#keyDown(int)
   */
  @Override
  public boolean keyDown(int keycode) {
    switch (keycode) {
      case Keys.W:
        walkDirection.add(Vector2Utils.UP);
//        triggerWalkEvent();
        entity.setPosition(entity.getPosition().x, entity.getPosition().y + 1.5f);
        if (entity.getPosition().y >= 8.5f){
          entity.setPosition(entity.getPosition().x,entity.getPosition().y - 1.5f);
        }
        return true;
      case Keys.S:
        walkDirection.add(Vector2Utils.DOWN);
//        triggerWalkEvent();
        entity.setPosition(entity.getPosition().x, entity.getPosition().y - 1.5f);
        if(entity.getPosition().y <= 3){
          entity.setPosition(entity.getPosition().x, entity.getPosition().y + 1.5f);
        }
        return true;

      case Keys.E:
        walkDirection.add(Vector2Utils.DOWN);
//        triggerWalkEvent();
        entity.setPosition(entity.getPosition().x, entity.getPosition().y = 3.25f);
        return true;

      case Keys.Q:
        walkDirection.add(Vector2Utils.UP);
//        triggerWalkEvent();
        entity.setPosition(entity.getPosition().x, entity.getPosition().y = 7.75f);
        return true;

      case Keys.SPACE:
        entity.getEvents().trigger("attack");
        return true;
      default:
        return false;
    }
  }

  /**
   * Triggers player events on specific keycodes.
   *
   * @return whether the input was processed
   * @see InputProcessor#keyUp(int)
   */
  @Override
  public boolean keyUp(int keycode) {
    switch (keycode) {
      case Keys.W:
        walkDirection.sub(Vector2Utils.UP);
//        triggerWalkEvent();
//        entity.setPosition(entity.getPosition().x, entity.getPosition().y + 1);
        return true;
      case Keys.S:
        walkDirection.sub(Vector2Utils.DOWN);
//        triggerWalkEvent();
//        entity.setPosition(entity.getPosition().x, entity.getPosition().y - 1);
        return true;
      case Keys.SPACE:
        entity.getEvents().trigger("unAttack");
        return true;
      default:
        return false;
    }
  }

  private void triggerWalkEvent() {
    if (walkDirection.epsilonEquals(Vector2.Zero)) {
      entity.getEvents().trigger("walkStop");
    } else {
      entity.getEvents().trigger("walk", walkDirection);
    }
  }
}
