package com.deco2800.game.entities.factories;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.PhysicsUtils;
import com.deco2800.game.physics.components.ColliderComponent;
import com.deco2800.game.physics.components.HitboxComponent;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.components.CombatStatsComponent;

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
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0))
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE));

		tree.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		tree.getComponent(TextureRenderComponent.class).scaleEntity();
		tree.scaleHeight(2.5f);
		PhysicsUtils.setScaledCollider(tree, 0.5f, 0.2f);
		return tree;
	}

	public static Entity createCarObstacle() {
		Entity car =
				new Entity(Entity.Type.OBSTACLE)
						.addComponent(new TextureRenderComponent("images/carObstacle.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		car.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		car.getComponent(TextureRenderComponent.class).scaleEntity();
		// car.scaleHeight(2.5f);
		PhysicsUtils.setScaledCollider(car, 0.5f, 0.2f);
		return car;
	}

	public static Entity createStoneObstacle() { //CombatStatsComponent combat) {
		Entity stone =
				new Entity(Entity.Type.OBSTACLE)
						.addComponent(new TextureRenderComponent("images/stone.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		stone.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		stone.getComponent(TextureRenderComponent.class).scaleEntity();
		//stone.scaleHeight(2.5f);
		PhysicsUtils.setScaledCollider(stone, 0.5f, 0.2f);
		//demote health by smallest amount
		// combat.addHealth(-1);
		return stone;
	}

	public static Entity createSnake() {
		Entity snake =
				new Entity(Entity.Type.OBSTACLE)
						.addComponent(new TextureRenderComponent("images/snake.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		snake.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		snake.getComponent(TextureRenderComponent.class).scaleEntity();
		//snake.scaleHeight(2.5f);
		PhysicsUtils.setScaledCollider(snake, 0.5f, 0.2f);
		//demote health medium
		// CombatStatsComponent.addHealth(-3);
		return snake;
	}

	public static Entity createFire() {
		Entity fire =
				new Entity(Entity.Type.OBSTACLE)
						.addComponent(new TextureRenderComponent("images/fire.png"))
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		fire.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		fire.getComponent(TextureRenderComponent.class).scaleEntity();
		//fire.scaleHeight(2.5f);
		PhysicsUtils.setScaledCollider(fire, 0.5f, 0.2f);
		//demote health the most
		//combat.addHealth(-3);
		return fire;
	}

	public static Entity createFirstAidKit() {
		Entity firstAidKit =
				new Entity()
						.addComponent(new TextureRenderComponent("images/FirstAidKit.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		firstAidKit.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		firstAidKit.getComponent(TextureRenderComponent.class).scaleEntity();
		PhysicsUtils.setScaledCollider(firstAidKit, 0.5f, 0.2f);
		//combat.addHealth(2);
		return firstAidKit;
	}

	public static Entity createFood() {
		Entity food =
				new Entity()
						.addComponent(new TextureRenderComponent("images/food.png"))
						.addComponent(new PhysicsComponent().setBodyType(BodyType.StaticBody))
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.OBSTACLE))
						.addComponent(new CombatStatsComponent(100, 0));

		food.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		food.getComponent(TextureRenderComponent.class).scaleEntity();
		PhysicsUtils.setScaledCollider(food, 0.5f, 0.2f);
		// combat.addHealth(1);
		return food;
	}

	/**
	 * Creates an invisible physics wall.
	 *
	 * @param width  Wall width in world units
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
				.addComponent(new CombatStatsComponent(100, 0));
		PhysicsUtils.setScaledCollider(obstacle, 0.5f, 0.2f);
		obstacle.getComponent(TextureRenderComponent.class).scaleEntity();
		obstacle.scaleHeight(2.5f);
		return obstacle;
	}
	public static Entity createSwordWeapon() { //CombatStatsComponent combat) {
		Entity sword =
				new Entity(Entity.Type.WEAPON)
						.addComponent(new TextureRenderComponent("images/sword.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.WEAPON))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.WEAPON))
						.addComponent(new CombatStatsComponent(100, 0));

		stone.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		stone.getComponent(TextureRenderComponent.class).scaleEntity();
		//stone.scaleHeight(2.5f);
		PhysicsUtils.setScaledCollider(sword, 0.5f, 0.2f);
		//Add 2 atk status, 1 hp heal
		// combat.addHealth(1);
		return sword;
	}
	public static Entity createAxeWeapon() { //CombatStatsComponent combat) {
		Entity axe =
				new Entity(Entity.Type.WEAPON)
						.addComponent(new TextureRenderComponent("images/axe.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.WEAPON))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.WEAPON))
						.addComponent(new CombatStatsComponent(100, 0));

		stone.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		stone.getComponent(TextureRenderComponent.class).scaleEntity();
		//stone.scaleHeight(2.5f);
		PhysicsUtils.setScaledCollider(axe, 0.5f, 0.2f);
		//Add 3 atk status
		return axe;
	}

	public static Entity createBowWeapon() { //CombatStatsComponent combat) {
		Entity bow =
				new Entity(Entity.Type.WEAPON)
						.addComponent(new TextureRenderComponent("images/bow.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.WEAPON))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.WEAPON))
						.addComponent(new CombatStatsComponent(100, 0));

		stone.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		stone.getComponent(TextureRenderComponent.class).scaleEntity();
		PhysicsUtils.setScaledCollider(bow, 0.5f, 0.2f);
		//Add 1 atk status, 2 hp heal
		// combat.addHealth(2);
		return bow;
	}
	public static Entity createGoldCollectable() { //CombatStatsComponent combat) {
		Entity gold =
				new Entity(Entity.Type.COLLECTABLE)
						.addComponent(new TextureRenderComponent("images/gold.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.WEAPON))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.WEAPON))
						.addComponent(new CombatStatsComponent(100, 0));

		stone.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		stone.getComponent(TextureRenderComponent.class).scaleEntity();
		PhysicsUtils.setScaledCollider(gold, 0.5f, 0.2f);
		//Add 1 atk status, 2 hp heal
		// combat.addHealth(2);
		return gold;
	}
	public static Entity createDiamondCollectable() { //CombatStatsComponent combat) {
		Entity diamond =
				new Entity(Entity.Type.COLLECTABLE)
						.addComponent(new TextureRenderComponent("images/diamond.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new ColliderComponent().setLayer(PhysicsLayer.WEAPON))
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.WEAPON))
						.addComponent(new CombatStatsComponent(100, 0));

		stone.getComponent(PhysicsComponent.class).setBodyType(BodyType.StaticBody);
		stone.getComponent(TextureRenderComponent.class).scaleEntity();
		PhysicsUtils.setScaledCollider(diamond, 0.5f, 0.2f);
		//Add 1 atk status, 2 hp heal
		// combat.addHealth(2);
		return diamond;
}
