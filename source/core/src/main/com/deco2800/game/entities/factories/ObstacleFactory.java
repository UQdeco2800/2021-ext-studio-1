package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.physics.components.PhysicsMovementComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.components.CombatStatsComponent;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.services.ServiceLocator;

/**
 * Factory to create obstacle entities.
 *
 * <p>Each obstacle entity type should have a creation method that returns a corresponding entity.
 */
public class ObstacleFactory {

	private ObstacleFactory() {
		throw new IllegalStateException("Instantiating static util class");
	}

	/**
	 * Creates a tree entity.
	 *
	 * @return entity
	 */
	public static Entity createTree() {
		Entity tree =
				new Entity(Entity.Type.OBSTACLE)
						.addComponent(new TextureRenderComponent("images/tree.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0))
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

		tree.getComponent(TextureRenderComponent.class).scaleEntity();
		tree.scaleHeight(2.5f);
		PhysicsUtils.setScaledCollider(tree, 0.5f, 0.2f);
		return tree;
	}

	public static Entity createRunesGate() {
		Entity RunesGate =
				new Entity(Entity.Type.OBSTACLE)
						.addComponent(new TextureRenderComponent("images/RunesGate.gif"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						// .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						// .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		// RunesGate.getEvents().addListener("contentReachedEndOfMap", this::disposeContent);

		RunesGate.getComponent(TextureRenderComponent.class).scaleEntity();
		// car.scaleHeight(2.5f);
		// PhysicsUtils.setScaledCollider(RunesGate, 0.5f, 0.2f);
		return RunesGate;
	}

	public static Entity createStoneObstacle() { //CombatStatsComponent combat) {
		Entity stone =
				new Entity(Entity.Type.OBSTACLE)
						.addComponent(new TextureRenderComponent("images/stone.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						// .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						// .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		// stone.getEvents().addListener("contentReachedEndOfMap", this::disposeContent);

		stone.getComponent(TextureRenderComponent.class).scaleEntity();
		//stone.scaleHeight(2.5f);
		// PhysicsUtils.setScaledCollider(stone, 0.5f, 0.2f);
		//demote health by smallest amount
		// combat.addHealth(-1);
    
		return stone;
	}

	public static Entity createthunderCloud() {
		Entity thunderCloud =
				new Entity(Entity.Type.OBSTACLE)
						.addComponent(new TextureRenderComponent("images/thunderCloud.gif"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						// .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						// .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		// thunderCloud.getEvents().addListener("contentReachedEndOfMap", this::disposeContent);

		thunderCloud.getComponent(TextureRenderComponent.class).scaleEntity();
		//snake.scaleHeight(2.5f);
		// PhysicsUtils.setScaledCollider(thunderCloud, 0.5f, 0.2f);
		//demote health medium
		// CombatStatsComponent.addHealth(-3);
		return thunderCloud;
	}

	public static Entity createFire() {
		Entity fire =
				new Entity(Entity.Type.OBSTACLE)
						.addComponent(new TextureRenderComponent("images/fire.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						// .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						// .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		// fire.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		fire.getComponent(TextureRenderComponent.class).scaleEntity();
		//fire.scaleHeight(2.5f);
		// PhysicsUtils.setScaledCollider(fire, 0.5f, 0.2f);
		//demote health the most
		//combat.addHealth(-3);
		return fire;
	}

	public static Entity createFood() {
		Entity food =
				new Entity()
						.addComponent(new TextureRenderComponent("images/food.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						// .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						// .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		// food.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		// food.getComponent(TextureRenderComponent.class).scaleEntity();
		// PhysicsUtils.setScaledCollider(food, 0.5f, 0.2f);
		// combat.addHealth(1);

		return food;
	}

	public static Entity createAxe() {
		Entity axe =
				new Entity()
						.addComponent(new TextureRenderComponent("images/axe.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						// .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						// .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		axe.getComponent(TextureRenderComponent.class).scaleEntity();
		// PhysicsUtils.setScaledCollider(axe, 0.5f, 0.2f);
		// combat.addHealth(1);
		return axe;
	}
	public static Entity createSword() {
		Entity sword =
				new Entity()
						.addComponent(new TextureRenderComponent("images/sword.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						// .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						// .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		sword.getComponent(TextureRenderComponent.class).scaleEntity();
		// PhysicsUtils.setScaledCollider(sword, 0.5f, 0.2f);
		// combat.addHealth(1);
		return sword;
	}
	public static Entity createBow() {
		Entity bow =
				new Entity()
						.addComponent(new TextureRenderComponent("images/bow.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						// .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						// .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		bow.getComponent(TextureRenderComponent.class).scaleEntity();
		// PhysicsUtils.setScaledCollider(bow, 0.5f, 0.2f);
		// combat.addHealth(1);
		return bow;
	}
	public static Entity createCoin() {
		Entity coin =
				new Entity()
						.addComponent(new TextureRenderComponent("images/coin.gif"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						// .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						// .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		coin.getComponent(TextureRenderComponent.class).scaleEntity();
		// PhysicsUtils.setScaledCollider(coin, 0.5f, 0.2f);
		// combat.addHealth(1);
		return coin;
	}
	public static Entity createDiamond() {
		Entity diamond =
				new Entity()
						.addComponent(new TextureRenderComponent("images/diamond.gif"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						// .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						// .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		diamond.getComponent(TextureRenderComponent.class).scaleEntity();
		// PhysicsUtils.setScaledCollider(diamond, 0.5f, 0.2f);
		// combat.addHealth(1);
		return diamond;
	}

  public static Entity createFirstAidKit() {
    Entity FirstAidKit =
            new Entity(Entity.Type.BUFF)
                    .addComponent(new TextureRenderComponent("images/FirstAidKit.png"))
                    .addComponent(new PhysicsComponent())
					.addComponent(new PhysicsMovementComponent())
                    // .addComponent(new ColliderComponent().setLayer(PhysicsLayer.BUFF))
                    // .addComponent(new HitboxComponent().setLayer(PhysicsLayer.BUFF))
                    .addComponent(new CombatStatsComponent(100,0));

					
	// FirstAidKit.getEvents().addListener("contentReachedEndOfMap", this::disposeContent);
    FirstAidKit.getComponent(TextureRenderComponent.class).scaleEntity();
    FirstAidKit.scaleHeight(2.5f);
    // PhysicsUtils.setScaledCollider(FirstAidKit, 0.5f, 0.2f);
    // combat.addHealth(2);
    return FirstAidKit;
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

  private static Entity createBaseObstacle(String texturePath) {
    Entity obstacle = new Entity(Entity.Type.OBSTACLE)
            .addComponent(new TextureRenderComponent(texturePath))
            .addComponent(new PhysicsComponent().setBodyType(BodyType.StaticBody))
            .addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
            .addComponent(new CombatStatsComponent(100,0));
    PhysicsUtils.setScaledCollider(obstacle,0.5f,0.2f);
    obstacle.getComponent(TextureRenderComponent.class).scaleEntity();
    obstacle.scaleHeight(2.5f);
    return obstacle;
  }
}
