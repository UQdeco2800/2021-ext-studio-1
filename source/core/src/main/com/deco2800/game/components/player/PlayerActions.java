package com.deco2800.game.components.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Scanner;

/**
 * Action component for interacting with the player. Player events should be initialised in create()
 * and when triggered should call methods within this class.
 */
public class PlayerActions extends Component {
  private static final Vector2 MAX_SPEED = new Vector2(3f, 3f); // Metres per second
  private static final Logger logger = LoggerFactory.getLogger(PlayerActions.class);
  private PhysicsComponent physicsComponent;
  private Vector2 walkDirection = Vector2.Zero.cpy();
  private boolean moving = false;
  private  CombatStatsComponent combatStatsComponent;
  AnimationRenderComponent animator;

  @Override
  public void create() {
    animator = this.entity.getComponent(AnimationRenderComponent.class);
    physicsComponent = entity.getComponent(PhysicsComponent.class);
    entity.getEvents().addListener("walk", this::walk);
    entity.getEvents().addListener("walkStop", this::stopWalking);
    entity.getEvents().addListener("attack", this::attack);
    entity.getEvents().addListener("unAttack", this::unAttack);
  }

  @Override
  public void update() {
    if (moving) {
      updateSpeed();
    }
  }

  private void updateSpeed() {
    Body body = physicsComponent.getBody();
    Vector2 velocity = body.getLinearVelocity();
    Vector2 desiredVelocity = walkDirection.cpy().scl(MAX_SPEED);
    // impulse = (desiredVel - currentVel) * mass
    Vector2 impulse = desiredVelocity.sub(velocity).scl(body.getMass());
    body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
  }

  /**
   * Moves the player towards a given direction.
   *
   * @param direction direction to move in
   */
  void walk(Vector2 direction) {
    this.walkDirection = direction;
    moving = true;
  }

  /**
   * Stops the player from walking.
   */
  void stopWalking() {
    this.walkDirection = Vector2.Zero.cpy();
    updateSpeed();
    moving = false;
  }

  /**
   * Makes the player attack.
   */
  void attack() {
    logger.info("attack");
    Array<Entity> entities = ServiceLocator.getEntityService().getEntities();
    Entity nearest = findNearestTargets(entities);
    logger.info("attack nearest--{}", nearest);
    if (nearest != null) {
      if (nearest.getType().equals(Entity.Type.GHOSTKING)) {
        logger.info("nearest.getType()--{}", nearest.getType());
        nearest.attack();
      }
//      else if (nearest.getType().equals(Entity.Type.BREAD) || nearest.getType().equals(Entity.Type.AID)) {
//        logger.info ("nearest.getType()--{}", nearest.getType());
//        nearest.dispose();
//        Sound attSound = ServiceLocator.getResourceService().getAsset("sounds/buff.ogg", Sound.class);
//        attSound.play();
//        animator.startAnimation("buff");
//      }
      else if (nearest.getType().equals(Entity.Type.GHOST) || nearest.getType().equals(Entity.Type.OBSTACLE)) {
        logger.info ("nearest.getType()--{}", nearest.getType());
        nearest.dispose();
        Sound attSound = ServiceLocator.getResourceService().getAsset("sounds/buff2.ogg", Sound.class);
        attSound.play();
        animator.startAnimation("buff2");
      }
//      } else if (nearest.getType().equals(Entity.Type.BREAD) || nearest.getType().equals(Entity.Type.AID)) {
//        logger.info ("nearest.getType()--{}", nearest.getType());
//        nearest.dispose();
//        Sound attSound = ServiceLocator.getResourceService().getAsset("sounds/buff.ogg", Sound.class);
//        attSound.play();
//        animator.startAnimation("buff");
//      } else {
//        logger.info ("nearest.getType()--{}", nearest.getType());
//        nearest.dispose();
//        Sound attSound = ServiceLocator.getResourceService().getAsset("sounds/buff2.ogg", Sound.class);
//        attSound.play();
//        animator.startAnimation("buff2");

    }
    Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/attack.ogg", Sound.class);
    attackSound.play();
    animator.startAnimation("attack");
  }

  void unAttack(){
    animator.stopAnimation();
  }

  private Entity findNearestTargets(Array<Entity> entities) {
    Entity result = null;
    float minDstEnemy = 2.0f;
    float minDstObstacle = 2.0f;
    for (Entity en: entities) {
      if (en.getType() == Entity.Type.GHOST || en.getType() == Entity.Type.GHOSTKING) {
        float dst = entity.getPosition().dst(en.getPosition());
        if (minDstEnemy > dst) {
          minDstEnemy = dst;
          result = en;
        }
      } else if (en.getType() == Entity.Type.OBSTACLE ) {
        float dst = entity.getPosition().dst(en.getPosition());
        if (minDstObstacle > dst) {
          minDstObstacle = dst;
          result = en;
        }
      }
    }
    return result;
  }

//  void touch() {
//    Array<Entity> entities = ServiceLocator.getEntityService().getEntities();
//    Entity nearest = findNearestTargets(entities);
//    if (nearest != null) {
//      if (nearest.getType().equals(Entity.Type.BREAD) || nearest.getType().equals(Entity.Type.AID)) {
//        entities.removeValue(nearest, true);
//        Sound attackSound1 = ServiceLocator.getResourceService().getAsset("sounds/buff.ogg", Sound.class);
//        attackSound1.play();
//        animator.startAnimation("attack");
//      }
//    }
//  }
}
