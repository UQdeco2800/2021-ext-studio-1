package com.deco2800.game.components;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.rendering.*;
import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.components.player.PlayerActions;
import com.deco2800.game.components.player.InventoryComponent;

/**
 * Component used to store information related to combat such as health, attack, etc. Any entities
 * which engage it combat should have an instance of this class registered. This class can be
 * extended for more specific combat needs.
 */
public class CombatStatsComponent extends Component {

  private static final Logger logger = LoggerFactory.getLogger(CombatStatsComponent.class);
  private int health;
  private int armour;
  private int gold;
  private int baseAttack;
  private long invincibleStart = 0L;
  AnimationRenderComponent animator;
  AnimationRenderComponent5 animator2;


  public CombatStatsComponent(int health, int baseAttack) {
    setHealth(health);
    setBaseAttack(baseAttack);
  }

  public CombatStatsComponent(int health, int baseAttack, int armour) {
    setHealth(health);
    setBaseAttack(baseAttack);
    setArmour(armour);
  }

  /**
   * Returns true if the entity's has 0 health, otherwise false.
   *
   * @return is player dead
   */
  public Boolean isDead() {
   return health == 0;
  }

  /**
   * Returns the entity's health.
   *
   * @return entity's health
   */
  public int getHealth() {
    return health;
  }

  /**
   * Returns the entity's armour.
   *
   * @return entity's armour
   */
  public int getArmour() {
    return armour;
  }

  /**
   * Returns the entity's gold.
   *
   * @return entity's armour
   */
  public int getGold() {
    return armour;
  }

  /**
   * Sets the entity's health. Health has a minimum bound of 0.
   *
   * @param health health
   */
  public void setHealth(int health) {
    if (health >= 0) {
      this.health = health;
    } else {
      this.health = 0;
    }
    if (entity != null) {
      entity.getEvents().trigger("updateHealth", this.health);
    }
  }

  /**
   * Sets the entity's armour. Armour has a minimum bound of 0.
   *
   * @param armour armour
   */
  public void setArmour(int armour) {
    if (armour >= 0) {
      this.armour = armour;
    } else {
      this.armour = 0;
    }
    if (entity != null) {
      entity.getEvents().trigger("updateArmour", this.armour);
    }
  }

  /**
   * Adds to the player's health. The amount added can be negative.
   *
   * @param health health to add
   */
  public void addHealth(int health) {
    setHealth(this.health + health);
  }

  /**
   * Adds to the player's armour. The amount added can not be negative.
   *
   * @param armour armour to add
   */
  public void addArmour(int armour) {
    setArmour(this.armour + armour);
  }

  /**
   * Returns the entity's base attack damage.
   *
   * @return base attack damage
   */
  public int getBaseAttack() {
    return baseAttack;
  }

  /**
   * Sets the entity's attack damage. Attack damage has a minimum bound of 0.
   *
   * @param attack Attack damage
   */
  public void setBaseAttack(int attack) {
    //removed check for all negative value base attack because BREAD AND FIRST AID KITS USE 
    // NEGATIVE ATTACK VALUES TO INCREASE HEALTH
    if (attack >= -3) {
      this.baseAttack = attack;
    }

  }

  public void hit(CombatStatsComponent attacker) {
    try {
      if (ServiceLocator.getTimeSource().getTimeSince(invincibleStart) < 1000L) {
        return;
      }
      if(attacker.getHealth() <= 0){
        AnimationRenderComponent7 animator7 =
                attacker.getEntity().getComponent(AnimationRenderComponent7.class);
        animator7.startAnimation("death");
        getEntity().getEvents().trigger("GameOver");
      }
      if (attacker.getEntity().getType() == Entity.Type.PLAYER) {
        logger.error("attacker--{}", attacker.getEntity().getType());
        AnimationRenderComponent2 animator =
                attacker.getEntity().getComponent(AnimationRenderComponent2.class);
        animator.startAnimation("touch");
        try{
        Sound hitSound = ServiceLocator.getResourceService().getAsset("sounds" +
                "/e.ogg", Sound.class);
        hitSound.play();
        } catch (GdxRuntimeException e) {
          //pass
        }
        logger.error("--end--attacker--{}",attacker.getEntity().getType());
      }
      int newHealth;
      if (armour > 0){
        int newArmour = getArmour() - attacker.getBaseAttack();
        setArmour(newArmour);
        invincibleStart = ServiceLocator.getTimeSource().getTime();
      }  else {
        if (attacker.getBaseAttack() == -3) { //this check is for first aid kits which fully restores health
          newHealth = 3; 
        } else {
          //for other health objects, check players health is not already full. If it is, keep health as is
          if (!(attacker.getBaseAttack() < 0 && getHealth() == 3)) {
            newHealth = getHealth() - attacker.getBaseAttack();
          } else {
            newHealth = 3;
          }
        }
        setHealth(newHealth);
        invincibleStart = ServiceLocator.getTimeSource().getTime();
      }
    } catch (NullPointerException e) {
      int newHealth = getHealth() - attacker.getBaseAttack();
      setHealth(newHealth);
    }
  }


