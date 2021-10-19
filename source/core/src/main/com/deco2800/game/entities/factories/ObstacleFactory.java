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
import com.deco2800.game.components.player.InventoryComponent;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.entities.configs.NPCConfigs;
import com.deco2800.game.entities.configs.BaseEntityConfig;
import com.deco2800.game.components.TouchAttackComponent;

/**
 * Factory to create obstacle entities.
 *
 * <p>Each obstacle entity type should have a creation method that returns a corresponding entity.
 */
public class ObstacleFactory {

	private ObstacleFactory() {
		throw new IllegalStateException("Instantiating static util class");
	}

	public static Entity createRunesGate() {
		Entity RunesGate =
				new Entity(Entity.Type.BUFF)
						.addComponent(new TextureRenderComponent("images/RunesGate.gif"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                   		.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
						.addComponent(new CombatStatsComponent(100, -1));


		RunesGate.getComponent(TextureRenderComponent.class).scaleEntity();

		return RunesGate;
	}

	public static Entity createStoneObstacle() {
		Entity stone =
				new Entity(Entity.Type.DEBUFF)
						.addComponent(new TextureRenderComponent("images/stone.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                    	.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
						.addComponent(new CombatStatsComponent(100, 1));


		stone.getComponent(TextureRenderComponent.class).scaleEntity();

		return stone;
	}

	public static Entity createthunderCloud() {
		Entity thunderCloud =
				new Entity(Entity.Type.DEBUFF)
						.addComponent(new TextureRenderComponent("images/thunderCloud.gif"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                    	.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
						.addComponent(new CombatStatsComponent(100, 1));


		thunderCloud.getComponent(TextureRenderComponent.class).scaleEntity();

		return thunderCloud;
	}

	public static Entity createFire() {
		Entity fire =
				new Entity(Entity.Type.DEBUFF)
						.addComponent(new TextureRenderComponent("images/fire.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                    	.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
						.addComponent(new CombatStatsComponent(100, 1));

		fire.getComponent(TextureRenderComponent.class).scaleEntity();

		return fire;
	}

	public static Entity createFood() {
		Entity food =
				new Entity(Entity.Type.BUFF)
						.addComponent(new TextureRenderComponent("images/food.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                    	.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
						.addComponent(new CombatStatsComponent(100, -1));

		food.getComponent(TextureRenderComponent.class).scaleEntity();
		food.scaleHeight(1f);
		return food;
	}


	public static Entity createCoin() {
		Entity coin =
				new Entity(Entity.Type.COLLECTABLES)
						.addComponent(new TextureRenderComponent("images/coin.gif"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
//						.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
						.addComponent(new CombatStatsComponent(100, 0));

		coin.getComponent(TextureRenderComponent.class).scaleEntity();

		return coin;
	}

	public static Entity createDiamond() {
		Entity diamond =
				new Entity(Entity.Type.COLLECTABLES)
						.addComponent(new TextureRenderComponent("images/diamond.gif"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
//						.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
						.addComponent(new CombatStatsComponent(100, 0));

		diamond.getComponent(TextureRenderComponent.class).scaleEntity();
		
		return diamond;
	}

  	public static Entity createFirstAidKit() {
		Entity FirstAidKit =
				new Entity(Entity.Type.BUFF)
						.addComponent(new TextureRenderComponent("images/FirstAidKit.png"))
						.addComponent(new PhysicsComponent())
						.addComponent(new PhysicsMovementComponent())
						.addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
						.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 0f))
						.addComponent(new CombatStatsComponent(100,-3));

		FirstAidKit.getComponent(TextureRenderComponent.class).scaleEntity();
		FirstAidKit.scaleHeight(1f);

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
