package com.deco2800.game.components.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.areas.RainbowBridge;
import com.deco2800.game.components.bridge.Bridge;
import com.deco2800.game.components.bridge.Lane;
import com.deco2800.game.input.InputComponent;
import com.deco2800.game.screens.RagnorakRacer;
import com.deco2800.game.utils.math.Vector2Utils;

import java.util.List;

/**
 * Input handler for the player for keyboard and touch (mouse) input.
 * This input handler only uses keyboard input.
 */
public class KeyboardPlayerInputComponent extends InputComponent {
  private final Vector2 walkDirection = Vector2.Zero.cpy();
  public static int i = 0;

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
    Bridge bridge = RagnorakRacer.rainbowBridge;
    List<Lane> lanes = bridge.getLanes();
//    for (Lane lane : lanes){
//      System.out.println(lane.getMid());
//    }
    float newY = 0;
    if (keycode == Keys.W){
      if(entity.getPosition().y <= lanes.get(3).getMid() - 9){
        System.out.println(lanes.get(3).getMid());
        entity.setPosition(entity.getPosition().x, lanes.get(i).getMid() - (1.7f * (i + 1)));
        newY = lanes.get(i).getMid() - (1.6f * (i + 1));
        i += 1;
        System.out.println("current lane number:" + i);
        System.out.println(newY);
      }
    } else if (keycode == Keys.S){
      if (entity.getPosition().y >= lanes.get(0).getMid() - 4){
          System.out.println(lanes.get(0).getMid());
          if (i > 0){
//            entity.setPosition(entity.getPosition().x, lanes.get(0).getMid() - (1.6f * (i + 2)));
//            System.out.println("new i = " + i);
            entity.setPosition(entity.getPosition().x, lanes.get(i - 1).getMid() - (1.7f * (i + 1)));
            i -= 1;
          }

          System.out.println("current lane number:" + i);
      }
    }else if (keycode == Keys.E){

      entity.setPosition(entity.getPosition().x, lanes.get(0).getMid() - (1.6f * (1 + 1)));
      i = 0;
    }else if (keycode == Keys.Q){
      entity.setPosition(entity.getPosition().x, lanes.get(3).getMid() - (1.6f * (4 + 1)));
      i = 3;
    }else if (keycode == Keys.SPACE){
      entity.getEvents().trigger("attack");
    }
    return true;
//    switch (keycode) {
//      case Keys.W:
//        walkDirection.add(Vector2Utils.UP);
//        for (Lane lane : lanes){
//          System.out.println(lane.getMid());
//        }
////        triggerWalkEvent();
//        entity.setPosition(entity.getPosition().x, entity.getPosition().y + 1.5f);
//        if (entity.getPosition().y >= 8.5f){
//          entity.setPosition(entity.getPosition().x,entity.getPosition().y - 1.5f);
//        }
//        return true;
//      case Keys.S:
//        walkDirection.add(Vector2Utils.DOWN);
////        triggerWalkEvent();
//        entity.setPosition(entity.getPosition().x, entity.getPosition().y - 1.5f);
//        if(entity.getPosition().y <= 3){
//          entity.setPosition(entity.getPosition().x, entity.getPosition().y + 1.5f);
//        }
//        return true;
//
//      case Keys.E:
//        walkDirection.add(Vector2Utils.DOWN);
////        triggerWalkEvent();
//        entity.setPosition(entity.getPosition().x, entity.getPosition().y = 3.25f);
//        return true;
//
//      case Keys.Q:
//        walkDirection.add(Vector2Utils.UP);
////        triggerWalkEvent();
//        entity.setPosition(entity.getPosition().x, entity.getPosition().y = 7.75f);
//        return true;
//
//      case Keys.SPACE:
//        entity.getEvents().trigger("attack");
//        return true;
//      default:
//        return false;
//    }
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
    entity.getEvents().trigger("run");
    if (walkDirection.epsilonEquals(Vector2.Zero)) {
      entity.getEvents().trigger("run");
    } else {
      entity.getEvents().trigger("walk", walkDirection);
    }
  }
}