  public void hitBuff(CombatStatsComponent attacker) {
    try {
      if (ServiceLocator.getTimeSource().getTimeSince(invincibleStart) < 1000L) {
        return;
      }
      if (attacker.getEntity().getType() == Entity.Type.PLAYER) {
        logger.error("attacker--{}", attacker.getEntity().getType(),
                attacker.getEntity());
        AnimationRenderComponent3 animator =
                attacker.getEntity().getComponent(AnimationRenderComponent3.class);
        animator.startAnimation("buff");
        try {
        Sound buffSound = ServiceLocator.getResourceService().getAsset(
                "sounds/buff_recover.ogg", Sound.class);
        buffSound.play();
        } catch (GdxRuntimeException e) {
          //pass
        }

        logger.error("--end--attacker--{}",attacker.getEntity().getType());

      }

    } catch (NullPointerException e) {
      int newHealth = getHealth() - attacker.getBaseAttack();
      setHealth(newHealth);
    }
  }

  public void hitDeBuff(CombatStatsComponent attacker) {
    try {
      if (ServiceLocator.getTimeSource().getTimeSince(invincibleStart) < 1000L) {
        return;
      }

      if (attacker.getEntity().getType() == Entity.Type.PLAYER) {
        logger.error("attacker--{}", attacker.getEntity().getType(),attacker.getEntity());
        AnimationRenderComponent4 animator =
                attacker.getEntity().getComponent(AnimationRenderComponent4.class);
        animator.startAnimation("deBuff");

        try {
        Sound deBuffSound = ServiceLocator.getResourceService().getAsset(
                "sounds/e.ogg", Sound.class);
        deBuffSound.play();
        } catch (GdxRuntimeException e) {
          //pass;
        }
        logger.error("--end--attacker--{}",attacker.getEntity().getType());
      }

//      if (armour > 0){
//        int newArmour = getArmour() - attacker.getBaseAttack();
//        setArmour(newArmour);
//        invincibleStart = ServiceLocator.getTimeSource().getTime();
//      }
//      else{
//        int newHealth = getHealth() - attacker.getBaseAttack();
//        setHealth(newHealth);
//        invincibleStart = ServiceLocator.getTimeSource().getTime();
//      }

    } catch (NullPointerException e) {
      int newHealth = getHealth() - attacker.getBaseAttack();
      setHealth(newHealth);
    }

  }
  public void hitCoins(Entity player) {
    CombatStatsComponent attacker = entity.getComponent(CombatStatsComponent.class);
    try {
      if (ServiceLocator.getTimeSource().getTimeSince(invincibleStart) < 1000L) {
        return;
      }

      if (attacker.getEntity().getType() == Entity.Type.COLLECTABLES) {
        InventoryComponent inventoryStats = player.getComponent(InventoryComponent.class);
        logger.error("attacker--{}", attacker.getEntity().getType(),attacker.getEntity());
//        AnimationRenderComponent6 animator =
//                attacker.getEntity().getComponent(AnimationRenderComponent6.class);
//        animator.startAnimation("coin");
//        AnimationRenderComponent4 animator =
//                attacker.getEntity().getComponent(AnimationRenderComponent4.class);
//        animator.startAnimation("deBuff");
//        Sound coinSound = ServiceLocator.getResourceService().getAsset(
//                "sounds/coin.ogg", Sound.class);
//        coinSound.play();
        logger.error("--end--attacker--{}",attacker.getEntity().getType());
        entity.getEvents().trigger("coin");

        inventoryStats.addGold(1);
      }
      player.getComponent(InventoryComponent.class).addGold(1);

    } catch (NullPointerException e) {
      player.getComponent(InventoryComponent.class).addGold(1);
    }

  }

  public void hitCoin(CombatStatsComponent attacker) {
    try {
      if (ServiceLocator.getTimeSource().getTimeSince(invincibleStart) < 1000L) {
        return;
      }

      if (attacker.getEntity().getType() == Entity.Type.PLAYER) {
        logger.error("attacker--{}", attacker.getEntity().getType(),attacker.getEntity());
        AnimationRenderComponent6 animator =
                attacker.getEntity().getComponent(AnimationRenderComponent6.class);
        animator.startAnimation("coin");
                Sound coinSound = ServiceLocator.getResourceService().getAsset(
                "sounds/coin.ogg", Sound.class);
        coinSound.play();

        try {
          Sound deBuffSound = ServiceLocator.getResourceService().getAsset(
                  "sounds/e.ogg", Sound.class);
          deBuffSound.play();
        } catch (GdxRuntimeException e) {
          //pass;
        }
        logger.error("--end--attacker--{}",attacker.getEntity().getType());
      }

//      if (armour > 0){
//        int newArmour = getArmour() - attacker.getBaseAttack();
//        setArmour(newArmour);
//        invincibleStart = ServiceLocator.getTimeSource().getTime();
//      }
//      else{
//        int newHealth = getHealth() - attacker.getBaseAttack();
//        setHealth(newHealth);
//        invincibleStart = ServiceLocator.getTimeSource().getTime();
//      }

    } catch (NullPointerException e) {
      int newHealth = getHealth() - attacker.getBaseAttack();
      setHealth(newHealth);
    }

  }



  public void getBackNormal(CombatStatsComponent attacker){
    logger.error("attacker--{}", attacker.getEntity().getType());
    AnimationRenderComponent4 animator =
            attacker.getEntity().getComponent(AnimationRenderComponent4.class);
    animator.startAnimation("default");
    logger.error("--end--attacker--{}",attacker.getEntity().getType());
  }
}



