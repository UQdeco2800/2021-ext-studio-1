package com.deco2800.game.components;

import com.deco2800.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component used to store information related to combat such as health, attack, etc. Any entities
 * which engage it combat should have an instance of this class registered. This class can be
 * extended for more specific combat needs.
 */
public class CombatStatsComponent extends Component {

  private static final Logger logger = LoggerFactory.getLogger(CombatStatsComponent.class);
  private int health;
  private int armour;
  private int baseAttack;
  private long invincibleStart = 0L;

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
    if (attack >= 0) {
      this.baseAttack = attack;
    } else {
      logger.error("Can not set base attack to a negative attack value");
    }
  }

  public void hit(CombatStatsComponent attacker) {
    try {
      if (ServiceLocator.getTimeSource().getTimeSince(invincibleStart) < 1000L) {
        return;
      }

      if (armour > 0){
        int newArmour = getArmour() - attacker.getBaseAttack();
        setArmour(newArmour);
        invincibleStart = ServiceLocator.getTimeSource().getTime();
      }
      else{
        int newHealth = getHealth() - attacker.getBaseAttack();
        setHealth(newHealth);
        invincibleStart = ServiceLocator.getTimeSource().getTime();
      }

    } catch (NullPointerException e) {
      int newHealth = getHealth() - attacker.getBaseAttack();
      setHealth(newHealth);
    }

  }
}
