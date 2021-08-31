package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.components.CombatStatsComponent;

/**
 * Factory to create obstacle entities.
 *
 * <p>Each obstacle entity type should have a creation method that returns a corresponding entity.
 */
public class ObstacleFactory {

  /**
   * Creates a tree entity.
   * @return entity
   */
  public static Entity createTree() {
    Entity tree =
        new Entity()
            .addComponent(new TextureRenderComponent("images/tree.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    tree.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    tree.getComponent(TextureRenderComponent.class).scaleEntity();
    tree.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(tree, 0.5f, 0.2f);
    return tree;
  }

  public static Entity createCarObstacle() {
    Entity car =
        new Entity()
            .addComponent(new TextureRenderComponent("images/carObstacle.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    car.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    car.getComponent(TextureRenderComponent.class).scaleEntity();
    car.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(car, 0.5f, 0.2f);
    return car;
  }

  public static Entity createStoneObstacle() { //CombatStatsComponent combat) {
    Entity stone =
        new Entity()
            .addComponent(new TextureRenderComponent("images/stone.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    stone.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    stone.getComponent(TextureRenderComponent.class).scaleEntity();
    stone.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(stone, 0.5f, 0.2f);
    //demote health by smallest amount
    // combat.addHealth(-1);
    return stone;
  }

  public static Entity createSnake() {
    Entity snake =
        new Entity()
            .addComponent(new TextureRenderComponent("images/snake.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    snake.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    snake.getComponent(TextureRenderComponent.class).scaleEntity();
    snake.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(snake, 0.5f, 0.2f);
    //demote health medium
    // CombatStatsComponent.addHealth(-3);
    return snake;
  }

  public static Entity createFire() {
    Entity fire =
        new Entity()
            .addComponent(new TextureRenderComponent("images/fire.png"))
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    fire.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    fire.getComponent(TextureRenderComponent.class).scaleEntity();
    fire.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(fire, 0.5f, 0.2f);
    //demote health the most
    // combat.addHealth(-3);
    return fire;
  }

  public static Entity createFirstAidKit() {
    Entity FirstAidKit =
            new Entity()
                    .addComponent(new TextureRenderComponent("images/FirstAidKit.png"))
                    .addComponent(new PhysicsComponent())
                    .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    FirstAidKit.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    FirstAidKit.getComponent(TextureRenderComponent.class).scaleEntity();
    FirstAidKit.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(FirstAidKit, 0.5f, 0.2f);
    // combat.addHealth(2);
    return FirstAidKit;
  }

  public static Entity createFood() {
    Entity Food =
            new Entity()
                    .addComponent(new TextureRenderComponent("images/food.png"))
                    .addComponent(new PhysicsComponent())
                    .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

    Food.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
    Food.getComponent(TextureRenderComponent.class).scaleEntity();
    Food.scaleHeight(2.5f);
    PhysicsUtils.setScaledCollider(Food, 0.5f, 0.2f);
    // combat.addHealth(1);
    return Food;
  }

  /**
   * Creates an invisible physics wall.
   * @param width Wall width in world units
   * @param height Wall height in world units
   * @return Wall entity of given width and height
   */
  public static Entity createWall(float width, float height) {
    Entity wall = new Entity()
        .addComponent(new PhysicsComponent().setBodyType(BodyType.StaticBody))
        .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));
    wall.setScale(width, height);
    return wall;
  }

  private ObstacleFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
