package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.*;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.EncryptedPrivateKeyInfo;
import java.security.Key;
import java.util.EmptyStackException;
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
  AnimationRenderComponent2 animator2;
  AnimationRenderComponent3 animator3;
  AnimationRenderComponent4 animator4;
  AnimationRenderComponent5 animator5;
  AnimationRenderComponent6 animator6;
  private int attackCount = 0;
  private boolean attackTrigger = false;


  @Override
  public void create() {
    animator = this.entity.getComponent(AnimationRenderComponent.class);
    animator2 = this.entity.getComponent(AnimationRenderComponent2.class);
    animator3 = this.entity.getComponent(AnimationRenderComponent3.class);
    animator4 = this.entity.getComponent(AnimationRenderComponent4.class);
    animator5 = this.entity.getComponent(AnimationRenderComponent5.class);
    animator6 = this.entity.getComponent(AnimationRenderComponent6.class);
    physicsComponent = entity.getComponent(PhysicsComponent.class);
    entity.getEvents().addListener("walk", this::walk);
    entity.getEvents().addListener("walkStop", this::stopWalking);
    entity.getEvents().addListener("attack", this::attack);
    entity.getEvents().addListener("unAttack", this::unAttack);
    entity.getEvents().addListener("run", this::attack);
    entity.getEvents().addListener("coin", this::attack);
  }

  @Override
  public void update() {
    if(animator5.getCurrentAnimation() == null) {
      animator5.startAnimation("run");
    }
    if(animator.getCurrentAnimation() != null || animator2.getCurrentAnimation() != null|| animator3.getCurrentAnimation() != null || animator4.getCurrentAnimation() != null || animator6.getCurrentAnimation() != null){
      animator5.stopAnimation();
    }
    if (animator2.isFinished() || animator3.isFinished() || animator4.isFinished() || animator6.isFinished()){
      animator2.stopAnimation();
      animator3.stopAnimation();
      animator4.stopAnimation();
      animator6.stopAnimation();
      animator5.startAnimation("run");
    }
    if (moving) {
      updateSpeed();
    }
    if(attackTrigger) {
      attackCount += 1;
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
    if(attackCount > 150 || attackTrigger == false){
      attackTrigger = true;
      logger.info("attack",attackTrigger);
      Array<Entity> entities = ServiceLocator.getEntityService().getEntities();
      Entity nearest = findNearestTargets(entities);
      logger.info("attack nearest--{}",attackCount);
      if (nearest != null) {
        if (nearest.getType().equals(Entity.Type.GHOST) || nearest.getType().equals(Entity.Type.GHOSTKING)) {
          logger.info("nearest.getType()--{}", nearest.getType());
          nearest.dispose();
          Sound attSound = ServiceLocator.getResourceService().getAsset("sounds/buff2.ogg", Sound.class);
          attSound.play();
          animator.startAnimation("buff2");
        }
      }
      Sound attackSound = ServiceLocator.getResourceService().getAsset("sounds/attack.ogg", Sound.class);
      attackSound.play();
      animator5.stopAnimation();
      animator.startAnimation("attack");
      attackCount = 0;
    }
  }

  void unAttack(){
    animator.stopAnimation();
    animator5.startAnimation("run");
  }


  private Entity findNearestTargets(Array<Entity> entities) {
    Entity result = null;
    float minDstEnemy = 1.8f;
    float minDstObstacle = 1.8f;
    for (Entity en: entities) {
      if (en.getType() == Entity.Type.GHOST || en.getType() == Entity.Type.GHOSTKING) {
        float dst = entity.getPosition().dst(en.getPosition());
        if (minDstEnemy > dst) {
          result = en;
        }
      } else if (en.getType() == Entity.Type.OBSTACLE ) {
        float dst = entity.getPosition().dst(en.getPosition());
        if (minDstObstacle > dst) {
          result = en;
        }
      }
    }
    return result;
  }
}
